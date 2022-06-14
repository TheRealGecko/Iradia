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
    int traingroup;
    Game game;

    /**
     * Stage1 class's constructor. Initializes the train image.
     */
    public Stage1(Game g) {
        game = g;
        train = ImageReader.reader("res/stage1/train.png");
        pos = 0;
        traingroup = 0;
        this.setFocusable(true);
        this.addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
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
        if (traingroup != 0) {
            String img = "res/stage1/group" + traingroup + "_highlighted.png";
    /*Image group1h = ImageReader.reader ("res/stage1/group1_highlighted.png");
    Image group2h = ImageReader.reader ("res/stage1/group2_highlighted.png");
    Image group3h = ImageReader.reader ("res/stage1/group3_highlighted.png");*/
            Game.graphics.drawImage(ImageReader.reader(img), 0, 0, null);
        }
        if (pos < dialogue.length) {
            if (!pause) {
                Game.graphics.drawImage(dialogueBack, -8, -10, null);
            }
            Game.graphics.drawImage(dialogue[pos], 40, 50, null);
        }

        if (pos == 25)
            Game.graphics.drawImage(ImageReader.reader("res/transition/end1.png"), 0, 0, null);
    }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
    }

    /**
     * Allows the user to move through the dialogue if any key is pressed. Also switches to the interactive tutorial at certain points of the dialogue.
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
     * Check for typing action on the keyboard (method not used but is necessary
     to implement KeyListener)
     * @param e     An action involving a key
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Check for key releasing action on the keyboard (method not used but is necessary
     to implement KeyListener)
     * @param e     An action involving a key
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Checks if the user has clicked on a group of people during the interactive tutorial, and then directs them to the corresponding dialogue
     * @param e     A click while the Iradia menu is
     *              onscreen
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
     * Clicking mouse (method not used but is necessary
     to implement MouseListener)
     * @param e     A click while the Iradia menu is
     *              onscreen
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Releasing mouse (method not used but is necessary
     to implement MouseListener)
     * @param e     A release while the Iradia menu is
     *              onscreen
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Mouse entering the bounds of a component (method
     not used but is necessary to implement
     MouseListener)
     * @param e     Entering the bounds of a component
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Mouse exiting the bounds of a component (method
     not used but is necessary to implement
     MouseListener)
     * @param e     Exiting the bounds of a component
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Moving mouse changes the colours of the buttons it hovers over
     * @param e     Mouse movement while Iradia is onscreen
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getX() >= 6 && e.getX() <= 231 && e.getY() >= 325 && e.getY() <= 609 && !done1 && pause) {
            traingroup = 1;
        } else if (e.getX() >= 515 && e.getX() <= 720 && e.getY() >= 366 && e.getY() <= 650 && !done2 && pause) {
            traingroup = 2;
        } else if (e.getX() >= 756 && e.getX() <= 1000 && e.getY() >= 325 && e.getY() <= 611 && !done3 && pause) {
            traingroup = 3;
        } else {
            traingroup = 0;
        }
        repaint();
    }

    /**
     * Dragging mosue (method not used but is necessary
     to implement MouseMotionListener)
     * @param e     A drag while the Iradia menu is
     *              onscreen
     */
    @Override
    public void mouseDragged(MouseEvent e) {
    }
}
