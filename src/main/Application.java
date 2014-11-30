package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by Light on 05.11.2014.
 */
public class Application implements Runnable {
    JPanel mainPanel;
    MapPanel mapPanel;
    JPanel elementsPanel;
    JPanel interruptsPanel;
    JFrame frame;

    Thread thread;
    boolean running;

    BufferedImage train1Image;
    JMenu startButton;

    static Hashtable<String, Integer> prop;

    public Application() {
        // get properties
        prop = Config.getProperties();

        // init all of the elements (trains, stations, barriers etc)
        Core.init();

        frame = new JFrame("TrainProject");
        mainPanel = new JPanel();
        elementsPanel = new JPanel();
        interruptsPanel = new JPanel();

        // map
        mapPanel = new MapPanel();

        running = false;
        thread =  new Thread(this, "Main Loop");

        mainPanel.setLayout(null);
        mapPanel.setBounds(prop.get("Map.PADDING_LEFT"), prop.get("Map.PADDING_TOP"), prop.get("Map.WIDTH"), prop.get("Map.HEIGHT"));
        elementsPanel.setBounds(prop.get("Map.WIDTH") + prop.get("ElementsPanel.PADDING_LEFT"), prop.get("ElementsPanel.PADDING_TOP"), prop.get("ElementsPanel.WIDTH"), prop.get("Map.HEIGHT"));
        interruptsPanel.setBounds(prop.get("InterruptsPanel.PADDING_LEFT"), prop.get("Map.HEIGHT") + prop.get("InterruptsPanel.PADDING_TOP"), prop.get("Window.WIDTH"), prop.get("InterruptsPanel.HEIGHT"));

        elementsPanel.setLayout(new GridLayout(5, 2));
        // temporarily settings
        mapPanel.setBackground(Color.ORANGE);
        elementsPanel.setBackground(Color.GREEN);
        interruptsPanel.setBackground(Color.BLUE);

        // add all trains to map
        mapPanel.setLayout(null);
        for (Train t : Core.getAllT()) {
            mapPanel.add(t);
        }

        // main menu
        setMenuBar();

        // Application.class.getClassLoader().getResource("test_train.png").getPath();
        try {
            train1Image = ImageIO.read(new File("resources\\img\\test_train.png"));
        } catch (IOException e) {
            System.out.println("Problem with train image file.");
        } catch (NullPointerException e) {
            System.out.println("Error. Invalid path to train image.");
        }

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
        frame.setBounds(0, 0, prop.get("Window.WIDTH"), prop.get("Window.HEIGHT"));
        frame.setLocationRelativeTo(null);
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
        startButton = new JMenu("Пуск");
        menuBar.add(startButton);
        startButton.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                System.out.println("menuSelected");
                thread.start();
            }
            public void menuDeselected(MenuEvent e) {}
            public void menuCanceled(MenuEvent e) {}
        });
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
                // remove it
                // remove it
                // remove it
                running = false;
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

    @Override
    public void run() {
        running = true;
        while (running) {

            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
            for (Train t : Core.getAllT()) {
                t.move();
                t.checkLights();
                t.checkBarriers();
            }

            for  (Station s : Core.getAllS()) { // для кожної станції
                s.releaseTrainsFromStorage();
                s.checkNewTrains();
            }

            consolePrint();

            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }

            mapPanel.repaint();
        }
    }

    private void consolePrint() {
        for (Train t : Core.getAllT()) {
            System.out.println(t + ": " + t.position + "; " + t.location);
            System.out.println("Остання станція: " + t.getLastDestination() + "; наступна: " + t.getNextDestination() + "; індекс: " + t.destinationIndex);
            System.out.println();
        }
        for (Station s : Core.getAllS()) { // для кожної станції
            System.out.print(s + " storage: ");
            for(Train t : s.storage) {
                System.out.print(t + "; ");
            }
            System.out.println();
        }
        System.out.println();
        for (Light l : Core.getAllL()) {
            System.out.print(l + " " + (l.enable ? "+" : "-") + "    ");
            // out: змінює стан світлофора l на “червоний”
            // out: світлофора l на мапі червоним кольором
        }
        System.out.println();
    }

    static public Hashtable<String, Integer> getProperties() {
        return (Hashtable<String, Integer>)prop.clone();
    }

    public static void main(String[] args) {
        Application app = new Application();
    }

}
