package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class LatLng {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(
				"src/files/Cities.txt"));
		String city = "";
		Connection conn = null;
		Statement stmt = null;
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		System.out.println("Connecting to database...");
		// conn = DriverManager.getConnection(DB_URL, USER, PASS);
		stmt = conn.createStatement();
		while ((city = br.readLine()) != null) {
			System.out.println(city);
			BufferedReader reader = new BufferedReader(new FileReader(
					"src/files/Localities"
							+ city.toLowerCase().replaceAll("-", "") + ".txt"));
			String area = "";
			while ((area = reader.readLine()) != null) {
				System.out.println(area);
				String latLongs[] = getLatLongPositions(area, city);
				System.out
						.println(area + "," + latLongs[0] + "," + latLongs[1]);
				String sql = String
						.format("Insert into %s ( area , lat , lon ) values ( '%s' , %s , %s) ",
								city.toLowerCase().replaceAll("-", ""), area
										.replaceAll(" ", "")
										.replaceAll("-", "")
										.replaceAll(",", "").toLowerCase(),
								latLongs[0], latLongs[1]);
				stmt.executeUpdate(sql);
			}
			reader.close();

		}
		br.close();
	}

	public static String[] getLatLongPositions(String address, String city)
			throws Exception {
		int responseCode = 0;
		String api = "http://maps.googleapis.com/maps/api/geocode/xml?address="
				+ URLEncoder.encode(address + "," + city, "UTF-8")
				+ "&sensor=true";
		Thread.sleep(200);
		URL url = new URL(api);
		HttpURLConnection httpConnection = (HttpURLConnection) url
				.openConnection();
		httpConnection.connect();
		responseCode = httpConnection.getResponseCode();
		if (responseCode == 200) {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			;
			Document document = builder.parse(httpConnection.getInputStream());
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile("/GeocodeResponse/status");
			String status = (String) expr.evaluate(document,
					XPathConstants.STRING);
			if (status.equals("OK")) {
				expr = xpath.compile("//geometry/location/lat");
				String latitude = (String) expr.evaluate(document,
						XPathConstants.STRING);
				expr = xpath.compile("//geometry/location/lng");
				String longitude = (String) expr.evaluate(document,
						XPathConstants.STRING);
				return new String[] { latitude, longitude };
			} else {
				throw new Exception("Error from the API - response status: "
						+ status);
			}
		}
		return null;

	}
}