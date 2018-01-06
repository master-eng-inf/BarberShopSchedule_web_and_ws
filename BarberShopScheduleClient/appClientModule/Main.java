import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String token = "";

		Scanner reader = new Scanner(System.in);
		System.out.println("Enter the token: ");

		token = reader.nextLine();
		reader.close();

		// insertClient();
		// getWebBarberShopLists();
		// insertBarberShop();
		// deleteClient(token);
		// updateClient(token);
		 insertAppointment(token);
		// insertService(token);
		// logout(9, token);
	}

	private static void getWebBarberShopLists() {
		try {
			URL url = new URL("http://localhost:8080/BarberShopScheduleWeb/barberShopScheduleAPI/barberShops/web-list");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("\nClient POST. Answer: " + output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void logout(int id, String token) {
		try {

			URL url = new URL("http://localhost:8080/BarberShopScheduleWeb/barberShopScheduleAPI/sessions/logout/" + id
					+ "/" + token);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("\nClient POST. Answer: " + output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void insertAppointment(String token) {
		try {

			URL url = new URL(
					"http://localhost:8080/BarberShopScheduleWeb/barberShopScheduleAPI/appointments/insertAppointment/"
							+ token);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"client_id\":19,\"barber_shop_id\":3,\"service_id\":4"
					+ ",\"promotion_id\":-1,\"date\":\"2018-01-26 10:45\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("\nClient POST. Answer: " + output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void insertService(String token) {
		try {

			URL url = new URL(
					"http://localhost:8080/BarberShopScheduleWeb/barberShopScheduleAPI/services/insertService/"
							+ token);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"barber_shop_id\":3,\"name\":\"servicio 1\",\"price\":10,\"duration\":15}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("\nClient POST. Answer: " + output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void deleteClient(String token) {
		try {

			URL url = new URL(
					"http://localhost:8080/BarberShopScheduleWeb/barberShopScheduleAPI/clients/deleteClient/7/"
							+ token);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("\nClient POST. Answer: " + output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void insertClient() {
		try {

			URL url = new URL(
					"http://82.223.24.126:28080/BarberShopScheduleWeb/barberShopScheduleAPI/clients/insertClient");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"password\":\"pass\",\"email\":\"5@gmail.com\",\"telephone\":\"555-555-555\""
					+ ",\"name\":\"Alex\",\"gender\":1,\"age\":10}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			/*
			 * if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) { throw new
			 * RuntimeException("Failed : HTTP error code : " + conn.getResponseCode()); }
			 */

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("\nClient POST. Answer: " + output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void insertBarberShop() {
		try {

			URL url = new URL(
					"http://82.223.24.126:28080/BarberShopScheduleWeb/barberShopScheduleAPI/barberShops/insertBarberShop");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"password\":\"password\",\"email\":\"perruqueria1@gmail.com\",\"telephone\":\"973788156\""
					+ ",\"name\":\"Perruqueria de prova 2\",\"address\":\"Carrer Prat de la Riba 108\",\"city\":\"Lleida\",\"description\":\"Perruqueria de prova del carrer Prat de la Riba 108, Perruqueria de prova del carrer Prat de la Riba 108\",\"places_id\":\"placeId\",\"gender\":1}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			/*
			 * if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) { throw new
			 * RuntimeException("Failed : HTTP error code : " + conn.getResponseCode()); }
			 */

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("\nClient POST. Answer: " + output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void updateClient() {
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
				System.out.println("\nClient POST. Answer: " + output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see java.lang.Object#Object()
	 */
	public Main() {
		super();
	}

}