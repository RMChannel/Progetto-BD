package GUI;

import GUI.Affiliazione.MenuAffiliazione;
import GUI.Piloti.MenuPiloti;
import GUI.Scuderie.MenuScuderie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuProgramma extends JFrame {
    private JPanel panel;
    private JButton pilotiButton;
    private JButton scuderieButton;
    private JButton sponsorButton;
    private JButton gareButton;
    private JButton tornaIndietroButton;
    private JButton affiliazioneButton;

    public MenuProgramma() {
        setTitle("Formula 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200, 400);
        setLocationRelativeTo(null);
        setContentPane(panel);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GUI();
            }
        });
        pilotiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuPiloti();
            }
        });
        scuderieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuScuderie();
            }
        });
        affiliazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuAffiliazione();
            }
        });
    }
}
