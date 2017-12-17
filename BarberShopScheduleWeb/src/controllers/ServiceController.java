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
	@Path("/list")
	public List<Service> getServiceList() {
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
					ResultSet rs = stm.executeQuery("SELECT * FROM service");

					while (rs.next()) {
						service_list.add(new Service(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4),
								rs.getInt(5)));
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
	@Path("/service/{id}")
	public Service getService(@PathParam("id") int id) {

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
					ResultSet rs = stm.executeQuery("SELECT * FROM service WHERE id = " + id);

					rs.next();

					service = new Service(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));

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
	@Path("/insertService")
	@Consumes("application/json")
	public Response insertService(Service service) {

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
					stm.executeUpdate("INSERT INTO service (id, barber_shop_id, name, price, duration) values " + "("
							+ service.getId() + ", " + service.getBarber_shop_id() + ",'" + service.getName() + "',"
							+ service.getPrice() + ", " + service.getDuration() + ")");

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
	@Path("/updateService")
	@Consumes("application/json")
	public Response updateService(Service service) {

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
					stm.executeUpdate("UPDATE service SET name = \'" + service.getName() + "\', price = "
							+ service.getPrice() + ", duration = " + service.getDuration() + ", barber_shop_id = "
							+ service.getBarber_shop_id() + " WHERE id = " + service.getId());

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
	@Path("/deleteService/{id}")
	public Response deleteService(@PathParam("id") int id) {

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
					stm.executeUpdate("DELETE FROM service WHERE id = " + id);

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