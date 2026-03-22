/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa26.t2s2.shopping;

import fa26.t2s2.utils.DBUtilsUserProduct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ProductDAO {

    public List<Product> getListProduct() throws Exception {
        List<Product> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtilsUserProduct.getConnection();
            String sql = "SELECT pid, name, price, quantity FROM Product";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
//                String id = rs.getString("id");
                String id = String.valueOf(rs.getInt("pid"));
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                list.add(new Product(id, name, price, quantity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
