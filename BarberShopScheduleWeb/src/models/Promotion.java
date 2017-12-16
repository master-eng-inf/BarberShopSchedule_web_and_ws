package models;

public class Promotion {

	private int id;
	private int barber_shop_id;
	private int service_id;
	private String name;
	private String description;
	private boolean is_promotional;
	
	public Promotion(int id, int barber_shop_id, int service_id, String name, String description,
			boolean is_promotional) {
		this.id = id;
		this.barber_shop_id = barber_shop_id;
		this.service_id = service_id;
		this.name = name;
		this.description = description;
		this.is_promotional = is_promotional;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBarber_shop_id() {
		return barber_shop_id;
	}
	public void setBarber_shop_id(int barber_shop_id) {
		this.barber_shop_id = barber_shop_id;
	}
	public int getService_id() {
		return service_id;
	}
	public void setService_id(int service_id) {
		this.service_id = service_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isIs_promotional() {
		return is_promotional;
	}
	public void setIs_promotional(boolean is_promotional) {
		this.is_promotional = is_promotional;
	}
	

}
