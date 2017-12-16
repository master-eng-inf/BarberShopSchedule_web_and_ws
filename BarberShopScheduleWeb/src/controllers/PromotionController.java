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

import models.Promotion;

@RequestScoped
@Path("promotions")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class PromotionController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list")
	public List<Promotion> getPromotionList() {
		ArrayList<Promotion> promotion_list = new ArrayList<>();

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
					ResultSet rs = stm.executeQuery("SELECT * FROM promotion");

					while (rs.next()) {
						promotion_list.add(new Promotion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
								rs.getString(5), rs.getBoolean(6)));
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

		return promotion_list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/promotion/{id}")
	public Promotion getPromotion(@PathParam("id") int id) {
		
		Promotion promotion = null;

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
					ResultSet rs = stm.executeQuery("SELECT * FROM promotion WHERE id = " + id);

					rs.next();

					promotion = new Promotion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
							rs.getString(5), rs.getBoolean(6));

					connection.close();
					stm.close();
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko";
		}

		return promotion;
	}
}