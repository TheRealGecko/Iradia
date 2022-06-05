import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Stage3 extends JPanel implements KeyListener
  {

    public Stage3 ()
    {
        this.setFocusable(true); // Allows the class to receive user input
        this.addKeyListener(this);
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
  }