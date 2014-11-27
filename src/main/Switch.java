package main;

import java.util.ArrayList;

/**
 * Перемикач
 */
public class Switch {
    String name; // Ім’я перемикача
    ArrayList<Location> direction; // Імена станція ↔ станція
    Cords position; // Координати розміщення світлофора

    public Switch (String name, Cords position) {
        this.name = name;
        this.position = position;

        direction = new ArrayList<Location>();
        direction.add(Core.s1);
        direction.add(Core.s2);
        // out: малює перемикач і стан “1↔2”
    }
}

