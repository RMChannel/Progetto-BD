package GUI.Gare;

import Database.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoScuderiaWhereGara extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JTable table1;

    public NoScuderiaWhereGara() {
        setTitle("Nazioni dove si svolge una gara ma non c'è una scuderia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        setSize(100, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGare();
            }
        });
    }

    private void createUIComponents() {
        try {
            String[] columns = {"Nazioni"};
            DefaultTableModel model = new DefaultTableModel(null,columns);
            Connection conn= DB.getConn();
            ResultSet rs = conn.createStatement().executeQuery("SELECT DISTINCT G.Nazione\n" +
                    "FROM Gara G\n" +
                    "WHERE G.Nazione NOT IN (\n" +
                    "    SELECT DISTINCT S.Nazione FROM Scuderia S\n" +
                    ")");
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString(1)});
            }
            table1=new JTable(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
