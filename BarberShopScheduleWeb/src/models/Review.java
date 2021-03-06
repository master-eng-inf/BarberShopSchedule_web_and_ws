package models;

import java.util.Date;

public class Review {

	private int client_id;
	private int barber_shop_id;
	private String description;
	private double mark;
	private Date date;

	public Review(int client_id, int barber_shop_id, String description, double mark, Date date) {
		this.client_id = client_id;
		this.barber_shop_id = barber_shop_id;
		this.description = description;
		this.mark = mark;
		this.date = date;
	}

	public Review()
	{
		
	}
	
	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	public int getBarber_shop_id() {
		return barber_shop_id;
	}

	public void setBarber_shop_id(int barber_shop_id) {
		this.barber_shop_id = barber_shop_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getMark() {
		return mark;
	}

	public void setMark(double mark) {
		this.mark = mark;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
