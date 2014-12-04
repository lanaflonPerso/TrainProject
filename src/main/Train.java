package main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Hashtable;

public class Train extends JPanel {
    int id;
    String name; // Ім’я потяга
    public int destinationIndex; //Індекс маршруту, що відображає пункт призначення
    public Station[] route; // Маршрут -> route[destinationIndex] – поточний пункт
    public Cords position; // Поточні координати розміщення потяга
    public boolean action; // Чи рухається?
    public Location location;
    public String state; // стан для відображення на панелі станів
    static int nextId;
    private Image image;

    public Train(String name, Cords position) {
        this.id = ++nextId;
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
        this.state = "на станції";

        this.image = new ImageIcon(getClass().getClassLoader().getResource("resources/img/t" + id + ".png")).getImage();
        Hashtable<String, Integer> prop = Config.getProperties();
        this.setSize(prop.get("Map.WIDTH"), prop.get("Map.HEIGHT"));
        this.setOpaque(false);
    }

    /**
     * Рух потяга
     */
    public void move() {
        Station[] ss = Core.getAllS();
        Switch p = Core.p;
        if (this.action) { // Якщо потяг рухається
            for (Station s : ss) {
                if (Cords.compare(this.position, s.position)) {
                    // потяг вирушає зі станції
                    s.state = "вільна";
                    break;
                }
            }
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
            this.state = "рухається";

            // Перевірка зі станцією
            for (Station s : ss) {
                if (Cords.compare(this.position, s.position)) {
                    this.action = false;
                    // Потяг на станції
                    this.location = s;
                    this.state = "на станції " + s;
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

    /**
     * Перевірка шлагбаума
     */
    public void checkBarriers() throws IOException { // Потяг перед/після шлагбаума
        Barrier[] bs = Core.getAllB();
        for (Barrier b : bs) {
            if ((Cords.compare(this.position, b.position.get(0))) || (Cords.compare(this.position, b.position.get(1))))
                b.changeStage();
        }
    }

    /**
     * Перевірка світлофора
     */
    public void checkLights() throws IOException {
        Light[] ls = Core.getAllL();
        // Потяг на світлофорі
        for (Light l : ls) {
            // якщо потяг на клітинці зі світлофором (прибув до перемикача, а не відбуває)
            if (Cords.compare(this.position, l.position) && l == getLightByStation(getLastDestination())) {
                if (!l.enable) { // Червоний
                    if (this.action == true) { // потяг тільки прибув
                        this.action = false; // зупиняємо його на 1 ітерацію
                        this.state = "на світлофорі " + l; // стан на панелі станів
                        Core.log.write("Потяг " + this + " стоїть на світлофорі " + l + "\n");
                    } else { // потяг вже постояв на світлофорі 1 ітерацію, відправляємо його
                        switchSystem(); // перемикаємо перемикач
                        // дозволяємо потягу рухатися на наступній ітерації
                        this.action = true;
                    }
                } else { // Зелений
                        switchSystem(); // перемикаємо перемикач тільки якщо потяг прибув до перемикача, а не відбуває
                }
            }
        }
    }

    /**
     * Беремо світлофор зі сторони вказаної станції
     */
    private Light getLightByStation(Station s) {
        if (s == Core.s1)
            return Core.l1;
        if (s == Core.s2)
            return Core.l2;
        return Core.l3;
    }

    /**
     * Перемикається перемикач
     */
    public void switchSystem() throws IOException {
        Switch p = Core.p;
        // оновлює світлофори, зеленими стають тільки світлофори зі сторін звідки і куди їде потяг
        Light[] ls = Core.getAllL();
        for (Light light : ls) {
            if (light == getLightByStation(getLastDestination()) || light == getLightByStation(getNextDestination())) {
                light.enable = true;
                Core.log.write("Світлофор " + light + " вмикає зелений колір" + "\n");
            } else {
                light.enable = false;
                Core.log.write("Світлофор " + light + " вмикає червоний колір" + "\n");
            }
        }
        // оновлюємо стан перемикача
        p.direction.set(0, getLastDestination()); // звідкіля їде потяг
        p.direction.set(1, getNextDestination()); // куди їде потяг
        Core.log.write("Перемикач перемкнувся у стан: " + getLastDestination() + "↔" + getNextDestination() + "\n");
    }

    /**
     * Змінна пункту призначення
     */
    public void setNextDestination() {
        // Потяг досяг станції, тому змінюємо індекс на
        // наступний в маршруті
        if (++this.destinationIndex == this.route.length) this.destinationIndex = 0; // циклічний рух потяга по заданому маршруту
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
        int index = (destinationIndex == this.route.length-1) ? 0 : destinationIndex+1; // циклічний рух
        return this.route[index];
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, position.x*40, position.y*40, null);
    }

    @Override
    public String toString() {
        return name;
    }
}