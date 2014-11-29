package main;

import java.util.ArrayList;

public class Train {
    String name; // Ім’я потяга
    public int destinationIndex; //Індекс маршруту, що відображає пункт призначення
    public Station[] route; // Маршрут -> route[destinationIndex] – поточний пункт
    public Cords position; // Поточні координати розміщення потяга
    public boolean action; // Чи рухається?
    public Location location;

    public Train(String name, Cords position) {
        this.name = name;
        this.destinationIndex = 0;
        this.position = position;
        // ініціювання пустих this.route
        this.route = new Station[5];
        int i = 0;
        for (Station r : route) {
            this.route[i++] = r;
        }
        this.action = false;
        // out: малює потяг
        // out: відображення стану “ набирає пасажирів ”
        System.out.println(this + " набирає пасажирів");
    }

    // Рух потяга
    public void move() {
        Station[] ss = Core.getAllS();
        Switch p = Core.p;
        if (this.action) { // Якщо потяг рухається
            // Пересування по дорозі
            Road road = (Road)location;
            int currentRoadIndex = road.way.indexOf(position);
            // the next coordinate depends on direction of train
            int nextRoadIndex;
            if (!(road.end instanceof Switch)) {
                if (getNextDestination() == road.end)
                    nextRoadIndex = currentRoadIndex+1;
                else
                    nextRoadIndex = currentRoadIndex-1;
            } else {
                if (getNextDestination() == road.start)
                    nextRoadIndex = currentRoadIndex-1;
                else
                    nextRoadIndex = currentRoadIndex+1;
            }
            position = road.way.get(nextRoadIndex);
            // out: змінює стан потяга на “рух (координати)”
            // repaint

            // Перевірка зі станцією
            for (Station s : ss) {
                if (Cords.compare(this.position, s.position)) {
                    this.action = false;
                    // Потяг на станції
                    this.location = s;
                    break;
                }
            }
            // Якщо потяг на перемикачі
            if (Cords.compare(this.position, p.position)) {
                // Ставить потяг на наступну дорогу
                if (getNextDestination() == Core.s1) { // s1
                    this.location = Core.r1p;
                } else if (getNextDestination() == Core.s2) { // s2
                    this.location = Core.r2p;
                } else { // s3
                    this.location = Core.r3p;
                }
            }
        }
    }

    // Перевірка шлагбаума
    public void checkBarriers() { // Потяг перед/після шлагбаума
        Barrier[] bs = Core.getAllB();
        for (Barrier b : bs) {
            if ((Cords.compare(this.position, b.position.get(0))) || (Cords.compare(this.position, b.position.get(1))))
                b.changeStage();
        }
    }

    // Перевірка світлофору
    public void checkLights() {
        Light[] ls = Core.getAllL();
        // Потяг на світлофорі
        for (Light l : ls) {
            // Якщо потяг на клітинці зі світлофором
            if (Cords.compare(this.position, l.position)) {
                if (!l.enable) { // Червоний
                    this.action = false;
                    //out: змінює стан потяга на “стоїть на світлофорі”
                } else { // Зелений
                    if (this.action == false) { // Якщо стояв на світлофорі
                        this.action = true; // Потяг має рухатись
                        // out: змінює стан потяга на “рух (координати)”
                    }
                    // Включаються всі світлофори
                    for (Light ll : ls) {
                        l.enable = true;
                        // out: змінює стан світлофора l на “зелений”
                        // out: світлофора l на мапі зеленим кольором
                    }
                    // Перемикається перемикач
                    this.switchSystem();
                }
            }
        }
    }

    // Перемикається перемикач
    public void switchSystem() {
        Switch p   = Core.p;
        // Ініціює масив для визначення світлофора, який включить червоний
        ArrayList<Light> redLights = new ArrayList<Light>();
        redLights.add(Core.l1);
        redLights.add(Core.l2);
        redLights.add(Core.l3);
        Road road = (Road)location;
        // Визначає звідкіля їде потяг
        Light lFirst;
        if (road == Core.r1p) {
            lFirst = Core.l1;
        } else if(road == Core.r2p) {
            lFirst = Core.l2;
        } else { // Rs[“R3p”]
            lFirst = Core.l3;
        }
        p.direction.set(1, getLastDestination());
        redLights.remove(lFirst);
        // Визначає куди їде потяг
        p.direction.set(1, getNextDestination());
        // Виключає зі списку визначення світлофору з червоним кольором
        if (getNextDestination() == Core.s1) {
            redLights.remove(Core.l1);
        } else if (getNextDestination() == Core.s2) {
            redLights.remove(Core.l2);
        } else { // s3
            redLights.remove(Core.l3);
        }
        // out: перемикач змінює стан на “i↔j” та перемальовується
        // Один світлофор стає червоним
        for (Light l : redLights) {
            l.enable = false;
            // out: змінює стан світлофора l на “червоний”
            // out: світлофора l на мапі червоним кольором
        }
    }

    /**
     * Змінна пункту призначення
     */
    public void setNextDestination() { // Потяг досяг станції, тому змінюємо індекс на
        // наступний в маршруті
        this.destinationIndex++;
        if (this.destinationIndex == 4) this.destinationIndex = 0;
    }

    /**
     * Повертає останню станцію, де був потяг
     */
    public Station getLastDestination() {
        return this.route[destinationIndex];
    }

    /**
     * Повертає наступну станцію, куди прямує потяг
     */
    public Station getNextDestination() {
        int index = (destinationIndex == 3) ? 0 : destinationIndex+1;
        return this.route[index];
    }

    @Override
    public String toString() {
        return name;
    }
}