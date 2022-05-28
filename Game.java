/**
 * This is the Second draft of the Game class. No changes were made between the first and second drafts. Current features include:
 * <ul>
 *     <li>Setting up and creating the JFrame
 *     <li>Allowing the user to quit at any point by hitting 'esc'
 *     <li>Running the scenes of the game in the desired sequence (logo splashscreen, warning splashscreen, main menu, stage 1)
 * </ul>
 * <p>
 * Version date: 05/27/2022
 * @author Alexandra Mitnik, Fatma Jadoon
 * @version 1.2.29
 * <p>
 * External Code Sources:
 * (1) https://stackoverflow.com/questions/10822787/binding-key-combination-to-jframe
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Game {
    public static Graphics2D graphics;
    public static int scene;
    public static JFrame frame;

    /**
     * Game class's constructor. Creates a JFrame, sets its values, and assigns a keystroke (esc) to close it.
     */
    public Game() {
        frame = new JFrame("Iradia");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Dimension dim = new Dimension(new Dimension(1000, 650 + (frame.getHeight() - frame.getContentPane().getHeight())));
        frame.setPreferredSize(dim);
        frame.setMaximumSize(dim);
        frame.setMinimumSize(dim);
        scene = 0;
  
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            frame.dispose();
         }
    };
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);
    }

    /**
     * The run() method. Runs the game and controls the sequence of scenes.
     */
    public void run() {
        Splash splash = new Splash("res/logoSplash.png");
        frame.add(splash);
        frame.pack();
        splash.run();
        frame.remove(splash);
        splash = new Splash("res/warningSplash.png");
        frame.add(splash);
        frame.pack();
        splash.run();
        frame.remove(splash);
        Menu menu = new Menu();
        frame.add(menu);
        frame.pack();
        while (scene == 0) Thread.onSpinWait();
        frame.remove(menu);
        switch(scene){
            case 1:
                Stage1 stage1 = new Stage1();
                frame.add(stage1);
                frame.pack();
                break;
            case 2:
            case 3:
                break;
        }
    }
}
