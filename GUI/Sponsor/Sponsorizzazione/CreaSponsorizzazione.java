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
import java.util.Hashtable;

public class CreaSponsorizzazione extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JComboBox pilotaComboBox;
    private JComboBox sponsorComboBox;
    private JButton creaSponsorizzazioneButton;

    public CreaSponsorizzazione() {
        setTitle("Crea Sponsorizzazione");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            Connection conn= DB.getConn();
            ResultSet rsPiloti=conn.createStatement().executeQuery("SELECT * FROM Pilota");
            ResultSet rsSponsorizzazioni=conn.createStatement().executeQuery("SELECT * FROM Sponsorizzazione");
            ResultSet rsSponsor=conn.createStatement().executeQuery("SELECT * FROM Sponsor");
            Dictionary<Integer,String> dic=new Hashtable<>();
            while(rsSponsorizzazioni.next()) {
                dic.put(rsSponsorizzazioni.getInt(1),rsSponsorizzazioni.getString(2));
            }
            while (rsPiloti.next()) {
                if(dic.get(rsPiloti.getInt(1))==null) pilotaComboBox.addItem(rsPiloti.getInt(1)+" "+rsPiloti.getString(2));
            }
            Dictionary<String,Integer> dic2=new Hashtable<>();
            rsSponsorizzazioni=conn.createStatement().executeQuery("SELECT * FROM Sponsorizzazione");
            while(rsSponsorizzazioni.next()) {
                dic2.put(rsSponsorizzazioni.getString(2),rsSponsorizzazioni.getInt(1));
            }
            while(rsSponsor.next()) {
                if(dic2.get(rsSponsor.getString(1))==null) sponsorComboBox.addItem(rsSponsor.getString(1)+" "+rsSponsor.getString(2));
            }
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Errore nel recupero dati dal database","Error SQL Sponsorizzazione",JOptionPane.ERROR_MESSAGE);
            dispose();
            new MenuSponsor();
            return;
        }
        setSize(500, 300);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuSponsor();
            }
        });
        creaSponsorizzazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn= DB.getConn();
                    ResultSet rsPiloti=conn.createStatement().executeQuery("SELECT * FROM Pilota");
                    ResultSet rsSponsor=conn.createStatement().executeQuery("SELECT * FROM Sponsor");
                    int nPilota=0;
                    String codiceSponsor="";
                    while(rsPiloti.next()) {
                        if((rsPiloti.getInt(1)+" "+rsPiloti.getString(2)).equals(pilotaComboBox.getSelectedItem().toString())) {
                            nPilota=rsPiloti.getInt(1);
                            break;
                        }
                    }
                    while(rsSponsor.next()) {
                        if((rsSponsor.getString(1)+" "+rsSponsor.getString(2)).equals(sponsorComboBox.getSelectedItem().toString())) {
                            codiceSponsor=rsSponsor.getString(1);
                        }
                    }
                    DB.getConn().createStatement().executeUpdate("INSERT INTO Sponsorizzazione (Pilota,Sponsor) VALUES ("+nPilota+",'"+codiceSponsor+"')");
                    JOptionPane.showMessageDialog(null,"Sponsorizzazione creata con successo","Sponsorizzazione avvenuta",JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new MenuProgramma();
                } catch (SQLException e2) {
                    System.err.println(e2);
                    JOptionPane.showMessageDialog(null,"Errore durante la connessione per la creazione della sponsorizzazione","Errore SQL Sponsorizzazione",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });
    }
}
