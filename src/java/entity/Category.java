package entity;

/**
 *
 * @author Ninh
 */
public class Category {
	private int id;
	private String name;

	public Category() {
	}

	public Category(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name; //To change body of generated methods, choose Tools | Templates.
	}
	
	public String getNameOut(){
		return name.isEmpty() ? "Loại Khác" : name;
	}
	
}
