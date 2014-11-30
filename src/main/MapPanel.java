package main;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created by Light on 22.11.2014.
 */
public class MapPanel extends JPanel {
    Image mapImage;
    Random random = new Random();
    Hashtable<String, Integer> properties;

    public MapPanel() {
        mapImage = new ImageIcon("C:\\Users\\Light\\IdeaProjects\\TrainProject\\resources\\img\\map.png").getImage();
        setOpaque(false);
        properties = Application.getProperties();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(mapImage, 0, 0, null);
        super.paintComponent(g);
    }
}
