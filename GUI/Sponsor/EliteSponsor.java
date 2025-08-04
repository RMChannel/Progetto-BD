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

public class EliteSponsor extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JTable table1;

    public EliteSponsor() {
        setTitle("Elite Sponsor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuSponsor();
            }
        });
    }

    private void createUIComponents() {
        String[] columns={"Nome Sponsor","Media Piloti Sponsorizzati"};
        DefaultTableModel tableModel=new DefaultTableModel(null,columns);
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("SELECT SP.NOME_SPONSOR,\n" +
                    "       COUNT(SPZ.Numero_Pilota) AS NUMERO_PILOTI_SPONSORIZZATI\n" +
                    "FROM SPONSORIZZAZIONE SPZ\n" +
                    "         JOIN SPONSOR SP ON SPZ.ID_Sponsor = SP.ID_SPONSOR\n" +
                    "GROUP BY SP.NOME_SPONSOR\n" +
                    "HAVING COUNT(SPZ.Numero_Pilota) > (\n" +
                    "    SELECT AVG(NUMERO_PILOTI)\n" +
                    "    FROM (\n" +
                    "             SELECT COUNT(SPZ2.Numero_Pilota) AS NUMERO_PILOTI\n" +
                    "             FROM SPONSORIZZAZIONE SPZ2\n" +
                    "             GROUP BY SPZ2.ID_Sponsor\n" +
                    "         ) AS MEDIA_SPONSORIZZAZIONI\n" +
                    ")\n" +
                    "ORDER BY NUMERO_PILOTI_SPONSORIZZATI DESC;");
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getString(1),rs.getString(2)});
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        table1=new JTable(tableModel);
    }
}
