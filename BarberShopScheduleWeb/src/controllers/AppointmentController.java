package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import models.Appointment;

@RequestScoped
@Path("appointments")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class AppointmentController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list/{token}")
	public List<Appointment> getAppointmentList(@PathParam("token") String token) {
		ArrayList<Appointment> appointment_list = new ArrayList<>();

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
						ResultSet rs = stm.executeQuery("SELECT * FROM appointment");

						while (rs.next()) {
							appointment_list.add(new Appointment(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
									rs.getInt(5), rs.getString(6)));
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

		return appointment_list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list/barberShop/{id}/{token}")
	public List<Appointment> getBarberShopAppointmentList(@PathParam("id") int id, @PathParam("token") String token) {
		ArrayList<Appointment> appointment_list = new ArrayList<>();

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
						ResultSet rs = stm.executeQuery("SELECT * FROM appointment WHERE barber_shop_id = " + id);

						while (rs.next()) {
							appointment_list.add(new Appointment(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
									rs.getInt(5), rs.getString(6)));
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

		return appointment_list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list/client/{id}/{token}")
	public List<Appointment> getClientAppointmentList(@PathParam("id") int id, @PathParam("token") String token) {
		ArrayList<Appointment> appointment_list = new ArrayList<>();

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
						ResultSet rs = stm.executeQuery("SELECT * FROM appointment WHERE client_id = " + id);

						while (rs.next()) {
							appointment_list.add(new Appointment(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
									rs.getInt(5), rs.getString(6)));
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

		return appointment_list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/appointment/{id}/{token}")
	public Appointment getAppointment(@PathParam("id") int id, @PathParam("token") String token) {

		Appointment appointment = null;

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
						ResultSet rs = stm.executeQuery("SELECT * FROM appointment WHERE id = " + id);

						rs.next();

						appointment = new Appointment(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
								rs.getInt(5), rs.getString(6));
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

		return appointment;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertAppointment/{token}")
	@Consumes("application/json")
	public int insertAppointment(Appointment appointment, @PathParam("token") String token) {

		String strEstat = new String("ok");

		int last_inserted_id = -1;

		try {
			InitialContext cxt = new InitialContext();
			if (cxt != null) {
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgresXA");

				if (ds == null)
					strEstat = "Error al crear el datasource";
				else {

					Connection connection = ds.getConnection();
					Statement stm = connection.createStatement();

					String tmp = "Date = " + appointment.getDate();
					System.out.println(tmp);

					ResultSet session = stm.executeQuery("SELECT * FROM session WHERE session_token = '" + token + "'");

					if (session.next()) {
						ResultSet rs = stm.executeQuery(
								"INSERT INTO appointment (client_id, barber_shop_id, service_id, promotion_id, date) values "
										+ "(" + appointment.getClient_id() + ", " + appointment.getBarber_shop_id()
										+ ", " + appointment.getService_id() + ", " + appointment.getPromotion_id()
										+ ", '" + appointment.getDate() + "') RETURNING id");

						rs.next();

						last_inserted_id = rs.getInt(1);
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

		return last_inserted_id;
	}

	@POST
	@Path("/deleteAppointment/{id}/{token}")
	public Response deleteAppointment(@PathParam("id") int id, @PathParam("token") String token) {

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
						stm.executeUpdate("DELETE FROM appointment WHERE id = " + id);
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