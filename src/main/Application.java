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
    JPanel trainLogPanel;
    JPanel commonLogPanel;

    JFrame frame;
    BufferedImage mapImage;


    public Application() {
        frame = new JFrame("TrainProject");
        mainPanel = new JPanel();
        mapPanel = new JPanel();
        mapPanel = new JPanel();
        elementsPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        elementsPanel.setLayout(new GridLayout(3,3));
        elementsPanel.setBackground(Color.YELLOW);
        GridBagConstraints c = new GridBagConstraints();

        // main menu
        setMenuBar();

        // map
        try {
            mapImage = ImageIO.read(new File("C:\\Users\\Light\\IdeaProjects\\TrainProject\\img\\common.png"));
        } catch (IOException e) {
            System.out.println("Error. Invalid path to map image.");
        }
        JLabel mapLabel = new JLabel(new ImageIcon(mapImage));

        mapPanel.add(mapLabel);
        c.gridx = 0;
        c.gridy = 0;
//        c.insets = new Insets(0, 0, 0, 200);
        mainPanel.add(mapPanel, c);

        // emements
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        elementsPanel.add(new JLabel("Test"));
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.NORTH;
//        mainPanel.add(elementsPanel, c);

        frame.add(mainPanel);
        frame.setBounds(130,16,1100,700);
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
        JMenuItem myItem = new JMenuItem("Пункт 1");
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
        JMenuItem myItem = new JMenuItem("Пункт 1");
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
