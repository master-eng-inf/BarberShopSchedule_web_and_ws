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
	@Path("/list")
	public List<BarberShop> getBarberShopList() {
		ArrayList<BarberShop> barber_shop_list = new ArrayList<>();

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
					ResultSet rs = stm.executeQuery("SELECT * FROM barbershop");

					while (rs.next()) {
						barber_shop_list.add(new BarberShop(rs.getInt(1), rs.getString(2), rs.getString(3),
								rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
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

		return barber_shop_list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/barberShop/{id}")
	public BarberShop getBarberShop(@PathParam("id") int id) {

		BarberShop barber_shop = null;

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
					ResultSet rs = stm.executeQuery("SELECT * FROM barbershop WHERE id = " + id);

					rs.next();

					barber_shop = new BarberShop(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getString(7));

					connection.close();
					stm.close();
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko";
		}

		return barber_shop;
	}

	@POST
	@Path("/insertBarberShop")
	@Consumes("application/json")
	public Response insertBarberShop(BarberShop barberShop) {

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
					stm.executeUpdate(
							"INSERT INTO barbershop (id, password, email, telephone, name, address, city) values " + "("
									+ barberShop.getId() + ", '" + barberShop.getPassword() + "', '"
									+ barberShop.getEmail() + "', '" + barberShop.getTelephone() + "', '"
									+ barberShop.getName() + "'" + ", '" + barberShop.getAddress() + "', '"
									+ barberShop.getCity() + "')");

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
	@Path("/updateBarberShop")
	@Consumes("application/json")
	public Response updateBarberShop(BarberShop barberShop) {

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
					stm.executeUpdate("UPDATE barbershop SET password = \'" + barberShop.getPassword()
							+ "\', email = \'" + barberShop.getEmail() + "\', telephone = \'"
							+ barberShop.getTelephone() + "\', name = \'" + barberShop.getName() + "\', address = '"
							+ barberShop.getAddress() + "', city = '" + barberShop.getCity() + "' WHERE id = "
							+ barberShop.getId());

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
	@Path("/deleteBarberShop/{id}")
	public Response deleteBarberShop(@PathParam("id") int id) {

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
					stm.executeUpdate("DELETE FROM barberShop WHERE id = " + id);

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
