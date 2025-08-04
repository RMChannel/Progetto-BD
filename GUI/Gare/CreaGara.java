package GUI.Gare;

import Database.DB;
import GUI.MenuProgramma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Formatter;
import java.util.Objects;

public class CreaGara extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JTextField nazioneGaraTextField;
    private JTextField numeroGiriTextField;
    private JTextField lunghezzaGiroTextField;
    private JTextField nomeCircuitoTextField;
    private JComboBox categoriaComboBox;
    private JComboBox pilotaComboBox;
    private JButton creaGaraButton;
    private JTextField dataeoraTextField;

    public CreaGara() {
        setTitle("Crea Gara");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM CATEGORIA");
            while (rs.next()) {
                categoriaComboBox.addItem(rs.getString(1));
            }
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Errore nella connessione al database","Error SQL CreaGara",JOptionPane.ERROR_MESSAGE);
        }
        updatePiloti();
        setContentPane(panel1);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGare();
            }
        });
        creaGaraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nazioneGaraTextField.getText().isEmpty() || numeroGiriTextField.getText().isEmpty() || lunghezzaGiroTextField.getText().isEmpty() || nomeCircuitoTextField.getText().isEmpty() || dataeoraTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Uno dei parametri è vuoto, controlla e riprova","Errore parametro vuoto gara", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int nGiri=0;
                float lunghezzaGiro=0;
                try {
                    nGiri = Integer.parseInt(numeroGiriTextField.getText());
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null,"Il numero di giri non è stato scritto correttamente, controlla e riprova","Errore numero di giri", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    lunghezzaGiro=Float.parseFloat(lunghezzaGiroTextField.getText());
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null,"La lunghezza di un giro non è stata scritta correttamente, controlla e riprova, ricorda che devi utilizzare il . come virgola","Errore Lunghezza Giro",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                LocalDateTime date = null;
                try {
                    Date temp= (Date) formatter.parseObject(dataeoraTextField.getText());
                    date=temp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                } catch (ParseException pe) {
                    JOptionPane.showMessageDialog(null,"La data non è stata scritta correttamente, controlla e riprova, ricorda ce il formato è dd/MM/yyyy hh:mm","Errore formato data",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {String codice="";
                    Connection conn=DB.getConn();
                    ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM GARA where ID_Categoria='"+categoriaComboBox.getSelectedItem()+"'");
                    while (rs.next()) {
                        codice=rs.getString(1);
                    }
                    String[] parts=codice.split("-");
                    String newCodice=parts[0]+"-"+parts[1]+"-"+(Integer.parseInt(parts[2])+1);
                    if(pilotaComboBox.getSelectedItem().equals("Nessun vincitore")) {
                        conn.createStatement().executeUpdate("insert into GARA (Data, Nazione, Lunghezza, ID_Gara, Numero_Giri, Numero_Pilota_Vincitore, Cognome_Vincitore, ID_Categoria, Nome_Circuito) VALUES ('"+date+"', '"+nazioneGaraTextField.getText()+"', '"+lunghezzaGiro+"', '"+newCodice+"', '"+nGiri+"', NULL, NULL, '"+categoriaComboBox.getSelectedItem()+"', '"+nomeCircuitoTextField.getText()+"')");
                    }
                    else {
                        parts=pilotaComboBox.getSelectedItem().toString().split(" ");
                        conn.createStatement().executeUpdate("insert into GARA (Data, Nazione, Lunghezza, ID_Gara, Numero_Giri, Numero_Pilota_Vincitore, Cognome_Vincitore, ID_Categoria, Nome_Circuito) VALUES ('"+date+"', '"+nazioneGaraTextField.getText()+"', '"+lunghezzaGiro+"', '"+newCodice+"', '"+nGiri+"', '"+parts[0]+"', '"+parts[1]+"', '"+categoriaComboBox.getSelectedItem()+"', '"+nomeCircuitoTextField.getText()+"')");
                    }
                    JOptionPane.showMessageDialog(null,"Gara creata con successo","Gara creata",JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new MenuProgramma();
                } catch (SQLException e2) {
                    System.err.println(e2);
                    JOptionPane.showMessageDialog(null,"Errore con la connessione al database","Errore SQL CreaGara",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });
        categoriaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePiloti();
            }
        });
    }

    private void updatePiloti() {
        pilotaComboBox.removeAllItems();
        pilotaComboBox.addItem("Nessun vincitore");
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("SELECT \n" +
                    "    p.Numero_Pilota,\n" +
                    "    p.Cognome,\n" +
                    "    s.ID_Categoria\n" +
                    "FROM AFFILIAZIONE a\n" +
                    "JOIN SCUDERIA s ON a.Nome_Scuderia = s.Nome_Scuderia AND s.ID_Categoria='"+categoriaComboBox.getSelectedItem()+"'\n" +
                    "JOIN PILOTA p ON a.Numero_Pilota = p.Numero_Pilota AND a.Cognome=p.Cognome;\n");
            while (rs.next()) {
                pilotaComboBox.addItem(rs.getInt(1)+" "+rs.getString(2));
            }
        } catch (SQLException e2) {
            System.err.println(e2);
            JOptionPane.showMessageDialog(null, e2, "Errore nella connessione al database", JOptionPane.ERROR_MESSAGE);
        }
    }
}
