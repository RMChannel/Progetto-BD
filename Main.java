import Database.DB;
import javax.swing.*;
import java.sql.SQLException;
import GUI.MenuProgramma;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            DB.setConnection();
            JOptionPane.showMessageDialog(null, "Connessione riuscita","MySQL Success",JOptionPane.INFORMATION_MESSAGE);
           } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Errore nella connessione al server","MySQL Server Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        new MenuProgramma();
    }
}