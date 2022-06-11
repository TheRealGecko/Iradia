/**
 This is the first draft of the credits class. It displays the credits page when the option is chosen by the user. 
 * </ul>
 * <p>
 * Version date: 
 * @author 
 * @version 1.3.63
 * <p>
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Credits extends JPanel implements KeyListener {
    boolean isPressed;

    public Credits() {
        isPressed = false;
        this.setFocusable(true);
        this.addKeyListener(this);
    }
 /**
     * Displays the graphics necessary for the credits page.
     * @param g     Used to draw graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        Game.graphics.drawImage(ImageReader.reader("res/credits.png"), 0, 0, null);
    }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
    }
 /**
     * Check for typing action on the keyboard (method not used but is necessary
     to implement KeyListener)
     * @param e     An action involving a key
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Game.frame.remove(this);
        isPressed = true;
    }
/**
     * Check for key releasing action on the keyboard (method not used but is necessary
     to implement KeyListener)
     * @param e     An action involving a key
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
