package models;

import java.util.UUID;

public class Session {
	private int id;
	private String token;
	
	public Session(int id, String token) {
		this.id = id;
		this.token = token;
	}
	
	public Session() {

	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public static String generateToken() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
