package models;

public class BarberShop {

	private int id;
	private String password;
	private String email;
	private String telephone;
	private String name;
	private String address;
	private String city;
	private String description;
	private String places_id;
	private int gender;
	
	public BarberShop(int id, String password, String email, String telephone, String name, String address,
			String city, String description, String places_id, int gender) {
		this.id = id;
		this.password = password;
		this.email = email;
		this.telephone = telephone;
		this.name = name;
		this.address = address;
		this.city = city;
		this.description = description;
		this.places_id = places_id;
		this.gender = gender;
	}

	public BarberShop()
	{
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlaces_id() {
		return places_id;
	}

	public void setPlaces_id(String places_id) {
		this.places_id = places_id;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
}
