package GUI.Categoria;

import Database.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisualizzaCategorie extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JTable table1;

    public VisualizzaCategorie() {
        setTitle("Visualizza Categorie");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuCategorie();
            }
        });
    }

    private void createUIComponents() {
        String[] columns = {"Categoria", "Data inizio","Data partenza"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        table1=new JTable(tableModel);
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("select * from CATEGORIA");
            while(rs.next()){
                tableModel.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(2)});
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error SQL VisualizzaCategorie",JOptionPane.ERROR_MESSAGE);
        }
    }
}
