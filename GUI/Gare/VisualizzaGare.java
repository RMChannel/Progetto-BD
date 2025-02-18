package GUI.Gare;

import Database.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VisualizzaGare extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JTable table1;
    private JComboBox nazioneComboBox;
    private JComboBox categoriaComboBox;

    public VisualizzaGare() {
        setContentPane(panel1);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGare();
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

    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0);
        String[] row={"ID_Gara","Nome Circuito","Data","Nazione","Lunghezza","Numero Giri","ID_Categoria","Vincitore"};
        model.setColumnIdentifiers(row);
        table1.setModel(model);
        try {
            Connection conn=DB.getConn();
            ResultSet rs;
            if(nazioneComboBox.getSelectedIndex()==0 && categoriaComboBox.getSelectedIndex()==0) rs=conn.createStatement().executeQuery("select * from Gara order by Data");
            else if(!(nazioneComboBox.getSelectedIndex()==0) && categoriaComboBox.getSelectedIndex()==0) rs=conn.createStatement().executeQuery("select * from Gara where Nazione='"+nazioneComboBox.getSelectedItem()+"' order by Data");
            else if(nazioneComboBox.getSelectedIndex()==0 && !(categoriaComboBox.getSelectedIndex()==0)) rs=conn.createStatement().executeQuery("select * from Gara where ID_Categoria='"+categoriaComboBox.getSelectedItem()+"' order by Data");
            else rs=conn.createStatement().executeQuery("select * from Gara where ID_Categoria='"+categoriaComboBox.getSelectedItem()+"' AND Nazione='"+nazioneComboBox.getSelectedItem()+"' order by Data");
            while(rs.next()){
                String[] temp;
                if(rs.getString(7)==null) temp= new String[]{rs.getString(4), rs.getString(9), rs.getString(1), rs.getString(2), String.valueOf(rs.getFloat(3)), String.valueOf(rs.getInt(5)), rs.getString(8), "Non ancora svolta"};
                else temp= new String[]{rs.getString(4), rs.getString(9), rs.getString(1), rs.getString(2), String.valueOf(rs.getFloat(3)), String.valueOf(rs.getInt(5)), rs.getString(8), rs.getInt(6) + " " + rs.getString(7)};
                model.addRow(temp);
            }
            } catch (SQLException e2) {
            System.err.println(e2.getMessage());
                JOptionPane.showMessageDialog(null, "Errore nella connessione al database","Error SQL Gare",JOptionPane.ERROR_MESSAGE);
            }
    }

    private void createUIComponents() {
        String[] row={"ID_Gara","Nome Circuito","Data","Nazione","Lunghezza","Numero Giri","ID_Categoria","Vincitore"};
        DefaultTableModel model = new DefaultTableModel(row,0);
        table1=new JTable(model);
        nazioneComboBox=new JComboBox();
        categoriaComboBox=new JComboBox();
        nazioneComboBox.addItem("Tutte");
        categoriaComboBox.addItem("Tutte");
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("select * from Gara ORDER BY Data");
            ArrayList<String> nazioni=new ArrayList<>();
            while(rs.next()){
                String[] temp;
                if(rs.getString(7)==null) temp= new String[]{rs.getString(4), rs.getString(9), rs.getString(1), rs.getString(2), String.valueOf(rs.getFloat(3)), String.valueOf(rs.getInt(5)), rs.getString(8), "Non ancora svolta"};
                else temp= new String[]{rs.getString(4), rs.getString(9), rs.getString(1), rs.getString(2), String.valueOf(rs.getFloat(3)), String.valueOf(rs.getInt(5)), rs.getString(8), rs.getInt(6) + " " + rs.getString(7)};
                if(!nazioni.contains(rs.getString(2))) nazioni.add(rs.getString(2));
                model.addRow(temp);
            }
            nazioni.sort(String::compareTo);
            for(String name:nazioni){
                nazioneComboBox.addItem(name);
            }
            rs=conn.createStatement().executeQuery("select * from Categoria");
            while(rs.next()){
                categoriaComboBox.addItem(rs.getString(1));
            }
        } catch(SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Errore nella connessione al Database","Error Connection Gare",JOptionPane.ERROR_MESSAGE);
            dispose();
            new MenuGare();
            return;
        }
        setTitle("Visualizzazione Gare");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
