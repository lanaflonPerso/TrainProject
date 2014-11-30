package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Перемикач
 */
public class Switch extends JPanel implements Location {
    String name; // Ім’я перемикача
    ArrayList<Location> direction; // Імена станція ↔ станція
    Cords position; // Координати розміщення світлофора
    private Image p12;
    private Image p13;
    private Image p23;

    public Switch (String name, Cords position) {
        this.name = name;
        this.position = position;

        direction = new ArrayList<Location>();
        direction.add(Core.s1);
        direction.add(Core.s2);
        // out: стан “1↔2”

        this.p12 = new ImageIcon(getClass().getClassLoader().getResource("resources\\img\\p_12.png")).getImage();
        this.p13 = new ImageIcon(getClass().getClassLoader().getResource("resources\\img\\p_13.png")).getImage();
        this.p23 = new ImageIcon(getClass().getClassLoader().getResource("resources\\img\\p_23.png")).getImage();

        Hashtable<String, Integer> prop = Config.getProperties();
        this.setSize(prop.get("Map.WIDTH"), prop.get("Map.HEIGHT"));
        this.setOpaque(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image state;
        if (direction.get(0) == Core.s1 && direction.get(1) == Core.s2 || direction.get(0) == Core.s2 && direction.get(1) == Core.s1) {
            state = p12;
        } else if (direction.get(0) == Core.s1 && direction.get(1) == Core.s3 || direction.get(0) == Core.s3 && direction.get(1) == Core.s1) {
            state = p13;
        } else {
            state = p23;
        }
        g.drawImage(state, position.x*40-1, position.y*40-1, null);
    }

    @Override
    public String toString() {
        return name;
    }
}

