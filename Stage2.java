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

public class Stage2 extends JPanel
  {

    Image table;

    /**
     * Stage2 class's constructor. Initializes the table image.
     */
    public Stage2() {
        table = ImageReader.reader("res/stage2/clip_bg.png");
    }

     /**
      * Displays the graphics necessary for stage 2.
      * @param g     Used to draw graphics.
      */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        Game.graphics.drawImage(table, 0, 0, null);
    }
  }