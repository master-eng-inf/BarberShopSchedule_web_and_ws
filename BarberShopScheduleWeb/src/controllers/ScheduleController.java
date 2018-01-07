package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.Schedule;

@RequestScoped
@Path("schedules")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class ScheduleController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/barberShop/{id}/list/{token}")
	public List<Schedule> getBarberShopScheduleList(@PathParam("id") int id, @PathParam("token") String token) {
		ArrayList<Schedule> schedule_list = new ArrayList<>();

		String strEstat = new String("ok");

		try {
			InitialContext cxt = new InitialContext();
			if (cxt != null) {
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgresXA");

				if (ds == null)
					strEstat = "Error al crear el datasource";
				else {

					Connection connection = ds.getConnection();
					Statement stm = connection.createStatement();

					ResultSet session = stm.executeQuery("SELECT * FROM session WHERE session_token = '" + token + "'");

					if (session.next()) {
						ResultSet rs = stm.executeQuery("SELECT * FROM schedule WHERE barber_shop_id = " + id);

						while (rs.next()) {
							schedule_list.add(new Schedule(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
									rs.getString(5), rs.getString(6), rs.getInt(7)));
						}
					}

					connection.close();
					stm.close();
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko";
		}

		return schedule_list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/barberShop/{id}/dayOfWeek/{day_of_week}/{token}")
	public Object getBarberShopScheduleForSpecificDay(@PathParam("id") int id,
			@PathParam("day_of_week") int day_of_week, @PathParam("token") String token) {
		Object objToReturn = "{}";
			
		String strEstat = new String("ok");

		try {
			InitialContext cxt = new InitialContext();
			if (cxt != null) {
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgresXA");

				if (ds == null)
					strEstat = "Error al crear el datasource";
				else {

					Connection connection = ds.getConnection();
					Statement stm = connection.createStatement();

					ResultSet session = stm.executeQuery("SELECT * FROM session WHERE session_token = '" + token + "'");

					if (session.next()) {
						ResultSet rs = stm.executeQuery("SELECT * FROM schedule WHERE barber_shop_id = " + id
								+ " and day_of_week = " + day_of_week);

						if(rs.next())
						{
							objToReturn = new Schedule(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
									rs.getString(5), rs.getString(6), rs.getInt(7));
						}
					}

					connection.close();
					stm.close();
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko";
		}

		return objToReturn;
	}

	@POST
	@Path("/insertSchedule/{token}")
	@Consumes("application/json")
	public Response insertSchedule(Schedule schedule, @PathParam("token") String token) {

		String strEstat = new String("ok");

		try {
			InitialContext cxt = new InitialContext();
			if (cxt != null) {
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgresXA");

				if (ds == null)
					strEstat = "Error al crear el datasource";
				else {

					Connection connection = ds.getConnection();
					Statement stm = connection.createStatement();

					ResultSet session = stm.executeQuery("SELECT * FROM session WHERE session_token = '" + token + "'");

					if (session.next()) {
						stm.executeUpdate(
								"INSERT INTO schedule (barber_shop_id, day_of_week, opening_1, closing_1, opening_2, closing_2, appointments_at_same_time) values "
										+ "(" + schedule.getBarber_shop_id() + ", " + schedule.getDay_of_week() + ", '"
										+ schedule.getOpening_1() + "', '" + schedule.getClosing_1() + "', '"
										+ schedule.getOpening_2() + "', '" + schedule.getClosing_2() + "', "
										+ schedule.getAppointments_at_same_time() + ")");

					}

					connection.close();
					stm.close();
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko due to -> " + e.getMessage();
		}

		return Response.status(201).entity(strEstat).build();
	}

	@POST
	@Path("/updateSchedule/{token}")
	@Consumes("application/json")
	public Response updateSchedule(Schedule schedule, @PathParam("token") String token) {

		String strEstat = new String("ok");

		try {
			InitialContext cxt = new InitialContext();
			if (cxt != null) {
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgresXA");

				if (ds == null)
					strEstat = "Error al crear el datasource";
				else {

					Connection connection = ds.getConnection();
					Statement stm = connection.createStatement();

					ResultSet session = stm.executeQuery("SELECT * FROM session WHERE session_token = '" + token + "'");

					if (session.next()) {
						stm.executeUpdate("UPDATE schedule SET opening_1 = \'" + schedule.getOpening_1()
								+ "\', closing_1 = \'" + schedule.getClosing_1() + "', opening_2 = '"
								+ schedule.getOpening_2() + "', closing_2 = '" + schedule.getClosing_2()
								+ "', appointments_at_same_time = " + schedule.getAppointments_at_same_time()
								+ " WHERE barber_shop_id = " + schedule.getBarber_shop_id() + " and day_of_week = "
								+ schedule.getDay_of_week());
					}

					connection.close();
					stm.close();
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko due to -> " + e.getMessage();
		}

		return Response.status(201).entity(strEstat).build();
	}
}