package GUI.Piloti;

import Database.DB;
import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RimuoviPilota extends JFrame {
    private JPanel panel;
    private JButton tornaIndietroButton;
    private JComboBox comboBox1;
    private JButton rimuoviQuestoPilotaButton;

    public RimuoviPilota() {
        setTitle("Rimuovi Pilota");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("select * from Pilota");
            while(rs.next()) comboBox1.addItem(Integer.toString(rs.getInt(1))+" "+rs.getString(2));
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Non è stato possibile recuperare i piloti dal database, controlla e riprova","Errore MySQL recupero pilota",JOptionPane.ERROR_MESSAGE);
            return;
        }
        setContentPane(panel);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuPiloti();
            }
        });
        rimuoviQuestoPilotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn= DB.getConn();
                    ResultSet rs=conn.createStatement().executeQuery("select * from Pilota");
                    while(rs.next()) {
                        int nPilota=rs.getInt(1);
                        if((Integer.toString(nPilota)+" "+rs.getString(2)).equals(comboBox1.getSelectedItem().toString())) {
                            conn.createStatement().executeUpdate("delete from Affiliazione where Pilota="+nPilota);
                            conn.createStatement().executeUpdate("delete from Pilota where Numero_Pilota="+nPilota);
                            dispose();
                            JOptionPane.showMessageDialog(null,"Pilota rimosso con successo","Rimozione Avvenuta",JOptionPane.INFORMATION_MESSAGE);
                            new MenuProgramma();
                            break;
                        }
                    }
                } catch(SQLException e2) {
                    System.out.println(e2.getMessage());
                    JOptionPane.showMessageDialog(null, "Non è stato possibile recuperare i piloti dal database, controlla e riprova","Errore MySQL recupero pilota",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });
    }
}
