import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Stage3 extends JPanel implements KeyListener, MouseListener
  {

    Game game;
    Image background;
    
    public Stage3 (Game g)
    {
        game = g;
        background = ImageReader.reader ("res/Name_Screen_Background_1.png");
        this.setFocusable(true); // Allows the class to receive user input
        this.addKeyListener(this);
    }

     /**
      * Displays the graphics necessary for stage 3.
      * @param g     Used to draw graphics.
      */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        
    }
    
    /**
     * KeyListener being added
     * @param l    KeyListener
     */
    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
    }

      /**
    * When any key is pressed, switches through dialogue in the tutorial + opens prompt for user input on cases
    * @param e     Pressing a key
    */
    @Override
    public void keyPressed(KeyEvent e) {

    }
    
      /**
    * Key being typed (method not used but is necessary to implement 
    KeyListener)
    * @param e     Typing a key
    */
    @Override
    public void keyTyped(KeyEvent e) {

    }

      /**
    * Key being released
    (method not used but is necessary to implement 
    KeyListener)
    * @param e     Releasing a key
    */
    @Override
    public void keyReleased(KeyEvent e) {

    }

     /**
     * Desc.
     * @param e     A click while stage 2 is
     *              onscreen
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Clicking mouse (method not used but is necessary
     to implement MouseListener)
     * @param e     A click while the Iradia menu is
     *              onscreen
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * Releasing mouse (method not used but is necessary
     to implement MouseListener)
     * @param e     A release while the Iradia menu is
     *              onscreen
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * Mouse entering the bounds of a component (method
     not used but is necessary to implement
     MouseListener)
     * @param e     Entering the bounds of a component
     */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /**
     * Mouse exiting the bounds of a component (method
     not used but is necessary to implement
     MouseListener)
     * @param e     Exiting the bounds of a component
     */
    @Override
    public void mouseExited(MouseEvent e) {}

    Image[] test;
    private class Sprite
      {
        private Sprite()
        {
        }
      }
  }