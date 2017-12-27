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

import models.Service;

@RequestScoped
@Path("services")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class ServiceController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list/{token}")
	public List<Service> getServiceList(@PathParam("token") String token) {
		ArrayList<Service> service_list = new ArrayList<>();

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
						ResultSet rs = stm.executeQuery("SELECT * FROM service");

						while (rs.next()) {
							service_list.add(new Service(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4),
									rs.getInt(5)));
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

		return service_list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list/barberShop/{id}/{token}")
	public List<Service> getBarberShopServiceList(@PathParam("id") int id, @PathParam("token") String token) {
		ArrayList<Service> service_list = new ArrayList<>();

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
						ResultSet rs = stm.executeQuery("SELECT * FROM service WHERE barber_shop_id = " + id);

						while (rs.next()) {
							service_list.add(new Service(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4),
									rs.getInt(5)));
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

		return service_list;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/service/{id}/{token}")
	public Service getService(@PathParam("id") int id, @PathParam("token") String token) {

		Service service = null;

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
						ResultSet rs = stm.executeQuery("SELECT * FROM service WHERE id = " + id);

						rs.next();

						service = new Service(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4),
								rs.getInt(5));

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

		return service;
	}

	@POST
	@Path("/insertService/{token}")
	@Consumes("application/json")
	public Response insertService(Service service, @PathParam("token") String token) {

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
						stm.executeUpdate("INSERT INTO service (id, barber_shop_id, name, price, duration) values "
								+ "(" + service.getId() + ", " + service.getBarber_shop_id() + ",'" + service.getName()
								+ "'," + service.getPrice() + ", " + service.getDuration() + ")");
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
	@Path("/updateService/{token}")
	@Consumes("application/json")
	public Response updateService(Service service, @PathParam("token") String token) {

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
						stm.executeUpdate("UPDATE service SET name = \'" + service.getName() + "\', price = "
								+ service.getPrice() + ", duration = " + service.getDuration() + ", barber_shop_id = "
								+ service.getBarber_shop_id() + " WHERE id = " + service.getId());
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
	@Path("/deleteService/{id}/{token}")
	public Response deleteService(@PathParam("id") int id, @PathParam("token") String token) {

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
						stm.executeUpdate("DELETE FROM service WHERE id = " + id);
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