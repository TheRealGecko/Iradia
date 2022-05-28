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

public class Stage2 extends JPanel implements KeyListener
  {

    Image table;
    Image buttons;
    Image[] dialogue;
    int pos;

    /**
     * Stage2 class's constructor. Initializes the table and button images.
     */
    public Stage2() {
        table = ImageReader.reader("res/stage2/clip_bg.png");
        buttons = ImageReader.reader ("res/stage2/yes_no.png");
        pos = 0;
        dialogue = ImageReader.storeDir("res/stage2/text/");
    }

     /**
      * Displays the graphics necessary for stage 2.
      * @param g     Used to draw graphics.
      */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        Game.graphics.drawImage(table, 0, 0, null);
        Game.graphics.drawImage (buttons, 0, 0, null);
        Game.graphics.drawImage (dialogue[pos], 180, 150, null);
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
        /*stuff happens
            repaint();
      */
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
}