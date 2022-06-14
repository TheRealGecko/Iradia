/**
This is the first draft of the EndScreen class. 
<p>
Current features include:
* <ul>
     <li> stuff
* </ul>
</p>
* <p>
* Version date: 06/13/2022
* @author Alexandra Mitnik
* @version: idk
* </p>
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class EndScreen extends JPanel implements KeyListener
  {
    Game game;
    private Font consolas;
    private boolean isPressed;
    
    public EndScreen (Game g)
    {
      System.out.println ("the endscreen has been called");
      game = g;
      isPressed = false;
      consolas = new Font ("res/Consolas.ttf", Font.PLAIN, 86);
      addKeyListener(this);
    }

    public void paintComponent (Graphics2D g)
    {
      System.out.println ("this runs");
      Game.graphics = (Graphics2D) g;
      this.requestFocus();
      Game.graphics.drawImage(ImageReader.reader("res/transition/finalScore"), 0, 0, null);
      Game.graphics.setFont (consolas);
      Game.graphics.setColor (new Color(92, 23, 40));
      Game.graphics.drawString("" + game.getPlayerScore(), 800, 200);
    }

    public boolean isPressed ()
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
     * Check for key pressing action on the keyboard. If a key is pressed, then it returns to the main menu.
     * @param e     An action involving a key
     */
    @Override
    public void keyPressed(KeyEvent e) {
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