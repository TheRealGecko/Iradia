/**
 This is the third draft of the Menu class. No changes were made between the first and third drafts. Current features include:
 * <ul>
 *    <li> Setting up the mouse input
 *    <li> Painting the background for the menu
 *    <li> Painting the options for the menu
 *    <li> Creating an operational play button
 * </ul>
 * <p>
 * Version date: 06/03/2022
 * @author Fatma Jadoon
 * @version: 1.3.63
 * </p>
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Menu extends JPanel implements MouseListener {
    Image menu;

    /**
     * Menu class's constructor. Initializes the menu image and the interface to receive mouse events.
     */
    public Menu() {
            menu = ImageReader.reader("res/menu.png");

            addMouseListener(this);
    }

    /**
      * Displays the graphics necessary for the menu.
      * @param g     Used to draw graphics.
      */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;

        Game.graphics.drawImage(menu, 0, 0, null);
    }

    /**
      * Clicking/releasing the mouse on the play button
      * @param e     A click while the Iradia menu is 
      *              onscreen
      */
    public void mousePressed(MouseEvent e) {
        if(e.getX() >= 193 && e.getX() <= 453 && e.getY() >= 180 && e.getY() <= 261)
            Game.scene = 1;
    }

    /**
      * Clicking mouse (method not used but is necessary 
        to implement MouseListener)
      * @param e     A click while the Iradia menu is 
      *              onscreen
      */
    public void mouseClicked(MouseEvent e) {}

    /**
      * Releasing mouse (method not used but is 
        necessary 
        to implement MouseListener)
      * @param e     A release while the Iradia menu is 
      *              onscreen
      */
    public void mouseReleased(MouseEvent e) {}

    /**
      * Mouse entering the bounds of a component 
        (method 
        not used but is necessary to implement 
        MouseListener)
      * @param e     Entering the bounds of a component
      */
    public void mouseEntered(MouseEvent e) {
       if(e.getX() >= 193 && e.getX() <= 453 && e.getY() >= 180 && e.getY() <= 261)
       {
         
       }
    }

    /**
      * Mouse exiting the bounds of a component 
        (method not used but is necessary to implement 
        MouseListener)
      * @param e     Exiting the bounds of a component
      */
    public void mouseExited(MouseEvent e) {}
}
