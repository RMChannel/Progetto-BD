package GUI.Categoria;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RimuoviCategoria extends JFrame {
    private JPanel panel;
    private JButton tornaIndietroButton;

    public RimuoviCategoria() {
        setTitle("Rimuovi Categoria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setContentPane(panel);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuCategorie();
            }
        });
    }
}
