package GUI;

import Database.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Partecipazione extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JTable table1;
    private JComboBox pilotaComboBox;
    private final String[] columns={"ID_Gara","Nazione","Circuto"};

    public Partecipazione() {
        setTitle("Partecipazione");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuProgramma();
            }
        });
        pilotaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
    }

    private void createUIComponents() {
        pilotaComboBox=new JComboBox();
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("select * from Pilota");
            while(rs.next()){
                pilotaComboBox.addItem(rs.getString(1)+" "+rs.getString(2));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        table1=new JTable();
        updateTable();
    }

    private void updateTable() {
        DefaultTableModel model = new DefaultTableModel(null,columns);
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("select * from Pilota");
            rs.next();
            for(int i=0;i<pilotaComboBox.getSelectedIndex();i++) {
                rs.next();
            }
            ResultSet rs2=conn.createStatement().executeQuery("select * from Gara");
            Dictionary<String,String> dicNazione=new Hashtable<>();
            Dictionary<String,String> dicCircuito=new Hashtable<>();
            while(rs2.next()){
                dicNazione.put(rs2.getString(4),rs2.getString(2));
                dicCircuito.put(rs2.getString(4),rs2.getString(9));
            }
            rs=conn.createStatement().executeQuery("select * from Partecipazione where Numero_Pilota="+rs.getString(1)+" AND Cognome_Pilota='"+rs.getString(2).replace("'","\\'")+"'");
            while(rs.next()) {
                model.addRow(new Object[]{rs.getString(1),dicNazione.get(rs.getString(1)),dicCircuito.get(rs.getString(1))});
            }
            table1.setModel(model);
        } catch (SQLException e2) {
            System.err.println(e2);
        }
    }
}
