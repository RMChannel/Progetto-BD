package GUI.Sponsor.Sponsorizzazione;

import Database.DB;
import GUI.Sponsor.MenuSponsor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class VisualizzaSponsorizzazioni extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JTable table1;

    public VisualizzaSponsorizzazioni() {
        setContentPane(panel1);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuSponsor();
            }
        });
    }

    private void createUIComponents() {
        String[] columns = {"Numero Pilota", "Cognome", "ID Sponsor", "Nome Sponsor"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table1=new JTable(model);
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("SELECT sp.Numero_Pilota, sp.Cognome, sp.ID_Sponsor, s.Nome_Sponsor FROM SPONSORIZZAZIONE sp JOIN SPONSOR s ON sp.ID_Sponsor = s.ID_Sponsor");
            String[] row=null;
            while(rs.next()){
                row= new String[]{String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4)};
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Errore nel recupero dati dal database","Error SQL Sponsorizzazione",JOptionPane.ERROR_MESSAGE);
            dispose();
            new MenuSponsor();
            return;
        }
        setTitle("Visualizza Sponsorizzazioni");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
