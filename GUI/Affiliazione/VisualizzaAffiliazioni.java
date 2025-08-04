package GUI.Affiliazione;

import Database.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisualizzaAffiliazioni extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JTable table1;
    private JComboBox comboBox1;

    public VisualizzaAffiliazioni() {
        setContentPane(panel1);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuAffiliazione();
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                model.setRowCount(0);
                String[] columns = {"Numero Pilota","Cognome","Scuderia","Durata Affiliazione"};
                model.setColumnIdentifiers(columns);
                table1=new JTable(model);
                Connection conn= DB.getConn();
                if (comboBox1.getSelectedItem().equals("Tutti")) {
                    try {
                        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM AFFILIAZIONE");
                        while(rs.next()){
                            model.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)});
                        }
                    } catch (SQLException e3) {
                        System.err.println(e3);
                        JOptionPane.showMessageDialog(null, "Errore nel recupero dati per la visualizzazione","Errore SQL Affiliazione",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    try {
                        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM AFFILIAZIONE a where a.Nome_Scuderia='"+comboBox1.getSelectedItem().toString()+"'");
                        while(rs.next()){
                            model.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)});
                        }
                    } catch (SQLException e4) {
                        System.err.println(e4);
                        JOptionPane.showMessageDialog(null, "Errore nel recupero dati per la visualizzazione","Errore SQL Affiliazione",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void createUIComponents() {
        DefaultTableModel model = new DefaultTableModel();
        String[] columns = {"Numero Pilota","Cognome","Scuderia","Durata Affiliazione"};
        model.setColumnIdentifiers(columns);
        table1=new JTable(model);
        comboBox1=new JComboBox();
        comboBox1.addItem("Tutti");
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("SELECT s.Nome_Scuderia, COUNT(a.Nome_Scuderia) AS NumAffiliazioni FROM SCUDERIA s INNER JOIN AFFILIAZIONE a ON s.Nome_Scuderia = a.Nome_Scuderia GROUP BY s.Nome_Scuderia;");
            while(rs.next()){
                comboBox1.addItem(rs.getString(1));
            }
            rs=conn.createStatement().executeQuery("SELECT * FROM AFFILIAZIONE");
            while(rs.next()){
                model.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)});
            }
        } catch (SQLException e2) {
            System.err.println(e2);
            JOptionPane.showMessageDialog(null, "Errore nel recupero dati per la visualizzazione","Errore SQL Affiliazione",JOptionPane.ERROR_MESSAGE);
            dispose();
            new MenuAffiliazione();
            return;
        }
        setTitle("Visualizza Affiliazioni");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
    }
}
