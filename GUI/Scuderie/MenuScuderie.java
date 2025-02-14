package GUI.Scuderie;

import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuScuderie extends JFrame {
    private JPanel panel;
    private JButton tornaIndietroButton;
    private JButton creaUnaScuderiaButton;
    private JButton rimuoviUnaScuderiaButton;
    private JButton visualizzaLeScuderieButton;
    private JButton creaAffiliazioneButton;

    public MenuScuderie() {
        setTitle("Menu Scuderie");
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
        visualizzaLeScuderieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VisualizzaScuderie();
            }
        });
        creaUnaScuderiaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreaScuderia();
            }
        });
        rimuoviUnaScuderiaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RimuoviScuderia();
            }
        });
        creaAffiliazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreaAffiliazione();
            }
        });
    }
}
