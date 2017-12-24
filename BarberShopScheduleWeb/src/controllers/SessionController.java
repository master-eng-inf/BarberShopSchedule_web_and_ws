package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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

import models.Session;

@RequestScoped
@Path("sessions")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class SessionController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login/{id}")
	public String Login(@PathParam("id") int id) {

		String token = "";

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
					ResultSet rs = stm.executeQuery("SELECT * FROM session WHERE id = " + id);

					if (rs.next()) {
						Session session = new Session(rs.getInt(1), rs.getString(2));
						token = session.getToken();
					}

					else {

						token = Session.generateToken();
						stm.executeUpdate("INSERT INTO session VALUES ( " + id + ", '" + token + "' )");
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

		return token;
	}
	
	@POST
	@Path("/logout/{id}/{token}")
	public Response Logout(@PathParam("id") int id, @PathParam("token") String token) {

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
					ResultSet rs = stm.executeQuery("SELECT * FROM session WHERE id = " + id + " and session_token = '" + token + "'");

					if (rs.next()) {
						stm.executeUpdate("DELETE FROM session WHERE id = " + id);
					}

					connection.close();
					stm.close();
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko due to " + e.getMessage();
		}

		return Response.status(201).entity(strEstat).build();
	}
}
