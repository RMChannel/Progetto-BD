package GUI.Categoria;

import Database.DB;
import GUI.MenuProgramma;

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
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM CATEGORIA");
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
                if(JOptionPane.showConfirmDialog(null,"Sei sicuro di voler cancellare questa categoria?\nVerranno rimosse tutte le scuderie (con correlate affiliazioni) e gare collegate a questa categoria","Conferma rimozione scuderia",JOptionPane.YES_NO_OPTION)==0) {
                    try {
                        Connection conn= DB.getConn();
                        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM SCUDERIA WHERE ID_Categoria='"+comboBox1.getSelectedItem()+"'");
                        while(rs.next()){
                            conn.createStatement().executeUpdate("DELETE FROM AFFILIAZIONE WHERE Scuderia='"+rs.getString(1)+"'");
                        }
                        conn.createStatement().executeUpdate("DELETE FROM GARA WHERE ID_Categoria='"+comboBox1.getSelectedItem()+"'");
                        conn.createStatement().executeUpdate("DELETE FROM SCUDERIA where ID_Categoria='"+comboBox1.getSelectedItem()+"'");
                        conn.createStatement().executeUpdate("DELETE FROM CATEGORIA WHERE ID_Categoria='"+comboBox1.getSelectedItem()+"'");
                        JOptionPane.showMessageDialog(null,"La categoria è stata rimossa con successo","Categoria rimossa",JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new MenuProgramma();
                    } catch (SQLException e2) {
                        System.err.println(e2.getMessage());
                        JOptionPane.showMessageDialog(null, e2.getMessage(),"Error SQL Rimuovi Categoria",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
