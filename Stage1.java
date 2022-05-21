/**
This is the first draft of the Stage1 class. Current features include:
* <ul>
*    <li>Setting up the painting code
*    <li>Painting the background for the first screen
* </ul>
* <p>
* Version date: 05/20/2022
* @author Fatma Jadoon
* version: 1.0.0
* </p>
*/

import javax.swing.*;
import java.awt.*;

public class Stage1 extends JPanel {
    Image train;

      /**
     * Stage1 class's constructor. Initializes the train image.
     */
    public Stage1() {
        train = ImageReader.reader("res/train.png");
    }

       /**
      * Displays the graphics necessary for stage 1.
      * @param g     Used to draw graphics.
      */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        Game.graphics.drawImage(train, 0, 0, null);
    }
}
