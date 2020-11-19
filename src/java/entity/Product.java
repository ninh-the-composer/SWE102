package entity;

/**
 *
 * @author Ninh
 */
public class Product {
	private String id;
	private String name;
	private Category category;
	private double price;
	private int view;
	private int orderLevel;
	private double discount;
	private String description;
	private String picture;

	public Product() {
	}

	public Product(String id, String name, Category category, double price, int view, int orderLevel, double discount, String description, String picture) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.view = view;
		this.orderLevel = orderLevel;
		this.discount = discount;
		this.description = description;
		this.picture = "assets/products/"+category+picture;
	}

	public int getView() {
		return view;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Category getCategory() {
		return category;
	}

	public double getPrice() {
		return price;
	}
	public double getPriceOut() {
		return price - price*discount/100;
	}

	
	public int getOrderLevel() {
		return orderLevel;
	}
	
	public double getDiscount() {
		return discount;
	}

	public String getPicture() {
		return picture;
	}

	public String getDescription() {
		return description;
	}
	
	public String getState(){
		return discount > 0 ? "sale" : "new";
	}
	
	public String getSummaryDescription(){
		if(description.length() < 100)
			return description;
		return description.substring(0, 100) + "...";
	}
	@Override
	public String toString() {
		return category + " " + name; 
	}
	
}
