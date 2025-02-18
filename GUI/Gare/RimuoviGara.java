package GUI.Gare;

import Database.DB;
import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RimuoviGara extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JComboBox gareComboBox;
    private JButton rimuoviGaraButton;

    public RimuoviGara() {
        setTitle("Rimuovi Gara");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 180);
        setLocationRelativeTo(null);
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("select * from Gara");
            while(rs.next()){
                gareComboBox.addItem(rs.getString(4));
            }
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Errore nella connessione al database","Erro SQL RimuoviGara",JOptionPane.ERROR_MESSAGE);
            dispose();
            new MenuGare();
        }
        setContentPane(panel1);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGare();
            }
        });
        rimuoviGaraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DB.getConn().createStatement().executeUpdate("delete from Gara where ID_Gara='"+gareComboBox.getSelectedItem()+"'");
                    JOptionPane.showMessageDialog(null,"Rimozione Gara avvenuta con successo","Rimozione avvenuta",JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new MenuProgramma();
                } catch (SQLException e2) {
                    System.err.println(e2);
                    JOptionPane.showMessageDialog(null,"Errore nella rimozione della gara","Error SQL RimuoviGara",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
