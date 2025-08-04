package GUI.Sponsor.Sponsorizzazione;

import Database.DB;
import GUI.MenuProgramma;
import GUI.Sponsor.MenuSponsor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RimuoviSponsorizzazione extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JComboBox comboBox1;
    private JButton rimuoviSponsorizzazioneButton;

    public RimuoviSponsorizzazione() {
        setTitle("Rimuovi Sponsorizzazione");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM SPONSOR");
            Dictionary<String,String> dic=new Hashtable<>();
            while(rs.next()){
                dic.put(rs.getString(1),rs.getString(2));
            }
            rs=conn.createStatement().executeQuery("SELECT * FROM SPONSORIZZAZIONE");
            while(rs.next()){
                comboBox1.addItem(rs.getString(1)+" "+rs.getInt(2)+" "+rs.getString(3));
            }
            if(comboBox1.getItemCount()==0) {
                JOptionPane.showMessageDialog(null, "Nessuna sponsorizzazione attiva","Errore Sponsorizzazione",JOptionPane.ERROR_MESSAGE);
                dispose();
                new MenuSponsor();
                return;
            }
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Errore nella connessione al database","Error SQL Sponsorizzazione",JOptionPane.ERROR_MESSAGE);
            dispose();
            new MenuSponsor();
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
        rimuoviSponsorizzazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] parts = comboBox1.getSelectedItem().toString().split(" ");
                String idSponsor = parts[0];
                int nPilota = Integer.parseInt(parts[1]);
                String cognome = parts[2];
                Connection conn=DB.getConn();
                try {
                    conn.createStatement().executeUpdate("delete from SPONSORIZZAZIONE where ID_Sponsor='"+idSponsor+"' AND Numero_Pilota="+nPilota+" AND Cognome='"+cognome+"'");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null,"Rimozione Sponsorizzazione avvenuta con successo","Rimozione avvenuta",JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new MenuProgramma();
            }
        });
    }
}
