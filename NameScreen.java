/**
 * This is the first draft of the NameScreen class. This class was added as of version 1.2.0.
 * <p>
 * Current features include:
 * <ul>
 * <li>On-screen keyboard
 * <li>Interaction w. the on-screen keyboard using arrow keys + enter key
 * <li>Setting player name w. the on-screen keyboard
 * <li>Name letter delete button
 * <li>Name enter button
 * <li>Name confirm screen
 * <li>Error trapping for names with no characters or > 16 characters
 * </ul>
 * </p>
 * <p>
 * Version date: 06/14/2022
 *
 * @author Alexandra Mitnik
 * @version: 1.3.63
 * </p>
 */

/*
External code sources: 
(1) https://stackoverflow.com/questions/13731710/allowing-the-enter-key-to-press-the-submit-button-as-opposed-to-only-using-mo - used for enter key 
(2) https://stackoverflow.com/questions/8197882/error-mousemotionlistener-mouselistener - used for mouse adapter
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class NameScreen extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
    /** The game whose JFrame the NameScreen should be drawn in*/
    private Game game;
    /** The background for the name screen */
    private Image background;
    /** The keyboard for the name screen */
    private Image text;
    /** The last key selected */
    private char key;
    /** The x coordinates for the last key selected (used to highlight the key onscreen)*/
    private int selectedKeyX;
    /** The y coordinates for the last key selected (used to highlight the key onscreen)*/
    private int selectedKeyY;
    /** The font to write the player's name in*/
    private Font consolas;
    /** The font to write the errortraps + name confirm pop-up text in*/
    private Font consolas2;
    /** Evaluates if the name has been verified or not*/
    private boolean verify;
    /** Evaluates if the name exceeds 16 characters*/
    private boolean nameTooLong;
    /** Colour of the no button (changes if the mouse is/isn't hovering over it)*/
    private Color noColor;
    /** Colour of the no button (changes if the mouse is/isn't hovering over it)*/
    private Color yesColor;

    /**
     * NameScreen class's constructor. Initializes the images, fonts, and colours, gives the instance variables their default values, and adds the necessary listeners.
     *
     * @param g The game that the NameScreen should be added in.
     */
    public NameScreen(Game g) {
        key = 'A';
        background = ImageReader.reader("res/Name_Screen_Background_1.png");
        text = ImageReader.reader("res/Name_Screen_Background_2.png");
        consolas = new Font("res/Consolas.ttf", Font.PLAIN, 36);
        consolas2 = new Font("res/Consolas.ttf", Font.PLAIN, 24);
        noColor = new Color(237, 107, 97);
        yesColor = new Color(102, 116, 112);
        nameTooLong = false;
        game = g;
        this.setFocusable(true);
        this.addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Updates the coordinates of the key highlight box to match those of the most recently selected key.
     */
    private void selectedKeyCoords() {
        if (Character.compare(key, 'A') < 0) { // Loops key to 'Z' if user tries to select key before 'A'.
            key = (char) 92;
        } else if ((int) key > 92) { // Loops key to 'A' if user tries to select key before 'Z'.
            key = 'A';
        }
        selectedKeyX = 180 + 96 * (((int) key - 2) % 7);
        selectedKeyY = 262 + 97 * (((int) key - 65) / 7);
    }

  
    /**
     * Displays the graphics necessary for NameScreen.
     *
     * @param g Used to draw graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        Game.graphics.drawImage(background, 0, 0, null);
        Game.graphics.setColor(new Color(243, 230, 223));
        selectedKeyCoords();
        Game.graphics.fillRect(selectedKeyX, selectedKeyY, 50, 50);
        Game.graphics.drawImage(text, 0, 0, null);
        Game.graphics.setColor(Color.BLACK);
        Game.graphics.setFont(consolas);
        int width = g.getFontMetrics().stringWidth(game.getPlayerName());
        Game.graphics.drawString(game.getPlayerName(), 500 - width / 2, 180);
        if (verify == true || nameTooLong == true) {
            Game.graphics.setColor(new Color(243, 230, 223));
            Game.graphics.fillRect(212, 350, 576, 200);
            Game.graphics.setFont(consolas2);
            if (verify == true) {
                if (game.getPlayerName().length() > 0) {
                  verify();
                } else {
                  tooShortError();
                }
                Game.graphics.drawString(game.getPlayerName(), 500 - width / 2, 455);
            } else {
                  tooLongError();
            }
        }
    }

    /**
     * Displays the graphics for the name verification pop-up.
     */
    private void verify()
    {
     Game.graphics.setColor(noColor);
                    Game.graphics.fillRect(225, 495, 80, 40);
                    Game.graphics.setColor(yesColor);
                    Game.graphics.fillRect(693, 495, 80, 40);
                    Game.graphics.setColor(Color.BLACK);
                    Game.graphics.drawString("Is this name correct?", 365, 395);
                    Game.graphics.drawString("No", 250, 522);
                    Game.graphics.drawString("Yes", 711, 522);
                    Game.graphics.setFont(consolas);
    }

    /**
    * Displays the graphics for the null name error pop-up.
    */
    private void tooShortError()
    {
                    Game.graphics.setColor(yesColor);
                    Game.graphics.fillRect(225, 495, 548, 40);
                    Game.graphics.setColor(Color.BLACK);
                    Game.graphics.drawString("Names must have at least 1 character", 270, 395);
                    Game.graphics.drawString("OK", 490, 522);
    }

    /**
    * Displays the graphics for the too long name error pop-up.
    */
    private void tooLongError()
    {
                    Game.graphics.setColor(yesColor);
                    Game.graphics.fillRect(225, 495, 548, 40);
                    Game.graphics.setColor(Color.BLACK);
                    Game.graphics.drawString("Names can't exceed 16 characters", 300, 395);
                    Game.graphics.drawString("OK", 490, 522);
    }

    /**
     * KeyListener being added
     *
     * @param l KeyListener
     */
    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
    }

    /**
     * When any arrow key is pressed, switches through letters. When enter is pressed, selects the letter. Also allows for deleting letters and selecting name in the same manner.
     *
     * @param e Pressing a key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            key = (char) (((int) key) - 1);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            key = (char) (((int) key) + 1);
        } else if (e.getKeyCode() == KeyEvent.VK_UP && selectedKeyY != 262) {
            key = (char) (((int) key) - 7);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && selectedKeyY != 553) {
            key = (char) (((int) key) + 7);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (key >= 65 && key <= 90) {
                if (game.getPlayerName().length() < 16)
                    game.addNameLetter(key);
                else
                    nameTooLong = true;
            } else if (key == 91) {
                game.deleteNameLetter();
            } else if (key == 92) {
                verify = true;
            }
        }
        repaint();
    }

    /**
     * Key being typed (method not used but is necessary to implement
     * KeyListener)
     *
     * @param e Typing a key
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Key being released
     * (method not used but is necessary to implement
     * KeyListener)
     *
     * @param e Releasing a key
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Checks if the user's name is correct based on if they click in the area of the "yes" or "no" button.
     *
     * @param e A click while the name verification is onscreen
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (verify == true) {
            if (game.getPlayerName().length() > 0) {
                if (e.getX() >= 693 && e.getX() <= 773 && e.getY() >= 495 && e.getY() <= 535) {
                    Game.frame.remove(this);
                    Game.frame.add(new Stage1(game));
                    Game.frame.pack();
                } else if (e.getX() >= 225 && e.getX() <= 305 && e.getY() >= 495 && e.getY() <= 535) {
                    verify = false;
                    repaint();
                }
            } else if (e.getX() >= 225 && e.getX() <= 773 && e.getY() >= 495 && e.getY() <= 535) {
                verify = false;
                repaint();
            }
        }
        if (nameTooLong == true) {
            if (e.getX() >= 225 && e.getX() <= 773 && e.getY() >= 495 && e.getY() <= 535) {
                nameTooLong = false;
                repaint();
            }
        }
    }

    /**
     * Clicking mouse (method not used but is necessary
     * to implement MouseListener)
     *
     * @param e A click while the Iradia menu is
     *          onscreen
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Releasing mouse (method not used but is necessary
     * to implement MouseListener)
     *
     * @param e A release while the Iradia menu is
     *          onscreen
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Mouse entering the bounds of a component (method
     * not used but is necessary to implement
     * MouseListener)
     *
     * @param e Entering the bounds of a component
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Mouse exiting the bounds of a component (method
     * not used but is necessary to implement
     * MouseListener)
     *
     * @param e Exiting the bounds of a component
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Moving mouse changes the colours of the buttons it hovers over.
     *
     * @param e Mouse movement while Iradia is onscreen
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (verify == true) {
            if (game.getPlayerName().length() > 0) {
                if (e.getX() >= 693 && e.getX() <= 773 && e.getY() >= 495 && e.getY() <= 535) {
                    yesColor = new Color(127, 209, 174);
                } else {
                    yesColor = new Color(102, 116, 112);
                    if (e.getX() >= 225 && e.getX() <= 305 && e.getY() >= 495 && e.getY() <= 535)
                        noColor = new Color(251, 141, 118);
                    else
                        noColor = new Color(237, 107, 97);
                }
            } else {
                if (e.getX() >= 225 && e.getX() <= 773 && e.getY() >= 495 && e.getY() <= 535)
                    yesColor = new Color(127, 209, 174);
                else
                    yesColor = new Color(102, 116, 112);
            }
        } else {
            if (e.getX() >= 225 && e.getX() <= 773 && e.getY() >= 495 && e.getY() <= 535)
                yesColor = new Color(127, 209, 174);
            else
                yesColor = new Color(102, 116, 112);
        }
        repaint();
    }

    /**
     * Dragging mouse (method not used but is necessary to implement MouseMotionListener).
     *
     * @param e A drag while the Iradia menu is
     *          onscreen
     */
    @Override
    public void mouseDragged(MouseEvent e) {
    }
}