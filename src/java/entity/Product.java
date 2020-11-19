package entity;

/**
 *
 * @author Ninh
 */
public class Product {

    private String id;
    private String name;
    private String category;
    private double price;
    private int view;
    private int orderLevel;
    private double discount;
    private String description;
    private String picture;

    public Product() {
    }

    public Product(String id, String name, String category, double price, int view, int orderLevel, double discount, String description, String picture) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.view = view;
        this.orderLevel = orderLevel;
        this.discount = discount;
        this.description = description;
        this.picture = "assets/products/" + category + picture;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setView(int view) {
        this.view = view;
    }

    public void setOrderLevel(int orderLevel) {
        this.orderLevel = orderLevel;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public double getPriceOut() {
        return price - price * discount / 100;
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

    public String getState() {
        return discount > 0 ? "sale" : "new";
    }

    public String getSummaryDescription() {
        if (description.length() < 100) {
            return description;
        }
        return description.substring(0, 100) + "...";
    }
//	@Override
//	public String toString() {
//		return category + " " + name; 
//	}

//        sua toString() phuc vu viec test DAO
    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", category=" + category + ", price=" + price + ", view=" + view + ", orderLevel=" + orderLevel + ", discount=" + discount + ", description=" + description + ", picture=" + picture + '}';
    }

}
