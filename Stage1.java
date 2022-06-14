/**
 * This is the third draft of the Stage1 class.
 * <p>
 * Changes made:
 * <ul>
 *    <li>Added dialogue
 *    <li>Completed interactive tutorial
 *    <li>Added transition into stage 2
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
import java.awt.event.*;

public class Stage1 extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
    /**
     * train - Stores the background image of stage 1
     */
    Image train;
    /**
     * dialogueBack - Stores the purple backing image of the dialogue
     */
    Image dialogueBack = ImageReader.reader("res/header_base.png");
    /**
     * group1 - Stores the image of the first group of people
     */
    Image group1 = ImageReader.reader("res/stage1/group1.png");
    /**
     * group2 - Stores the image of the second group of people
     */
    Image group2 = ImageReader.reader("res/stage1/group2.png");
    /**
     * group3 - Stores the image of the third group of people
     */
    Image group3 = ImageReader.reader("res/stage1/group3.png");
    /**
     * dialogue - Stores stage 1's dialogue
     */
    Image[] dialogue = ImageReader.storeDir("res/stage1/text/");
    /**
     * pos - Stores the position of where the dialogue is at
     */
    int pos;
    /**
     * pause - Stores whether the dialogue is paused
     */
    boolean pause = false;
    /**
     * done1 - Stores whether group 1 is done being investigated
     */
    boolean done1 = false;
    /**
     * done2 - Stores whether group 2 is done being investigated
     */
    boolean done2 = false;
    /**
     * done3 - Stores whether group 3 is done being investigated
     */
    boolean done3 = false;
    /**
     * trainGroup - Stores the current group being investigated
     */
    int trainGroup;
    /**
     * game - Stores the game
     */
    Game game;

    /**
     * The Stage1 class constructor.
     * Does the following:
     * - Initializes the instance variables
     * - Creates a KeyListener
     * - Creates a MouseListener
     * @param g     Refers to the game
     */
    public Stage1(Game g) {
        game = g;
        train = ImageReader.reader("res/stage1/train.png");
        pos = 0;
        trainGroup = 0;
        this.setFocusable(true);
        this.addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * The paintComponent method.
     * Displays the graphics necessary for stage 1.
     * @param g     Used to draw graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        Game.graphics.drawImage(train, 0, 0, null);
        Game.graphics.drawImage(group1, 0, 0, null);
        Game.graphics.drawImage(group2, 0, 0, null);
        Game.graphics.drawImage(group3, 0, 0, null);
        if (trainGroup != 0) {
            String img = "res/stage1/group" + trainGroup + "_highlighted.png";
            Game.graphics.drawImage(ImageReader.reader(img), 0, 0, null);
        }
        if (pos < dialogue.length) {
            if (!pause) {
                Game.graphics.drawImage(dialogueBack, 40, 50, null);
            }
            Game.graphics.drawImage(dialogue[pos], 40, 50, null);
        }

        if (pos == 25)
            Game.graphics.drawImage(ImageReader.reader("res/transition/end1.png"), 0, 0, null);
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
     * Allows the user to move through the dialogue if any key is pressed.
     * Also switches to the interactive tutorial at certain points of the dialogue.
     * @param e     An action involving a key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (pos == 25) {
            Game.frame.remove(this);
            Game.frame.add(new Stage2(game));
            Game.frame.pack();
        } else if (!pause) {
            if (pos == 8 || pos == 11 || pos == 14 || pos == 18) {
                if (done1 && done2 && done3) {
                    pos = 19;
                } else {
                    pos = dialogue.length - 1;
                    pause = true;
                }
            } else if (pos == 23) {
                pos = 25;
            } else {
                pos++;
            }
            repaint();
        }
    }

    /**
     * The keyTyped method.
     * Check for typing action on the keyboard (method not used but is necessary to implement KeyListener).
     * @param e     An action involving a key.
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * The keyReleased method.
     * Check for key releasing action on the keyboard (method not used but is necessary to implement KeyListener).
     * @param e     An action involving a key.
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * The mousePressed method.
     * Checks if the user has clicked on a group of people during the interactive tutorial,
     * and then directs them to the corresponding dialogue.
     * @param e     A click while the Iradia menu is
     *              onscreen.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() >= 6 && e.getX() <= 231 && e.getY() >= 325 && e.getY() <= 609 && !done1 && pause) {
            pos = 9;
            pause = false;
            repaint();
            done1 = true;
        } else if (e.getX() >= 515 && e.getX() <= 720 && e.getY() >= 366 && e.getY() <= 650 && !done2 && pause) {
            pos = 12;
            pause = false;
            repaint();
            done2 = true;
        } else if (e.getX() >= 756 && e.getX() <= 1000 && e.getY() >= 325 && e.getY() <= 611 && !done3 && pause) {
            pos = 15;
            pause = false;
            repaint();
            done3 = true;
        }
    }

    /**
     * The mouseClicked method.
     * Clicking mouse (method not used but is necessary to implement MouseListener).
     * @param e     A click while the Iradia menu is
     *              onscreen.
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * The mouseReleased method.
     * Releasing mouse (method not used but is necessary to implement MouseListener).
     * @param e     A release while the Iradia menu is
     *              onscreen.
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * The mouseEntered method.
     * Mouse entering the bounds of a component (method not
     * used but is necessary to implement MouseListener).
     * @param e     Entering the bounds of a component.
     */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /**
     * The mouseExited method.
     * Mouse exiting the bounds of a component (method
     * not used but is necessary to implement MouseListener).
     * @param e     Exiting the bounds of a component.
     */
    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * The mouseMoved method.
     * Moving mouse changes the colours of the buttons it hovers over.
     * @param e     Mouse movement while Iradia is onscreen.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getX() >= 6 && e.getX() <= 231 && e.getY() >= 325 && e.getY() <= 609 && !done1 && pause) {
            trainGroup = 1;
        } else if (e.getX() >= 515 && e.getX() <= 720 && e.getY() >= 366 && e.getY() <= 650 && !done2 && pause) {
            trainGroup = 2;
        } else if (e.getX() >= 756 && e.getX() <= 1000 && e.getY() >= 325 && e.getY() <= 611 && !done3 && pause) {
            trainGroup = 3;
        } else {
            trainGroup = 0;
        }
        repaint();
    }

    /**
     * The mouseDragged method.
     * Dragging mouse (method not used but is necessary
     * to implement MouseMotionListener).
     * @param e     A drag while the Iradia menu is
     *              onscreen.
     */
    @Override
    public void mouseDragged(MouseEvent e) {}
}
