package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Світлофори
 */
public class Light extends JPanel {
    int id;
    String name; // Ім’я світлофора
    public boolean enable; // Вкл./Викл.
    public Cords position; // Координати розміщення світлофора
    private static int nextId;
    private Image imageGreen;
    private Image imageRed;

    public Light (String name, Cords position) {
        this.id = ++nextId;
        this.name = name;
        this.enable = true;
        this.position = position;
        try {
            this.imageGreen = ImageIO.read(new File("resources\\img\\l_green.png"));
            this.imageRed = ImageIO.read(new File("resources\\img\\l_red.png"));
        } catch (IOException ignored) {
        }
        Hashtable<String, Integer> prop = Config.getProperties();
        this.setSize(prop.get("Map.WIDTH"), prop.get("Map.HEIGHT"));
        this.setOpaque(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image img = (enable) ? imageGreen : imageRed;
        int x, y;
        if (id == 1) {
            x = 350;
            y = 409;
        } else if (id == 2) {
            x = 288;
            y = 469;
        } else {
            x = 368;
            y = 469;
        }
        g.drawImage(img, x, y, null);
    }

    @Override
    public String toString() {
        return name;
    }
}
