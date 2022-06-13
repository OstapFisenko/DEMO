package org.medmask.app.manager;

import org.medmask.app.Main;
import org.medmask.app.entity.ProductEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//Title, ProductType, Description, Image, Cost, RegistrationDate
//Title=?, ProductType=?, Description=?, Image=?, Cost=?, RegistrationDate=?
public class ProductEntityManager {
    public static void insert(ProductEntity product) throws SQLException {
        try(Connection c = Main.getConnection()) {
            String sql = "INSERT INTO product(Title, ProductType, Description, Image, Cost, RegisterDate) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getTitle());
            ps.setString(2, product.getProductType());
            ps.setString(3, product.getDesc());
            ps.setString(4, product.getImagePath());
            ps.setDouble(5, product.getCost());
            ps.setTimestamp(6, new Timestamp(product.getRegDate().getTime()));

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                product.setId(keys.getInt(1));
                return;
            }

            throw new SQLException("entity not added");
        }
    }

    public static List<ProductEntity> selectAll() throws SQLException {
        try(Connection c = Main.getConnection()) {
            String sql = "SELECT ALL * FROM product";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<ProductEntity> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(new ProductEntity(
                        resultSet.getInt("ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("ProductType"),
                        resultSet.getString("Description"),
                        resultSet.getString("Image"),
                        resultSet.getDouble("Cost"),
                        resultSet.getTimestamp("RegisterDate")
                ));
            }
            return list;
        }
    }

    public static void update(ProductEntity product) throws SQLException {
        try(Connection c = Main.getConnection()){
            String sql = "UPDATE product SET Title=?, ProductType=?, Description=?, Image=?, Cost=?, RegisterDate=? WHERE ID=?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, product.getTitle());
            ps.setString(2, product.getProductType());
            ps.setString(3, product.getDesc());
            ps.setString(4, product.getImagePath());
            ps.setDouble(5, product.getCost());
            ps.setTimestamp(6, new Timestamp(product.getRegDate().getTime()));
            ps.setInt(7, product.getId());

            ps.executeUpdate();
        }
    }

    public static void delete(ProductEntity product) throws SQLException {
        try(Connection c = Main.getConnection()) {
            String sql = "DELETE FROM product WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, product.getId());
            ps.executeUpdate();
        }
    }
}
