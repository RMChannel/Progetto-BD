package GUI.Gare;

import GUI.Affiliazione.MenuAffiliazione;
import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGare extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;

    public MenuGare() {
        setTitle("Menu Gare");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuProgramma();
            }
        });
    }
}
