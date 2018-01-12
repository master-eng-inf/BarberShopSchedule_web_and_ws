package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

import models.Appointment;
import models.Client;

@RequestScoped
@Path("notifications")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class NotificationController {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/cancelAppointment/{id}/{token}")
	@Consumes("application/json")
	public boolean cancelAppointment(@PathParam("id") int id, @PathParam("token") String token) {

		HttpURLConnection conn = null;
		Connection connection = null;
		Statement stm = null;
		String strEstat = new String("ok");
		boolean result = false;

		try {
			InitialContext cxt = new InitialContext();
			if (cxt != null) {
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgresXA");

				if (ds == null)
					strEstat = "Error al crear el datasource";
				else {
					connection = ds.getConnection();
					stm = connection.createStatement();

					ResultSet session = stm.executeQuery("SELECT * FROM session WHERE session_token = '" + token + "'");

					if (session.next()) {

						ResultSet appointment_rs = stm.executeQuery("SELECT * appointment WHERE id = " + id);

						if (appointment_rs.next()) {
							ResultSet client_rs = stm
									.executeQuery("SELECT * FROM client WHERE id = " + appointment_rs.getInt(2));

							if (client_rs.next()) {
								String deviceToken = client_rs.getString(8);

								try {

									ResultSet service_rs = stm.executeQuery(
											"SELECT * FROM service WHERE id = " + appointment_rs.getInt(4));

									if (service_rs.next()) {
										URL url = new URL("https://fcm.googleapis.com/fcm/send");

										conn = (HttpURLConnection) url.openConnection();
										conn.setDoOutput(true);
										conn.setRequestMethod("POST");
										conn.setRequestProperty("Content-Type", "application/json");
										conn.setRequestProperty("Authorization",
												"server_token");

										String input = "{\"to\":\"" + deviceToken
												+ "\", \"data\":{\"type\" : \"cancel\", \"service\" : \""
												+ service_rs.getString(3) + "\", \"time\" : \""
												+ appointment_rs.getString(6) + "\" }}";

										OutputStream os = conn.getOutputStream();
										os.write(input.getBytes());
										os.flush();

										BufferedReader br = new BufferedReader(
												new InputStreamReader((conn.getInputStream())));

										String output;
										while ((output = br.readLine()) != null) {
											System.out.println("\nClient POST. Answer: " + output);
										}

										conn.disconnect();
									}

								} catch (MalformedURLException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								} finally {
									if (conn != null)
										conn.disconnect();
								}
							}

						}

						stm.executeUpdate("DELETE FROM appointment WHERE id = " + id);

						result = true;
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko due to -> " + e.getMessage();
		} finally {
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
		return result;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/acceptAppointment/{id}/{token}")
	@Consumes("application/json")
	public boolean acceptAppointment(@PathParam("id") int id, @PathParam("token") String token) {

		HttpURLConnection conn = null;
		Connection connection = null;
		Statement stm = null;
		String strEstat = new String("ok");
		boolean result = false;

		try {
			InitialContext cxt = new InitialContext();
			if (cxt != null) {
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgresXA");

				if (ds == null)
					strEstat = "Error al crear el datasource";
				else {
					connection = ds.getConnection();
					stm = connection.createStatement();

					ResultSet session = stm.executeQuery("SELECT * FROM session WHERE session_token = '" + token + "'");

					if (session.next()) {

						ResultSet appointment_rs = stm.executeQuery("SELECT * appointment WHERE id = " + id);

						if (appointment_rs.next()) {
							ResultSet client_rs = stm
									.executeQuery("SELECT * FROM client WHERE id = " + appointment_rs.getInt(2));

							if (client_rs.next()) {
								String deviceToken = client_rs.getString(8);

								try {

									ResultSet service_rs = stm.executeQuery(
											"SELECT * FROM service WHERE id = " + appointment_rs.getInt(4));

									if (service_rs.next()) {
										URL url = new URL("https://fcm.googleapis.com/fcm/send");

										conn = (HttpURLConnection) url.openConnection();
										conn.setDoOutput(true);
										conn.setRequestMethod("POST");
										conn.setRequestProperty("Content-Type", "application/json");
										conn.setRequestProperty("Authorization",
												"server_token");

										String input = "{\"to\":\"" + deviceToken
												+ "\", \"data\":{\"type\" : \"accept\", \"service\" : \""
												+ service_rs.getString(3) + "\", \"time\" : \""
												+ appointment_rs.getString(6) + "\" }}";

										OutputStream os = conn.getOutputStream();
										os.write(input.getBytes());
										os.flush();

										BufferedReader br = new BufferedReader(
												new InputStreamReader((conn.getInputStream())));

										String output;
										while ((output = br.readLine()) != null) {
											System.out.println("\nClient POST. Answer: " + output);
										}

										conn.disconnect();
									}

								} catch (MalformedURLException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								} finally {
									if (conn != null)
										conn.disconnect();
								}
							}

						}

						stm.executeUpdate("UPDATE appointment SET pendingconfirmation = 0 WHERE id = " + id);

						result = true;
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko due to -> " + e.getMessage();
		} finally {
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
		return result;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/requestAppointment/{id}/{token}")
	@Consumes("application/json")
	public boolean requestAppointment(@PathParam("id") int id, @PathParam("token") String token) {

		HttpURLConnection conn = null;
		Connection connection = null;
		Statement stm = null;
		String strEstat = new String("ok");
		boolean result = false;

		try {
			InitialContext cxt = new InitialContext();
			if (cxt != null) {
				DataSource ds = (DataSource) cxt.lookup("java:jboss/PostgresXA");

				if (ds == null)
					strEstat = "Error al crear el datasource";
				else {
					connection = ds.getConnection();
					stm = connection.createStatement();

					ResultSet session = stm.executeQuery("SELECT * FROM session WHERE session_token = '" + token + "'");

					if (session.next()) {

						ResultSet appointment_rs = stm.executeQuery("SELECT * appointment WHERE id = " + id);

						if (appointment_rs.next()) {
							ResultSet client_rs = stm
									.executeQuery("SELECT * FROM client WHERE id = " + appointment_rs.getInt(2));

							if (client_rs.next()) {
								String deviceToken = client_rs.getString(8);

								try {

									ResultSet service_rs = stm.executeQuery(
											"SELECT * FROM service WHERE id = " + appointment_rs.getInt(4));

									if (service_rs.next()) {
										URL url = new URL("https://fcm.googleapis.com/fcm/send");

										conn = (HttpURLConnection) url.openConnection();
										conn.setDoOutput(true);
										conn.setRequestMethod("POST");
										conn.setRequestProperty("Content-Type", "application/json");
										conn.setRequestProperty("Authorization",
												"server_token");

										String input = "{\"to\":\"" + deviceToken
												+ "\", \"data\":{\"type\" : \"request\", \"service\" : \""
												+ service_rs.getString(3) + "\", \"time\" : \""
												+ appointment_rs.getString(6) + "\" }}";

										OutputStream os = conn.getOutputStream();
										os.write(input.getBytes());
										os.flush();

										BufferedReader br = new BufferedReader(
												new InputStreamReader((conn.getInputStream())));

										String output;
										while ((output = br.readLine()) != null) {
											System.out.println("\nClient POST. Answer: " + output);
										}

										conn.disconnect();
									}

								} catch (MalformedURLException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								} finally {
									if (conn != null)
										conn.disconnect();
								}
							}
						}

						result = true;
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			strEstat = "status ko due to -> " + e.getMessage();
		} finally {
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
		return result;
	}
}
