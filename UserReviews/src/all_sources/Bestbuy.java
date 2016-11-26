package all_sources;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Bestbuy {
	public static void main(String[] args) throws IOException {
		try {
			// BufferedReader br = new BufferedReader(new FileReader(
			// "mi4i_links.txt"));
			// Scanner br = new Scanner(new File("mi4i_links.txt"));
			PrintWriter pr = new PrintWriter(new FileWriter(
					"src/all_sources/GalaxyTab4/GalaxyTab4_bestbuy.txt", true));
			String line = "";
			int count = 0;
			boolean flag = false;/* (line = br.readLine()) != null */
			for (int i = 1; i < 228; i++) {
				try {

					if (!flag)
						line = "http://api.bestbuy.com/v1/reviews(sku=5420045)?page="
								+ i
								+ "&format=xml&apiKey=4cbu2xpz5yz98qv7xbmpp8yq&show=comment,rating#";

					// Thread.sleep(1000 * 20);
					Document doc = Jsoup.connect(line).get();
					Elements ele = doc.select("reviews");
					String element = ele.toString();
					for (int j = 1; j <= 10; j++) {
						String review = element.split("<review>")[j]
								.split("<comment>")[1].split("</comment>")[0]
								.trim();
						String rating1 = element.split("<review>")[j]
								.split("<comment>")[1].split("<rating>")[1]
								.split("</rating> ")[0].trim();
						String rating = rating1.split("\\.")[0];
						count++;
						System.out
								.println(count + ") " + rating + "|" + review);
						pr.println(rating + "|" + review);
					}

					// String element = Jsoup.parse(ele.toString()).text();

					// br.close();
					// pr.close();
					flag = false;
				} catch (Exception e) {
					flag = true;
					System.out.println("Timed out!");
					Thread.sleep(1000 * 3);
				}
			}
			pr.close();
			// br.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
