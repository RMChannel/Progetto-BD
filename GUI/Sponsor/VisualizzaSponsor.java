package GUI.Sponsor;

import Database.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class VisualizzaSponsor extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JTable table1;

    public VisualizzaSponsor() {
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
        String[] row={"ID Sponsor","Nome","Numero Pilota Sponsorizzato"};
        DefaultTableModel model = new DefaultTableModel(row,0);
        table1=new JTable(model);
        try {
            Connection conn= DB.getConn();
            Dictionary<String,Integer> dictionary=new Hashtable<>();
            ResultSet rs=conn.createStatement().executeQuery("select * from Sponsorizzazione");
            while(rs.next()){
                dictionary.put(rs.getString(2),rs.getInt(1));
            }
            rs=conn.createStatement().executeQuery("SELECT * FROM Sponsor");
            System.out.println(dictionary);
            while(rs.next()){
                String sponsor=rs.getString(1);
                String[] temp=null;
                if(dictionary.get(sponsor)==null) {
                    temp= new String[]{sponsor, rs.getString(2), "Nessun pilota trovato"};
                }
                else {
                    temp= new String[]{sponsor, rs.getString(2), String.valueOf(dictionary.get(sponsor))};
                }
                model.addRow(temp);
            }
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "C'è stato qualche errore durante la connessione al database","Errore SQL Sposnor",JOptionPane.ERROR_MESSAGE);
            dispose();
            new MenuSponsor();
        }
        setTitle("Visualizza Sponsor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
