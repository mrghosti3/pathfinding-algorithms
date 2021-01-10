package org.mrghosti3.PathfindingAlgorithms;

import java.awt.Font;
import java.awt.Color;

public interface Style {
    Font bigText = new Font("arial", Font.BOLD, 24);
    Font numbers = new Font("arial", Font.BOLD, 12);

    Color openColor = new Color(1, 170, 7);
    Color closedColor = new Color(253, 90, 90);
    Color pathColor = new Color(32, 233, 255);
    Color btnPanel = new Color(120, 120, 120, 80);
    Color darkText = new Color(48, 48, 48);
    Color lightText = new Color(232, 232, 232);
}
