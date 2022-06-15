/**
 * This is the second draft of the Credits class. No changes have been made since the previous version.
 * <p>Current features include:
 * <ul>
 *   <li>Credits display
 *   <li>Going back to main screen when key is pressed
 * </ul>
 * </p>
 * <p>
 * Version date: 06/14/2022
 *
 * @author @Fatma Jadoon
 * @version 1.5.132
 * </p>
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Credits extends JPanel implements KeyListener {

    /**
     * IsPressed - Stores whether a key has been pressed in the Credits scene
     */
    private boolean isPressed;

    /**
     * The Credits class constructor.
     * Assigns values to instance variables and creates a key listener
     */
    public Credits() {
        isPressed = false;
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    /**
     * The painComponent method.
     * Displays the graphics necessary for the credits page.
     * @param g     Used to draw graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        Game.graphics.drawImage(ImageReader.reader("res/credits.png"), 0, 0, null);
    }

    /**
     * The isPressed method.
     * Gets the status of isPressed.
     * @return if a key has been pressed.
     */
    public boolean isPressed() {
        return isPressed;
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
     * The keyTyped method.
     * Check for typing action on the keyboard (method not used but is necessary
     to implement KeyListener).
     * @param e     An action involving a key.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }


    /**
     * The keyReleased method.
     * Check for key releasing action on the keyboard (method not used but is necessary
     to implement KeyListener).
     * @param e     An action involving a key.
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
