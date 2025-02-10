package GUI;

import javax.swing.*;

public class GUI extends JFrame {
    private JPanel panel;

    public GUI() {
        setTitle("Formula 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setContentPane(panel);
        setVisible(true);
    }
}
