package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("users")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class UserController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/isAvailable/{username}")
	public boolean isAvailable(@PathParam("username") String username) {

		boolean isAvailable = true;
		ResultSet rs;
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

					rs = stm.executeQuery("SELECT * FROM barbershop WHERE name = '" + username + "'");

					if (rs.next()) {
						isAvailable = false;
					}

					else {
						rs = stm.executeQuery("SELECT * FROM client WHERE name = '" + username + "'");

						if (rs.next()) {
							isAvailable = false;
						}
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
		return isAvailable;
	}
}
