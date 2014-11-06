package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Light on 05.11.2014.
 */
public class Application {
    JPanel mainPanel;
    JPanel mapPanel;
    JPanel elementsPanel;
    JPanel interruptsPanel;

    JFrame frame;
    BufferedImage mapImage;


    public Application() {
        frame = new JFrame("TrainProject");
        mainPanel = new JPanel();
        mapPanel = new JPanel();
        elementsPanel = new JPanel();
        interruptsPanel = new JPanel();

        mainPanel.setLayout(null);
        mapPanel.setBounds(0, 0, Map.WIDTH, Map.HEIGHT);
        elementsPanel.setBounds(Map.WIDTH + 20, 0, 260, Map.HEIGHT);
        interruptsPanel.setBounds(0, Map.HEIGHT + 5, 880, 40);

        elementsPanel.setLayout(new GridLayout(5,2));
        // temporarily settings
        mapPanel.setBackground(Color.YELLOW);
        elementsPanel.setBackground(Color.GREEN);
        interruptsPanel.setBackground(Color.BLUE);

        // main menu
        setMenuBar();

        // map
        try {
            mapImage = ImageIO.read(new File("C:\\Users\\Light\\IdeaProjects\\TrainProject\\img\\map.png"));
        } catch (IOException e) {
            System.out.println("Error. Invalid path to map image.");
        }
        JLabel mapLabel = new JLabel(new ImageIcon(mapImage));
        mapPanel.setBackground(Color.ORANGE);
        mapPanel.add(mapLabel);

        // elements
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));

        // add all parts to main Panel
        mainPanel.add(mapPanel);
        mainPanel.add(elementsPanel);
        mainPanel.add(interruptsPanel);

        frame.add(mainPanel);
        frame.setBounds(225,16,900,700);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuItem = getMenu();
        menuBar.add(menuItem);
        menuItem = getTranslation();
        menuBar.add(menuItem);
        menuItem = new JMenu("Пуск");
        menuBar.add(menuItem);
        frame.setJMenuBar(menuBar);
    }

    private JMenu getMenu() {
        JMenu myMenu = new JMenu("Меню");
        JMenuItem myItem = new JMenuItem("Пункт 1                    ");
        myMenu.add(myItem);
        myItem = new JMenuItem("Пункт 2");
//        myItem.setEnabled(false);
        myMenu.add(myItem);
        myItem = new JMenuItem("Пункт 3");
        myMenu.add(myItem);
        myItem = new JMenuItem("Пункт 4");
        myMenu.add(myItem);
        myMenu.addSeparator();
        myItem = new JMenuItem("Вихід");
        myMenu.add(myItem);
        myItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int exitResult = JOptionPane.showConfirmDialog(null, "Ви впевнені, що хочете завершити роботу програми?", "Вихід з програми", JOptionPane.ERROR_MESSAGE);
                if (exitResult == 0) {
                    System.exit(0);
                }
            }
        });
        return myMenu;
    }

    private JMenu getTranslation() {
        JMenu myMenu = new JMenu("Трансляція");
        JMenuItem myItem = new JMenuItem("Пункт 1          ");
        myMenu.add(myItem);
        myItem = new JMenuItem("Пункт 2");
        myMenu.add(myItem);
        myItem = new JMenuItem("Пункт 3");
        myMenu.add(myItem);
        return myMenu;
    }

    public static void main(String[] args) {
        Application app = new Application();
    }
}
