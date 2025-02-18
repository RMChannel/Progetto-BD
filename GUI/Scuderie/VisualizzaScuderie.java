package GUI.Scuderie;

import Database.DB;
import GUI.Piloti.MenuPiloti;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisualizzaScuderie extends JFrame {
    private JPanel panel;
    private JButton tornaIndietroButton;
    private JTable table1;

    public VisualizzaScuderie() {
        setContentPane(panel);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuScuderie();
            }
        });
    }

    private void createUIComponents() {
        String[] columnNames = {"Nome","Data Entrata","Città","Via","CAP","ID_Categoria"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        Connection conn= DB.getConn();
        try {
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM Scuderia");
            while (rs.next()) {
                String []row=new String[6];
                row[0]=rs.getString(1);
                row[1]=rs.getString(2);
                row[2]=rs.getString(3);
                row[3]=rs.getString(4);
                row[4]=rs.getString(5);
                row[5]=rs.getString(6);
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Connessione al database fallita","Errore connessione DB",JOptionPane.ERROR_MESSAGE);
            new MenuPiloti();
            return;
        }
        table1=new JTable(model);
        setTitle("Visualizza Scuderie");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
