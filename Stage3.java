/**
 * This is the fourth draft of the Stage3 class.
 * <p>
 * Changes include:
 * <ul>
 *   <li>Writeup for each piece of advice explaining why it is or isn't ideal + graphics required
 *   <li>Fixed sprite movement
 *   <li>Game ending screen
 *   <li>Case dialogue
 *   <li>Case user input
 *   <li>Stage outro dialogue
 * </ul>
 * </p>
 * <p>
 * Current features include:
 * <ul>
 *   <li>Moving sprite with walk cycle
 *   <li>Stage intro and outro dialogue
 *   <li>Case dialogue
 *   <li>Case user input
 *   <li>Answer evaluation (score increasing for correct answers)
 *   <li>Game ending screen
 * </ul>
 * </p>
 * <p>
 * Version date: 06/14/2022
 *
 * @author Alexandra Mitnik, Fatma Jadoon, Bethany Lum
 * @version: 1.5.132
 * </p>
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.io.*;

public class Stage3 extends JPanel implements KeyListener, MouseListener {
    /**
     * game - Stores the game
     */
    private Game game;

    /**
     * sprite - Stores the player/sprite
     */
    private Sprite sprite;

    /**
     * dSprite - Stores the thread which the sprite runs on
     */
    private Thread dSprite;

    /**
     * background - Stores the background image of stage 3
     */
    private Image background;

    /**
     * dialogueBack - Stores the purple backing image of the dialogue
     */
    private Image dialogueBack;

    /**
     * introDialogue - Stores the introductory dialogue
     */
    private Image[] introDialogue;

    /**
     * endDialogue - Stores the closing dialogue
     */
    private Image[] endDialogue;

    /**
     * pos - Stores the position of where the dialogue is at
     */
    private int pos;

    /**
     * caseNum - Stores the current case's number
     */
    private int caseNum;

    /**
     * s3Score - Stores the player's score for stage 3 specifically
     */
    private int s3Score;

    /**
     * cases - Stores stage 3's cases
     */
    private ArrayList<Case> cases;

    /**
     * caseOpen - Stores whether a case is open
     */
    private static boolean caseOpen;

    /**
     * consolas - Stores the consolas font for stage 3 score
     */
    Font consolas;

    /**
     * consolas2 - Stores the consolas font for final score
     */
    Font consolas2;

    /**
     * nextScene - Stores whether to proceed to the next scene
     */
    boolean nextScene;

    /**
     * arrowPressed - Evaluates if an arrow key has been pressed
     */
    boolean arrowPressed;

    /**
     * explanations - Explanations for the answers of all the cases in stage 3
     */
    private Image[] explanations;

    /**
     * The Stage3 class constructor. Initializes the instance variables and creates a KeyListener and MouseListener.
     * @param g     Refers to the game.
     */
    public Stage3(Game g) {
        game = g;
        sprite = new Sprite();
        pos = 0;
        s3Score = 0;
        background = ImageReader.reader("res/stage3/background.png");
        introDialogue = ImageReader.storeDir("res/stage3/introText/");
        endDialogue = ImageReader.storeDir("res/stage3/endDialogue/");
        dialogueBack = ImageReader.reader("res/header_base.png"); // Dialogue background image

        nextScene = false;

        cases = new ArrayList<Case>();
        int[] tempAnswers = {3, 2, 3, 3, 3, 2};

        arrowPressed = false;

        for (int a = 1; a <= 6; a++) {
            String caseImgAddress = "res/stage3/files/" + a + ".png";
            String optionImgAddress = "res/stage3/options/c" + a + ".png";
            String answerImgAddress = "res/stage3/answers/c" + a + "_answers.png";
            String dialogueDir = "res/stage3/caseDialogue/c" + a + "/";
            cases.add(new Case(caseImgAddress, optionImgAddress, answerImgAddress, dialogueDir, tempAnswers[a - 1]));
        }

        cases.get(0).setImgCoords(682, 1000, 0, 28);
        cases.get(1).setImgCoords(638, 1000, 208, 269);
        cases.get(2).setImgCoords(426, 590, 368, 650);
        cases.get(3).setImgCoords(252, 330, 118, 122);
        cases.get(4).setImgCoords(0, 98, 0, 52);
        cases.get(5).setImgCoords(0, 48, 420, 650);

        explanations = ImageReader.storeDir("res/stage3/explanations/");

        consolas = new Font("res/Consolas.ttf", Font.PLAIN, 86);
        consolas2 = new Font("res/Consolas.ttf", Font.PLAIN, 186);

        this.setFocusable(true); // Allows the class to receive user input
        this.addKeyListener(this);
        addMouseListener(this);
    }

    /**
     * The paintComponent method.
     * Displays the graphics necessary for stage 3.
     * @param g     Used to draw graphics.
     */
    @Override
    public void paintComponent(Graphics g) {

        Game.graphics = (Graphics2D) g;
        this.requestFocus();

        if (!caseOpen) {
            Game.graphics.drawImage(background, 0, 0, null);
            if (pos >= 7) {
                for (int i = 0; i < cases.size(); i++) {
                    Game.graphics.drawImage(cases.get(i).getCaseImg(), 0, 0, null);
                }
                if (cases.size() > 0 && !caseOpen) {
                    for (int a = 0; a < cases.size(); a++) {
                        if (cases.get(a).isTouched(sprite.getXPos(), sprite.getYPos())) {
                            caseNum = a;
                            caseOpen = true;
                            paperScreen();
                            break;
                        }
                    }
                }
            }
            if (pos < 10 && cases.size() > 0)
                Game.graphics.drawImage(introDialogue[pos], 0, 0, null);
            else if (cases.size() > 0) {

                dSprite = new Thread(new DrawSprite(Game.graphics, sprite));
                dSprite.run();
            } else if (pos < 2 && !nextScene) {
                Game.graphics.drawImage(dialogueBack, 0, 0, null);
                Game.graphics.drawImage(endDialogue[pos], 0, 0, null);
            } else if (pos >= 2 && !nextScene) {
                Game.graphics.drawImage(ImageReader.reader("res/transition/end3.png"), 0, 0, null);
                Game.graphics.setFont(consolas);
                Game.graphics.setColor(new Color(92, 23, 40));
                Game.graphics.drawString("" + s3Score, 800, 310);
                nextScene = true;
                pos = 0;
                endDialogue = ImageReader.storeDir("res/transition/endDialogue/");
            } else if (nextScene) {
                if (pos < 7) {
                    Game.graphics.drawImage(endDialogue[pos], 0, 0, null);
                    pos++;
                } else if (pos == 7) {
                    game.recordScore();
                    game.recordScore();
                    Game.graphics.drawImage(ImageReader.reader("res/transition/finalScore.png"), 0, 0, null);
                    Game.graphics.setFont(consolas2);
                    Game.graphics.setColor(new Color(92, 23, 40));
                    Game.graphics.drawString("" + game.getPlayerScore(), 400, 500);
                    pos++;
                } else if (pos == 8) {
                    Game.graphics.drawImage(ImageReader.reader("res/transition/ty.png"), 0, 0, null);
                    pos++;
                } else if (pos == 9) {
                    System.exit(0);
                }
            }
        } else if (cases.size() > 0 && cases.get(caseNum).getDialogueLength() > 0) {
            paperScreen();
        }
        if (dSprite != null)
            dSprite.stop();
    }

    /**
     * The getCaseOpen method.
     * @return Whether a case is open.
     */
    public static boolean getCaseOpen() {
        return caseOpen;
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
     * Regulates sprite movement (arrow keys).
     * @param e     An key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (nextScene) {
            repaint();
        } else if ((pos < 10 && cases.size() > 0 || pos < 2) && !caseOpen) {
            pos++;
            repaint();
        } else {
            if (!caseOpen && cases.size() > 0) {

                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    arrowPressed = true;
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        if (sprite.getXPos() >= 50 && !(sprite.getXPos() == 624 && sprite.getYPos() < 118)) {
                            sprite.setXPos(-2);
                        }
                        sprite.setDir('l');
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        if (sprite.getXPos() <= 756 && !(sprite.getXPos() == 180 && sprite.getYPos() < 118)) {
                            sprite.setXPos(2);
                        }
                        sprite.setDir('r');
                    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                        if (sprite.getYPos() >= 4 && !(sprite.getXPos() > 180 && sprite.getXPos() < 624 && sprite.getYPos() <= 119)) {
                            sprite.setYPos(-2);
                        }
                        sprite.setDir('b');
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        if (sprite.getYPos() <= 454) {
                            sprite.setYPos(2);
                        }

                        sprite.setDir('f');
                    }
                    repaint();
                }
            }
        }
    }

    /**
     * The keyTyped method
     * Toggles stage 3 case dialogue when any key is typed.
     * This is used instead of keyPressed() to avoid accidental key presses from attempts to move the sprite.
     * @param e     An key type.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (caseNum < cases.size() && cases.get(caseNum).getDialogueLength() == 0) {
            caseOpen = false;
            cases.remove(caseNum);
            if (cases.size() == 0) {
                pos = 0;
            }
            sprite.resetImgIndex();
            repaint();
        } else if (caseNum < cases.size() && cases.get(caseNum).getDialogueLength() > 1) {
            repaint();
        }
    }

    /**
     * The keyReleased method.
     * Check for key releasing action on the keyboard. If a key is released, then reset the sprite
     * to standing position.
     * @param e     An key release.
     */
    public void keyReleased(KeyEvent e) {
        if (!caseOpen && !nextScene) {
            arrowPressed = false;
            sprite.resetImgIndex();
            repaint();
        }
    }

    /**
     * The mousePressed method.
     * Checks if the user has selected the correct answer in case file scenes.
     * @param e     A mousepress.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if ((e.getX() >= 35 && e.getX() <= 473 && e.getY() >= 372 && e.getY() <= 473) || (e.getX() >= 35 && e.getX() <= 473 && e.getY() >= 506 && e.getY() <= 607) || (e.getX() >= 527 && e.getX() <= 965 && e.getY() >= 372 && e.getY() <= 473) || (e.getX() >= 527 && e.getX() <= 965 && e.getY() >= 506 && e.getY() <= 607)) {
            if (cases.get(caseNum).isCorrect(e)) {
                s3Score++;
                game.increasePlayerScore(1);
            }
            cases.get(caseNum).finished();
            repaint();
        }
    }

    /**
     * The mouseClicked method.
     * Checks if a mouse was clicked (method not used but is necessary to implement MouseListener).
     * @param e     A mouseclick.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * The mouseReleased method.
     * Checks if a mouse was released (method not used but is necessary to implement MouseListener).
     * @param e     A mouse release.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * The mouseEntered method.
     * Checks if the mouse has entered the bounds of a component (method
     * not used but is necessary to implement
     * MouseListener).
     * @param e     The mouse entering the screen.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * The mouseExited method.
     * Checks if the mouse has exited the bounds of a component (method
     * not used but is necessary to implement
     * MouseListener).
     * @param e    The mouse exiting the screen.
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * The paperScreen method.
     * Draws the case file scenes.
     */
    private void paperScreen() {
        Game.graphics.drawImage(ImageReader.reader("res/Name_Screen_Background_1.png"), 0, 0, null);
        if (cases.get(caseNum).getDialogueLength() > 2)
            Game.graphics.drawImage(dialogueBack, 0, 0, null);
        else if (cases.get(caseNum).getDialogueLength() == 2)
            Game.graphics.drawImage(ImageReader.reader("res/stage3/prompt.png"), 0, 0, null);
        else if (caseOpen && cases.get(caseNum).getDialogueLength() == 1) {
            Game.graphics.drawImage(dialogueBack, 0, 0, null);
            Game.graphics.drawImage(explanations[caseNum], 50, 20, null);
        }
        Game.graphics.drawImage(cases.get(caseNum).getCaseDialogue(), 0, 0, null);
    }

    /**
     * The Case class.
     */
    private class Case {
        /**
         * caseImg - Stores the paper image of the case
         */
        Image caseImg;
        /**
         * options - Stores the image of the case's advice options
         */
        Image optionsImg;
        /**
         * options - Stores the image of the case's advice answers
         */
        Image answerImg;
        /**
         * answer - Stores the case's correct answer
         */
        int answer;
        /**
         * finished - Stores whether the case is complete
         */
        boolean finished;
        /**
         * imgCoords - Stores the hit-box coordinates of the case
         */
        int[] imgCoords;
        /**
         * dialogue - Stores the case's dialogue
         */
        ArrayList<Image> caseDialogue;

        /**
         * The Case class constructor.
         * Initialized instance variables.
         * @param cImg      Refers to the paper image of the case.
         * @param optImg    Refers to the image of the case's options.
         * @param ansImg    Refers to the image of the case's answers.
         * @param diaDir    Refers to the directory of the case's dialogue.
         * @param ans       Stores the answer of the case.
         */
        private Case(String cImg, String optImg, String ansImg, String diaDir, int ans) {
            caseImg = ImageReader.reader(cImg);
            optionsImg = ImageReader.reader(optImg);
            answerImg = ImageReader.reader(ansImg);
            caseDialogue = new ArrayList<Image>();
            finished = false;
            answer = ans;

            for (int a = 1; a < 6; a++) {
                String path = diaDir + diaDir.substring(24, 26);
                path += "_" + a + ".png";
                File tempFile = new File(path);
                if (tempFile.exists()) {
                    caseDialogue.add(ImageReader.reader(path));
                } else
                    break;
            }
            caseDialogue.add(optionsImg);
            caseDialogue.add(answerImg);

            imgCoords = new int[4];
        }

        /**
         * The getDialogueLength method.
         * @return The remaining length of the dialogue.
         */
        public int getDialogueLength() {
            return caseDialogue.size();
        }

        /**
         * The getCaseDialogue method.
         * @return The case dialogue to be spoken.
         */
        public Image getCaseDialogue() {
            return (caseDialogue.remove(0));
        }

        /**
         * The finished method.
         * Marks the current case file as complete.
         */
        public void finished() {
            finished = true;
        }

        /**
         * The isCorrect method.
         * @param e     An action involving a mouse.
         * @return Whether the selected advice option was the correct answer.
         */
        public boolean isCorrect(MouseEvent e) {
            if (e.getX() >= 35 && e.getX() <= 473 && e.getY() >= 372 && e.getY() <= 473) {
                if (answer == 1) {
                    return true;
                } else {
                    return false;
                }
            } else if (e.getX() >= 35 && e.getX() <= 473 && e.getY() >= 506 && e.getY() <= 607) {
                if (answer == 2) {
                    return true;
                } else {
                    return false;
                }
            } else if (e.getX() >= 527 && e.getX() <= 965 && e.getY() >= 372 && e.getY() <= 473) {
                if (answer == 3) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (answer == 4) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        /**
         * The setImgCoords method
         * Sets the case's hit-box.
         * @param xMin      The minimum X coordinate
         * @param xMax      The maximum X coordinate
         * @param yMin      The minimum Y coordinate
         * @param yMax      The maximum Y coordinate
         */
        public void setImgCoords(int xMin, int xMax, int yMin, int yMax) {
            imgCoords[0] = xMin;
            imgCoords[1] = xMax;
            imgCoords[2] = yMin;
            imgCoords[3] = yMax;
        }

        /**
         * The isTouched method.
         * @param spriteX       The sprite's X coordinate.
         * @param spriteY       The sprite's Y coordinate.
         * @return Whether the hit-box was "touched" by the sprite.
         */
        public boolean isTouched(int spriteX, int spriteY) {
            if (spriteX >= imgCoords[0] && spriteX <= imgCoords[1]) {
                return spriteY >= imgCoords[2] && spriteY <= imgCoords[3];
            }
            return false;
        }

        /**
         * The getCaseImg method.
         * @return The paper image of the case.
         */
        public Image getCaseImg() {
            return caseImg;
        }

    }

    /**
     * The Sprite class
     */
    private class Sprite {
        /**
         * xPos - Stores the X coordinate of the sprite
         */
        private int xPos;
        /**
         * yPos - Stores the Y coordinate of the sprite
         */
        private int yPos;
        /**
         * imgIndex - Stores the state index of the sprite's walk cycle
         */
        private int imgIndex;
        /**
         * dir - Stores the direction in which the sprite is facing
         */
        private char dir;

        /**
         * The Sprite constructor.
         * Initializes the instance variables.
         */
        private Sprite() {
            xPos = 450;
            yPos = 150;
            imgIndex = 1;
            dir = 'f';
        }

        /**
         * The getXPos method.
         * @return The sprite's X coordinate.
         */
        public int getXPos() {
            return xPos;
        }

        /**
         * The getYPos method.
         * @return The sprite's Y coordinate.
         */
        public int getYPos() {
            return yPos;
        }

        /**
         * The setXPos method.
         * @param num       The amount in which the sprite's X coordinate is to increase by.
         */
        public void setXPos(int num) {
            xPos += num;
        }

        /**
         * The setYPos method.
         * @param num       The amount in which the sprite's Y coordinate is to increase by.
         */
        public void setYPos(int num) {
            yPos += num;
        }

        /**
         * The getSprite method.
         * @return The sprite image.
         */
        public Image getSprite() {
            String directory = "res/stage3/sprite/" + dir + imgIndex + ".png";
            Image i = ImageReader.reader(directory);
            return i;
        }

        /**
         * The incrementImgIndex method.
         * Changes the state of the sprite's walk cycle.
         */
        public void incrementImgIndex() {
            imgIndex++;
            if (imgIndex > 3)
                imgIndex = 1;
        }

        /**
         * The resetImgIndex method.
         * Sets the state of the sprite to a standing/idle position.
         */
        public void resetImgIndex() {
            imgIndex = 1;
        }

        /**
         * The setDir method.
         * @param s     The new direction of where the sprite should be facing
         */
        public void setDir(char s) {
            dir = s;
        }
    }

    /**
     * The DrawSprite class.
     */
    private class DrawSprite implements Runnable {
        /**
         * graphics - Stores the sprite thread's graphics
         */
        Graphics2D graphics;
        /**
         * sprite - Stores the sprite.
         */
        Sprite sprite;

        /**
         * The DrawSprite class constructor.
         * @param g     Refers to the graphics which will be used in DrawSprite.
         * @param s     Refers to the sprite which will be used in DrawSprite.
         */
        public DrawSprite(Graphics2D g, Sprite s) {
            graphics = g;
            sprite = s;
        }

        /**
         * The run method.
         * Loops through the sprite's walk cycle.
         */
        public void run() {
            if (!Stage3.getCaseOpen()) {
                try {
                    graphics.drawImage(sprite.getSprite(), sprite.getXPos(), sprite.getYPos(), null);
                    if (arrowPressed) {
                        sprite.incrementImgIndex();
                    }
                    Thread.sleep(110);
                } catch (Exception e) {
                }
            }
        }
    }
}
