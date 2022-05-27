/**
 * This is the second draft of the Stage1 class. Current features include:
 * <p>
 * Changes made:
 * <ul>
 *    <li>Added dialogue
 *    <li>Added function for users to go through the dialogue using the keyboard
 *    <li>Added 2/3 of the interactive tutorial
 *    <li>Added mouse interaction
 * </ul>
 * <p>
 * Version date: 05/27/2022
 * @author Fatma Jadoon
 * version: 1.2.29
 * </p>
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Stage1 extends JPanel implements KeyListener, MouseListener {
    Image train;
    Image dialogueBack = ImageReader.reader("res/header_base.png");
    Image group1 = ImageReader.reader("res/stage1/group1.png");
    Image group2 = ImageReader.reader("res/stage1/group2.png");
    Image group3 = ImageReader.reader("res/stage1/group3.png");
    Image[] dialogue = ImageReader.storeDir("res/stage1/text/");
    int pos;
    boolean pause = false;
    boolean done1 = false;
    boolean done2 = false;
    boolean done3 = false;

    /**
     * Stage1 class's constructor. Initializes the train image.
     */
    public Stage1() {
        train = ImageReader.reader("res/stage1/train.png");
        pos = 0;
        this.setFocusable(true);
        this.addKeyListener(this);
        addMouseListener(this);
    }

    /**
     * Displays the graphics necessary for stage 1.
     * @param g     Used to draw graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        if (pos == 15)
        {
            Stage2 stage2 = new Stage2 ();
            Game.frame.add(stage2);
            Game.frame.pack();
        }
        Game.graphics.drawImage(train, 0, 0, null);
        Game.graphics.drawImage(group1, 0, 0, null);
        Game.graphics.drawImage(group2, 0, 0, null);
        Game.graphics.drawImage(group3, 0, 0, null);
        if(!pause) {
            Game.graphics.drawImage(dialogueBack, 40, 50, null);
        }
        Game.graphics.drawImage(dialogue[pos], 40, 50, null);
    }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
    }

    /**
     * Allows the user to move through the dialouge if any key is pressed. Also switches to the interactive tutorial at certain points of the dialogue.
     * @param e     An action involving a key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(pos < dialogue.length - 1 && !pause) {
            if(pos == 8 || pos == 11 || pos == 14) {
                pos = dialogue.length - 1;
                pause = true;
            } else {
                pos++;
            }
            repaint();
        }
    }

    /**
     * Check for typing action on the keyboard (method not used but is necessary
     to implement KeyListener)
     * @param e     An action involving a key
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Check for key releasing action on the keyboard (method not used but is necessary
     to implement KeyListener)
     * @param e     An action involving a key
     */
    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Checks if the user has clicked on a group of people during the interactive tutorial, and then directs them to the corresponding dialogue
     * @param e     A click while the Iradia menu is
     *              onscreen
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getX() >= 6 && e.getX() <= 231 && e.getY() >= 325 && e.getY() <= 609 && !done1 && pause) {
            pos = 9;
            pause = false;
            repaint();
            done1 = true;
        } else if(e.getX() >= 515 && e.getX() <= 720 && e.getY() >= 366 && e.getY() <= 650 && !done2 && pause) {
            pos = 12;
            pause = false;
            repaint();
            done2 = true;
        }
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
}
