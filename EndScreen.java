/**
This is the first draft of the EndScreen class. 
<p>
Current features include:
* <ul>
     <li>Recording the player's score
     <li>Displaying the player's score
* </ul>
</p>
* <p>
* Version date: 06/14/2022
* @author Alexandra Mitnik
* @version: ???
* </p>
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class EndScreen extends JPanel
  {

    /**
     * game - The game this screen will be displayed in.
     */
    Game game;

    /**
     * consolas - The font to draw Strings with.
     */
    private Font consolas;

    /**
     * The EndScreen class constructor.
     * Assigns values to instance variables.
     * @param g The game to add the scene to.
     */
    public EndScreen (Game g)
    {
      game = g;
      consolas = new Font ("res/Consolas.ttf", Font.PLAIN, 86);
      System.out.println ("new constructor was made");
    }

    /**
     * The painComponent method.
     * Displays the graphics necessary for the end screen.
     * @param g     Used to draw graphics.
     */
    public void paintComponent (Graphics g)
    {
      Game.graphics = (Graphics2D) g;
      System.out.println ("this runs");
      game.recordScore();
      this.requestFocus();
      Game.graphics.drawImage(ImageReader.reader("res/transition/finalScore.png"), 0, 0, null);
      Game.graphics.setFont (consolas);
      Game.graphics.setColor (new Color(92, 23, 40));
      Game.graphics.drawString("Pls is this doing something", 100, 100);
     // Game.graphics.drawString("" + game.getPlayerScore(), 800, 
    }
  }