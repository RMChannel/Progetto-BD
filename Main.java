import Database.DB;
import GUI.GUI;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection conn = DB.getConnection();
        System.out.println(conn.getMetaData().getUserName());
        new GUI();
    }
}