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
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM Sponsor");
            Dictionary<String,String> dic=new Hashtable<>();
            while(rs.next()){
                dic.put(rs.getString(1),rs.getString(2));
            }
            rs=conn.createStatement().executeQuery("SELECT * FROM Sponsorizzazione");
            while(rs.next()){
                comboBox1.addItem(rs.getInt(1)+" "+rs.getString(2)+" "+dic.get(rs.getString(2)));
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
                try {
                    Connection conn= DB.getConn();
                    ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM Sponsorizzazione");
                    Pattern pattern = Pattern.compile("^\\d+");
                    while(rs.next()){
                        Matcher matcher = pattern.matcher(comboBox1.getSelectedItem().toString());
                        if(!matcher.find()) {
                            JOptionPane.showMessageDialog(null,"Errore recupero numero pilota","Errore N Pilota",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        int n=Integer.parseInt(matcher.group());
                        if(comboBox1.getSelectedItem().toString().contains(rs.getString(2))) {
                            conn.createStatement().executeUpdate("delete from Sponsorizzazione where Sponsor='"+rs.getString(2)+"' && Pilota="+n);
                            JOptionPane.showMessageDialog(null,"Rimozione Sponsorizzazione avvenuta con successo","Rimozione avvenuta",JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new MenuProgramma();
                            break;
                        }
                    }
                } catch (SQLException e2) {
                    System.err.println(e2);
                    JOptionPane.showMessageDialog(null, "Errore nella connessione col database durante la rimozione","Error SQL Sponsorizzazione",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });
    }
}
