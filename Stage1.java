import javax.swing.*;
import java.awt.*;

public class Stage1 extends JPanel {
    Image train;

    public Stage1() {
        train = ImageReader.reader("res/train.png");
    }

    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;

        Game.graphics.drawImage(train, 0, 0, null);
    }
}
