package Utilities;

import javax.swing.*;
import java.awt.*;

public class Utilities {
    public static JPanel[][] addJPanelsToGrid(int rows, int columns, JPanel panel, int vGap, int hGap) {
        panel.setLayout(new GridLayout(rows, columns, vGap, hGap));
        JPanel[][] grid = new JPanel[rows][columns];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = new JPanel();
                //grid[i].add(new JTextArea(String.valueOf(i)));
                grid[row][col].setLayout(new BorderLayout());
                grid[row][col].setOpaque(false);
                panel.add(grid[row][col]);
            }
        }
        return grid;
    }
}
