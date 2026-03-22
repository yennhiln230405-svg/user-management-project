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
public class OrderDAO {
    public int insertOrder(Connection conn, int uid, double total) throws Exception{
        int oid = -1;
        String sql = "INSERT INTO Orders(date, total, uid) VALUES(GETDATE(), ?, ?)";
        try (PreparedStatement ptm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ptm.setDouble(1, total);
            ptm.setInt(2, uid);
            ptm.executeUpdate();
            try(ResultSet rs = ptm.getGeneratedKeys()){
                if(rs.next()){
                    oid = rs.getInt(1);
                }
            }
        }
        return oid;
    }
    public void insertOrderDetail(Connection conn, int oid, int pid, double price,int quantity)throws Exception{
        String sql = "INSERT INTO OrderDetail(oid, pid, price, quantity) VALUES(?, ?, ?, ?)";
        try(PreparedStatement ptm = conn.prepareStatement(sql)){
            ptm.setInt(1, oid);
            ptm.setInt(2, pid);
            ptm.setDouble(3, price);
            ptm.setInt(4, quantity);
            ptm.executeUpdate();
        }
    }
    public void updateProductQuantity(Connection conn, int pid, int buyQty) throws Exception{
        String sql = "UPDATE Product SET quantity = quantity - ? WHERE pid = ?";
        try(PreparedStatement ptm = conn.prepareStatement(sql)){
            ptm.setInt(1, buyQty);
            ptm.setInt(2, pid);
            ptm.executeUpdate();
        }
    }
    public int getStockForUpdate(Connection conn, int pid) throws Exception{
        int stock = -1;
        String sql = "SELECT quantity FROM Product WITH (UPDLOCK, HOLDLOCK) WHERE pid = ?";
        try(PreparedStatement ptm = conn.prepareStatement(sql)){
            ptm.setInt(1, pid);
            try(ResultSet rs = ptm.executeQuery()){
                if(rs.next()){
                    stock = rs.getInt("quantity");
                }
            }
        }
        return stock;
    }
    public double getPriceForUpdate(Connection conn, int pid) throws Exception{
        double price = -1;
        String sql = "SELECT price FROM Product WITH (UPDLOCK, HOLDLOCK) WHERE pid = ?";
        try(PreparedStatement ptm = conn.prepareStatement(sql)){
            ptm.setInt(1, pid);
            try(ResultSet rs = ptm.executeQuery()){
                if(rs.next()){
                    price = rs.getDouble("price");
                }
            }
        }
        return price;
    }
            
}
