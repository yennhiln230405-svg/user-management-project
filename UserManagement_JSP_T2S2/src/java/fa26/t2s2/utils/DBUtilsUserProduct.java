/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa26.t2s2.utils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ASUS
 */
public class DBUtilsUserProduct {
    private static final String DB_NAME = "ShoppingDB";
    
    private static final String USER = "sa";
    private static final String PASS = "12345";
    private static final String URL = 
            "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME
            + ";encrypt=true;trustServerCertificate=true";
    public static Connection getConnection() throws Exception{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
