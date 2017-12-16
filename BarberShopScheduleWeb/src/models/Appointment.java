package models;

import java.util.Calendar;

public class Appointment {

	private int id;
	private int client_id;
	private int barber_shop_id;
	private int service_id;
	private int promotion_id;
	private Calendar date;

	public Appointment(int id, int client_id, int barber_shop_id, int service_id, int promotion_id, Calendar date) {
		this.id = id;
		this.client_id = client_id;
		this.barber_shop_id = barber_shop_id;
		this.service_id = service_id;
		this.promotion_id = promotion_id;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	public int getPromotion_id() {
		return promotion_id;
	}

	public void setPromotion_id(int promotion_id) {
		this.promotion_id = promotion_id;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

}
