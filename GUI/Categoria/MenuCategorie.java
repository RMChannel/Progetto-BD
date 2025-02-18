package GUI.Categoria;

import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuCategorie extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JButton creaUnaNuovaCategoriaButton;
    private JButton rimuoviUnaCategoriaButton;
    private JButton visualizzaLeCategorieDisponibiliButton;

    public MenuCategorie() {
        setTitle("Menu Categorie");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
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
        creaUnaNuovaCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreaCategoria();
            }
        });
        rimuoviUnaCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RimuoviCategoria();
            }
        });
        visualizzaLeCategorieDisponibiliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VisualizzaCategorie();
            }
        });
    }
}
