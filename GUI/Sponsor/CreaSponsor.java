package GUI.Sponsor;

import Database.DB;
import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreaSponsor extends JFrame {
    private JPanel panel;
    private JButton tornaIndietroButton;
    private JTextField textField1;
    private JButton creaSponsorButton;

    public CreaSponsor() {
        setTitle("Crea Sponsor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setContentPane(panel);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuSponsor();
            }
        });
        creaSponsorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField1.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Parametro nome Sponsor vuoto, controlla e riprova","Errore parametro Sponsor",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    Connection conn= DB.getConn();
                    ResultSet rs=conn.createStatement().executeQuery("SELECT ID_Sponsor from Sponsor");
                    String value="";
                    while(rs.next()) {
                        value=rs.getString(1);
                    }
                    int codice=Integer.parseInt(value.substring(3))+1;
                    value="SPN"+codice;
                    conn.createStatement().executeUpdate("INSERT INTO Sponsor (ID_Sponsor,Nome_Sponsor) VALUES ('"+value+"','"+textField1.getText()+"')");
                    JOptionPane.showMessageDialog(null, "Sponsor creato con successo","Sponsor creato",JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new MenuProgramma();
                } catch (SQLException e2) {
                    System.err.println(e2);
                    JOptionPane.showMessageDialog(null, "Errore nel recupero dati dal database","Errore SQL for Sponsor",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });
    }
}
