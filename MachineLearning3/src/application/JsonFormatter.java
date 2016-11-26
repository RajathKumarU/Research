package application;

import java.io.FileOutputStream;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class JsonFormatter {
	
	public static void writeToFile(String result, String city) {
		JSONParser parser = new JSONParser();
		result = result.replaceAll(" =", "=");
		result = result.replaceAll("= ", "=");
		result = result.replaceAll("=", ":");
		result = result.replaceAll(" ,", ",");
		result = result.replaceAll(", ", ",");
		result = result.replace("{ ", "{");
		result = result.replaceAll(" }", "}");
		result = result.replace("{", "{\"");
		result = result.replaceAll(":", "\":\"");
		result = result.replaceAll(",size", "\",\"size");
		result = result.replaceAll(",area", "\",\"area");
		result = result.replaceAll(",price", "\",\"price");
		result = result.replaceAll(",status", "\",\"status");
		result = result.replaceAll(",bedrooms", "\",\"bedrooms");
		result = result.replaceAll("}", "\"}");
		result = result.replace("}\"", "}");
		result = result.replace("\"{", "{");
		// result = result.replaceAll("[House, Auction:]","1 BHK Apartment");
		// System.out.println(result);
		try {
			Object obj = parser.parse(result);
			JSONArray array = (JSONArray) obj;
			@SuppressWarnings("resource")
			FileOutputStream fos = new FileOutputStream("src/files1/data" + city.toLowerCase().replaceAll("-", "") + ".txt",
					true);
			for (int i = 0; i < array.size(); i++) {
				fos.write((array.get(i).toString() + "\n").getBytes());
			}
		} catch (Exception e) {
			System.out.println("Error!!");
		}
	}
}
