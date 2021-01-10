package org.mrghosti3.PathfindingAlgorithms;

import javax.swing.JFrame;
import java.awt.Dimension;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Pathfinding Algorithm Visualisation");
        window.setContentPane(new VisualPanel());
        window.getContentPane().setPreferredSize(new Dimension(700, 600));
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
