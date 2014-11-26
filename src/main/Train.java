package main;

import java.util.ArrayList;
import java.util.Collection;

public class Train {
    String name; // Ім’я потяга
    int destinationIndex; //Індекс маршруту, що відображає пункт призначення
    ArrayList<String> route; // Маршрут -> route[destinationIndex] – поточний пункт
    Cords position; // Поточні координати розміщення потяга
    boolean action; // Чи рухається?
    String road; // На якій дорозі/станції знаходиться

    public Train(String name, Cords position, ArrayList<String> route, String road) {
        this.name = name;
        this.destinationIndex = 0;
        this.position = position;
        // ініціювання пустих this.route
        for (String r : route) {
            this.route.add(r);
        }
        this.action = false;
        this.road = road;
        // out: малює потяг
        // out: відображення стану “ набирає пасажирів ”
    }

    // Змінна пункту призначення
    void NextDestination() { // Потяг досяг станції, тому змінюємо індекс на
        // наступний в маршруті
        this.destinationIndex++;
        if (this.destinationIndex == 4) this.destinationIndex = 0;
    }

    // Рух потяга
    void Move(ArrayList<Road> rs, ArrayList<Station> ss, Switch p) { // Рух потягу
        if (this.action) { // Якщо потяг рухається
            // Пересування по дорозі
            for (Road r : rs) {
                if (this.road == r.name) { // Якщо потяг на цій дорозі
                    this.position = r.trainMove(this.route.get(this.destinationIndex));
                    // out: перемальовує потяг на інше місце
                    // out: змінює стан потяга на “рух (координати)”
                }
            }
            // Перевірка зі станцією
            for (Station s : ss) {
                if (ComparePositions(this.position, s.position)) {
                    this.action = false;
                    // Знімає з дороги потяг
                    for (Road r : rs)
                        for (String t : r.trains)
                            if (t == this.name) // якщо потяг, прибувши
                            {        // на станцію був на цій дорозі
                                r.trains.remove(t); // з ArrayList<String> trains
                                break;    // прибираємо даний потяг
                            }
                    // out: змінює стан потяга на “випускає пасажирів”
                    this.road = s.name; // Потяг на станції
                    break;
                }
                // Якщо потяг на перемикачі
                if (ComparePositions(this.position, p.position)) {
                    // Знімає з дороги потяг
                    for (Road r : rs)
                        for (String t : r.trains)
                            if (t == this.name) // якщо потяг, прибувши
                            {        // на перемикач був на цій дорозі
                                r.trains.remove(t); // з ArrayList<String> trains
                                break;    // прибираємо даний потяг
                            }
                    // Ставить на дорогу потяг
                    if (this.route.get(destinationIndex) == "S1") {
                        this.road = "R1p";
                        rs.get(2).trains.add(this.name); // Rs[“R1p”]
                    } else if (this.route.get(destinationIndex) == "S2") {
                        this.road = "R2p";
                        rs.get(3).trains.add(this.name); // Rs[“R2p”]
                    } else {
                        this.road = "R3p";
                        rs.get(4).trains.add(this.name); // Rs[“R3p”]
                    }
                } else if (this.road.charAt(0) == 'S') { // Якщо потяг стоїть на станції
                    // out: змінює стан потяга на “набирає пасажирів”
                } else {
                    //out: змінює стан потяга на “стоїть(координата)”
                }
            }
        }
    }

    // Перевірка шлагбаума
    void CheckBarriers(ArrayList<Barrier> bs) { // Потяг перед/після шлагбаума
        for (Barrier b : bs) {
            if ((ComparePositions(this.position, b.position.get(0))) || (ComparePositions(this.position, b.position.get(1))))
                b.changeStage();
        }
    }

    // Перевірка світлофору
    void CheckLights(ArrayList<Light> ls, Switch p, ArrayList<Road> rs) {
        // Потяг на світлофорі
        for (Light l : ls) {
            // Якщо потяг на клітинці зі світлофором
            if (ComparePositions(this.position, l.position)) {
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
                    this.switchSystem(ls, p, rs);
                }
            }
        }
    }

    // Перемикається перемикач
    void switchSystem(ArrayList<Light> ls, Switch p, ArrayList<Road> rs) {
        // Ініціює масив для визначення світлофора, який включить червоний
        ArrayList<String> redLights = new ArrayList<String>() {
        };
        redLights.add("L2");
        redLights.add("L3");
        // Визначає звідкіля їде потяг
        if (this.road == rs.get(2).name) { // Rs[“R1p”]
            p.direction.set(0, "S1");
            redLights.remove("L1");
        } else if (this.road == rs.get(3).name) { // Rs[“R2p”]
            p.direction.set(0, "S2");
            redLights.remove("L2");
        } else { // Rs[“R3p”]
            p.direction.set(0, "S3");
            redLights.remove("L3");
        }
        // Визначає куди їде потяг
        p.direction.set(1, this.route.get(this.destinationIndex));
        // Виключає зі списку визначення світлофору з червоним кольором
        if (p.direction.get(1) == rs.get(2).name) { // Rs[“R1p”]
            redLights.remove("L1");
        } else if (p.direction.get(1) == rs.get(3).name) { // Rs[“R2p”]
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
}