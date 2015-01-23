package org.mg.catcoder.fractals;

//java source code for a recursive koch snow flakes

import javax.swing.*;
import java.awt.*;

public class RecursiveKochSquareSnowflakes extends JApplet {
    int level = 0;

    public void init() {
//        String levelStr = JOptionPane.showInputDialog("Enter the depth of recursion");
//
//        level = Integer.parseInt(levelStr);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while (level < 5) {
                        Thread.sleep(3000);

                        level++;
                        repaint();
                    }
                } catch (Exception e) {
                    // ignore
                }
            }
        }).start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawSnow(g, level, 80, 280, 280, 280);
        drawSnow(g, level, 280, 280, 280, 80);
        drawSnow(g, level, 280, 80, 80, 80);
        drawSnow(g, level, 80, 80, 80, 280);

    }

    private void drawSnow(Graphics g, int lev, int x1, int y1, int x6, int y6) {
        int deltaX, deltaY, x2, y2, x3, y3, x4, y4, x5, y5;

        if (lev == 0) {
            g.drawLine(x1, y1, x6, y6);
        } else {
            deltaX = x6 - x1;
            deltaY = y6 - y1;

            x2 = x1 + deltaX / 3;
            y2 = y1 + deltaY / 3;

            x3 = x2 - deltaY / 3;
            y3 = y2 + deltaX / 3;

            x4 = x3 + deltaX / 3;
            y4 = y3 + deltaY / 3;

            x5 = x4 + deltaY / 3;
            y5 = y4 - deltaX / 3;

            drawSnow(g, lev - 1, x1, y1, x2, y2);
            drawSnow(g, lev - 1, x2, y2, x3, y3);
            drawSnow(g, lev - 1, x3, y3, x4, y4);
            drawSnow(g, lev - 1, x4, y4, x5, y5);
            drawSnow(g, lev - 1, x5, y5, x6, y6);
        }
    }
}
