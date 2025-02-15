package GUI.Sponsor;

import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuSponsor extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JButton creaUnNuovoSponsorButton;
    private JButton visualizzaTuttiGliSponsorButton;
    private JButton rimuoviUnoSponsorButton;

    public MenuSponsor() {
        setTitle("Menu Sponsor");
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
        creaUnNuovoSponsorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreaSponsor();
            }
        });
        visualizzaTuttiGliSponsorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VisualizzaSponsor();
            }
        });
        rimuoviUnoSponsorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RimuoviSponsor();
            }
        });
    }
}
