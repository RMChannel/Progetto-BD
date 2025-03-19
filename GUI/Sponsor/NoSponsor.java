package GUI.Sponsor;

import Database.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoSponsor extends JFrame {
    private JButton button1;
    private JPanel panel1;
    private JTable table1;

    public NoSponsor() {
        setTitle("Piloti senza Sponsor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuSponsor();
            }
        });
    }

    private void createUIComponents() {
        String[] columns={"Numero Pilota","Cognome Pilota"};
        DefaultTableModel model = new DefaultTableModel(null,columns);
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("SELECT P.NUMERO_PILOTA, P.COGNOME\n" +
                    "FROM Pilota P\n" +
                    "         LEFT JOIN Sponsorizzazione SP ON P.NUMERO_PILOTA = SP.PILOTA\n" +
                    "WHERE SP.SPONSOR IS NULL;");
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString(1),rs.getString(2)});
            }
            table1=new JTable(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
