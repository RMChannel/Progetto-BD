package GUI.Categoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualizzaCategorie extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JTable table1;

    public VisualizzaCategorie() {
        setTitle("Visualizza Categorie");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setContentPane(panel1);
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
