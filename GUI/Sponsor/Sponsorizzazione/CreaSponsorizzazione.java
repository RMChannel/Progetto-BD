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
import java.util.ArrayList;
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
            ResultSet rsPiloti=conn.createStatement().executeQuery("SELECT * FROM PILOTA");
            while (rsPiloti.next()) {
                pilotaComboBox.addItem(rsPiloti.getInt(1)+" "+rsPiloti.getString(2));
            }
            updateSponsor();
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
                    ResultSet rsPiloti=conn.createStatement().executeQuery("SELECT * FROM PILOTA");
                    ResultSet rsSponsor=conn.createStatement().executeQuery("SELECT * FROM SPONSOR");
                    int nPilota=0;
                    String cognomePilota="";
                    String codiceSponsor="";
                    while(rsPiloti.next()) {
                        if((rsPiloti.getInt(1)+" "+rsPiloti.getString(2)).equals(pilotaComboBox.getSelectedItem().toString())) {
                            nPilota=rsPiloti.getInt(1);
                            cognomePilota=rsPiloti.getString(2);
                            break;
                        }
                    }
                    while(rsSponsor.next()) {
                        if((rsSponsor.getString(1)+" "+rsSponsor.getString(2)).equals(sponsorComboBox.getSelectedItem().toString())) {
                            codiceSponsor=rsSponsor.getString(1);
                        }
                    }
                    DB.getConn().createStatement().executeUpdate("INSERT INTO SPONSORIZZAZIONE (Numero_Pilota, ID_Sponsor, Cognome) VALUES ("+nPilota+",'"+codiceSponsor+"','"+cognomePilota+"')");
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
        sponsorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSponsor();
            }
        });
    }
    private void updateSponsor() {
        sponsorComboBox.removeAllItems();
        String actualPilota=pilotaComboBox.getSelectedItem().toString();
        String[] parts=actualPilota.split(" ");
        try {
            Connection conn= DB.getConn();
            ResultSet rsSponsor=conn.createStatement().executeQuery("SELECT * FROM SPONSOR ORDER BY ID_Sponsor");
            ResultSet rsSponsorizzazione=conn.createStatement().executeQuery("SELECT * FROM SPONSORIZZAZIONE where Numero_Pilota="+parts[0]+" AND Cognome='"+parts[1]+"'");
            ArrayList<String> sponsors=new ArrayList<>();
            while (rsSponsorizzazione.next()) {
                sponsors.add(rsSponsorizzazione.getString(2));
            }
            while (rsSponsor.next()) {
                if(!sponsors.contains(rsSponsor.getString(1))) {
                    sponsorComboBox.addItem(rsSponsor.getString(1)+" "+rsSponsor.getString(2));
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null,e.getMessage(),"Errore SQL Sponsorizzazione",JOptionPane.ERROR_MESSAGE);
        }
    }
}
