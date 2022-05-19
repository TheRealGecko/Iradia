import java.awt.*;
import javax.swing.*;

public class Game {
    public static Graphics2D graphics;
    public static int scene = 0;
    private JFrame frame;

    public Game() {
        frame = new JFrame("Iradia");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Dimension dim = new Dimension(new Dimension(1000, 650 + (frame.getHeight() - frame.getContentPane().getHeight())));
        frame.setPreferredSize(dim);
        frame.setMaximumSize(dim);
        frame.setMinimumSize(dim);
    }

    public void run() {
        LogoSplash splash = new LogoSplash();
        frame.add(splash);
        frame.pack();
        splash.run();
        frame.remove(splash);
        frame.add(new Menu());
        frame.pack();
    }
}
