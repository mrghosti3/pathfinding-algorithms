package org.mrghosti3.PathfindingAlgorithms;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;

public class ControlPanel {

    public static final String NODE_LABEL_FORMAT = "%s %s";
    public static final String OPEN_LABEL_FORMAT   = "Open:   %d";
    public static final String CLOSED_LABEL_FORMAT = "Closed: %d";
    public static final String PATH_LABEL_FORMAT   = "Path:   %d";

    private final JPanel panel;
    private JLabel labels[];
    private JCheckBox checks[];
    private JButton buttons[];

    public ControlPanel(JPanel panel) {
        this.panel = panel;

        // Setup JLabels
        labels = new JLabel[] {
                new JLabel("Node"),
                new JLabel(String.format(OPEN_LABEL_FORMAT, 0)),
                new JLabel(String.format(CLOSED_LABEL_FORMAT, 0)),
                new JLabel(String.format(PATH_LABEL_FORMAT, 0))
        };

        labels[0].setName("label-node");
        labels[0].setFont(Style.bigText);
        labels[0].setForeground(Style.darkText);
        labels[0].setVisible(true);

        labels[1].setName("label-open");
        labels[1].setFont(Style.numbers);
        labels[1].setForeground(Style.darkText);
        labels[1].setVisible(true);

        labels[2].setName("label-closed");
        labels[2].setFont(Style.numbers);
        labels[2].setForeground(Style.darkText);
        labels[2].setVisible(true);

        labels[3].setName("label-path");
        labels[3].setFont(Style.numbers);
        labels[3].setForeground(Style.darkText);
        labels[3].setVisible(true);

        // Setup JCheckBoxes
        checks = new JCheckBox[] {
                new JCheckBox("Diagonal"),
                new JCheckBox("Steps")
        };

        checks[0].setName("check-diagonal");
        checks[0].setSelected(true);
        checks[0].setOpaque(false);
        checks[0].setFocusable(false);
        checks[0].setVisible(true);

        checks[1].setName("check-steps");
        checks[1].setSelected(true);
        checks[1].setOpaque(false);
        checks[1].setFocusable(false);
        checks[1].setVisible(true);

        // Setup JButton
        buttons = new JButton[] {
                new JButton("Start")
        };

        buttons[0].setName("run-btn");
        buttons[0].setFocusable(false);
        buttons[0].addActionListener((ActionListener) panel);
        buttons[0].setMargin(new Insets(0, 0, 0, 0));
        buttons[0].setVisible(true);
    }

    // Getters, Setters
    public JLabel getLabel(String name) {
        for (JLabel label : labels) {
            if (label.getName().equals(name))
                return label;
        }

        return null;
    }

    public boolean getCheck(String name) {
        for (JCheckBox check : checks) {
            if (check.getName().equals(name))
                return check.isSelected();
        }

        return false;
    }

    public JButton getButton(String name) {
        for (JButton button : buttons) {
            if (button.getName().equals(name))
                return button;
        }

        return null;
    }

    // Other methods
    public void position() {
        // Set label bounds
        Dimension size = labels[0].getPreferredSize();
        labels[0].setBounds(20, panel.getHeight() - 39, size.width, size.height);
        labels[1].setBounds(254, panel.getHeight()-92, 80, 20);
        labels[2].setBounds(254, panel.getHeight()-76, 80, 20);
        labels[3].setBounds(254, panel.getHeight()-60, 80, 20);

        // Set check box bounds
        checks[0].setBounds(20, panel.getHeight()-88, 90, 20);
        checks[1].setBounds(20, panel.getHeight()-64, 90, 20);

        // Sets button bounds
        buttons[0].setBounds(116, panel.getHeight()-88, 70, 25);
    }

    public void hoverStyle() {
        for (JLabel label : labels) {
            label.setForeground(Style.lightText);
        }

        for (JCheckBox check : checks) {
            check.setForeground(Style.lightText);
        }
    }

    public void nonHoverStyle() {
        for (JLabel label : labels) {
            label.setForeground(Style.darkText);
        }

        for (JCheckBox check : checks) {
            check.setForeground(Style.darkText);
        }
    }

    public void add() {
        for (JLabel label : labels) {
            panel.add(label);
        }

        for (JCheckBox check : checks) {
            panel.add(check);
        }

        for (JButton button : buttons) {
            panel.add(button);
        }
    }
}
