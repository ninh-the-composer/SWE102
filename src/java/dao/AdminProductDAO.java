/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.DBContext;

/**
 *
 * @author Administrator
 */
public class AdminProductDAO {

    public List<Product> getAll() {
        String sql = "SELECT * FROM Product";

        try (PreparedStatement ps = new DBContext().getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            List<Product> list = new ArrayList<>();

            while (rs.next()) {
                Product p = new Product();

                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setCategory(rs.getString("category_id"));
                p.setPrice(rs.getDouble("price"));
                p.setDescription(rs.getString("description"));

                list.add(p);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
    
     public Product getOne(int id) {
        String sql = "SELECT * FROM Product WHERE id = ?";

        try (PreparedStatement ps = new DBContext().getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();

                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setCategory(rs.getString("category_id"));
                p.setPrice(rs.getDouble("price"));
                p.setDescription(rs.getString("description"));

                return p;
            }
            

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public boolean add(Product p) {

        String sql = "INSERT INTO Product(name, category_id, price, description)"
                + " VALUES(?, ?, ?, ?)";

        int check = 0;

        try (PreparedStatement ps = new DBContext().getConnection().prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setInt(2, Integer.parseInt(p.getCategory()));
            ps.setDouble(3, p.getPrice());
            ps.setString(4, p.getDescription());

            check = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean update(Product p, int id) {
        String sql = "UPDATE Product SET name = ?, category_id = ?, price = ?, description = ? WHERE id = ?";

        int check = 0;

        try (PreparedStatement ps = new DBContext().getConnection().prepareStatement(sql)) {
            System.out.println(p.getCategory());
            ps.setString(1, p.getName());
            ps.setInt(2, Integer.parseInt(p.getCategory()));
            ps.setDouble(3, p.getPrice());
            ps.setString(4, p.getDescription());
            ps.setInt(5, id);

            check = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean delete(int id) {
        String sql = "DELETE from Product WHERE id = ?";

        int check = 0;
        try (PreparedStatement ps = new DBContext().getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);

            check = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

//    pagination part
    public List<Product> getProductPerPage(int pageIndex, int numberProduct) {

        String sql = "SELECT * FROM product"
                + " ORDER BY id"
                + " OFFSET ? ROWS FETCH NEXT ? ROW ONLY";

        try (PreparedStatement ps = new DBContext().getConnection().prepareStatement(sql)) {

            ps.setInt(1, pageIndex);
            ps.setInt(2, numberProduct);

            List<Product> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();

                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setCategory(rs.getString("category_id"));
                p.setPrice(rs.getDouble("price"));
                p.setDescription(rs.getString("description"));

                list.add(p);
            }
            return list;

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        return null;
    }

    public int countTotalProduct() {
        return getAll().size();
    }
    

    public static void main(String[] args) {
        AdminProductDAO adp = new AdminProductDAO();

        System.out.println(adp.getOne(3));
//        Product productAdded = new Product();
//        productAdded.setName("testAdd");
//        productAdded.setCategory("1");
//        productAdded.setPrice(500);
//
//        Product productUpdate = new Product();
//        productUpdate.setName("testUpdate");
//        productUpdate.setCategory("2");
//        productUpdate.setPrice(600);
//        productUpdate.setDescription("updated");

//        System.out.println(adp.add(productAdded));
//        System.out.println(adp.update(productUpdate, 53));
//        System.out.println(adp.delete(53));

//        List<Product> list = adp.getAll();
//        
//        for (Product p : list) {
//            System.out.println(p);
//        }
    }
}
