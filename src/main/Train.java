package main;

import java.util.ArrayList;

public class Train {
    String name; // Ім’я потяга
    int destinationIndex; //Індекс маршруту, що відображає пункт призначення
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
        this.action = true;
        // out: малює потяг
        // out: відображення стану “ набирає пасажирів ”
    }

    // Змінна пункту призначення
    public void setNextDestination() { // Потяг досяг станції, тому змінюємо індекс на
        // наступний в маршруті
        this.destinationIndex++;
        if (this.destinationIndex == 4) this.destinationIndex = 0;
    }

    // Рух потяга
    public void move() { // Рух потягу
        Station[] ss = Core.getAllS();
        Switch p = Core.p;
        if (this.action) { // Якщо потяг рухається
            // Пересування по дорозі
            Road road = (Road)location;
            int currentRoadIndex = road.way.indexOf(position);
//            System.out.println(road.way);
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
                    // out: змінює стан потяга на “випускає пасажирів”
                    break;
                }
            }
            // Якщо потяг на перемикачі
            if (Cords.compare(this.position, p.position)) {
                // Ставить потяг на наступну дорогу
                if (getLastDestination() == ss[0]) { // s1
                    // this.road = "R1p";
//                    NEED ROAD OBJECT
                } else if (getLastDestination() == ss[0]) { // s2
                    // this.road = "R2p";
//                    NEED ROAD OBJECT
                } else { // s3
                    // this.road = "R3p";
//                    NEED ROAD OBJECT
                }
            } else if (this.location instanceof Station) { // Якщо потяг стоїть на станції
                // out: змінює стан потяга на "набирає пасажирів"
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
        Light[] ls = Core.getAllL();
        Road[] rs  = Core.getAllR();
        Switch p   = Core.p;
        // Ініціює масив для визначення світлофора, який включить червоний
        ArrayList<String> redLights = new ArrayList<String>();
        redLights.add("L1");
        redLights.add("L2");
        redLights.add("L3");
        Road road = (Road)location;
        // Визначає звідкіля їде потяг
        if (road == rs[2]) { // Rs[“R1p”]
            p.direction.set(0, Core.s1);
            redLights.remove("L1");
        } else if (road == rs[3]) { // Rs[“R2p”]
            p.direction.set(0, Core.s2);
            redLights.remove("L2");
        } else { // Rs[“R3p”]
            p.direction.set(0, Core.s3);
            redLights.remove("L3");
        }
        // Визначає куди їде потяг
        p.direction.set(1, getLastDestination());
        // Виключає зі списку визначення світлофору з червоним кольором
        if (p.direction.get(1) == Core.r1p) { // Rs[“R1p”]
            redLights.remove("L1");
        } else if (p.direction.get(1) == Core.r2p) { // Rs[“R2p”]
            redLights.remove("L2");
        } else { // Rs[“R3p”]
            redLights.remove("L3");
        }
        // out: перемикач змінює стан на “i↔j” та перемальовується
        // Один світлофор стає червоним
        for (String redlights : redLights) {
            for (Light l : ls) {
                if (redlights == l.name) {
                    l.enable = false;
                    // out: змінює стан світлофора l на “червоний”
                    // out: світлофора l на мапі червоним кольором
                }
            }
        }
    }

    public Station getLastDestination() {
        return this.route[destinationIndex];
    }

    public Station getNextDestination() {
        int index = (destinationIndex == 4) ? 0 : destinationIndex+1;
        return this.route[index];
    }
}