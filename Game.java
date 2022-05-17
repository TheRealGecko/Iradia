import java.awt.*;
import javax.swing.*;

public class Game {
    public Game() {
        JFrame frame = new JFrame("Iradia");
        frame.setSize(1000, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new JPanel());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
