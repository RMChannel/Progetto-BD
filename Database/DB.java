package Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DB {
    private static Connection conn = null;

    public static Connection getConn() {
        return conn;
    }

    public static void setConnection()
    {
        String driver ="com.mysql.cj.jdbc.Driver";
        String username = "root";
        String password;
        try {
            File file = new File("password.txt");
            Scanner sc = new Scanner(file);
            password=sc.nextLine();
        } catch (FileNotFoundException f) {
            System.out.println("Password not found");
            password = "";
        }
        String url ="jdbc:mysql://localhost:32768/ProjectBD";

        try {
        	Class.forName(driver);
            conn = DriverManager.getConnection(url,username,password);

        }catch (ClassNotFoundException e) {
			throw new DriverNotFound();
		} catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
