package bubleshooter;

import javax.swing.*;

/**
 * Created by do1ar on 9/15/2017.
 */
public class GameStart {
    public static void main(String[] args) {
        GamePanel panel = new GamePanel();
        JFrame startFrame = new JFrame("BubleShooter2D");
        startFrame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));

        startFrame.setContentPane(panel);
        startFrame.pack();
        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);
        panel.start();
    }
}
