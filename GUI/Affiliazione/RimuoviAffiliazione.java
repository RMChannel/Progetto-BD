package GUI.Affiliazione;

import Database.DB;
import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RimuoviAffiliazione extends JFrame {
    private JPanel panel;
    private JButton tornaIndietroButton;
    private JComboBox comboBox1;
    private JButton rimuoviAffiliazioneButton;

    public RimuoviAffiliazione() {
        setContentPane(panel);
        setTitle("Rimuovi Affiliazione");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("select * from Affiliazione");
            while(rs.next()){
                comboBox1.addItem(rs.getString(2)+" con Pilota: "+rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Errore nel recupero dei dati delle affiliazioni","Errore SQL Affiliazioni",JOptionPane.ERROR_MESSAGE);
            return;
        }
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuAffiliazione();
            }
        });
        rimuoviAffiliazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn= DB.getConn();
                    ResultSet rs=conn.createStatement().executeQuery("select * from Affiliazione");
                    int n=0;
                    while(rs.next()){
                        n=rs.getInt(1);
                        if((rs.getString(2)+" con Pilota: "+n).equals(comboBox1.getSelectedItem().toString())){
                            conn.createStatement().executeUpdate("delete from Affiliazione where Pilota="+n);
                            JOptionPane.showMessageDialog(null,"Affiliazione rimossa con successo","Affiliazione Rimossa",JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new MenuProgramma();
                        }
                    }
                } catch (SQLException e1) {
                    System.err.println(e1);
                    JOptionPane.showMessageDialog(null, "Errore nel recupero dei dati delle affiliazioni","Errore SQL Affiliazioni",JOptionPane.ERROR_MESSAGE);
                    dispose();
                    new MenuAffiliazione();
                }
            }
        });
    }
}
