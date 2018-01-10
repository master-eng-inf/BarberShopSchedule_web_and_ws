package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import models.Client;

@RequestScoped
@Path("clients")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class ClientController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list/{token}")
	public List<Client> getClientList(@PathParam("token") String token) {
		ArrayList<Client> clients_list = new ArrayList<>();
		Connection connection = null;
		Statement stm = null;
		String strEstat = new String("ok");

		try {
			InitialContext cxt = new InitialContext();
			if (cxt != null) {
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgresXA");

				if (ds == null)
					strEstat = "Error al crear el datasource";
				else {

					connection = ds.getConnection();
					stm = connection.createStatement();

					ResultSet session = stm.executeQuery("SELECT * FROM session WHERE session_token = '" + token + "'");

					if (session.next()) {
						ResultSet rs = stm.executeQuery("SELECT * FROM client");

						while (rs.next()) {
							clients_list.add(new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
									rs.getString(5), rs.getInt(6), rs.getInt(7)));
						}
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko";
		}
		finally {
			try {
				if (connection != null) {
					connection.close();
				}

				if (stm != null) {
					stm.close();
				}
			}

			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return clients_list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/client/{id}/{token}")
	public Client getClient(@PathParam("id") int id, @PathParam("token") String token) {

		Client client = null;
		Connection connection = null;
		Statement stm = null;
		String strEstat = new String("ok");

		try {
			InitialContext cxt = new InitialContext();
			if (cxt != null) {
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgresXA");

				if (ds == null)
					strEstat = "Error al crear el datasource";
				else {

					connection = ds.getConnection();
					stm = connection.createStatement();

					ResultSet session = stm.executeQuery("SELECT * FROM session WHERE session_token = '" + token + "'");

					if (session.next()) {
						ResultSet rs = stm.executeQuery("SELECT * FROM client WHERE id = " + id);

						rs.next();

						client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getString(5), rs.getInt(6), rs.getInt(7));

					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko";
		}
		finally {
			try {
				if (connection != null) {
					connection.close();
				}

				if (stm != null) {
					stm.close();
				}
			}

			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return client;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertClient")
	@Consumes("application/json")
	public int insertClient(Client client) {
		Connection connection = null;
		Statement stm = null;
		String strEstat = new String("ok");

		int last_inserted_id = -1;
		
		try {
			InitialContext cxt = new InitialContext();
			if (cxt != null) {
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgresXA");

				if (ds == null)
					strEstat = "Error al crear el datasource";
				else {

					connection = ds.getConnection();
					stm = connection.createStatement();

					ResultSet rs = stm.executeQuery("INSERT INTO client (password, email, telephone, name, gender, age) values "
							+ "('" + client.getPassword() + "','" + client.getEmail() + "','"
							+ client.getTelephone() + "', '" + client.getName() + "'" + "," + client.getGender() + ", "
							+ client.getAge() + ") RETURNING id");

					rs.next();
					
					last_inserted_id = rs.getInt(1);
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko due to -> " + e.getMessage();
		}
		finally {
			try {
				if (connection != null) {
					connection.close();
				}

				if (stm != null) {
					stm.close();
				}
			}

			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return last_inserted_id;
	}

	@POST
	@Path("/updateClient/{token}")
	@Consumes("application/json")
	public Response updateClient(Client client, @PathParam("token") String token) {

		String strEstat = new String("ok");
		Connection connection = null;
		Statement stm = null;
		
		try {
			InitialContext cxt = new InitialContext();
			if (cxt != null) {
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgresXA");

				if (ds == null)
					strEstat = "Error al crear el datasource";
				else {

					connection = ds.getConnection();
					stm = connection.createStatement();

					ResultSet session = stm.executeQuery("SELECT * FROM session WHERE session_token = '" + token + "'");

					if (session.next()) {
						stm.executeUpdate("UPDATE client SET password = \'" + client.getPassword() + "\', email = \'"
								+ client.getEmail() + "\', telephone = \'" + client.getTelephone() + "\', name = \'"
								+ client.getName() + "\', gender = " + client.getGender() + ", age = " + client.getAge()
								+ " WHERE id = " + client.getId());
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko due to -> " + e.getMessage();
		}
		finally {
			try {
				if (connection != null) {
					connection.close();
				}

				if (stm != null) {
					stm.close();
				}
			}

			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Response.status(201).entity(strEstat).build();
	}

	@POST
	@Path("/deleteClient/{id}/{token}")
	public Response deleteClient(@PathParam("id") int id, @PathParam("token") String token) {
		Connection connection = null;
		Statement stm = null;
		String strEstat = new String("ok");

		try {
			InitialContext cxt = new InitialContext();
			if (cxt != null) {
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgresXA");

				if (ds == null)
					strEstat = "Error al crear el datasource";
				else {

					connection = ds.getConnection();
					stm = connection.createStatement();

					ResultSet session = stm.executeQuery("SELECT * FROM session WHERE session_token = '" + token + "'");

					if (session.next()) {
						stm.executeUpdate("DELETE FROM client WHERE id = " + id);
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko due to -> " + e.getMessage();
		}
		finally {
			try {
				if (connection != null) {
					connection.close();
				}

				if (stm != null) {
					stm.close();
				}
			}

			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Response.status(201).entity(strEstat).build();
	}
}