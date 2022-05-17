import javax.swing.*;

public class Menu extends JPanel{
    public Menu(JFrame frame) {
        ImageIcon img = new ImageIcon("res/menu.png");
        JLabel pic = new JLabel(img);
        frame.add(pic);
    }
}
