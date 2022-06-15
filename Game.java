/**
 * This is the 4th draft of the Game class.
 * <p>
 * Changes include:
 * <ul>
 *     <li>Reading from the highscores file.
 *     <li>Sorting the scores from the highscores file.
 *     <li>Writing the player's name and score in a sorted position in the highscores file.
 * </ul>
 * </p>
 * <p>
 * Current features include:
 * <ul>
 *     <li>Setting up and creating the JFrame.
 *     <li>Allowing the user to quit at any point by hitting 'esc'.
 *     <li>Running the scenes of the game in the desired sequence.
 * </ul>
 * </p>
 * <p>
 * Version date: 06/14/2022
 *
 * @author Alexandra Mitnik, Fatma Jadoon
 * @version 1.5.132
 * </p>
 */

/*
 * External Code Sources:
 * (1) https://stackoverflow.com/questions/10822787/binding-key-combination-to-jframe
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class Game {

    /**
     * graphics - Stores the graphics of the game
     */
    public static Graphics2D graphics;

    /**
     * scene - Stores the current scene state
     */
    public static int scene;

    /**
     * playerName - Stores the name of the player
     */
    private String playerName;

    /**
     * playerScore - Stores the score of the player
     */
    private int playerScore;

    /**
     * frame - Stores the JFrame of the game
     */
    public static JFrame frame;

    /**
     * The Game class constructor.
     * Creates a JFrame and sets its values, initializes the scene number, initializes the player's score, and assigns a keystroke (esc) to close it.
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

        playerScore = 0;
        playerName = "";
    }

    /**
     * The run method.
     * Runs the game and controls the sequence of scenes.
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
        menu();
    }

    /**
     * The menu method.
     * Deals with actions regarding the main menu.
     */
    public void menu() {
        while (scene != 1 && scene != 2) {
            Menu menu = new Menu();
            frame.add(menu);
            frame.pack();
            while (scene == 0) Thread.onSpinWait();
            frame.remove(menu);
            switch (scene) {
                case 1:
                    NameScreen n = new NameScreen(this);
                    frame.add(n);
                    frame.pack();
                    break;
                case 2:
                    Leaderboard l = new Leaderboard(this);
                    frame.add(l);
                    frame.pack();
                    while (!l.isPressed()) Thread.onSpinWait();
                    scene = 0;
                    break;
                case 3:
                    Credits credit = new Credits();
                    frame.add(credit);
                    frame.pack();
                    while (!credit.isPressed()) Thread.onSpinWait();
                    scene = 0;
                    break;
            }
        }
    }

    /**
     * The getPlayerScore method.
     * @return The player's score.
     */
    public int getPlayerScore() {
        return playerScore;
    }

    /**
     * The increasePlayerScore method.
     * Increases player score.
     * @param n     Amount the player score should increase by.
     */
    public void increasePlayerScore(int n) {
        playerScore += n;
    }

    /**
     * The addNameLetter method.
     * Adds a letter to the name
     * @param letter    The letter which the user would like to add to their name.
     */
    public void addNameLetter(char letter) {
        playerName += letter;
    }

    /**
     * The deleteNameLetter method.
     * Deletes the most recently added letter of a user's name.
     */
    public void deleteNameLetter() {
        if (playerName.length() > 0)
            playerName = playerName.substring(0, playerName.length() - 1);
    }

    /**
     * The getPlayerName method.
     * @return The name of the player.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * The recordScore method.
     * Records the player's score in the highscores file.
     */
    public void recordScore() {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> scores = new ArrayList<Integer>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("highscores.txt"));
            String temp = reader.readLine();
            while (temp != null) {
                if (!temp.equals("")) {
                    names.add(temp);
                    temp = reader.readLine();
                    scores.add(Integer.parseInt(temp));
                    temp = reader.readLine();
                }
            }
            reader.close();
        } catch (IOException e) {
        }

        if (!names.contains(playerName)) {
            names.add(playerName);
            scores.add(playerScore);
        } else {
            if (scores.get(names.indexOf(playerName)) < playerScore) {
                scores.set(names.indexOf(playerName), playerScore);
            }
        }


        for (int a = 0; a < scores.size() - 1; a++)
            for (int b = 0; b < scores.size() - a - 1; b++)
                if (scores.get(b) < scores.get(b + 1)) {
                    Collections.swap(scores, b, b + 1);
                    Collections.swap(names, b, b + 1);
                }
        try {
            PrintWriter output = new PrintWriter(new FileWriter("highscores.txt"));
            for (int a = 0; a < names.size(); a++) {
                output.println(names.get(a));
                output.println(scores.get(a));
            }
            output.close();
        } catch (IOException e) {
        }
    }
}
