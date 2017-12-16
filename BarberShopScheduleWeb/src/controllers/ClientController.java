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

import models.Client;

@RequestScoped
@Path("clients")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class ClientController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list")
	public List<Client> getClientList() {
		ArrayList<Client> clients_list = new ArrayList<>();

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
					ResultSet rs = stm.executeQuery("SELECT * FROM client");

					while (rs.next()) {
						clients_list.add(new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getString(5), rs.getInt(6), rs.getInt(7)));
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

		return clients_list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/client/{id}")
	public Client getClient(@PathParam("id") int id) {
		
		Client client = null;

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
					ResultSet rs = stm.executeQuery("SELECT * FROM client WHERE id = " + id);

					rs.next();

					client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getInt(6), rs.getInt(7));

					connection.close();
					stm.close();
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko";
		}

		return client;
	}
}