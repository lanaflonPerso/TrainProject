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

/**
 * Created by Light on 05.11.2014.
 */
public class Application {
    JPanel mainPanel;
    JPanel mapPanel;
    JPanel elementsPanel;
    JPanel interruptsPanel;
    JFrame frame;

    BufferedImage train1Image;
    JLabel train1Label;
    JMenu startButton;


    public Application() {
        frame = new JFrame("TrainProject");
        mainPanel = new JPanel();
        elementsPanel = new JPanel();
        interruptsPanel = new JPanel();

        // map
        mapPanel = new JPanel() {
            Image image = (new ImageIcon("C:\\Users\\Light\\IdeaProjects\\TrainProject\\img\\map.png")).getImage();
            {setOpaque(false);}
            public void paintComponent (Graphics g) {
                g.drawImage(image, 0, 0, this);
                super.paintComponent(g);
            }
        };

        mainPanel.setLayout(null);
        mapPanel.setBounds(0, 0, Map.WIDTH, Map.HEIGHT);
        elementsPanel.setBounds(Map.WIDTH + 20, 0, 260, Map.HEIGHT);
        interruptsPanel.setBounds(0, Map.HEIGHT + 5, 880, 40);

        elementsPanel.setLayout(new GridLayout(5,2));
        // temporarily settings
        mapPanel.setBackground(Color.ORANGE);
        elementsPanel.setBackground(Color.GREEN);
        interruptsPanel.setBackground(Color.BLUE);

        // main menu
        setMenuBar();

        try {
            train1Image = ImageIO.read(new File("C:\\Users\\Light\\IdeaProjects\\TrainProject\\img\\test_train.png"));
        } catch (IOException e) {
            System.out.println("Problem with train image file.");
        } catch (NullPointerException e) {
            System.out.println("Error. Invalid path to train image.");
        }
        train1Label = new JLabel(new ImageIcon(train1Image));
        mapPanel.add(train1Label);

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
        train1Label.setBounds(0, 0, 23, 21);
        run();
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
                run();
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

    private void run() {
        for (int i = 0; i < 20; i++) {
            train1Label.setBounds(i*10, 0, 23, 21);
            try {
                Thread.sleep(100);
                System.out.println("test");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Application app = new Application();
    }

}
