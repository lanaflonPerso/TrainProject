package main;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Light on 22.11.2014.
 */
public class MapPanel extends JPanel {
    Image image = (new ImageIcon("C:\\Users\\Light\\IdeaProjects\\TrainProject\\resources\\img\\map.png")).getImage();
    {setOpaque(false);}
    public void paintComponent (Graphics g) {
        g.drawImage(image, 0, 0, this);
        super.paintComponent(g);
        System.out.println("test");
    }
}
