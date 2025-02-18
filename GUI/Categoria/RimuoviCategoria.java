package GUI.Categoria;

import Database.DB;

import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RimuoviCategoria extends JFrame {
    private JPanel panel;
    private JButton tornaIndietroButton;
    private JComboBox comboBox1;
    private JButton rimuoviCategoriaButton;

    public RimuoviCategoria() {
        setTitle("Rimuovi Categoria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM Categoria");
            while(rs.next()){
                comboBox1.addItem(rs.getString(1));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error SQL Rimuovi Categoria",JOptionPane.ERROR_MESSAGE);
        }
        setContentPane(panel);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuCategorie();
            }
        });
        rimuoviCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null,"Sei sicuro di voler cancellare questa categoria?\nVerranno rimosse tutte le scuderie e gare collegate a questa categoria","Conferma rimozione scuderia",JOptionPane.YES_NO_OPTION)==0) {
                    System.out.println("Confermo");
                }
            }
        });
    }
}
