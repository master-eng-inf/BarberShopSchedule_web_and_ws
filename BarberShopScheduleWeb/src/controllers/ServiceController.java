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

					service = new Service(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4),
							rs.getInt(5));

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
}