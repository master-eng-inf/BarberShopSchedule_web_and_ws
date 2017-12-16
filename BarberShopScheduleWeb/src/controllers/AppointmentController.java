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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import models.Appointment;

@RequestScoped
@Path("appointments")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class AppointmentController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list")
	public List<Appointment> getAppointmentList() {
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
					ResultSet rs = stm.executeQuery("SELECT * FROM appointment");

					while (rs.next()) {
						appointment_list.add(new Appointment(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
								rs.getInt(5), rs.getDate(6)));
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
	@Path("/appointment/{id}")
	public Appointment getAppointment(@PathParam("id") int id) {
		
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
					ResultSet rs = stm.executeQuery("SELECT * FROM appointment WHERE id = " + id);

					rs.next();

					appointment = new Appointment(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
							rs.getInt(5), rs.getDate(6));

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
}