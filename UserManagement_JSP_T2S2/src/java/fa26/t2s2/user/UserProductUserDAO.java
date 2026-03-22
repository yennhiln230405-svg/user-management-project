/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa26.t2s2.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author ASUS
 */
public class UserProductUserDAO {
    public int getOrCreateUidByEmail(Connection conn, String email) throws Exception{
        int uid = -1;
        String sqlFind = "SELECT uid FROM Users WHERE email = ?";
        try(PreparedStatement ptm = conn.prepareStatement(sqlFind)){
            ptm.setString(1,email);
            try(ResultSet rs = ptm.executeQuery()){
                if(rs.next()){
                    return rs.getInt("uid");
                }
            }
        }
        String sqlInsert = "INSERT INTO Users(name, address, email, phone) VALUES (?, ?, ?, ?)";
        try(PreparedStatement ptm = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)){
            ptm.setString(1, "Unknow");
            ptm.setString(2, null);
            ptm.setString(3, email);
            ptm.setString(4, null);
            
            ptm.executeUpdate();
            try(ResultSet rs = ptm.getGeneratedKeys()){
                if(rs.next()){
                    uid = rs.getInt(1);
                }
            }
        }
        return uid;
    }
}
