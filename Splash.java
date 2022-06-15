/**
 * This is the fourth draft of the Splash class. No changes have been made since the previous version.
 * Current features include:
 * <ul>
 *     <li>Fading in/out an image
 *     <li>Logo image
 *     <li>Content advisory image
 * </ul>
 * /p>
 * <p>
 * Version date: 06/14/2022
 *
 * @author Fatma Jadoon
 * @version 1.5.132
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Splash extends JPanel {

    /**
     * logo - Stores Antlion Studios' logo image
     */
    Image logo;

    /**
     * alpha - Stores the alpha/opacity value of the logo
     */
    double alpha;

    /**
     * fadeStage - Stores whether the logo is in the fade-in/fade-out stage
     */
    int fadeStage;

    /**
     * The Splash class constructor.
     * Initialize Splash's instance variables.
     *
     * @param path The path of the image that will be fading in/out in the splash screen.
     */
    public Splash(String path) {
        logo = ImageReader.reader(path);
        alpha = 0;
        fadeStage = 0;
    }

    /**
     * The actionPerformed method.
     * Repaints the image on the screen.
     *
     * @param event An action performed by the user defined in the JFrame.
     */
    private void actionPerformed(ActionEvent event) {
        repaint();
    }

    /**
     * The paintComponent method.
     * Draws the fading image on the screen.
     *
     * @param g Used to draw graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        if (fadeStage == 0)
            alpha += 0.01;
        else {
            alpha -= 0.01;
        }

        if (alpha > 1) {
            alpha = 1;
            fadeStage++;
        } else if (alpha < 0) {
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
     * The run method
     * Runs the timer for how long the fading image lasts, and stops once the fading is complete.
     */
    public void run() {
        Timer time = new Timer(30, this::actionPerformed);
        time.start();
        while (fadeStage < 2)
            Thread.onSpinWait();
    }
}
