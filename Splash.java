import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Splash extends JPanel {
    Image logo;
    double alpha;
    int fadeStage;

    public Splash(String path) {
        logo = ImageReader.reader(path);
        alpha = 0;
        fadeStage = 0;
    }

    private void actionPerformed(ActionEvent event) {
        repaint();
    }

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

    public void run() {
        Timer time = new Timer(30, this::actionPerformed);
        time.start();
        while (fadeStage < 2)
            Thread.onSpinWait();
    }
}
