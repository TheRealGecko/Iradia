/**
 * This is the second draft of the Leaderboard class.
 <p>
 * Changes include:
 * <ul>
 *     <li>Reading from the highscores file.
 *     <li>Storing the top scores + scorers data in arrays
 *     <li>Displaying the top scorers + their scores.
 * </ul>
 * </p>
 * <p>
 * Current features include:
 * <ul>
 *     <li>Drawing the highscores podium.
 *     <li>Reading from the highscores file.
 *     <li>Storing the top scores + scorers data.
 *     <li>Displaying the top scorers + their scores.
 *     <li>Going back to main screen when key is pressed
 * </ul>
 * </p>
 * <p>
 * Version date: 06/08/2022
 * @author Alexandra Mitnik, Bethany Lum, Fatma Jadoon
 * @version ???
 * </p>
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;

public class Leaderboard extends JPanel implements KeyListener {
  
    /**
     * background - Stores the background of the Leaderboard
     */
    Image background;

    /**
     * IsPressed - Stores whether a key has been pressed in the Leaderboard scene
     */
     boolean isPressed;

     boolean scoresGot;

    
    /**
     * scores - The top three scores
     */
    int[] scores;

    /**
     * scorers - The top three scorers
     */
    String[] scorers;

    /**
     * game - The game this screen will be displayed in.
     */
    Game game;
  
    /**
     * The Leaderboard class constructor.
     * Initializes the background image, initializes instance variables, and creates a KeyListener.
     * @param g The game to add the scene to.
     */
    public Leaderboard(Game g) {
        game = g;
        scoresGot = false;
        background = ImageReader.reader("res/leaderboard-bg1.png");
        isPressed = false;
        scores = new int[3];
        scorers = new String[3];
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    /**
     * The addKeyListener method.
     * Creates a keyListener.
     * @param l     Listens for key input.
     */
    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
    }

    /**
     * The painComponent method.
     * Displays the graphics necessary for the Leaderboard.
     * @param g     Used to draw graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        Game.graphics.drawImage(background, 0, 0, null);
        Font largeMono = new Font("Monospaced", Font.PLAIN, 50);
        g.setFont(largeMono);
        g.drawString("Leaderboard", 350, 100);
        Font smallerMono = new Font("Monospaced", Font.PLAIN, 30);
        g.setFont(smallerMono);
      if (scoresGot == false)
        getScores();
      int[] widths = new int[3];
      for (int i = 0; i <= 2; i++) {
         widths[i] = g.getFontMetrics().stringWidth(scorers[i]);
        }
        g.drawString(scorers[2], 733 - widths[0]/2, 400);
        g.drawString(scorers[1], 265 - widths[1]/2, 380);
        g.drawString(scorers[0], 510 - widths[2]/2, 300);
        g.setFont(largeMono);
        g.drawString(scores[2] + "", 733, 470);
        g.drawString(scores[1] + "", 250, 450);
        g.drawString(scores[0] + "", 475, 400);
    }

    /**
     * The getScores method.
     * Retrieves the scores of the players and stores them in parallel ArrayLists.
     */
    private void getScores() {
      try
        {
        BufferedReader reader = new BufferedReader (new FileReader ("highscores.txt"));
          String tempName = reader.readLine();
          int tempScore = Integer.parseInt(reader.readLine());
      while (tempName != null)
        {
           if (scores [0] <= tempScore)
          {
            scores [2] = scores[1];
            scorers[2] = scorers[1];
            scores[1] = scores[0];
            scorers[1] = scorers[0];
            scores [0] = tempScore;
            scorers[0] = tempName;
          }
          else if (scores[1] <= tempScore)
          {
            scores[2] = scores[1];
            scorers[2] = scorers[1];
            scores[1] = tempScore;
            scorers[1] = tempName;
          }
          else if (scores[2] <= tempScore)
          {
            scores[2] = tempScore;
            scorers[2] = tempName;
          }
          tempName = reader.readLine();
            if (tempName != null)
          tempScore = Integer.parseInt(reader.readLine());
        }
          reader.close();
          scoresGot = true;
        }
      catch (IOException e)
        {}
    }

    /**
     * The isPressed method.
     * Gets the status of isPressed.
     * @return if a key has been pressed.
     */
    public boolean isPressed()
  {
    return isPressed;
  }

    /**
     * The keyTyped method.
     * Check for typing action on the keyboard (method not used but is necessary
     to implement KeyListener)
     * @param e     An action involving a key
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * The keyPressed method.
     * Changes isPressed to true if a key is pressed.
     * @param e     An action involving a key.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        Game.frame.remove(this);
        isPressed = true;
    }

    /**
     * The keyReleased method.
     * Check for key releasing action on the keyboard (method not used but is necessary
     to implement KeyListener)
     * @param e     An action involving a key
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }
}