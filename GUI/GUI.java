package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class GUI extends JFrame {
    private JPanel panel;
    private JButton menuQueryButton;
    private JButton menuProgrammaButton;

    public GUI() {
        setTitle("Formula 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(250, 200);
        setLocationRelativeTo(null);
        setContentPane(panel);
        setVisible(true);
        menuQueryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuQuery();
            }
        });
        menuProgrammaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuProgramma();
            }
        });
    }
}
