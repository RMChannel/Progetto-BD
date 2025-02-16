package GUI.Sponsor;

import Database.DB;
import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RimuoviSponsor extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JComboBox comboBox1;
    private JButton eliminaSponsorButton;

    public RimuoviSponsor() {
        setTitle("Rimuovi Sponsor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 200);
        setLocationRelativeTo(null);
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("select * from Sponsor");
            while(rs.next()){
                comboBox1.addItem(rs.getString(1)+" "+rs.getString(2));
            }
            if(comboBox1.getSelectedIndex()==0){
                JOptionPane.showMessageDialog(null,"Nessuno Sponsor disponibile","Errore Sponsor vuoto",JOptionPane.ERROR_MESSAGE);
                dispose();
                new MenuSponsor();
                return;
            }
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Errore nel recupero dati dal Database","Errore SQL Sponsor",JOptionPane.ERROR_MESSAGE);
            return;
        }
        setContentPane(panel1);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuSponsor();
            }
        });
        eliminaSponsorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn= DB.getConn();
                    ResultSet rs=conn.createStatement().executeQuery("select * from Sponsor");
                    while(rs.next()){
                        String codice=rs.getString(1);
                        if((codice+" "+rs.getString(2)).equals(comboBox1.getSelectedItem().toString())){
                            conn.createStatement().executeUpdate("delete from Sponsor where ID_Sponsor='"+codice+"'");
                            JOptionPane.showMessageDialog(null,"Sponsor eliminato con successo","Sponsor Eliminato",JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new MenuProgramma();
                            return;
                        }
                    }
                } catch (SQLException e1) {
                    System.err.println(e1);
                    JOptionPane.showMessageDialog(null, "Errore nel recupero dati dal Database","Errore SQL Sponsor",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });
    }
}
