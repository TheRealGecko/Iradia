/**
 * This is the third draft of the Menu class. No changes were made between the first and third drafts. Current features include:
 * <ul>
 *    <li> Setting up the mouse input
 *    <li> Painting the background for the menu
 *    <li> Painting the options for the menu
 *    <li> Creating an operational play button
 * </ul>
 * <p>
 * Version date: 06/03/2022
 *
 * @author Fatma Jadoon
 * @version: 1.3.63
 * </p>
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Menu extends JPanel implements MouseListener {
    /**
     * menu - Stores the image/background of the menu screen
     */
    Image menu;

    /**
     * The Menu class constructor.
     * Does the following:
     * - Initializes the menu image
     * - Creates a mouseListener.
     */
    public Menu() {
        menu = ImageReader.reader("res/menu.png");

        addMouseListener(this);
    }

    /**
     * The paintComponent method.
     * Displays the graphics necessary for the menu.
     * @param g     Used to draw graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;

        Game.graphics.drawImage(menu, 0, 0, null);
    }

    /**
     * The mousePressed method.
     * Clicking/releasing the mouse on the play button.
     * @param e     A click while the Iradia menu is
     *              onscreen.
     */
    public void mousePressed(MouseEvent e) {
        if (e.getX() >= 193 && e.getX() <= 453 && e.getY() >= 180 && e.getY() <= 261)
            Game.scene = 1;
        else if (e.getX() >= 193 && e.getX() <= 453 && e.getY() >= 285 && e.getY() <= 365)
            Game.scene = 2;
        else if (e.getX() >= 193 && e.getX() <= 453 && e.getY() >= 388 && e.getY() <= 469)
            Game.scene = 3;
    }

    /**
     * The mouseClicked method
     * Clicking mouse (method not used but is necessary
     to implement MouseListener).
     * @param e     A click while the Iradia menu is
     *              onscreen.
     */
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * The mouseReleased method.
     * Releasing mouse (method not used but is
     necessary to implement MouseListener).
     * @param e     A release while the Iradia menu is
     *              onscreen.
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * The mouseEntered method.
     * Mouse entering the bounds of a component
     (method not used but is necessary to implement
     MouseListener).
     * @param e     Entering the bounds of a component.
     */
    public void mouseEntered(MouseEvent e) {
        if (e.getX() >= 193 && e.getX() <= 453 && e.getY() >= 180 && e.getY() <= 261) {

        }
    }

    /**
     * The mouseExited method.
     * Mouse exiting the bounds of a component
     (method not used but is necessary to implement MouseListener).
     * @param e     Exiting the bounds of a component.
     */
    public void mouseExited(MouseEvent e) {
    }
}
