package GUI.Piloti;

import Database.DB;
import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CreaPilota extends JFrame {
    private JPanel panel;
    private JButton tornaIndietroButton;
    private JTextField numeroPilotaTextField;
    private JTextField cognomePilotaTextField;
    private JTextField nCampionatiVintiTextField;
    private JButton creaPilotaButton;

    public CreaPilota() {
        setTitle("Crea Pilota");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setContentPane(panel);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuPiloti();
            }
        });
        creaPilotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(numeroPilotaTextField.getText().isEmpty() || cognomePilotaTextField.getText().isEmpty() || nCampionatiVintiTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Uno dei parametri è vuoto, controlla e riprova", "Errore parametri vuoti", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int numeroPilota=0, nCampionatiVinti=0;
                try {
                    numeroPilota = Integer.parseInt(numeroPilotaTextField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,"Il numero di pilota non è stato scritto correttamente, controlla e riprova", "Errore numero pilota", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    nCampionatiVinti = Integer.parseInt(nCampionatiVintiTextField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,"Il numero di campionati vinti non è stato scritto correttamente, controlla e riprova", "Errore numero campionati", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    DB.getConn().createStatement().executeUpdate("INSERT INTO Pilota (Numero_pilota, Cognome, Numero_Campionati) VALUES ("+String.valueOf(numeroPilota)+",'"+cognomePilotaTextField.getText()+"',"+String.valueOf(nCampionatiVinti)+");");
                } catch (SQLException e2) {
                    System.err.println(e2);
                    JOptionPane.showMessageDialog(null,"Esiste già un pilota con i parametri: "+String.valueOf(numeroPilota)+" "+cognomePilotaTextField.getText(),"Errore pilota già esistente",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(null,"Il Pilota è stato aggiunto con successo","Aggiunta Pilota Avvenuta",JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new MenuProgramma();
            }
        });
    }
}
