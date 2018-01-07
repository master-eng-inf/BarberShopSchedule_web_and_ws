package models;

public class Schedule {

	private int barber_shop_id;
	private int day_of_week;
	private String opening_1;
	private String opening_2;
	private String closing_1;
	private String closing_2;
	private int appointments_at_same_time;

	public Schedule(int barber_shop_id, int day_of_week, String opening_1, String closing_1, String opening_2,
			String closing_2, int appointments_at_same_time) {
		this.barber_shop_id = barber_shop_id;
		this.day_of_week = day_of_week;
		this.opening_1 = opening_1;
		this.opening_2 = opening_2;
		this.closing_1 = closing_1;
		this.closing_2 = closing_2;
		this.appointments_at_same_time = appointments_at_same_time;
	}

	public Schedule()
	{
		
	}
	
	public int getBarber_shop_id() {
		return barber_shop_id;
	}

	public void setBarber_shop_id(int barber_shop_id) {
		this.barber_shop_id = barber_shop_id;
	}

	public int getDay_of_week() {
		return day_of_week;
	}

	public void setDay_of_week(int day_of_week) {
		this.day_of_week = day_of_week;
	}

	public String getOpening_1() {
		return opening_1;
	}

	public void setOpening_1(String opening_1) {
		this.opening_1 = opening_1;
	}

	public String getOpening_2() {
		return opening_2;
	}

	public void setOpening_2(String opening_2) {
		this.opening_2 = opening_2;
	}

	public String getClosing_1() {
		return closing_1;
	}

	public void setClosing_1(String closing_1) {
		this.closing_1 = closing_1;
	}

	public String getClosing_2() {
		return closing_2;
	}

	public void setClosing_2(String closing_2) {
		this.closing_2 = closing_2;
	}

	public int getAppointments_at_same_time() {
		return appointments_at_same_time;
	}

	public void setAppointments_at_same_time(int appointments_at_same_time) {
		this.appointments_at_same_time = appointments_at_same_time;
	}

}
