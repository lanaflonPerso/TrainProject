package main;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

/**
 * Panel of element states at right side of app
 */
public class ElementsPanel extends JPanel {
    static Hashtable<String, Integer> prop;
    JPanel[] panels;
    JLabel[][] labels;

    public ElementsPanel() {
        prop = Config.getProperties();
        this.setBounds(prop.get("Map.WIDTH") + prop.get("ElementsPanel.PADDING_LEFT"), prop.get("ElementsPanel.PADDING_TOP"), prop.get("ElementsPanel.WIDTH"), prop.get("Map.HEIGHT"));
        this.setLayout(new GridLayout(6, 2));
        this.setOpaque(false);

        panels  = new JPanel[12];
        labels  = new JLabel[12][3];

        // elements
        for (int i = 0; i < panels.length; i++) {
            panels[i] = new JPanel();
            panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.Y_AXIS));
            panels[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            for (int j = 0; j < labels[i].length; j++) {
                labels[i][j] = new JLabel();
                labels[i][j].setAlignmentX(Component.CENTER_ALIGNMENT);
                if (j == 0) { // title of element
                    labels[i][j].setPreferredSize(new Dimension(100,60));
                    labels[i][j].setFont(new Font("Times New Roman", Font.BOLD, 22));
                    labels[i][j].setForeground(new Color(0x1C4387));
                } else if (j == 1) { // state
                    labels[i][j].setFont(new Font("Times New Roman", Font.BOLD, 14));
                } else if (j == 2) { // cords (for trains)
                    labels[i][j].setPreferredSize(new Dimension(100,60));
                    labels[i][j].setFont(new Font("Times New Roman", Font.BOLD, 20));
                }
                panels[i].add(labels[i][j]);
            }
            add(panels[i]);
        }

        // *** TITLES
        // Trains
        labels[0][0].setText(Core.t1.toString());
        labels[2][0].setText(Core.t2.toString());
        labels[4][0].setText(Core.t3.toString());
        // Stations
        labels[1][0].setText(Core.s1.toString());
        labels[3][0].setText(Core.s2.toString());
        labels[5][0].setText(Core.s3.toString());
        // Lights
        labels[6][0].setText(Core.l1.toString());
        labels[8][0].setText(Core.l2.toString());
        labels[10][0].setText(Core.l3.toString());
        // Barriers
        labels[7][0].setText(Core.b1.toString());
        labels[9][0].setText(Core.b2.toString());
        // Switch
        labels[11][0].setText(Core.p.toString());

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Trains
        labels[0][1].setText(Core.t1.state);
        labels[2][1].setText(Core.t2.state);
        labels[4][1].setText(Core.t3.state);
        labels[0][2].setText(Core.t1.position.x + "; " + Core.t1.position.y);
        labels[2][2].setText(Core.t2.position.x + "; " + Core.t2.position.y);
        labels[4][2].setText(Core.t3.position.x + "; " + Core.t3.position.y);

        // Stations
        labels[1][1].setText(Core.s1.state);
        labels[3][1].setText(Core.s2.state);
        labels[5][1].setText(Core.s3.state);

        // Lights
        String color;
        if (Core.l1.enable) {
            color = "зелений";
            labels[6][1].setForeground(new Color(0x009900));
        } else {
            color = "червоний";
            labels[6][1].setForeground(new Color(0xff0000));
        }
        labels[6][1].setText(color);

        if (Core.l2.enable) {
            color = "зелений";
            labels[8][1].setForeground(new Color(0x009900));
        } else {
            color = "червоний";
            labels[8][1].setForeground(new Color(0xff0000));
        }
        labels[8][1].setText(color);

        if (Core.l3.enable) {
            color = "зелений";
            labels[10][1].setForeground(new Color(0x009900));
        } else {
            color = "червоний";
            labels[10][1].setForeground(new Color(0xff0000));
        }
        labels[10][1].setText(color);

        // Barriers
        if (Core.b1.enable) {
            color = "дозволено";
            labels[7][1].setForeground(new Color(0x009900));
        } else {
            color = "заборонено";
            labels[7][1].setForeground(new Color(0xff0000));
        }
        labels[7][1].setText(color);

        if (Core.b2.enable) {
            color = "дозволено";
            labels[9][1].setForeground(new Color(0x009900));
        } else {
            color = "заборонено";
            labels[9][1].setForeground(new Color(0xff0000));
        }
        labels[9][1].setText(color);

        // Switch
        labels[11][1].setText(Core.p.direction.get(0) + " ↔ " + Core.p.direction.get(1));
    }
}
