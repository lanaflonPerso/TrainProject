package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Light on 20.11.2014.
 */
public class Road implements Location {
    String name; // Ім’я дороги
    // Імена станцій/перемикача на кінцях дороги
    String start;
    String end;
    ArrayList<Cords> way; // Перелік всіх координат дороги
    ArrayList<String> trains; // Імена поїздів, які зараз на цій дорозі

    public Road(String name, String start, String end, List<Cords> way) {
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

    // Рух потяга
    Cords trainMove(String destination) { // Повертає наступну координату руху
        int i = 0;
        for (Cords xy : this.way) {
            i++;
            if (Cords.compare(way, xy)) {
                if (start.equals(destination))
                    return this.way.get(i--);
                else if (end.equals(destination))
                    return this.way.get(i++);
                else return null; // треба опрацювати варіант false
            }
        }
        return null;
    }
}
