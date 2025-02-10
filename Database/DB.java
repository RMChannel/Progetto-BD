package Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DB {
    public static Connection getConnection()
    {
        Connection con;
        String driver ="com.mysql.cj.jdbc.Driver";
        String username = "ProjectDB_silveraway";
        String password;
        try {
            File file = new File("password.txt");
            Scanner sc = new Scanner(file);
            password=sc.nextLine();
        } catch (FileNotFoundException f) {
            System.out.println("Password not found");
            password = "";
        }
        String url ="jdbc:mysql://ProjectDB_silveraway:"+password+"@xq5qx.h.filess.io:3307/ProjectDB_silveraway";

        try {
        	Class.forName(driver);
            con = DriverManager.getConnection(url,username,password);

        }catch (ClassNotFoundException e) {
			throw new DriverNotFound();
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }
}
