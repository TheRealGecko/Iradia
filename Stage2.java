/**
This is the first draft of the Stage2 class. This class was added as of version 1.2.0. Current features include:
* <ul>
*    <li>Setting up the painting code
     <li>Painting the background (same for all screens in this stage)
     <li>Adding the yes and no buttons
     <li>Adding the dialogue/instrucitons box
* </ul>
* <p>
* Version date: 05/27/2022
* @author Alexandra Mitnik
* @version: 1.2.29
* </p>
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Stage2 extends JPanel implements KeyListener, MouseListener
  {

    Image table;
    Image buttons;
    Image[] dialogue;
    Image dialogueBack;
    int pos;
    boolean pause;

    int caseNum;

    Image[] cases;
    boolean[] isToxic;
    boolean[] caseComplete;

    /**
     * Stage2 class's constructor. Initializes the table and button images.
     */
    public Stage2() {
        table = ImageReader.reader("res/stage2/clip_bg.png");
        buttons = ImageReader.reader ("res/stage2/yes_no.png");
        pos = 1;
        dialogue = ImageReader.storeDir("res/stage2/text/");
        dialogueBack = ImageReader.reader("res/header_base.png");

        pause = false;

        cases = ImageReader.storeDir("res/stage2/cases/");
        isToxic = ImageReader.isToxic("res/stage2/cases/");
        caseComplete = new boolean[cases.length];

        this.setFocusable(true);
        this.addKeyListener(this);
        addMouseListener(this);
    }

    public void tutorial() {
        Game.graphics.drawImage(dialogueBack, 40, 50, null);
        Game.graphics.drawImage(dialogue[pos], 40, 50, null);
    }

    public void getCase() {
        caseNum = (int) (Math.random() * 7);
        while(caseComplete[caseNum]) {
            caseNum = (int) (Math.random() * 7);
        }
        pause = true;
        caseComplete[caseNum] = true;
        Game.graphics.drawImage(cases[caseNum], 0, 0, null);
    }

    public void prompt() {
        Game.graphics.drawImage(dialogue[0], 328, 488, null);
    }

    public void endScene() {
        System.out.println("Scene end ;)");
    }

     /**
      * Displays the graphics necessary for stage 2.
      * @param g     Used to draw graphics.
      */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        Game.graphics.drawImage(table, -9, 0, null);
        Game.graphics.drawImage (buttons, -9, 0, null);
        if(pos != 0 && !isCasesComplete()) {
            tutorial();
        } else if(!isCasesComplete()) {
            getCase();
            prompt();
        } else {
            pause = false;
            endScene();
        }
    }

    public boolean isCasesComplete() {
        int count = 0;
        for(boolean complete : caseComplete) {
            if(complete) {
                count++;
            }
        }

        return count == caseComplete.length;
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
    * Key being typed
    * @param e     Typing a key
    */
    @Override
    public void keyTyped(KeyEvent e) {

    }

      /**
    * Key being pressed
    * @param e     Pressing a key
    */
    @Override
    public void keyPressed(KeyEvent e) {
        if (!pause) {
            if (pos == 6) {
                pos = 0;
            } else if (pos != 0) {
                pos++;
            } else {
                pause = true;
            }
            repaint();
        }
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(pause && e.getX() >= 11 && e.getX() <= 253 && e.getY() >= 451 && e.getY() <= 616) {
            if(isToxic[caseNum]) {
                System.out.println("YOOOO YOU'RE RIGHT! This case is toxic because blah blah blah");
            } else {
                System.out.println("Skill issueeeee");
            }
            pause = false;
        } else if(pause && e.getX() >= 730 && e.getX() <= 976 && e.getY() >= 451 && e.getY() <= 616) {
            if(!isToxic[caseNum]) {
                System.out.println("YOOOO YOU'RE RIGHT! This case is not toxic because blah blah blah");
            } else {
                System.out.println("Skill issueeeee");
            }
            pause = false;
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