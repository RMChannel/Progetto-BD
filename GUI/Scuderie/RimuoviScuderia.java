package GUI.Scuderie;

import Database.DB;
import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RimuoviScuderia extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JComboBox comboBox1;
    private JButton rimuoviScuderiaButton;

    public RimuoviScuderia() {
        setTitle("Rimuovi Scuderia");
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("select * from Scuderia");
            while(rs.next()) comboBox1.addItem(rs.getString(1));
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Non è stato possibile recuperare le scuderie dal database, controlla e riprova","Errore MySQL recupero scuderia",JOptionPane.ERROR_MESSAGE);
            return;
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuScuderie();
            }
        });
        rimuoviScuderiaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn= DB.getConn();
                    conn.createStatement().executeUpdate("delete from Affiliazione where Scuderia='"+comboBox1.getSelectedItem()+"'");
                    conn.createStatement().executeUpdate("delete from Scuderia where Nome='"+comboBox1.getSelectedItem()+"'");
                    JOptionPane.showMessageDialog(null,"Scuderia rimossa con successo","Rimozione Scuderia avvenuta",JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e1) {
                    System.err.println(e1);
                    JOptionPane.showMessageDialog(null, e1.getMessage(),"Rimozione Scuderia fallita", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
                new MenuProgramma();
            }
        });
    }
}
