/**
 * This is the first draft of the Leaderboard class.
 * <p>
 * Version date: 06/08/2022
 *
 * @author Fatma Jadoon, Bethany Lum
 * @version 1.3.63
 * <p>
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;

public class Leaderboard extends JPanel implements KeyListener {
    /**
     * background - Stores the background of the Leaderboard
     */
    Image background;
    /**
     * sgold - Stores the score of the player whose rank is gold
     */
    int sgold;
    /**
     * ssilver - Stores the score of the player whose rank is silver
     */
    int ssilver;
    /**
     * sbronze - Stores the score of the player whose rank is bronze
     */
    int sbronze;
    /**
     * ugold - Stores the name of the player whose rank is gold
     */
    String ugold;
    /**
     * usilver - Stores the name of the player whose rank is silver
     */
    String usilver;
    /**
     * ubronze - Stores the name of the player who's rank is bronze
     */
    String ubronze;
    /**
     * IsPressed - Stores whether a key has been pressed in the Leaderboard scene
     */
    boolean isPressed;

    /**
     * The Leaderboard class constructor.
     * Does the following:
     * - Initializes the background image.
     * - Initializes instance variables.
     * - Creates a keyListener
     */
    public Leaderboard() {
        background = ImageReader.reader("res/Leaderboard-bg1.png");
        isPressed = false;
        sgold = 0;
        ssilver = 0;
        sbronze = 0;
        ugold = "";
        usilver = "";
        ubronze = "";
        this.setFocusable(true);
        this.addKeyListener(this);
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
     * The painComponent method.
     * Displays the graphics necessary for the Leaderboard.
     * @param g     Used to draw graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        this.getScores();
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        Game.graphics.drawImage(background, 0, 0, null);
        Font largeMono = new Font("Monospaced", Font.PLAIN, 50);
        g.setFont(largeMono);
        g.drawString("Leaderboard", 350, 100);
        Font smallerMono = new Font("Monospaced", Font.PLAIN, 30);
        g.setFont(smallerMono);
        int[] lengths = {ubronze.length(), usilver.length(), ugold.length()};
        for (int i = 0; i <= 2; i++) {
            if (lengths[i] > 5) {
                lengths[i] += 3;
            }
        }
        g.drawString(ubronze, 730 - lengths[0] * 6, 400);
        g.drawString(usilver, 250 - lengths[1] * 6, 380);
        g.drawString(ugold, 495 - lengths[2] * 6, 300);
        g.setFont(largeMono);
        g.drawString(sbronze + "", 730, 470);
        g.drawString(ssilver + "", 250, 450);
        g.drawString(sgold + "", 490, 400);
    }

    /**
     * The getScores method.
     * Retrieves the scores of the players and stores them in parallel ArrayLists.
     */
    public void getScores() {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> scores = new ArrayList<Integer>();
        BufferedReader input;
        try {
            String username = " ";
            input = new BufferedReader(new FileReader("names.txt"));
            while (username != null) {
                username = input.readLine();
                if (username == null) {
                    break;
                }
                names.add(username);
                String placeholder = input.readLine();
                int score = Integer.parseInt(placeholder);
                scores.add(score);
            }
            sort(names, scores);
        } catch (IOException e) {
        }
    }

    /**
     * The sort method
     * Sorts the scores of all the players based on who scored the highest amount of points.
     * @param names     Contains the names of all the players.
     * @param scores    Contains the scores of all the players.
     */
    private void sort(ArrayList<String> names, ArrayList<Integer> scores) {
        for (int j = 0; j < names.size(); j++) {
            String temp1 = names.get(j);
            Integer temp2 = scores.get(j);
            int i = j - 1;
            while ((i > -1) && ((scores.get(i).compareTo(temp2)) == 1)) {
                scores.set(i + 1, scores.get(i));
                names.set(i + 1, names.get(i));
                i--;
            }
            scores.set(i + 1, temp2);
            names.set(i + 1, temp1);
        }
        //assign ranks
        sbronze = scores.get(scores.size() - 3);
        ssilver = scores.get(scores.size() - 2);
        sgold = scores.get(scores.size() - 1);
        ubronze = names.get(names.size() - 3);
        usilver = names.get(names.size() - 2);
        ugold = names.get(names.size() - 1);
    }

    /**
     * The keyTyped method.
     * Check for typing action on the keyboard (method not used but is necessary
     to implement KeyListener)
     * @param e     An action involving a key
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * The keyPressed method.
     * Check for key pressing action on the keyboard. If a key is pressed, then it returns to the main menu.
     * @param e     An action involving a key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        isPressed = true;
    }

    /**
     * The keyReleased method.
     * Check for key releasing action on the keyboard (method not used but is necessary
     to implement KeyListener)
     * @param e     An action involving a key
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }
}