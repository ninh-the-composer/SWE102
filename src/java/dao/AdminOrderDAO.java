package dao;

import entity.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.DBContext;

/**
 *
 * @author Ninh
 */
public class AdminOrderDAO {
	DBContext dbc = new DBContext();
	public int countOrders(){
		String sql = "select count(id) [r] from [Order]";
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
	public List<Order> getOrderedByUserId(int page, int productsAPage) {
		int start = (page-1)*productsAPage;
		List<Order> data = new ArrayList<>();
		String sql = "select o.id, o.customer_id, cast(o.order_date as date) [order_date], o.city, o.address, o.phone, o.email, o.note ,sum(od.unit_price*od.quantity) [value] \n" +
"			from [Order] o inner join [Order Details] od on o.id = od.order_id \n" +
"			group by o.id, o.customer_id, o.order_date, o.city, o.address, o.phone, o.email, o.note \n" +
"			order by O.id\n" +
"			offset ? rows\n" +
"			fetch next ? rows only ";
		try {
			Connection con = dbc.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, productsAPage);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int customerId = rs.getInt("customer_id");
				String city = rs.getString("city");
				String orderDate = rs.getString("order_date");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String note = rs.getString("note");
				double totalValue = rs.getDouble("value");
				Order o = new Order(id, customerId, orderDate, address, city, phone, email, note, totalValue);
				data.add(o);
			}
		} catch (Exception e) {
			System.out.println("Bug in CartDAO");
			e.printStackTrace();
		}
		return data;
	}
	
	public static void main(String[] args) {
		AdminOrderDAO dao = new AdminOrderDAO();
		System.out.println(dao.getOrderedByUserId(1, 8));
	}
}
