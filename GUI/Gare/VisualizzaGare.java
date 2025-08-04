package GUI.Gare;

import Database.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VisualizzaGare extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JTable table1;
    private JComboBox nazioneComboBox;
    private JComboBox categoriaComboBox;
    private JLabel lunghezzaMaxLabel;
    private JLabel lunghezzaMinLabel;
    private JTextField lunghezzaCircuitoTextField;
    private JTextField numeroGiriTextField;
    private JButton filtraButton;
    private float lunghezzaMax;
    private String circuitoMax;
    private float lunghezzaMin;
    private String circuitoMin;


    public VisualizzaGare() {
        setContentPane(panel1);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGare();
            }
        });
        nazioneComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
        categoriaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
        lunghezzaMaxLabel.setText("Lunghezza gara massima: " + lunghezzaMax + "km sul circuito: "+circuitoMax);
        lunghezzaMinLabel.setText("Lunghezza gara minima: "+lunghezzaMin + "km sul circuito: "+circuitoMin);
        filtraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(lunghezzaCircuitoTextField.getText().isEmpty())) {
                    try {
                        Float.parseFloat(lunghezzaCircuitoTextField.getText());
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(null, "Il parametro lunghezza circuito non è stato scritto correttamente, controlla e riprova","Errore Lunghezza Circuito", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                if(!(numeroGiriTextField.getText().isEmpty())) {
                    try {
                        Integer.parseInt(numeroGiriTextField.getText());
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(null, "Il parametro numero giri non è stato scritto correttamente, controlla e riprova","Errore Numero Giri", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                updateTable();
            }
        });
    }

    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0);
        String[] row={"ID_Gara","Nome Circuito","Data","Nazione","Lunghezza","Numero Giri","ID_Categoria","Vincitore"};
        model.setColumnIdentifiers(row);
        table1.setModel(model);
        int nGiri=9999;
        float lunghezzaCircuito=9999;
        if(!numeroGiriTextField.getText().isEmpty()) nGiri=Integer.parseInt(numeroGiriTextField.getText());
        if(!lunghezzaCircuitoTextField.getText().isEmpty()) lunghezzaCircuito=Float.parseFloat(lunghezzaCircuitoTextField.getText());
        try {
            Connection conn=DB.getConn();
            ResultSet rs = null;
            if(nazioneComboBox.getSelectedIndex()==0 && categoriaComboBox.getSelectedIndex()==0) {
                if(numeroGiriTextField.getText().isEmpty() && lunghezzaCircuitoTextField.getText().isEmpty()) rs=queryAuto("SELECT Nome_Circuito, Lunghezza*Numero_Giri as Totale FROM GARA WHERE lunghezza * Numero_Giri = (SELECT MAX(lunghezza * Numero_Giri) FROM GARA)","SELECT Nome_Circuito, Lunghezza*Numero_Giri as Totale FROM GARA WHERE lunghezza * Numero_Giri = (SELECT MIN(lunghezza * Numero_Giri) FROM GARA)","select * from GARA order by Data",conn);
                else {
                    rs = queryAuto(
                            "SELECT Nome_Circuito, Lunghezza * Numero_Giri AS Totale FROM GARA WHERE Lunghezza * Numero_Giri = (SELECT MAX(Lunghezza * Numero_Giri) FROM GARA WHERE Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + ") AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + ")",
                            "SELECT Nome_Circuito, Lunghezza * Numero_Giri AS Totale FROM GARA WHERE Lunghezza * Numero_Giri = (SELECT MIN(Lunghezza * Numero_Giri) FROM GARA WHERE Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + ") AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + ")",
                            "SELECT * FROM GARA WHERE Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + " ORDER BY Data",
                            conn
                    );
                }
            }
            else if(!(nazioneComboBox.getSelectedIndex()==0) && categoriaComboBox.getSelectedIndex()==0) {
                if(numeroGiriTextField.getText().isEmpty() && lunghezzaCircuitoTextField.getText().isEmpty()) rs=queryAuto("SELECT Nome_Circuito, lunghezza * Numero_Giri AS Totale FROM GARA WHERE Nazione = '" + nazioneComboBox.getSelectedItem() + "' AND (lunghezza * Numero_Giri) = (SELECT MAX(lunghezza * Numero_Giri) FROM GARA WHERE Nazione = '" + nazioneComboBox.getSelectedItem() + "')", "SELECT Nome_Circuito, lunghezza * Numero_Giri AS Totale FROM GARA WHERE Nazione = '" + nazioneComboBox.getSelectedItem() + "' AND (lunghezza * Numero_Giri) = (SELECT MIN(lunghezza * Numero_Giri) FROM GARA WHERE Nazione = '" + nazioneComboBox.getSelectedItem() + "')", "SELECT * FROM GARA WHERE Nazione = '" + nazioneComboBox.getSelectedItem() + "' ORDER BY Data", conn);
                else {
                    rs = queryAuto(
                            "SELECT Nome_Circuito, Lunghezza * Numero_Giri AS Totale FROM GARA WHERE Nazione = '" + nazioneComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + ") AND Lunghezza * Numero_Giri = (SELECT MAX(Lunghezza * Numero_Giri) FROM GARA WHERE Nazione = '" + nazioneComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + "))",
                            "SELECT Nome_Circuito, Lunghezza * Numero_Giri AS Totale FROM GARA WHERE Nazione = '" + nazioneComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + ") AND Lunghezza * Numero_Giri = (SELECT MIN(Lunghezza * Numero_Giri) FROM GARA WHERE Nazione = '" + nazioneComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + "))",
                            "SELECT * FROM GARA WHERE Nazione = '" + nazioneComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + ") ORDER BY Data",
                            conn
                    );
                }
            }
            else if(nazioneComboBox.getSelectedIndex()==0 && !(categoriaComboBox.getSelectedIndex()==0)) {
                if(numeroGiriTextField.getText().isEmpty() && lunghezzaCircuitoTextField.getText().isEmpty()) rs=queryAuto("SELECT Nome_Circuito, lunghezza * Numero_Giri AS Totale FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND (lunghezza * Numero_Giri) = (SELECT MAX(lunghezza * Numero_Giri) FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "')", "SELECT Nome_Circuito, lunghezza * Numero_Giri AS Totale FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND (lunghezza * Numero_Giri) = (SELECT MIN(lunghezza * Numero_Giri) FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "')", "SELECT * FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' ORDER BY Data", conn);
                else {
                    rs = queryAuto(
                            "SELECT Nome_Circuito, Lunghezza * Numero_Giri AS Totale FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + ") AND Lunghezza * Numero_Giri = (SELECT MAX(Lunghezza * Numero_Giri) FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + "))",
                            "SELECT Nome_Circuito, Lunghezza * Numero_Giri AS Totale FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + ") AND Lunghezza * Numero_Giri = (SELECT MIN(Lunghezza * Numero_Giri) FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + "))",
                            "SELECT * FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + ") ORDER BY Data",
                            conn
                    );
                }
            }
            else if(!(nazioneComboBox.getSelectedIndex()==0) && !(categoriaComboBox.getSelectedIndex()==0)) {
                if(numeroGiriTextField.getText().isEmpty() && lunghezzaCircuitoTextField.getText().isEmpty()) rs=queryAuto("SELECT Nome_Circuito, lunghezza * Numero_Giri AS Totale FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND Nazione = '" + nazioneComboBox.getSelectedItem() + "' AND (lunghezza * Numero_Giri) = (SELECT MAX(lunghezza * Numero_Giri) FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND Nazione = '" + nazioneComboBox.getSelectedItem() + "')", "SELECT Nome_Circuito, lunghezza * Numero_Giri AS Totale FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND Nazione = '" + nazioneComboBox.getSelectedItem() + "' AND (lunghezza * Numero_Giri) = (SELECT MIN(lunghezza * Numero_Giri) FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND Nazione = '" + nazioneComboBox.getSelectedItem() + "')", "SELECT * FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND Nazione = '" + nazioneComboBox.getSelectedItem() + "' ORDER BY Data", conn);
                else {
                    rs = queryAuto(
                            "SELECT Nome_Circuito, Lunghezza * Numero_Giri AS Totale FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND Nazione = '" + nazioneComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + ") AND Lunghezza * Numero_Giri = (SELECT MAX(Lunghezza * Numero_Giri) FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND Nazione = '" + nazioneComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + "))",
                            "SELECT Nome_Circuito, Lunghezza * Numero_Giri AS Totale FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND Nazione = '" + nazioneComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + ") AND Lunghezza * Numero_Giri = (SELECT MIN(Lunghezza * Numero_Giri) FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND Nazione = '" + nazioneComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + "))",
                            "SELECT * FROM GARA WHERE ID_Categoria = '" + categoriaComboBox.getSelectedItem() + "' AND Nazione = '" + nazioneComboBox.getSelectedItem() + "' AND (Lunghezza >= " + lunghezzaCircuito + " AND Numero_Giri >= " + nGiri + ") ORDER BY Data",
                            conn
                    );
                }
            }
            while(rs.next()){
                String[] temp;
                if(rs.getString(7)==null) temp= new String[]{rs.getString(4), rs.getString(9), rs.getString(1), rs.getString(2), String.valueOf(rs.getFloat(6)), String.valueOf(rs.getInt(5)), rs.getString(8), "Non ancora svolta"};
                else temp= new String[]{rs.getString(4), rs.getString(9), rs.getString(1), rs.getString(2), String.valueOf(rs.getFloat(6)), String.valueOf(rs.getInt(5)), rs.getString(8), rs.getInt(6) + " " + rs.getString(7)};
                model.addRow(temp);
            }
            lunghezzaMaxLabel.setText("Lunghezza gara massima: " + lunghezzaMax + "km sul circuito: "+circuitoMax);
            lunghezzaMinLabel.setText("Lunghezza gara minima: "+lunghezzaMin+"km sul circuito: "+circuitoMin);
            } catch (SQLException e2) {
                System.err.println(e2.getMessage());
                if(e2.getMessage().equals("Illegal operation on empty result set.")) JOptionPane.showMessageDialog(this, "La richiesta inviata ha avuto come risultato una tabella vuota.");
                else JOptionPane.showMessageDialog(null, "Errore nella connessione al database","Error SQL Gare",JOptionPane.ERROR_MESSAGE);
            }
    }

    private ResultSet queryAuto(String query1, String query2, String query3, Connection conn) throws SQLException {
        ResultSet rs=conn.createStatement().executeQuery(query1);
        rs.next();
        circuitoMax=rs.getString(1);
        lunghezzaMax=rs.getFloat(2);
        rs=conn.createStatement().executeQuery(query2);
        rs.next();
        circuitoMin=rs.getString(1);
        lunghezzaMin=rs.getFloat(2);
        rs=conn.createStatement().executeQuery(query3);
        return rs;
    }

    private void createUIComponents() {
        String[] row={"ID_Gara","Nome Circuito","Data","Nazione","Lunghezza","Numero Giri","ID_Categoria","Vincitore"};
        DefaultTableModel model = new DefaultTableModel(row,0);
        table1=new JTable(model);
        nazioneComboBox=new JComboBox();
        categoriaComboBox=new JComboBox();
        nazioneComboBox.addItem("Tutte");
        categoriaComboBox.addItem("Tutte");
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("SELECT Nome_Circuito, Lunghezza*Numero_Giri as Totale FROM GARA WHERE lunghezza * Numero_Giri = (SELECT MAX(lunghezza * Numero_Giri) FROM GARA)");
            rs.next();
            circuitoMax=rs.getString(1);
            lunghezzaMax =rs.getFloat(2);
            rs=conn.createStatement().executeQuery("SELECT Nome_Circuito, Lunghezza*Numero_Giri as Totale FROM GARA WHERE lunghezza * Numero_Giri = (SELECT MIN(lunghezza * Numero_Giri) FROM GARA)");
            rs.next();
            circuitoMin=rs.getString(1);
            lunghezzaMin =rs.getFloat(2);
            rs=conn.createStatement().executeQuery("select * from GARA ORDER BY Data");
            ArrayList<String> nazioni=new ArrayList<>();
            while(rs.next()){
                String[] temp;
                if(rs.getString(7)==null) temp= new String[]{rs.getString(1), rs.getString(3), rs.getString(4), rs.getString(2), String.valueOf(rs.getFloat(6)), String.valueOf(rs.getInt(5)), rs.getString(9), "Non ancora svolta"};
                else temp= new String[]{rs.getString(1), rs.getString(3), rs.getString(4), rs.getString(2), String.valueOf(rs.getFloat(6)), String.valueOf(rs.getInt(5)), rs.getString(9), rs.getInt(7) + " " + rs.getString(8)};
                if(!nazioni.contains(rs.getString(2))) nazioni.add(rs.getString(2));
                model.addRow(temp);
            }
            nazioni.sort(String::compareTo);
            for(String name:nazioni){
                nazioneComboBox.addItem(name);
            }
            rs=conn.createStatement().executeQuery("select * from CATEGORIA");
            while(rs.next()){
                categoriaComboBox.addItem(rs.getString(1));
            }
        } catch(SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Errore nella connessione al Database","Error Connection Gare",JOptionPane.ERROR_MESSAGE);
            dispose();
            new MenuGare();
            return;
        }
        setTitle("Visualizzazione Gare");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
