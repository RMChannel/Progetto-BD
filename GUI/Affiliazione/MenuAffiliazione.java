package GUI.Affiliazione;

import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAffiliazione extends JFrame {
    private JPanel panel;
    private JButton tornaIndietroButton;
    private JButton creaAffiliazioneButton;
    private JButton rimuoviAffiliazioneButton;
    private JButton visualizzaLeAffiliazioniButton;

    public MenuAffiliazione() {
        setTitle("Affiliazione");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setContentPane(panel);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuProgramma();
            }
        });
        creaAffiliazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreaAffiliazione();
            }
        });
        visualizzaLeAffiliazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VisualizzaAffiliazioni();
            }
        });
        rimuoviAffiliazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RimuoviAffiliazione();
            }
        });
    }
}
