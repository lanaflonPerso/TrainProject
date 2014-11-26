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
    Image trainImage;
    Random random = new Random();
    Hashtable<String, Integer> properties;

    public MapPanel() {
        mapImage = new ImageIcon("C:\\Users\\Light\\IdeaProjects\\TrainProject\\resources\\img\\map.png").getImage();
        trainImage = new ImageIcon("C:\\Users\\Light\\IdeaProjects\\TrainProject\\resources\\img\\test_train.png").getImage();
        setOpaque(false);
        properties = Application.getProperties();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(mapImage, 0, 0, null);
        g.drawImage(trainImage, random.nextInt(properties.get("Map.WIDTH")), random.nextInt(properties.get("Map.HEIGHT")), null);
        super.paintComponent(g);
        System.out.println("Map.paintComponent()");
    }
}
