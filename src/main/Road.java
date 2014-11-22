package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Light on 20.11.2014.
 */
public class Road {
    String name; // Ім’я дороги
    ArrayList<String> ends; // Імена станцій/перемикача на кінцях дороги
    ArrayList<Cords> way; // Перелік всіх координат дороги
    ArrayList<String> trains; // Імена поїздів, які зараз на цій дорозі

    public Road(String name, List<String> ends, List<Cords> way) {

        this.ends = new ArrayList<String>();
        this.way = new ArrayList<Cords>();
        this.trains = new ArrayList<String>();

        this.name = name;

        for (String e : ends) {
            this.ends.add(e);
        }
        for (Cords w : way) {
            this.way.add(w);
        }
    }

	/* Якщо треба прописати функції AddWay, AddEnd, AddTrain, якщо в джаві не передбачені Add-и та RemoveTrain, якщо не передбачено trains.Remove */

    boolean trainsEmpty() {
        // Перевірка наявності елементів в this.trains
        return (trains.size() == 0);
    }

    // Рух потяга
    Cords trainMove(String destination) { // Повертає наступну координату руху
        int i = 0;
        for (Cords xy : this.way) {
            i++;
            /*if (ComparePositions(way, xy)) {
                if (this.ends.get(0).equals(destination))
                    return this.way.get(i--);
                else if (this.ends.get(1).equals(destination))
                    return this.way.get(i++);
                else return null; // треба опрацювати варіант false
            }*/
        }
        return null;
    }
}
