package GUI.Scuderie;

import Database.DB;
import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CreaScuderia extends JFrame {
    private JPanel panel;
    private JButton tornaIndietroButton;
    private JTextField nomeTextField;
    private JTextField dataTextField;
    private JTextField cittaTextField;
    private JTextField viaTextField;
    private JTextField CAPTextField;
    private JButton creaScuderiaButton;

    public CreaScuderia() {
        setTitle("Crea una Scuderia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuScuderie();
            }
        });
        creaScuderiaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nomeTextField.getText().isEmpty() || dataTextField.getText().isEmpty() || cittaTextField.getText().isEmpty() || viaTextField.getText().isEmpty() || CAPTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Uno dei parametri è vuoto, controlla e riprova", "Errore parametro vuoto", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                LocalDate date = null;
                try {
                    Date temp= (Date) formatter.parseObject(dataTextField.getText());
                    date=temp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                } catch (ParseException pe) {
                    System.err.println(pe);
                }
                try {
                    String dati ="('"+nomeTextField.getText()+"','"+date.getYear()+"-"+date.getMonthValue()+"-"+date.getDayOfMonth()+"','"+cittaTextField.getText()+"','"+viaTextField.getText()+"','"+CAPTextField.getText()+"')";
                    DB.getConn().createStatement().executeUpdate("INSERT INTO Scuderia (Nome, Data_entrata, Città, Via, CAP) VALUES "+dati);
                    JOptionPane.showMessageDialog(null,"Scuderia aggiunta con successo", "Scuderia aggiunta", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e2) {
                    System.err.println(e2);
                    JOptionPane.showMessageDialog(null,"Errore nell'aggiunta della scuderia, lato SQL", "Errore aggiunta Scuderia SQL", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
                new MenuProgramma();
            }
        });
    }
}
