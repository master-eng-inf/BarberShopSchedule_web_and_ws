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

import models.Client;
import models.Review;

@RequestScoped
@Path("reviews")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class ReviewController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/barberShop/{id}/list/{token}")
	public List<Review> getBarberShopReviewList(@PathParam("id") int id, @PathParam("token") String token) {
		ArrayList<Review> review_list = new ArrayList<>();

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
						ResultSet rs = stm.executeQuery("SELECT * FROM review WHERE barber_shop_id = " + id);

						while (rs.next()) {
							review_list.add(new Review(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4),
									rs.getDate(5)));
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

		return review_list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/barberShop/{barber_shop_id}/client/{client_id}/{token}")
	public Object getClientReviewForBarberShop(@PathParam("barber_shop_id") int barber_shop_id,
			@PathParam("client_id") int client_id, @PathParam("token") String token) {
		Object objToReturn = "{}";

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
						ResultSet rs = stm.executeQuery("SELECT * FROM review WHERE barber_shop_id = " + barber_shop_id
								+ " AND client_id = " + client_id);

						if (rs.next()) {
							objToReturn = new Review(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4),
									rs.getDate(5));
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

		return objToReturn;
	}

	@POST
	@Path("/insertReview/{token}")
	@Consumes("application/json")
	public Response insertReview(Review review, @PathParam("token") String token) {

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
								"INSERT INTO review (client_id, barber_shop_id, description, mark, date) values " + "("
										+ review.getClient_id() + ", " + review.getBarber_shop_id() + ",'"
										+ review.getDescription() + "'," + review.getMark() + ", '" + review.getDate()
										+ "')");
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
	@Path("/updateReview/{token}")
	@Consumes("application/json")
	public Response updateReview(Review review, @PathParam("token") String token) {

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
						stm.executeUpdate("UPDATE review SET description = \'" + review.getDescription() + "\', mark = "
								+ review.getMark() + ", date = \'" + review.getDate() + "\' WHERE client_id = "
								+ review.getClient_id() + " and barber_shop_id = " + review.getBarber_shop_id());
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
	@Path("/deleteReview/client_id/{client_id}/barber_shop_id/{barber_shop_id}/{token}")
	public Response deleteReview(@PathParam("client_id") int client_id, @PathParam("barber_shop_id") int barber_shop_id,
			@PathParam("token") String token) {

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
						stm.executeUpdate("DELETE FROM review WHERE client_id = " + client_id + " and barber_sop_id = "
								+ barber_shop_id);
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