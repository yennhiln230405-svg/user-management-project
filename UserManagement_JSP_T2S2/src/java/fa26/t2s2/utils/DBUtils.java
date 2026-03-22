/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa26.t2s2.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;  
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author ASUS
 */
public class DBUtils {

  private static final String DB_NAME = "UserManagement";
  //private static final String DB_NAME = "ShoppingDB";

    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "12345";
// DriverManager
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME;
        conn = DriverManager.getConnection(url, USER_NAME, PASSWORD);
        return conn;

    }
// DataSource
    public static Connection getConnectionV1() throws NamingException, Exception {
        Context context = new InitialContext();
        Context env = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) env.lookup("DBCon");
        Connection conn = ds.getConnection();
        return conn;
    }

}


