import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Menu extends JPanel{
    Image menu;

    public Menu() {
            menu = ImageReader.reader("res/menu.png");
    }

    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;

        Game.graphics.drawImage(menu, 0, 0, null);
    }
}
