package GUI.Categoria;

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

public class CreaCategoria extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JTextField nomeCategoriaTextField;
    private JTextField dataInizioTextField;
    private JTextField ID_Categoria;
    private JButton creaCategoriaButton;

    public CreaCategoria() {
        setTitle("Crea categoria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        setVisible(true);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuCategorie();
            }
        });
        creaCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nomeCategoriaTextField.getText().isEmpty() || dataInizioTextField.getText().isEmpty() || ID_Categoria.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Uno dei parametri è vuoto, controlla e riprova","Errore parametro vuoto",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                LocalDate dataInizio = null;
                try {
                    Date temp= (Date) formatter.parseObject(dataInizioTextField.getText());
                    dataInizio=temp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                } catch (ParseException pe) {
                    JOptionPane.showMessageDialog(null,"La data di inizio non è stata scritto correttamente, controlla e riprova, ricorda ce il formato è dd/MM/yyyy","Errore formato data",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    DB.getConn().createStatement().executeUpdate("INSERT INTO CATEGORIA (Nome, Partenza, ID_Categoria) VALUES ('"+nomeCategoriaTextField.getText()+"','"+dataInizio.getYear()+"-"+dataInizio.getMonthValue()+"-"+dataInizio.getDayOfMonth()+"','"+ID_Categoria.getText()+"')");
                    JOptionPane.showMessageDialog(null,"Categoria creata con successo","Categoria creata",JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new MenuProgramma();
                } catch (SQLException e2) {
                    System.err.println(e2.getMessage());
                    JOptionPane.showMessageDialog(null,e2.getMessage(),"Error SQL CreaCategoria",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
