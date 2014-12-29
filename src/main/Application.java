package main;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
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

    public Application() {
        // get properties
        prop = Config.getProperties();

        // init all of the elements (trains, stations, barriers etc)
        Core.init();

        frame = new JFrame("TrainProject");
        // original icon
        Image img = new ImageIcon(getClass().getClassLoader().getResource("resources/img/ico.png")).getImage();
        frame.setIconImage(img);
        // init all of the panels
        panelsInit();


        // main menu
        setMenuBar();

        frame.setBounds(0, 0, prop.get("Window.WIDTH"), prop.get("Window.HEIGHT"));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        running = false;
        thread =  new Thread(this, "Main Loop");
        thread.start();
    }

    private void panelsInit() {
        if (thread != null) // it's not a first init
            frame.remove(mainPanel);
        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        elementsPanel = new ElementsPanel();
        interruptsPanel = new JPanel();
        mapPanel = new MapPanel(); // map

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

        mapPanel.setBounds(prop.get("Map.PADDING_LEFT"), prop.get("Map.PADDING_TOP"), prop.get("Map.WIDTH"), prop.get("Map.HEIGHT"));

        // Interrupts panel
        interruptsPanel.setLayout(new BoxLayout(interruptsPanel, BoxLayout.Y_AXIS));
        interruptsPanel.setBounds(prop.get("InterruptsPanel.PADDING_LEFT"), prop.get("Map.HEIGHT") + prop.get("InterruptsPanel.PADDING_TOP"), prop.get("Window.WIDTH"), prop.get("InterruptsPanel.HEIGHT"));
        interruptsPanel.setBackground(new Color(0xD8F7BA));
        JLabel interruptsText = new JLabel("F1 - перекшода для T1,           F2 - перекшода для T2,           F3 - перекшода для T3");
        interruptsText.setPreferredSize(new Dimension(prop.get("Window.WIDTH"), 50));
        interruptsText.setFont(new Font("Times New Roman", Font.BOLD, 16));
        interruptsText.setAlignmentX(Component.CENTER_ALIGNMENT);
        interruptsPanel.add(interruptsText);

        // add all parts to main Panel
        mainPanel.add(mapPanel);
        mainPanel.add(elementsPanel);
        mainPanel.add(interruptsPanel);

        mainPanel.setFocusable(true);
        mainPanel.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                System.out.println(key);
                try {
                    if (key == 112) { // T1
                        Core.t1.interrupt1 = 1;
                        Core.t1.state = "перешкода";
                        System.out.println("Перешкода перед потягом T1");
                        Core.log.write("Перешкода перед потягом T1\n");
                    } else if (key == 113) { // T2
                        Core.t2.interrupt1 = 1;
                        Core.t2.state = "перешкода";
                        System.out.println("Перешкода перед потягом T2");
                        Core.log.write("Перешкода перед потягом T2\n");
                    } else if (key == 114) { // T3
                        Core.t3.interrupt1 = 1;
                        Core.t3.state = "перешкода";
                        System.out.println("Перешкода перед потягом T3");
                        Core.log.write("Перешкода перед потягом T3\n");
                    }
                } catch (IOException ignored) {
                }
            }
        });

        frame.add(mainPanel);
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
                startExecution();
                try {
                    Core.log.write(" *** Виконання програми розпочато \n");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void menuDeselected(MenuEvent e) {}
            public void menuCanceled(MenuEvent e) {}
        });
        pauseButton = new JMenu("Стоп");
        menuBar.add(pauseButton);
        pauseButton.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                try {
                    Core.log.write(" *** Виконання програми зупинено \n");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                running = false;
            }
            public void menuDeselected(MenuEvent e) {}
            public void menuCanceled(MenuEvent e) {}
        });

        frame.setJMenuBar(menuBar);
    }

    private JMenu getMenu() {
        JMenu myMenu = new JMenu("Меню");
        JMenuItem myItem = new JMenuItem("Лог системи                    ");
        myMenu.add(myItem);
        myItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prepareLog();
                try {
                    Desktop.getDesktop().open(new File("log.txt"));
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        });
        myItem = new JMenuItem("Маршрути потягів");
        myMenu.add(myItem);
        myItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File("src/resources/routes.txt"));
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        });
//        myItem.setEnabled(false);
        myMenu.add(myItem);
        myMenu.addSeparator();
        myItem = new JMenuItem("Вихід");
        myMenu.add(myItem);
        myItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int exitResult = JOptionPane.showConfirmDialog(null, "Ви впевнені, що хочете завершити роботу програми?", "Вихід з програми", JOptionPane.YES_NO_OPTION);
                if (exitResult == 0) {
                    prepareLog();
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

    @Override
    public void run() {
//        running = true;
        while (true) {
            String x3WhyButItWorksWithIt = running + ""; // костиль
            if (this.running) {
                try {
//                    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    Core.log.write("-----------------------------------------------------\n" + new Date() + ":\n\n");
                    for (Train t : Core.getAllT()) {
                        // переривання
                        if (t.isInterrupted()) {
                            if (t.interrupt1 != 0) { // переривання 1
                                switch (t.interrupt1) {
                                    case 1:
                                        System.out.println("Потяг " + t + " подає звуковий сигнал");
                                        Core.log.write("Потяг " + t + " подає звуковий сигнал\n");
                                        t.interrupt1++;
                                        break;
                                    case 2:
                                        System.out.println("Потяг " + t + " зупинився перед перешкодою");
                                        Core.log.write("Потяг " + t + " зупинився перед перешкодою\n");
                                        t.interrupt1++;
                                        break;
                                    case 3:
                                        System.out.println("Машиніст потяга " + t + " усунув перешкоду");
                                        Core.log.write("Машиніст потяга " + t + " усунув перешкоду\n");
                                        t.interrupt1 = 0;
                                        break;
                                }

                            } else if (t.interrupt2 != 0) { // переривання 2

                            }
                        } else {
                            t.move();
                            t.checkLights();
                            t.checkBarriers();
                        }
                    }

                    for (Station s : Core.getAllS()) { // для кожної станції
                        s.releaseTrainsFromStorage();
                        s.checkNewTrains();
                    }

                    consolePrint();
                    Thread.sleep(prop.get("App.DELAY"));
                } catch (Exception ignored) {
                }
                mapPanel.repaint();
                elementsPanel.repaint();
            }
        }
    }

    private void consolePrint() throws IOException {
        for (Train t : Core.getAllT()) {
            System.out.println(t + ": " + t.position + "; " + t.location);
            System.out.println("Остання станція: " + t.getLastDestination() + "; наступна: " + t.getNextDestination() + "; індекс: " + t.destinationIndex);
            System.out.println();

            Core.log.write(t + ": " + t.position + "; розміщення: " + t.location + "\n");
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
        if (Core.log == null) {
            Core.logInit();
        }
        running = true;
    }

    private void prepareLog() {
        try {Core.log.close();} catch (Exception ignored) {}
        Core.log = null;
    }

    static public Hashtable<String, Integer> getProperties() {
        return (Hashtable<String, Integer>)prop.clone();
    }

    public static void main(String[] args) {
        Application app = new Application();
    }

}
