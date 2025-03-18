package GUI.Gare;

import Database.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LunghezzaMediaGare extends JFrame {
    private JButton tornaIndietroButton;
    private JPanel panel1;
    private JComboBox comboBox1;
    private JTable table1;
    private JCheckBox includiNazioniConUnaCheckBox;
    private final String[] columns = {"Nazione","Numero Gare","Lunghezza Media","Categoria"};

    public LunghezzaMediaGare() {
        setTitle("Lunghezza Media Gare");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        setVisible(true);
        comboBox1.addItem("Tutte");
        Connection conn= DB.getConn();
        try {
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM Categoria");
            while (rs.next()) comboBox1.addItem(rs.getString(1));
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGare();
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
        includiNazioniConUnaCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
    }

    private void updateTable() {
        ResultSet rs=null;
        try {
            Connection conn= DB.getConn();
            if(comboBox1.getSelectedIndex()==0) {
                if(includiNazioniConUnaCheckBox.isSelected()) {
                    rs=conn.createStatement().executeQuery("SELECT Gara.Nazione, Count(Gara.ID_Gara) as Gare, AVG(Gara.Lunghezza) as LunghezzaMedia, ID_Categoria as Categoria\n" +
                            "from Gara\n"+
                            "GROUP BY Nazione, ID_Categoria\n" +
                            "ORDER BY Gare");
                }
                else {
                    rs=conn.createStatement().executeQuery("SELECT Gara.Nazione, Count(Gara.ID_Gara) as Gare, AVG(Gara.Lunghezza) as LunghezzaMedia, ID_Categoria as Categoria\n" +
                            "from Gara\n"+
                            "GROUP BY Nazione, ID_Categoria\n" +
                            "HAVING COUNT(ID_Categoria)>=2\n"+
                            "ORDER BY Gare");
                }
            }
            else {
                if(includiNazioniConUnaCheckBox.isSelected()) {
                    rs=conn.createStatement().executeQuery("SELECT Gara.Nazione, Count(Gara.ID_Gara) as Gare, AVG(Gara.Lunghezza) as LunghezzaMedia, ID_Categoria as Categoria\n" +
                            "from Gara where ID_Categoria='"+comboBox1.getSelectedItem()+"'\n" +
                            "GROUP BY Nazione, ID_Categoria\n" +
                            "ORDER BY Gare");
                }
                else {
                    rs=conn.createStatement().executeQuery("SELECT Gara.Nazione, Count(Gara.ID_Gara) as Gare, AVG(Gara.Lunghezza) as LunghezzaMedia, ID_Categoria as Categoria\n" +
                            "from Gara where ID_Categoria='"+comboBox1.getSelectedItem()+"'\n" +
                            "GROUP BY Nazione, ID_Categoria\n" +
                            "HAVING COUNT(ID_Categoria)>=2\n"+
                            "ORDER BY Gare");
                }
            }
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columns);
            table1.setModel(model);
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
            }
        } catch (SQLException e4) {
            System.out.println(e4.getMessage());
        }
    }

    private void createUIComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table1=new JTable(model);
        try {
            Connection conn= DB.getConn();
            ResultSet rs=conn.createStatement().executeQuery("SELECT Gara.Nazione, Count(Gara.ID_Gara) as Gare, AVG(Gara.Lunghezza) as LunghezzaMedia, ID_Categoria as Categoria\n" +
                    "from Gara\n" +
                    "GROUP BY Nazione, ID_Categoria\n" +
                    "ORDER BY Gare");
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
            }
        } catch (SQLException e2) {
            System.out.println(e2.getMessage());
        }
    }
}
