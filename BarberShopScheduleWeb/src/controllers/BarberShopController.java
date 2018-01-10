package controllers;

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

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.BarberShop;
import models.Client;

@RequestScoped
@Path("barberShops")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class BarberShopController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list/{token}")
	public List<BarberShop> getBarberShopList(@PathParam("token") String token) {
		ArrayList<BarberShop> barber_shop_list = new ArrayList<>();
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
						ResultSet rs = stm.executeQuery("SELECT * FROM barbershop");

						while (rs.next()) {
							barber_shop_list.add(new BarberShop(rs.getInt(1), rs.getString(2), rs.getString(3),
									rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
									rs.getString(9), rs.getInt(10)));
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
		return barber_shop_list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/web-list/")
	public List<BarberShop> getWebBarberShopList() {
		ArrayList<BarberShop> barber_shop_list = new ArrayList<>();
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

					ResultSet rs = stm.executeQuery("SELECT * FROM barbershop");

					while (rs.next()) {

						BarberShop barberShop = new BarberShop();

						barberShop.setId(rs.getInt(1));
						barberShop.setEmail(rs.getString(3));
						barberShop.setTelephone(rs.getString(4));
						barberShop.setName(rs.getString(5));
						barberShop.setAddress(rs.getString(6));
						barberShop.setCity(rs.getString(7));
						barberShop.setDescription(rs.getString(8));
						barberShop.setGender(rs.getInt(10));

						barber_shop_list.add(barberShop);
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
		return barber_shop_list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/barberShop/{id}/{token}")
	public BarberShop getBarberShop(@PathParam("id") int id, @PathParam("token") String token) {

		BarberShop barber_shop = null;
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
						ResultSet rs = stm.executeQuery("SELECT * FROM barbershop WHERE id = " + id);

						rs.next();

						barber_shop = new BarberShop(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
								rs.getInt(10));

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
		return barber_shop;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertBarberShop")
	@Consumes("application/json")
	public int insertBarberShop(BarberShop barberShop) {

		String strEstat = new String("ok");
		Connection connection = null;
		Statement stm = null;
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

					ResultSet rs = stm.executeQuery(
							"INSERT INTO barbershop (password, email, telephone, name, address, city, description, places_id, gender) values "
									+ "('" + barberShop.getPassword() + "', '" + barberShop.getEmail() + "', '"
									+ barberShop.getTelephone() + "', '" + barberShop.getName() + "'" + ", '"
									+ barberShop.getAddress() + "', '" + barberShop.getCity() + "', '"
									+ barberShop.getDescription() + "', '" + barberShop.getPlaces_id() + "', "
									+ barberShop.getGender() + ") RETURNING id");

					rs.next();

					last_inserted_id = rs.getInt(1);

					stm.executeUpdate(
							"INSERT INTO schedule (barber_shop_id, day_of_week, opening_1, closing_1, opening_2, closing_2, appointments_at_same_time) values "
									+ "(" + last_inserted_id + ", 0, '9:00', '13:00', '16:00', '20:00', 1)");
					stm.executeUpdate(
							"INSERT INTO schedule (barber_shop_id, day_of_week, opening_1, closing_1, opening_2, closing_2, appointments_at_same_time) values "
									+ "(" + last_inserted_id + ", 1, '9:00', '13:00', '16:00', '20:00', 1)");
					stm.executeUpdate(
							"INSERT INTO schedule (barber_shop_id, day_of_week, opening_1, closing_1, opening_2, closing_2, appointments_at_same_time) values "
									+ "(" + last_inserted_id + ", 2, '9:00', '13:00', '16:00', '20:00', 1)");
					stm.executeUpdate(
							"INSERT INTO schedule (barber_shop_id, day_of_week, opening_1, closing_1, opening_2, closing_2, appointments_at_same_time) values "
									+ "(" + last_inserted_id + ", 3, '9:00', '13:00', '16:00', '20:00', 1)");
					stm.executeUpdate(
							"INSERT INTO schedule (barber_shop_id, day_of_week, opening_1, closing_1, opening_2, closing_2, appointments_at_same_time) values "
									+ "(" + last_inserted_id + ", 4, '9:00', '13:00', '16:00', '20:00', 1)");
					stm.executeUpdate(
							"INSERT INTO schedule (barber_shop_id, day_of_week, opening_1, closing_1, opening_2, closing_2, appointments_at_same_time) values "
									+ "(" + last_inserted_id + ", 5, '9:00', '13:00', '16:00', '20:00', 1)");
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
	@Path("/updateBarberShop/{token}")
	@Consumes("application/json")
	public Response updateBarberShop(BarberShop barberShop, @PathParam("token") String token) {
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
						stm.executeUpdate("UPDATE barbershop SET password = \'" + barberShop.getPassword()
								+ "\', email = \'" + barberShop.getEmail() + "\', telephone = \'"
								+ barberShop.getTelephone() + "\', name = \'" + barberShop.getName() + "\', address = '"
								+ barberShop.getAddress() + "', city = '" + barberShop.getCity() + "', description = '"
								+ barberShop.getDescription() + "', places_id = '" + barberShop.getPlaces_id()
								+ "', gender = " + barberShop.getGender() + " WHERE id = " + barberShop.getId());
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
	@Path("/deleteBarberShop/{id}/{token}")
	public Response deleteBarberShop(@PathParam("id") int id, @PathParam("token") String token) {
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
						stm.executeUpdate("DELETE FROM barberShop WHERE id = " + id);
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
