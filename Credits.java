import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Credits extends JPanel implements KeyListener {
    boolean isPressed;

    public Credits() {
        isPressed = false;
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        Game.graphics.drawImage(ImageReader.reader("res/credits.png"), 0, 0, null);
    }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Game.frame.remove(this);
        isPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
