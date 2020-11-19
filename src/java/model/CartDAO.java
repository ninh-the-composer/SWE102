package model;

import entity.Category;
import entity.Product;
import entity.ProductInCart;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ninh
 */
public class CartDAO {

	DBContext dbc = new DBContext();

	public List<ProductInCart> getProductsInCart(String customerId) {
		List<ProductInCart> data = new ArrayList<>();
		String sql = "select * , b.name [category_name], b.id [category_id] from Cart c inner join Product p on c.product_id = p.id inner join Category b on b.id = p.category_id where customer_id = ?";
		try {
			Connection con = dbc.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, customerId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString("product_id");
				String name = rs.getString("name");
				int categoryId = rs.getInt("category_id");
				String categoryName = rs.getString("category_name");
				Category category = new Category(categoryId, categoryName);
				double price = rs.getDouble("price");
				int orderLevel = rs.getInt("order_level");
				int view = rs.getInt("view");
				double discount = rs.getDouble("discount");
				String description = rs.getString("description");
				String picture = rs.getString("picture");
				int quantity = rs.getInt("quantity");
				ProductInCart p = new ProductInCart(quantity,id, name, category, price,  orderLevel, view, discount, description, picture);
				data.add(p);
			}
		} catch (Exception e) {
			System.out.println("Bug in CartDAO");
			e.printStackTrace();
		}
		return data;
	}

	public double getTotal(List<ProductInCart> data) {
		double total = 0;
		for (ProductInCart p : data) {
			total += p.getQuantity() * p.getPrice();
		}
		return total;
	}

	public boolean addToCart(String customerId, String productId,  int quantity) {
		String sql = "insert into Cart values(?,?,?)";
		int r = 0;
		try {
			Connection con = dbc.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, customerId);
			ps.setString(2, productId);
			ps.setInt(3, quantity);
			r = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Bug in CartDAO");
			e.printStackTrace();
		}
		return r != 0;
	}
	
	public boolean removeItem(String customerId, String productId) {
		String sql = "delete from Cart where customer_id = ? and product_id = ?";
		int r = 0;
		try {
			Connection con = dbc.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, customerId);
			ps.setString(2, productId);
			r = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Bug in CartDAO");
			e.printStackTrace();
		}
		return r != 0;
	}
	
	public boolean updateItem(String customerId, String productId, int quantity) {
		String sql = "update Cart set quantity = ? where customer_id = ? and product_id = ? ";
		int r = 0;
		try {
			Connection con = dbc.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, quantity);
			ps.setString(2, customerId);
			ps.setString(3, productId);

			r = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Bug in CartDAO");
			e.printStackTrace();
		}
		return r != 0;
	}
	
	public boolean hasExistInCart(String customerId, String productId) {
		String sql = "select * from Cart where customer_id = ? and product_id = ?";
		int r = 0;
		try {
			Connection con = dbc.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, customerId);
			ps.setString(2, productId);
			r = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Bug in CartDAO");
			e.printStackTrace();
		}
		return r != 0;
	}
	
	public ProductInCart getProductInCart(String customerId, String productId) {
		ProductInCart data = null;
		String sql = "select * , b.name [category_name], b.id [category_id] "
				+ "from Cart c inner join Product p on c.product_id = p.id "
				+ "inner join Category b on b.id = p.category_id "
				+ "where customer_id = ? and product_id = ? ";
		try {
			Connection con = dbc.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, customerId);
			ps.setString(2, productId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String id = rs.getString("product_id");
				String name = rs.getString("name");
				int categoryId = rs.getInt("category_id");
				String categoryName = rs.getString("category_name");
				Category category = new Category(categoryId, categoryName);
				double price = rs.getDouble("price");
				int orderLevel = rs.getInt("order_level");
				int view = rs.getInt("view");
				double discount = rs.getDouble("discount");
				String description = rs.getString("description");
				String picture = rs.getString("picture");
				int quantity = rs.getInt("quantity");
				data = new ProductInCart(quantity, id, name, category, price, orderLevel, view, discount, description, picture);
				
			}
		} catch (Exception e) {
			System.out.println("Bug in CartDAO");
			e.printStackTrace();
		}
		return data;
	}
	
	public boolean order(List<ProductInCart> productsInCart, String userId, String address, String city, String phone, String email, String note){
		String sqlOrder = "insert into [Order] values(?,?,?,?,?,?,?)";
		String sqlOrderDetail = "insert into [Order Details] values((select top 1 id from [Order] order by id desc),?,?,?)";
		int r = 0;
		try {
			Connection con = dbc.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlOrder);
			ps.setString(1, userId);
			ps.setString(2, Date.valueOf(java.time.LocalDate.now()).toString());
			ps.setString(3, address);
			ps.setString(4, city);
			ps.setString(5, phone);
			ps.setString(6, email);
			ps.setString(7, note);
			r = ps.executeUpdate();
			for(ProductInCart p : productsInCart){
				PreparedStatement psp = con.prepareStatement(sqlOrderDetail);
				psp.setString(1, p.getId());
				psp.setDouble(2, p.getPriceOut());
				psp.setInt(3, p.getQuantity());
				psp.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println("Bug in CartDAO");
			e.printStackTrace();
		}
		return r != 0;
	}
	

	public static void main(String[] args) {
		CartDAO dao = new CartDAO();
		
	}
}
