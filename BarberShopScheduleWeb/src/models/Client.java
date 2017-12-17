package models;

public class Client {

	private int id;
	private String password;
	private String email;
	private String telephone;
	private String name;
	private int gender;
	private int age;

	public Client(int id, String password, String email, String telephone, String name, int gender, int age) {
		this.id = id;
		this.password = password;
		this.email = email;
		this.telephone = telephone;
		this.name = name;
		this.gender = gender;
		this.age = age;
	}

	public Client()
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

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
