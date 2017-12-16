package models;

import java.util.Calendar;

public class SpecialDay {

	private int barber_shop_id;
	private Calendar date;
	private int type;

	public SpecialDay(int barber_shop_id, Calendar date, int type) {
		this.barber_shop_id = barber_shop_id;
		this.date = date;
		this.type = type;
	}

	public int getBarber_shop_id() {
		return barber_shop_id;
	}

	public void setBarber_shop_id(int barber_shop_id) {
		this.barber_shop_id = barber_shop_id;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
