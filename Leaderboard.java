/**
 * This is the first draft of the Leaderboard class.
 * <p>
 * Version date: 06/08/2022
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
    Image background;
    int sgold = 0;
    int ssilver = 0;
    int sbronze = 0;
    String ugold = "";
    String usilver = "";
    String ubronze ="";
    boolean isPressed;
   /**
     * Leaderboard's constructor. Initializes the background image. 
     */
    public Leaderboard(){
        background = ImageReader.reader("res/Leaderboard-bg1.png");
        isPressed = false;
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
    }

    @Override
    /**
     * Displays the graphics necessary for the Leaderboard.
     * @param g     Used to draw graphics.
     */
    public void paintComponent(Graphics g) {
        this.getScores();
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        Game.graphics.drawImage(background, 0, 0, null);
        Font largeMono = new Font("Monospaced", Font.PLAIN, 50);
        g.setFont(largeMono);
        g.drawString("Leaderboard",350,100);
        Font smallerMono = new Font("Monospaced", Font.PLAIN, 30);
        g.setFont(smallerMono);
        int[] lengths = {ubronze.length(), usilver.length(), ugold.length()};
        for(int i = 0; i <= 2; i++){
            if(lengths[i] > 5){
                lengths[i] += 3;
            }
        }
        g.drawString(ubronze, 730 - lengths[0]*6, 400);
        g.drawString(usilver, 250 - lengths[1]*6, 380);
        g.drawString(ugold, 495 - lengths[2]*6, 300);
        g.setFont(largeMono);
        g.drawString(sbronze+"", 730, 470);
        g.drawString(ssilver+"", 250, 450);
        g.drawString(sgold+"", 490, 400);
    }

    public void getScores()
    {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> scores = new ArrayList<Integer>();
        BufferedReader input;
        try
        {
            String username = " ";
            input = new BufferedReader(new FileReader("names.txt"));
            while(username != null){
                username = input.readLine();
                if(username == null){
                    break;
                }
                names.add(username);
                String placeholder = input.readLine();
                int score = Integer.parseInt(placeholder);
                scores.add(score);
            }
            sort(names, scores);
        }
        catch (IOException e) {}
    }

    private void sort(ArrayList<String> names, ArrayList<Integer> scores) {
        for (int j = 0; j < names.size(); j++) {
            String temp1 = names.get(j);
            Integer temp2 = scores.get(j);
            int i = j-1;
            while ((i > -1) && ((scores.get(i).compareTo(temp2)) == 1)) {
                scores.set(i+1, scores.get(i));
                names.set(i+1, names.get(i));
                i--;
            }
            scores.set(i+1, temp2);
            names.set(i+1, temp1);
        }
        //assign ranks
        sbronze = scores.get(scores.size()-3);
        ssilver = scores.get(scores.size()-2);
        sgold = scores.get(scores.size()-1);
        ubronze = names.get(names.size()-3);
        usilver = names.get(names.size()-2);
        ugold = names.get(names.size()-1);
    }
 /**
     * Check for typing action on the keyboard (method not used but is necessary
     to implement KeyListener)
     * @param e     An action involving a key
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        isPressed = true;
    }
/**
     * Check for key releasing action on the keyboard (method not used but is necessary
     to implement KeyListener)
     * @param e     An action involving a key
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
}