package GUI.Affiliazione;

import Database.DB;
import GUI.MenuProgramma;
import GUI.Scuderie.MenuScuderie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreaAffiliazione extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JComboBox scuderiaComboBox;
    private JComboBox pilotaComboBox;
    private JButton creaAffiliazioneButton;
    private JTextField durataTextField;

    public CreaAffiliazione() {
        setTitle("Crea Affiliazione");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("select * from Scuderia");
            while(rs.next()) scuderiaComboBox.addItem(rs.getString(1));
            rs=conn.createStatement().executeQuery("select * from Pilota");
            while(rs.next()) pilotaComboBox.addItem(Integer.toString(rs.getInt(1))+" "+rs.getString(2));
        } catch (SQLException e) {
            System.err.println(e);
        }
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
        creaAffiliazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(durataTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Il parametro della durata è vuoto, controlla e riprova","Errore parametro durata vuoto",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int nPilota=0, durata=0;
                try {
                    durata = Integer.parseInt(durataTextField.getText());
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null,"Il parametro durata non è stato scritto correttamente, controlla e riprova","Errore lettura parametro durata",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    Connection conn= DB.getConn();
                    ResultSet rs=conn.createStatement().executeQuery("select * from Pilota");
                    while(rs.next()) {
                        nPilota=rs.getInt(1);
                        if((Integer.toString(nPilota)+" "+rs.getString(2)).equals(pilotaComboBox.getSelectedItem().toString())) {
                            break;
                        }
                    }
                    rs=conn.createStatement().executeQuery("select * from Affiliazione");
                    while(rs.next()) {
                        if(rs.getInt(1)==nPilota) {
                            JOptionPane.showMessageDialog(null,"Il pilota è già affliato con un'altra scuderia","Pilota già affiliato",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    DB.getConn().createStatement().executeUpdate("INSERT INTO Affiliazione (Pilota, Scuderia, Durata) VALUES ("+nPilota+",'"+scuderiaComboBox.getSelectedItem().toString()+"',"+durata+")");
                    JOptionPane.showMessageDialog(null,"Affiliazione creata correttamente","Affiliazione effettuata",JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new MenuProgramma();
                } catch (SQLException e2) {
                    System.err.println(e2);
                }
            }
        });
    }
}
