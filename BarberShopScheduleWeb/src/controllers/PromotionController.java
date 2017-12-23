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

import models.Promotion;

@RequestScoped
@Path("promotions")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class PromotionController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list/{token}")
	public List<Promotion> getPromotionList(@PathParam("token") String token) {
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

					ResultSet session = stm.executeQuery("SELECT * FROM session WHERE session_token = '" + token + "'");

					if (session.next()) {
						ResultSet rs = stm.executeQuery("SELECT * FROM promotion");

						while (rs.next()) {
							promotion_list.add(new Promotion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
									rs.getString(5), rs.getBoolean(6)));
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

		return promotion_list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/promotion/{id}/{token}")
	public Promotion getPromotion(@PathParam("id") int id, @PathParam("token") String token) {

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

					ResultSet session = stm.executeQuery("SELECT * FROM session WHERE session_token = '" + token + "'");

					if (session.next()) {
						ResultSet rs = stm.executeQuery("SELECT * FROM promotion WHERE id = " + id);

						rs.next();

						promotion = new Promotion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
								rs.getString(5), rs.getBoolean(6));
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

		return promotion;
	}

	@POST
	@Path("/insertPromotion/{token}")
	@Consumes("application/json")
	public Response insertPromotion(Promotion promotion, @PathParam("token") String token) {

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
						stm.executeUpdate(
								"INSERT INTO promotion (id, barber_shop_id, service_id, name, description, is_promotional) values "
										+ "(" + promotion.getId() + ", " + promotion.getBarber_shop_id() + ", "
										+ promotion.getService_id() + ", '" + promotion.getName() + "', '"
										+ promotion.getDescription() + "', " + promotion.isIs_promotional() + ")");
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
	@Path("/updatePromotion/{token}")
	@Consumes("application/json")
	public Response updatePromotion(Promotion promotion, @PathParam("token") String token) {

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
						stm.executeUpdate(
								"UPDATE promotion SET name = \'" + promotion.getName() + "\', barber_shop_id = "
										+ promotion.getBarber_shop_id() + ", service_id = " + promotion.getService_id()
										+ ", description = '" + promotion.getDescription() + "', is_promotional = "
										+ promotion.isIs_promotional() + " WHERE id = " + promotion.getId());
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
	@Path("/deletePromotion/{id}/{token}")
	public Response deletePromotion(@PathParam("id") int id, @PathParam("token") String token) {

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
						stm.executeUpdate("DELETE FROM promotion WHERE id = " + id);
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