package entity;

/**
 *
 * @author Ninh
 */
public class ProductInCart extends Product {
	private int quantity;

	public ProductInCart() {
	}

	public ProductInCart(int quantity, String id, String name, String category, double price, int view, int orderLevel, double discount, String description, String picture) {
		super(id, name, category, price, view, orderLevel, discount, description, picture);
		this.quantity = quantity;
	}


	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
}
