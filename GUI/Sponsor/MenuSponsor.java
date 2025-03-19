package GUI.Sponsor;

import GUI.MenuProgramma;
import GUI.Sponsor.Sponsorizzazione.CreaSponsorizzazione;
import GUI.Sponsor.Sponsorizzazione.RimuoviSponsorizzazione;
import GUI.Sponsor.Sponsorizzazione.VisualizzaSponsorizzazioni;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuSponsor extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JButton creaUnNuovoSponsorButton;
    private JButton visualizzaTuttiGliSponsorButton;
    private JButton rimuoviUnoSponsorButton;
    private JButton creaSponsorizzazioneButton;
    private JButton visualizzaSponsorizzazioniButton;
    private JButton rimuoviSponsorizzazioneButton;
    private JButton eliteSponsorButton;

    public MenuSponsor() {
        setTitle("Menu Sponsor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
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
        creaSponsorizzazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreaSponsorizzazione();
            }
        });
        visualizzaSponsorizzazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VisualizzaSponsorizzazioni();
            }
        });
        rimuoviSponsorizzazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RimuoviSponsorizzazione();
            }
        });
        eliteSponsorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EliteSponsor();
            }
        });
    }
}
