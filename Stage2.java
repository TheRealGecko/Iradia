/**
This is the first draft of the Stage2 class. Current features include:
* <ul>
*    <li>
* </ul>
* <p>
* Version date: 05/27/2022
* @author Alexandra Mitnik
* version: 1.0.0
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

    /**
     * Stage2 class's constructor. Initializes the table image.
     */
    public Stage2() {
        table = ImageReader.reader("res/stage2/clip_bg.png");
        buttons = ImageReader.reader ("res/stage2/yes_no.png");
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
        /*if(pos < dialogue.length - 1 && !pause) {
            pos++;
            repaint();
        }
      */
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}