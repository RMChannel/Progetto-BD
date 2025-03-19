package GUI.Scuderie;

import Database.DB;
import GUI.Piloti.MenuPiloti;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisualizzaScuderie extends JFrame {
    private JPanel panel;
    private JButton tornaIndietroButton;
    private JTable table1;
    private JComboBox nazioneComboBox;
    private JComboBox categoriaComboBox;
    private String[] columnNames = {"Nome","Data Entrata","Nazione","Città","Via","CAP","ID_Categoria"};

    public VisualizzaScuderie() {
        setContentPane(panel);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuScuderie();
            }
        });
        nazioneComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
        categoriaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
    }

    private void createUIComponents() {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        Connection conn= DB.getConn();
        nazioneComboBox=new JComboBox();
        categoriaComboBox=new JComboBox();
        nazioneComboBox.addItem("Tutte");
        categoriaComboBox.addItem("Tutte");
        try {
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM Scuderia");
            while (rs.next()) {
                String []row={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)};
                model.addRow(row);
            }
            table1=new JTable(model);
            rs=conn.createStatement().executeQuery("SELECT * FROM Categoria");
            while (rs.next()) categoriaComboBox.addItem(rs.getString(1));
            rs=conn.createStatement().executeQuery("SELECT DISTINCT Nazione FROM Scuderia ORDER BY Nazione ASC");
            while (rs.next()) nazioneComboBox.addItem(rs.getString(1));
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Connessione al database fallita","Errore connessione DB",JOptionPane.ERROR_MESSAGE);
            new MenuPiloti();
            return;
        }
        setTitle("Visualizza Scuderie");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateTable() {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        try {
            Connection conn= DB.getConn();
            ResultSet rs;
            if(nazioneComboBox.getSelectedIndex()==0 && categoriaComboBox.getSelectedIndex()==0) {
                rs=conn.createStatement().executeQuery("SELECT * FROM Scuderia");
            }
            else if(nazioneComboBox.getSelectedIndex()==0 && !(categoriaComboBox.getSelectedIndex()==0)) {
                rs=conn.createStatement().executeQuery("SELECT * FROM Scuderia where ID_Categoria='"+categoriaComboBox.getSelectedItem()+"'");
            }
            else if(!(nazioneComboBox.getSelectedIndex()==0) && categoriaComboBox.getSelectedIndex()==0) {
                rs=conn.createStatement().executeQuery("SELECT * FROM Scuderia where Nazione='"+nazioneComboBox.getSelectedItem()+"'");
            }
            else {
                rs=conn.createStatement().executeQuery("SELECT * FROM Scuderia where Nazione='"+nazioneComboBox.getSelectedItem()+"' AND ID_Categoria='"+categoriaComboBox.getSelectedItem()+"'");
            }
            while (rs.next()) {
                String []row={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)};
                model.addRow(row);
            }
            table1.setModel(model);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}