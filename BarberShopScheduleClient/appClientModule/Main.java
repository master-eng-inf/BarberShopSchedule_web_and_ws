import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
		String token = "61a7a2afb22b47a09b93eceaf67c9525";
		
		insertClient(token);
		//deleteClient(token);
		//updateClient(token);
		//insertAppointment(token);
		
		//logout(9, token);
	}

	private static void logout(int id, String token)
	{
		try {

			URL url = new URL("http://localhost:8080/BarberShopScheduleWeb/barberShopScheduleAPI/sessions/logout/"+id+"/" + token);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("\nClient POST. Answer: "+ output);
			}

			conn.disconnect();

		  } 
	  catch (MalformedURLException e) {e.printStackTrace();}
	  catch (IOException e) {e.printStackTrace(); }
	}
	
	private static void insertAppointment()
	{
		try {

			URL url = new URL("http://localhost:8080/BarberShopScheduleWeb/barberShopScheduleAPI/appointments/insertAppointment");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"id\":10,\"client_id\":1,\"barber_shop_id\":1,\"service_id\":1"
					+ ",\"promotion_id\":1,\"date\":\"2018-01-26\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("\nClient POST. Answer: "+ output);
			}

			conn.disconnect();

		  } 
	  catch (MalformedURLException e) {e.printStackTrace();}
	  catch (IOException e) {e.printStackTrace(); }
	}
	
	private static void deleteClient(String token)
	{
		try {

			URL url = new URL("http://localhost:8080/BarberShopScheduleWeb/barberShopScheduleAPI/clients/deleteClient/7/" + token);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("\nClient POST. Answer: "+ output);
			}

			conn.disconnect();

		  } 
	  catch (MalformedURLException e) {e.printStackTrace();}
	  catch (IOException e) {e.printStackTrace(); }
	}
	
	private static void insertClient(String token)
	{
		try {

			URL url = new URL("http://localhost:8080/BarberShopScheduleWeb/barberShopScheduleAPI/clients/insertClient/"+token);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"id\":14,\"password\":\"5\",\"email\":\"5@gmail.com\",\"telephone\":\"555-555-555\""
					+ ",\"name\":\"Client 5\",\"gender\":1,\"age\":10}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("\nClient POST. Answer: "+ output);
			}

			conn.disconnect();

		  } 
	  catch (MalformedURLException e) {e.printStackTrace();}
	  catch (IOException e) {e.printStackTrace(); }
	}
	
	private static void updateClient()
	{
		try {

			URL url = new URL("http://localhost:8080/BarberShopScheduleWeb/barberShopScheduleAPI/clients/updateClient");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"id\":7,\"password\":\"5\",\"email\":\"5@gmail.com\",\"telephone\":\"555-555-555\""
					+ ",\"name\":\"Alex Ghenghiu\",\"gender\":1,\"age\":12}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("\nClient POST. Answer: "+ output);
			}

			conn.disconnect();

		  } 
	  catch (MalformedURLException e) {e.printStackTrace();}
	  catch (IOException e) {e.printStackTrace(); }
	}
	
	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public Main() {
		super();
	}

}