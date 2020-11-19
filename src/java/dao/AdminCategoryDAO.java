/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Category;
import entity.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.DBContext;

/**
 *
 * @author Administrator
 */
public class AdminCategoryDAO {
    
    public List<Category> getAll(){
        String sql = "SELECT * FROM Category";

        try (PreparedStatement ps = new DBContext().getConnection().prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            List<Category> list = new ArrayList<>();

            while (rs.next()) {
                Category c = new Category();

                c.setId(rs.getString("id"));
                c.setName(rs.getString("name"));


                list.add(c);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
    
    public static void main(String[] args) {
        AdminCategoryDAO acd = new AdminCategoryDAO();
        
        List<Category> list = acd.getAll();
        
        for (Category c : list) {
            System.out.println(c);
        }
    }
}
