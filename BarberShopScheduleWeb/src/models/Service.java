package models;

public class Service {

	private int id;
	private int barber_shop_id;
	private String name;
	private Double price;
	private int duration;

	public Service(int id, int barber_shop_id, String name, Double price, int duration) {
		this.id = id;
		this.barber_shop_id = barber_shop_id;
		this.name = name;
		this.price = price;
		this.duration = duration;
	}

	public Service()
	{
		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
