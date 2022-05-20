import java.awt.*;
import javax.swing.*;

public class Game {
    public static Graphics2D graphics;
    public static int scene;
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
        scene = 0;
    }

    public void run() {
        Splash splash = new Splash("res/logoSplash.png");
        frame.add(splash);
        frame.pack();
        splash.run();
        frame.remove(splash);
        splash = new Splash("res/warningSplash.png");
        frame.add(splash);
        frame.pack();
        splash.run();
        frame.remove(splash);
        Menu menu = new Menu();
        frame.add(menu);
        frame.pack();
        while (scene == 0) Thread.onSpinWait();
        frame.remove(menu);
        switch(scene){
            case 1:
                Stage1 stage1 = new Stage1();
                frame.add(stage1);
                frame.pack();
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
}
