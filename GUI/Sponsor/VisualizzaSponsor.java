package GUI.Sponsor;

import Database.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

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
        String[] row={"ID Sponsor","Nome","Piloti Sponsorizzati"};
        DefaultTableModel model = new DefaultTableModel(row,0);
        table1=new JTable(model);
        try {
            Connection conn= DB.getConn();
            HashMap<String, ArrayList<String>> dictionary = new HashMap<>();
            ResultSet rs=conn.createStatement().executeQuery("select * from Sponsorizzazione");
            while(rs.next()){
                String sponsor = rs.getString(2);
                dictionary.putIfAbsent(sponsor, new ArrayList<>());
                dictionary.get(sponsor).add(rs.getInt(1)+" "+rs.getString(3));
            }
            rs=conn.createStatement().executeQuery("SELECT * FROM Sponsor");
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
