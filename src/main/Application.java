package main;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    static Hashtable<String, Integer> prop;

    JMenu startButton;
    JMenu pauseButton;
    JMenu stopButton;

    public Application() {
        // get properties
        prop = Config.getProperties();

        // init all of the elements (trains, stations, barriers etc)
        Core.init();

        frame = new JFrame("TrainProject");
        mainPanel = new JPanel();
        elementsPanel = new ElementsPanel();
        interruptsPanel = new JPanel();
        mapPanel = new MapPanel(); // map

        // original icon
        Image img = new ImageIcon(getClass().getClassLoader().getResource("resources/img/ico.png")).getImage();
        frame.setIconImage(img);

        running = false;
        thread =  new Thread(this, "Main Loop");

        mainPanel.setLayout(null);
        mapPanel.setBounds(prop.get("Map.PADDING_LEFT"), prop.get("Map.PADDING_TOP"), prop.get("Map.WIDTH"), prop.get("Map.HEIGHT"));
        interruptsPanel.setBounds(prop.get("InterruptsPanel.PADDING_LEFT"), prop.get("Map.HEIGHT") + prop.get("InterruptsPanel.PADDING_TOP"), prop.get("Window.WIDTH"), prop.get("InterruptsPanel.HEIGHT"));

        // temporarily settings
        mapPanel.setBackground(Color.ORANGE);
        interruptsPanel.setBackground(Color.BLUE);

        // add all items to map
        mapPanel.setLayout(null);
        for (Train t : Core.getAllT()) {
            mapPanel.add(t);
        }
        for (Light l: Core.getAllL()) {
            mapPanel.add(l);
        }
        for (Barrier b: Core.getAllB()) {
            mapPanel.add(b);
        }
        mapPanel.add(Core.p);

        // main menu
        setMenuBar();

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

        thread.start();
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
                System.out.println("Start");
                startExecution();
                running = true;
            }
            public void menuDeselected(MenuEvent e) {}
            public void menuCanceled(MenuEvent e) {}
        });
        pauseButton = new JMenu("Пауза");
        menuBar.add(pauseButton);
        pauseButton.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                System.out.println("Pause");
                pauseExecution();
            }
            public void menuDeselected(MenuEvent e) {}
            public void menuCanceled(MenuEvent e) {}
        });
        stopButton = new JMenu("Стоп");
        menuBar.add(stopButton);
        stopButton.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                System.out.println("Stop");
                stopExecution();
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
            public void actionPerformed(ActionEvent e) {
                int exitResult = JOptionPane.showConfirmDialog(null, "Ви впевнені, що хочете завершити роботу програми?", "Вихід з програми", JOptionPane.ERROR_MESSAGE);
                if (exitResult == 0) {
                    stopExecution();
//                    System.exit(0);
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

    @Override
    public void run() {
//        running = true;
        while (true) {
            String x3WhyButItWorksWithIt = running + ""; // костиль
            if (this.running) {
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
                    Thread.sleep(prop.get("App.DELAY"));
                } catch (InterruptedException ignored) {
                }

                mapPanel.repaint();
                elementsPanel.repaint();
            }
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
    }

    private void startExecution() {
        running = true;
    }

    private void pauseExecution() {
        running = false;
    }

    private void stopExecution() {
        running = false;
        try {Core.log.close();} catch (Exception ignored) {}
        // init all of the elements (trains, stations, barriers etc)
        Core.init();
        // add all items to map
        mapPanel.setLayout(null);
        for (Train t : Core.getAllT()) {
            mapPanel.add(t);
        }
        for (Light l: Core.getAllL()) {
            mapPanel.add(l);
        }
        for (Barrier b: Core.getAllB()) {
            mapPanel.add(b);
        }
        mapPanel.add(Core.p);
    }
add mapItemsInit
    static public Hashtable<String, Integer> getProperties() {
        return (Hashtable<String, Integer>)prop.clone();
    }

    public static void main(String[] args) {
        Application app = new Application();
    }

}
