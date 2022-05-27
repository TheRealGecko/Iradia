/**
 This is the first draft of the Stage1 class. Current features include:
 * <ul>
 *    <li>Setting up the painting code
 *    <li>Painting the background for the first screen
 * </ul>
 * <p>
 * Version date: 05/20/2022
 * @author Fatma Jadoon
 * version: 1.0.0
 * </p>
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;
import java.io.IOException;

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
    }

    public void training() {
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
        if (pos == 16)
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
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(pos < dialogue.length - 1 && !pause) {
            if(pos == 8 || pos == 11 || pos == 14) {
                pos = dialogue.length - 1;
                pause = true;
                training();
            } else {
                pos++;
            }
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

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

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
