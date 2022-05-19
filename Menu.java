import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Menu extends JPanel implements MouseListener {
    Image menu;
    double alpha = 1;

    public Menu() {
            menu = ImageReader.reader("res/menu.png");

            addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;

        Game.graphics.drawImage(menu, 0, 0, null);
    }

    public void mousePressed(MouseEvent e) {
        if(e.getX() >= 193 && e.getX() <= 453 && e.getY() >= 180 && e.getY() <= 261) {
            System.out.println("Play game :D");
        }
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
