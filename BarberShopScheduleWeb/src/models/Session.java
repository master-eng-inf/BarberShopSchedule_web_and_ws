package models;

import java.util.UUID;

public class Session {
	private String username;
	private String token;

	public Session(String username, String token) {
		this.username = username;
		this.token = token;
	}

	public Session() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
