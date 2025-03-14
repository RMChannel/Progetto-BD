package GUI.Gare;

import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGare extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JButton creaUnaNuovaGaraButton;
    private JButton visualizzaTutteLeGareButton;
    private JButton rimuoviGaraButton;

    public MenuGare() {
        setTitle("Menu Gare");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200, 300);
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
        visualizzaTutteLeGareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VisualizzaGare();
            }
        });
        rimuoviGaraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RimuoviGara();
            }
        });
        creaUnaNuovaGaraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreaGara();
            }
        });
    }
}
