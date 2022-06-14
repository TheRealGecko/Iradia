/**
 * This is the third draft of the Stage2 class. This class was added as of version 1.2.0.
 * <p>
 * Changes include:
 * <ul>
 * <li>Adding the cases
 * <li>Allowing user input
 * <li>Evaluating the user's answer
 * <li>Incrementing the user's score for correct answers
 * </ul>
 * </p>
 * <p>
 * Version date: 06/03/2022
 *
 * @author Alexandra Mitnik
 * @version: 1.3.63
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
     * table - Stores the background image of stage 2
     */
    private Image table;
    /**
     * buttons - Stores the yes/no button image
     */
    private Image buttons;
    /**
     * tutorialCase - Stores the tutorial case image
     */
    private Image tutorialCase;
    /**
     * dialogue - Stores stage 2's dialogue
     */
    private Image[] dialogue;
    /**
     * dialogueBack - Stores the purple backing image of the dialogue
     */
    private Image dialogueBack;
    /**
     * pos - Stores the position of where the dialogue is at
     */
    private int pos;
    /**
     * game - Stores the game
     */
    private Game game;
    /**
     * caseNUm - Stores the current case number
     */
    private int caseNum;
    /**
     * s2Score - Stores the player's score for stage 2 specifically
     */
    private int s2Score;
    /**
     * cases - Stores the cases' images
     */
    private ArrayList<Image> cases;
    /**
     * isToxic - Stores the cases' answers
     */
    private ArrayList<Boolean> isToxic;
    /**
     * reasons - Stores the cases' explanations
     */
    private ArrayList<Image> reasons;
    /**
     * profiles - Stores the cases' profile images
     */
    private ArrayList<Image> profiles;
    /**
     * answer - Stores whether the answer selected was correct
     */
    private boolean answer;
    /**
     * nextStage - Stores whether to proceed to the next stage
     */
    private boolean nextStage;
    /**
     * consolas - Stores the consolas font
     */
    private Font consolas;

    /**
     * The Stage2 class constructor.
     * Does the following:
     * - Initializes the instance variables
     * - Creates a KeyListener
     * - Creates a MouseListener
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

        nextStage = false;

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
        Game.graphics.drawImage(table, -9, 0, null);
        if (pos >= 5)
            Game.graphics.drawImage(tutorialCase, 0, 0, null);
        if (pos != 5) {
            Game.graphics.drawImage(dialogueBack, 40, 50, null);
            Game.graphics.drawImage(dialogue[pos], 40, 50, null);
        }
    }

    /**
     * The getCase method
     * Displays the graphics for a random case the player has not yet solved.
     */
    private void getCase() {
        if (pos % 2 == 0) {
            Game.graphics.drawImage(table, -9, 0, null);
            caseNum = (int) (Math.random() * cases.size());
            Game.graphics.drawImage(cases.remove(caseNum), 0, 0, null);
            Game.graphics.drawImage(profiles.remove(caseNum), 350, 197, null);
            answer = isToxic.remove(caseNum);
        }
    }


    /**
     * The prompt method.
     * Displays the graphics for the prompt asking the player if the case is toxic.
     */
    private void prompt() {
        if (pos % 2 == 0) {
            Game.graphics.drawImage(buttons, -9, 0, null);
            Game.graphics.drawImage(dialogue[0], 0, 0, null);
        } else {
            if (answer) {
                Game.graphics.drawImage(reasons.remove(caseNum * 2), 0, 0, null);
                reasons.remove(caseNum * 2);
            } else {
                Game.graphics.drawImage(reasons.remove((caseNum * 2) + 1), 0, 0, null);
                reasons.remove(caseNum * 2);
            }
        }
    }

    /**
     * The paintComponent method.
     * Displays the graphics necessary for stage 2. Calls tutorial(), promp(), or getCase() depending on which screen the
     player is on.
     * @param g     Used to draw graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        if (pos < 8) {
            tutorial();
        } else if (cases.size() != 0) {
            getCase();
            prompt();
        } else {
            Game.graphics.drawImage(ImageReader.reader("res/transition/end2.png"), 0, 0, null);
            Game.graphics.setFont(consolas);
            Game.graphics.setColor(new Color(92, 23, 40));
            Game.graphics.drawString("" + (s2Score - 1), 800, 310);
        }
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
     * When any key is pressed, switches through dialogue in the tutorial + opens prompt for user input on cases.
     * @param e     An action involving a key.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (cases.size() == 0) {
            nextStage = true;
            game.increasePlayerScore(s2Score);
        } else if (pos < 8 || (pos != 8 && pos % 2 == 1)) {
            pos++;
            repaint();
        }

        if (nextStage) {
            Game.frame.add(new Stage3(game));
            Game.frame.pack();
            Game.frame.remove(this);
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
     * Increases the user's score if they clicked the correct answer + switches to next case file.
     * @param e     A click while stage 2 is
     *              onscreen
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (pos >= 8 && pos % 2 == 0) {
            if ((e.getX() <= 600 && answer) || (e.getX() > 500 && !answer)) {
                answer = true;
                s2Score++;
            } else {
                answer = false;
            }
            pos++;
            repaint();
        }
    }

    /**
     * The mouseClicked method.
     * Checks if a mouse was clicked (method not used but is necessary to implement MouseListener).
     * @param e     A click while the Iradia menu is
     *              onscreen.
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * The mouseReleased method.
     * Checks if a mouse was released (method not used but is necessary to implement MouseListener).
     * @param e     A release while the Iradia menu is
     *              onscreen.
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * The mouseEntered method.
     * Checks if the mouse has entered the bounds of a component (method
     * not used but is necessary to implement
     * MouseListener).
     * @param e     An action involving a mouse.
     */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /**
     * The mouseExited method.
     * Checks if the mouse has exited the bounds of a component (method
     * not used but is necessary to implement
     * MouseListener).
     * @param e     An action involving a mouse.
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }


    //At the end of stage 3 when the user finishes the game, the current score and corresponding name is written down. Toggled by a boolean or smth?
    //this.recordScore();
    public void recordScore() {
        PrintWriter output; //declares object of the PrintWriter class
        String fileName = "names.txt";
        try {
            output = new PrintWriter(new FileWriter(fileName, true));
            output.println(game.getPlayerName());
            output.println(game.getPlayerScore());
            output.close();
        } catch (IOException e) {
        }
    }
}
