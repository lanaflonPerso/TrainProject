package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Light on 20.11.2014.
 */
public class Road implements Location {
    String name; // Ім’я дороги
    // станції/перемикачі на кінцях дороги
    Location start;
    Location end;
    ArrayList<Cords> way; // Перелік всіх координат дороги

    public Road(String name, Location start, Location end, List<Cords> way) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.way = new ArrayList<Cords>();

        for (Cords w : way) {
            this.way.add(w);
        }
    }
}
