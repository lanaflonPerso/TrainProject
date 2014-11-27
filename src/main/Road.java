package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Light on 20.11.2014.
 */
public class Road implements Location {
    String name; // Ім’я дороги
    // Імена станцій/перемикача на кінцях дороги
    Location start;
    Location end;
    ArrayList<Cords> way; // Перелік всіх координат дороги
    ArrayList<String> trains; // Імена поїздів, які зараз на цій дорозі

    public Road(String name, Location start, Location end, List<Cords> way) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.way = new ArrayList<Cords>();
        this.trains = new ArrayList<String>();

        for (Cords w : way) {
            this.way.add(w);
        }
    }
    boolean trainsEmpty() {
        // Перевірка наявності елементів в this.trains
        return (trains.size() == 0) ? true : false;
    }
}
