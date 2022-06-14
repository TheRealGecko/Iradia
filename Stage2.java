/**
 * This is the fourth draft of the Stage2 class.
 * <p>
 * Changes include:
 * <ul>
 *   <li>Errortrapping
 *   <li>Different button hitboxes
 *   <li>Score specifically for stage 2 in addition to the game total
 * </ul>
 * </p>
 * <p>
 * Current features include:
 * <ul>
 *   <li>Displaying cases
 *   <li>Allowing user input
 *   <li>Evaluating the user's answer
 *   <li>Incrementing the user's score for correct answers
 *   <li>Transition into stage 3
 * </ul>
 * </p>
 * <p>
 * Version date: 06/14/2022
 * @author Alexandra Mitnik
 * @version: ???
 * </p>
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class Stage2 extends JPanel implements KeyListener, MouseListener {

    /**
     * game - The game this screen will be displayed in.
     */
    private Game game;

    /**
     * table - The background for stage 2.
     */
    private Image table;

    /**
     * buttons - The yes/no buttons.
     */
    private Image buttons;

    /**
     * tutorialCase - The tutorial case for stage 2.
     */
    private Image tutorialCase;

    /**
     * dialogue - All of dialogue for stage 2.
     */
    private Image[] dialogue;

    /**
     * dialogueBack - The background for dialogue.
     */
    private Image dialogueBack;

    /**
    * The current position (array index) of dialogue.
    */
    private int pos;

    /**
    * The current case number (cases array index).
    */
    private int caseNum;

    /**
    * The current position (array index) of dialogue.
    */
    private int s2Score;

    /**
    * All of the cases for stage 2.
    */
    private ArrayList<Image> cases;

    /**
    * The correct answers for cases.
    */
    private ArrayList<Boolean> isToxic;

    /**
    * The answer reasons for cases.
    */
    private ArrayList<Image> reasons;

    /**
    * The profile list for cases.
    */
    private ArrayList<Image> profiles;

    /**
    * Evaluates if the player has answered the current case.
    */
    private boolean answer;

    /**
     * consolas - The font to draw Strings with.
     */
    private Font consolas;

    /**
     * Stage2 class's constructor. Initializes the instance variables and adds a KeyListener and MouseListener.
     * @param g The game to add the scene to.
     */
    public Stage2(Game g) {
        game = g;
        cases = new ArrayList<Image>(); // Case images
        for (int i = 1; i <= 7; i++) {
            String dir = "res/stage2/cases/Case_Files_" + i;
            if (i == 2 || i == 3 || i == 5 || i == 6) {
                dir += "t";
            } else {
                dir += "f";
            }
            dir += ".png";

            cases.add(ImageReader.reader(dir));
        }

        reasons = new ArrayList<Image>();
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j <= 1; j++) {
                String dir = "res/stage2/reasons/c" + i;

                if (j == 0) {
                    dir += "_right";
                } else {
                    dir += "_wrong";
                }

                dir += ".png";
              reasons.add(ImageReader.reader(dir));
            }
        }

      reasons.add(ImageReader.reader("res/stage2/reasons/lastReason.png"));

        profiles = new ArrayList<Image>();
        Image[] temp = ImageReader.storeDir("res/stage2/profiles/");
        profiles.addAll(Arrays.asList(temp).subList(0, 7));

        isToxic = ImageReader.isToxic(cases); // Case answers

        table = ImageReader.reader("res/stage2/clip_bg.png"); // Table image
        buttons = ImageReader.reader("res/stage2/yes_no.png"); // Button image
        dialogue = ImageReader.storeDir("res/stage2/text/"); // Dialogue images
        dialogueBack = ImageReader.reader("res/header_base.png"); // Dialogue background image
        tutorialCase = ImageReader.reader("res/stage2/cases/Tutorial_Case.png");

        pos = 1; // Dialogue position
        s2Score = 0;
        consolas = new Font("res/Consolas.ttf", Font.PLAIN, 86);

        this.setFocusable(true); // Allows the class to receive user input
        this.addKeyListener(this);
        addMouseListener(this);
    }

    /**
     * The tutorial method.
     * Displays the graphics for the tutorial dialogue.
     */
    private void tutorial() {
        Game.graphics.drawImage(table, 0, 0, null);
        if (pos >= 5)
            Game.graphics.drawImage(tutorialCase, 0, 0, null);
        if (pos != 5) {
            Game.graphics.drawImage(dialogueBack, 0, 0, null);
            Game.graphics.drawImage(dialogue[pos], 40, 50, null);
        }
    }

    /**
     * The getCase method.
     * Displays the graphics for a random case the player has not yet solved.
     */
    private void getCase() {
      if (reasons.size () >= 2 && caseNum < cases.size())
      {
          if (pos % 2 == 0) {
          Game.graphics.drawImage(table, 0, 0, null);
          caseNum = (int) (Math.random() * cases.size());
          Game.graphics.drawImage(cases.remove(caseNum), 0, 0, null);
          Game.graphics.drawImage(profiles.remove(caseNum), 350, 197, null);
          answer = isToxic.remove(caseNum);
        }
      }

    }


    /**
     * The prompt method.
     * Displays the graphics for the prompt asking the player if the case is toxic, and the answer evaluation.
     */
    private void prompt() {
        if (reasons.size() == 1)
        {
          Game.graphics.drawImage(ImageReader.reader("res/transition/end2.png"), 0, 0, null);
          Game.graphics.setFont(consolas);
          Game.graphics.setColor(new Color(92, 23, 40));
          Game.graphics.drawString("" + s2Score, 800, 310);
          reasons.remove(0);
        }
        else{      
          if (pos % 2 == 0) {
            Game.graphics.drawImage(buttons, 0, 0, null);
            Game.graphics.drawImage(dialogue[0], 0, 0, null);
        } else {
        if (answer) {
                Game.graphics.drawImage(reasons.remove(caseNum * 2), 0, 0, null);
                reasons.remove(caseNum * 2);
            } else if(caseNum * 2 + 1 < reasons.size()) {
                Game.graphics.drawImage(reasons.remove(caseNum * 2 + 1), 0, 0, null);
                reasons.remove(caseNum * 2);
            }            
          }}

        }
    

    /**
     * The paintComponent method.
     * Displays the graphics necessary for stage 2. Calls tutorial (), prompt (), or getCase() depending on which screen the
     player is on.
     * @param g     Used to draw graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        if (pos < 8) {
            tutorial();
        } else if (reasons.size() != 0) {
          getCase();
          prompt();
        } else {
           Game.frame.remove(this);
           Stage3 stage3 = new Stage3(game);
           Game.frame.add(stage3);
           Game.frame.pack();
           }
             
        }

    /**
     * The addKeyListener method.
     * Adds a KeyListener.
     * @param l    KeyListener being added.
     */
    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
    }

    /**
     * The keyPressed method.
     * Key being pressed (method not used but is necessary to implement
     KeyListener).
     * @param e     Pressing a key
     */
    @Override
    public void keyPressed(KeyEvent e) {
      
    }

    /**
     * The keyTyped method.
     * Key being typed (method not used but is necessary to implement
     KeyListener).
     * @param e     Typing a key
     */
    @Override
    public void keyTyped(KeyEvent e) {
if (pos < 8 || (pos != 8 && pos % 2 == 1)) {
            pos++;
            repaint();
        }
      if (reasons.size() == 0)
      {
        repaint();
      }
    }

    /**
     * The keyReleased method.
     * Key being released (method not used but is necessary to implement
     KeyListener).
     * @param e     Releasing a key
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * The mousePressed method.
     * Increases the user's score if they clicked the correct answer + switches to next case file.
     * @param e     A press while Iradia is onscreen.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (pos >= 8 && pos % 2 == 0) {
            if ((e.getX() >= 20 && e.getX() <= 265 && e.getY() >= 450 && e.getY() <= 617) || (e.getX() >= 740 && e.getX() <= 984 && e.getY() >= 450 && e.getY() <= 617)) {
              if(((e.getX() >= 20 && e.getX() <= 265 && e.getY() >= 450 && e.getY() <= 617) && answer) || (e.getX() >= 740 && e.getX() <= 984 && e.getY() >= 450 && e.getY() <= 617) && !answer) {
                answer = true;
                s2Score++;
              } else {
                answer = false;
              }
              if (pos != 8)
              {
                System.out.println("slay " + s2Score);
              }
              pos++;
              repaint();
            }
        }
    }

    /**
     * The mouseClicked method.
     * Clicking mouse (method not used but is necessary
     to implement MouseListener).
     * @param e     A click while Iradia is onscreen.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * The mouseReleased method.
     * Releasing mouse (method not used but is necessary
     to implement MouseListener)
     * @param e     A release while Iradia is onscreen.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * The mouseEntered method.
     * Mouse entering the bounds of a component (method
     not used but is necessary to implement
     MouseListener)
     * @param e     Entering the bounds of a component.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * The mouseExited method.
     * Mouse exiting the bounds of a component (method
     not used but is necessary to implement
     MouseListener)
     * @param e     Exiting the bounds of a component.
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
}