package main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Barrier extends JPanel {
    private int id;
    String name; // Ім’я шлагбаума
    boolean enable; // Вкл./Викл.
    ArrayList<Cords> position; // Координати розміщення датчиків шлагбаума
    static int nextId;
    private Image image;

    public Barrier(String name, ArrayList<Cords> position){
        this.id = ++nextId;
        this.name = name;
        this.enable = true;
        this.position = position;

        Hashtable<String, Integer> prop = Config.getProperties();
        this.setSize(prop.get("Map.WIDTH"), prop.get("Map.HEIGHT"));
        this.setOpaque(false);
    }

    // Змінна стану шлагбаума
    public void changeStage() throws IOException {
        this.enable = !this.enable;
        System.out.println(this + " змінив стан на " + enable);
        Core.log.write("Шлагбаум " + this + " змінив стан на: " + (enable ? "рух автомобілям дозволено\n" : "рух автомобілям заборонено\n"));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        String state = (this.enable) ? "green" : "red";
        image = new ImageIcon(getClass().getClassLoader().getResource("resources/img/b" + id + "_" + state + ".png")).getImage();
        int posX = 0, posY = 0;
        if (this.id == 1) {
            posX = 23;
            posY = 320;
        } else if (this.id == 2) {
            posX = 363;
            posY = 18;
        }
        g.drawImage(image, posX, posY, null);
    }

    @Override
    public String toString() {
        return name;
    }
}
