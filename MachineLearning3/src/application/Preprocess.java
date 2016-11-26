package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Preprocess {
	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet buffer = null;
		String city = "";
		float f1 = 0, f2 = 0;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			System.out.println("Connecting to database...");
			// conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			BufferedReader br1 = new BufferedReader(new FileReader(
					"src/files/Cities.txt"));
			while ((city = br1.readLine()) != null) {
				String line, pr;
				float realprice, sz;
				int neold, bdrooms;
				BufferedReader br = new BufferedReader(new FileReader(
						"src/files1/data"
								+ city.toLowerCase().replaceAll("-", "")
								+ ".txt"));
				PrintWriter p = new PrintWriter(new FileWriter("src/files/d"
						+ city.toLowerCase().replaceAll("-", "") + ".arff"));
				p.println("@relation house");
				p.println("@attribute housesize numeric");
				p.println("@attribute bedrooms numeric");
				p.println("@attribute lattitude numeric");
				p.println("@attribute longitude numeric");
				p.println("@attribute status numeric");
				p.println("@attribute sellingprice numeric");
				p.println("@data");
				while ((line = br.readLine()) != null) {

					try {
						String linearr[] = line.split("\",\"");
						String bedrooms = linearr[0].split(":")[1].replaceAll(
								"\"", "").split("BHK")[0].trim();
						bdrooms = Integer.parseInt(bedrooms);
						String area = linearr[1].split(":")[1]
								.replaceAll("\"", "").replace("Location", "")
								.replace("see on map", "").trim();

						try {
							buffer = stmt.executeQuery("select * from "
									+ city.toLowerCase().replaceAll("-", "")
									+ " where area LIKE '%"
									+ area.replaceAll(" ", "")
											.replaceAll("-", "")
											.replaceAll(",", "").toLowerCase()
									+ "%'");
						} catch (Exception n) {
							n.printStackTrace();
						}

						while (buffer.next()) {

							f1 = buffer.getFloat("lat");
							f2 = buffer.getFloat("lon");
							System.out.println("yes" + f1 + f2);

						}
						String size = linearr[2].split(":")[1].replaceAll("\"",
								"").split("s")[0].trim();
						sz = Float.parseFloat(size);
						String status = linearr[4].split(":")[1].replaceAll(
								"\"", "").trim();

						if (status.contains("Resale")) {
							neold = 0;
						} else {
							neold = 1;
						}
						pr = linearr[3].split(":")[1].replaceAll("\"", "")
								.trim();
						if (pr.contains("Lac")) {
							pr = pr.split("Lac")[0].trim();
							realprice = Float.parseFloat(pr);
							realprice = realprice * 100000;
						} else if (pr.contains("Cr")) {
							pr = pr.split("Cr")[0].trim();
							realprice = Float.parseFloat(pr);
							realprice = realprice * 10000000;
						} else {
							realprice = Float.parseFloat(pr);
						}
						System.out.println(sz + "|" + bdrooms + "|" + f1 + "|"
								+ f2 + "|" + neold + "|" + realprice);
						p.println(sz + "," + bdrooms + "," + f1 + "," + f2
								+ "," + neold + "," + realprice);
					} catch (Exception e1) {
					}
				}
				br.close();
				p.close();

			}
			br1.close();
			conn.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
