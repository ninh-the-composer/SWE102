package model;

import entity.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Ninh
 */
public class ProductDAO {
	
	private final DBContext dbc = new DBContext();
	
	private String getQueryOrderBySortMode(String sortMode){
		switch (sortMode) {
			case "priceHigh":
				return " order by p.price desc ";
			case "priceLow":
				return " order by p.price asc ";
			case "popularity":
				return " order by p.[view] desc ";
			case "bestSelling":
				return " order by p.order_level desc ";
			default:
				return " order by b.name, p.name ";
		}
	}
	/**
	 * Count number of records by category id.
	 * @param categoryId filter by specific category id. otherwise leave it blank
	 * @return number of records
	 */
	public int getCountByCategory(String categoryId) {
		String sql = "select COUNT(p.id) [r] from Product p inner join Category b on p.category_id = b.id where b.id like '%"+categoryId+"%'";
		int r = 0;
		try {
			Connection con = dbc.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				r = rs.getInt("r");
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public int getCountSearchResult(String keyword) {
		String sql = "select count(*) as r\n"
				+ "from Product p inner join Category b on p.category_id = b.id \n"
				+ "where (b.name +' ' +p.name) like '%" + keyword + "%'";
		int r = 0;
		try {
			Connection con = dbc.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				r = rs.getInt("r");
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public Product getProductById(String id){
		Product product = null;
		String sql = "select p.*, b.name [category_name], b.id [category_id] from Product p inner join Category b on p.category_id = b.id where p.id = ? ";
		try {
			Connection con = dbc.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String productId = rs.getString("id");
				String name = rs.getString("name");
				String categoryId = rs.getString("category_id");
				String categoryName = rs.getString("category_name");
				Category category = new Category(categoryId, categoryName);
				double price = rs.getDouble("price");
				int orderLevel = rs.getInt("order_level");
				int view = rs.getInt("view");
				double discount = rs.getDouble("discount");
				String description = rs.getString("description");
				String picture = rs.getString("picture");
				product = new Product(id, name, category, price, orderLevel, view++, discount, description, picture);
				String updateSql = "update Product set [view] = ? where id = ?";
				ps = con.prepareStatement(updateSql);
				ps.setInt(1, view);
				ps.setString(2, productId);
				ps.executeUpdate();
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}
	
	/**
	 * Get arraylist of product for a page by start and end index.<br/>
	 * <strong>Filter by category:</strong>
	 * <ul>
	 *	<li><b>validated Category ID</li>
	 *	<li><b>Get all - leave it blank</li>
	 * </ul>
	 * <strong>Sort Mode:</strong>
	 * <ul>
	 *	<li><b>priceHigh</b> - price high -> low</li>
	 *	<li><b>priceLow</b> - price low -> high</li>
	 *	<li><b>popularity</b> - highest views</li>
	 *	<li><b>otherwise</b> -	leave it blank</li>
	 * </ul>
	 * @param page index of page(1,2,3...)
	 * @param productsAPage number of products will return 
	 * @param sortMode described above <strong>validated</strong>
	 * @param category filter by category  <strong>validated</strong>
	 * @return 
	 */
	public List<Product> getProducts(int page, int productsAPage, String categoryId, String sortMode) {
		int start = (page-1)*productsAPage;
		List<Product> list = new ArrayList<>();
		String sql = "select  p.*, b.name [category_name], b.id [category_id] from Product p inner join Category b on p.category_id = b.id  \n"
				+ "where b.id like '%" + categoryId + "%'\n"
				+ getQueryOrderBySortMode(sortMode) + "\n"
				+ "offset ? rows\n"
				+ "fetch next ? rows only";

		try {
			Connection con = dbc.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, productsAPage);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String categoryName = rs.getString("category_name");
				Category category = new Category(categoryId, categoryName);
				double price = rs.getDouble("price");
				int orderLevel = rs.getInt("order_level");
				int view = rs.getInt("view");
				double discount = rs.getDouble("discount");
				String description = rs.getString("description");
				String picture = rs.getString("picture");
				Product p = new Product(id, name, category, price, orderLevel, view, discount, description, picture);
				list.add(p);
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	

	public List<Product> searchByKeyword(String keyword, int page, int productsAPage, String sortMode){
		List<Product> list = new ArrayList<>();
		int start = (page-1)*productsAPage;
		String sql = "select  p.*, b.name [category_name], b.id [category_id] \n"
				+ "from Product p inner join Category b on p.category_id = b.id \n"
				+ "where (b.name +' ' +p.name) like '%"+keyword+"%' \n"
				+ getQueryOrderBySortMode(sortMode)+"\n"
				+ "offset ? rows\n"
				+ "fetch next ? rows only";
		try {
			Connection con = dbc.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, productsAPage);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String id = rs.getString("id");
				String name = rs.getString("name");
				String categoryId = rs.getString("category_id");
				String categoryName = rs.getString("category_name");
				Category category = new Category(categoryId, categoryName);
				double price = rs.getDouble("price");
				int orderLevel = rs.getInt("order_level");
				int view = rs.getInt("view");
				double discount = rs.getDouble("discount");
				String description = rs.getString("description");
				String picture = rs.getString("picture");
				Product p = new Product(id, name, category, price, orderLevel, view, discount, description, picture);
				list.add(p);
			}
		} catch (Exception e) {
		}
		return list;
	}
	
//	public boolean addProduct(Product p){
//		String sql = "insert into Product values('Test Drink', 3, 14000, 0, 'UONG NHU LOZ', '/drink14/pepsi_m.png', 0,0)";
//		int r = 0;
//		try {
//			Connection con = dbc.getConnection();
//			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setString(1, p.getName());
//			r = ps.executeUpdate();
//		} catch (Exception e) {
//			System.out.println("Bug in ProductDAO");
//			e.printStackTrace();
//		}
//		return r != 0;
//	}
	
	public static void main(String[] args) {
		ProductDAO dao = new ProductDAO();
		System.out.println(dao.searchByKeyword("s", 1, 9, ""));
	}

}
