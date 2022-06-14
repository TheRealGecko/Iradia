/**
 * This is the third draft of the Game class. No changes were made between the first and third drafts. Current features include:
 * <ul>
 *     <li>Setting up and creating the JFrame
 *     <li>Allowing the user to quit at any point by hitting 'esc'
 *     <li>Running the scenes of the game in the desired sequence (logo splashscreen, warning splashscreen, main menu, stage 1-2-3, end)
 * </ul>
 * <p>
 * Version date: 06/10/2022
 *
 * @author Alexandra Mitnik, Fatma Jadoon
 * @version 1.3.63
 * <p>
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
     * Does the following:
     * - Creates a JFrame and sets its values
     * - Initializes the scene number
     * - Initializes the player's score
     * - Assigns a keystroke (esc) to close it.
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
     * @param n     Indicates how much the player score should increase by.
     */
    public void increasePlayerScore(int n) {
        playerScore += n;
    }

    /**
     * The addNameLetter method.
     * Allows the player to set their name.
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

  
    public void recordScore() {
      write(sort(read()));
    }

    private ArrayList read()
  {
        System.out.println ("reading commenced");
    ArrayList<String>fileData = new ArrayList<String>(); 
          try {
          BufferedReader reader = new BufferedReader (new FileReader("highscores.txt"));
            String temp = reader.readLine();
            while (temp != null)
              {
                fileData.add(temp);
                temp = reader.readLine();
              }
          reader.close();
        } 
        catch (IOException e) {
        }
      return fileData;
  }

    private ArrayList sort(ArrayList arr)
  {
    System.out.println ("sorting commenced");
    ArrayList<String>arr2 = arr;
    arr.add(playerName);
    arr.add(playerScore);
   /* System.out.println (arr2.size());
        for (int a = 1; a < arr2.size() - 2; a+= 2)
        {
         System.out.println ("The a for comparison is" + arr2.get(a));
          for (int b = a + 2; b < arr.size(); b += 2)
            {
              System.out.println ("We are looking at " + arr2.get(b));
              // if (Integer.valueOf(arr2.get(b)) > Integer.valueOf(arr2.get(a)))
               //  Collections.swap (arr2, a, b);
            }
        }*/

    return arr2;
  }

    private void write(ArrayList arr)
  {
    System.out.println ("writing commenced");
    try
      {
         PrintWriter output = new PrintWriter(new FileWriter("highscores.txt"));
          for (int a = 0; a < arr.size(); a++)
            {
              output.println(arr.get(a));
              System.out.println (arr.get(a));
            }
          output.close();
      }
    catch (IOException e)
      {}
  }
}
