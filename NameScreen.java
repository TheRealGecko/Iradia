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
    Image background;
    Image text;
    char key;
    int selectedKeyX;
    int selectedKeyY;
    Font consolas;
    Font consolas2;
    boolean verify;
    Color noColor;
    Color yesColor;
    boolean nameTooLong; // should i make these private?
    Game game;

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

    private void selectedKeyCoords() {
        if (Character.compare(key, 'A') < 0) {
            key = (char) 92;
        } else if ((int) key > 92) {
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
                    Game.graphics.setColor(noColor);
                    Game.graphics.fillRect(225, 495, 80, 40);
                    Game.graphics.setColor(yesColor);
                    Game.graphics.fillRect(693, 495, 80, 40);
                    Game.graphics.setColor(Color.BLACK);
                    Game.graphics.drawString("Is this name correct?", 365, 395);
                    Game.graphics.drawString("No", 250, 522);
                    Game.graphics.drawString("Yes", 711, 522);
                    Game.graphics.setFont(consolas);

                } else {
                    Game.graphics.setColor(yesColor);
                    Game.graphics.fillRect(225, 495, 548, 40);
                    Game.graphics.setColor(Color.BLACK);
                    Game.graphics.drawString("Names must have at least 1 character", 270, 395);
                    Game.graphics.drawString("OK", 490, 522);
                }
                Game.graphics.drawString(game.getPlayerName(), 500 - width / 2, 455);
            } else {
                Game.graphics.setColor(yesColor);
                Game.graphics.fillRect(225, 495, 548, 40);
                Game.graphics.setColor(Color.BLACK);
                Game.graphics.drawString("Names can't exceed 16 characters", 300, 395);
                Game.graphics.drawString("OK", 490, 522);
            }
        }
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
     * When any left arrow or right arrow is pressed, switches through letters.
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
     * Checks if the user's name is correct based on if they click in the area of the "yes" or "no" button
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
     * Moving mouse changes the colours of the buttons it hovers over
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
     * Dragging mosue (method not used but is necessary
     * to implement MouseMotionListener)
     *
     * @param e A drag while the Iradia menu is
     *          onscreen
     */
    @Override
    public void mouseDragged(MouseEvent e) {
    }
}