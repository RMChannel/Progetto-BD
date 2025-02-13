package GUI.Piloti;

import Database.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisualizzaPiloti extends JFrame {
    private JPanel panel;
    private JButton tornaIndietroButton;
    private JTable table1;
    private static boolean control=false;

    public VisualizzaPiloti() {
        setContentPane(panel);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuPiloti();
            }
        });
    }

    private void createUIComponents() {
        String[] columnNames = {"Numero Pilota","Cognome","N°Campionati vinti"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        Connection conn= DB.getConn();
        try {
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM Pilota");
            while (rs.next()) {
                String []row=new String[3];
                row[0]=Integer.toString(rs.getInt(1));
                row[1]=rs.getString(2);
                row[2]=Integer.toString(rs.getInt(3));
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Connessione al database fallita","Errore connessione DB",JOptionPane.ERROR_MESSAGE);
            new MenuPiloti();
            return;
        }
        table1=new JTable(model);
        setTitle("Visualizza Piloti");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
