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

import models.SpecialDay;

@RequestScoped
@Path("specialDays")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class SpecialDayController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/barberShop/{id}/list")
	public List<SpecialDay> getSpecialDayList(@PathParam("id") int id) {
		ArrayList<SpecialDay> specialDay_list = new ArrayList<>();

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
					ResultSet rs = stm.executeQuery("SELECT * FROM specialday WHERE barber_shop_id = " + id);

					while (rs.next()) {
						specialDay_list.add(new SpecialDay(rs.getInt(1), rs.getDate(2), rs.getInt(3)));
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

		return specialDay_list;
	}
}