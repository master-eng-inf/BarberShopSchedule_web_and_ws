package models;

import java.util.Date;

public class SpecialDay {

	private int barber_shop_id;
	private Date date;
	private int type;

	public SpecialDay(int barber_shop_id, Date date, int type) {
		this.barber_shop_id = barber_shop_id;
		this.date = date;
		this.type = type;
	}

	public SpecialDay()
	{
		
	}
	
	public int getBarber_shop_id() {
		return barber_shop_id;
	}

	public void setBarber_shop_id(int barber_shop_id) {
		this.barber_shop_id = barber_shop_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
