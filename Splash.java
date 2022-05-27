/**
 * This is the second draft of the Splash class. No changes were made between the first and second drafts. Current features include:
 * <ul>
 *     <li>Fading in/out an image
 * </ul>
 * <p>
 * Currently, it is used for the following screens:
 * <ul>
 *     <li>Logo splashscreen
 *     <li>Warning splashscreen
 * </ul>
 * <p>
 * Version date: 05/27/2022
 * @author Fatma Jadoon
 * @version 1.2.9
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Splash extends JPanel {
    Image logo;
    double alpha;
    int fadeStage;

    /**
     * The Splash class's constructor. Initialize values needed for the splash screen to run.
     * @param path      The path of the image that will be fading in/out in the splash screen.
     */
    public Splash(String path) {
        logo = ImageReader.reader(path);
        alpha = 0;
        fadeStage = 0;
    }

    /**
     * Repaints the image on the screen.
     * @ param event  An action performed by the user defined in the JFrame
     */
    private void actionPerformed(ActionEvent event) {
        repaint();
    }

    /**
     * Draws the fading image on the screen.
     * @param g     Used to draw graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        if(fadeStage == 0)
            alpha += 0.01;
        else {
            alpha -= 0.01;
        }

        if(alpha > 1) {
            alpha = 1;
            fadeStage++;
        } else if(alpha < 0) {
            alpha = 0;
            fadeStage++;
        }

        Game.graphics = (Graphics2D) g;
        Game.graphics.setColor(Color.black);
        Game.graphics.fillRect(0, 0, getWidth(), getHeight());
        Game.graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha));
        Game.graphics.drawImage(logo, 0, 0, null);
    }

    /**
     * Runs the timer for how long the fading image lasts, and stops once the fading is complete.
     */
    public void run() {
        Timer time = new Timer(30, this::actionPerformed);
        time.start();
        while (fadeStage < 2)
            Thread.onSpinWait();
    }
}
