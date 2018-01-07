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

import models.BarberShop;
import models.Client;
import models.Session;

@RequestScoped
@Path("sessions")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class SessionController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login/{username}/{password}")
	public Object Login(@PathParam("username") String username, @PathParam("password") String password) {

		Object objToReturn = null;
		ResultSet rs;
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
					
					rs = stm.executeQuery("SELECT * FROM barbershop WHERE name = '" + username + "' AND password = '"
							+ password + "'");

					if (rs.next()) {

						token = Session.generateToken();

						BarberShop barber_shop = new BarberShop(rs.getInt(1), rs.getString(2), rs.getString(3),
								rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
								rs.getString(9), rs.getInt(10));

						barber_shop.setToken(token);

						stm.executeUpdate("DELETE FROM session WHERE username = '" + username + "'");
						
						stm.executeUpdate("INSERT INTO session VALUES ( '" + username + "', '" + token + "' )");
						objToReturn = barber_shop;
					}

					else {

						rs = stm.executeQuery("SELECT * FROM client WHERE name = '" + username + "' AND password = '"
								+ password + "'");

						if (rs.next()) {

							token = Session.generateToken();

							Client client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
									rs.getString(5), rs.getInt(6), rs.getInt(7));

							client.setToken(token);

							stm.executeUpdate("DELETE FROM session WHERE username = '" + username + "'");
							
							stm.executeUpdate("INSERT INTO session VALUES ( '" + username + "', '" + token + "' )");
							objToReturn = client;
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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/checkUserSession/{username}/{token}")
	public Object CheckUserSession(@PathParam("username") String username, @PathParam("token") String token) {

		Object objToReturn = false;
		
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
					ResultSet rs = stm.executeQuery("SELECT * FROM session WHERE username = '" + username
							+ "' and session_token = '" + token + "'");

					if (rs.next()) {
						objToReturn = true;
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

		return objToReturn;
	}
	
	@POST
	@Path("/logout/{username}/{token}")
	public Response Logout(@PathParam("username") String username, @PathParam("token") String token) {

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
					ResultSet rs = stm.executeQuery("SELECT * FROM session WHERE username = '" + username
							+ "' and session_token = '" + token + "'");

					if (rs.next()) {
						stm.executeUpdate("DELETE FROM session WHERE username = '" + username + "'");
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
