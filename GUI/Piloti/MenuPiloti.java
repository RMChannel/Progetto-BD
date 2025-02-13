package GUI.Piloti;

import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPiloti extends JFrame {
    private JPanel panel;
    private JButton creaNuovoPilotaButton;
    private JButton visualizzaTuttiIPilotiButton;
    private JButton tornaIndietroButton;
    private JButton rimuoviUnPilotaButton;

    public MenuPiloti() {
        setTitle("Menu Piloti");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(200,200);
        setLocationRelativeTo(null);
        setVisible(true);
        creaNuovoPilotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreaPilota();
            }
        });
        visualizzaTuttiIPilotiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VisualizzaPiloti();
            }
        });
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuProgramma();
            }
        });
        rimuoviUnPilotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RimuoviPilota();
            }
        });
    }
}
