package main;

import java.util.ArrayList;

/**
 * Перемикач
 */
public class Switch {
    String name; // Ім’я перемикача
    ArrayList<String> direction; // Імена станція ↔ станція
    Cords position; // Координати розміщення світлофора

    public Switch (String name, Cords position) {
        this.name = name;
        this.position = position;

        direction = new ArrayList<String>();
        direction.add("S1");
        direction.add("S2");
        // out: малює перемикач і стан “1↔2”
    }
}

