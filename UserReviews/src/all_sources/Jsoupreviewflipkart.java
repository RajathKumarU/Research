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

public class Jsoupreviewflipkart {
	public static void main(String[] args) throws IOException {
		try {
			// BufferedReader br = new BufferedReader(new FileReader(
			// "mi4i_links.txt"));
			Scanner br = new Scanner(
					new File(
							"src/all_sources/tp_link_router/tp_link_router_flipkart_temp.txt"));
			PrintWriter pr = new PrintWriter(new FileWriter(
					"src/all_sources/tp_link_router/tp_link_router_flipkart.txt", true));
			String line = "", print = "";
			int count = 0;
			boolean flag = false;/* (line = br.readLine()) != null */
			while (br.hasNext()) {
				try {

					if (!flag)
						line = br.nextLine();

					// Thread.sleep(1000 * 20);
					Document doc = Jsoup.connect(line).get();
					Elements ele = doc.select("span.review-text");
					Element ele1 = doc.select("div.fk-stars").get(1);

					String rating = ele1.toString().split("title")[1]
							.split("star\"")[0].split("\"")[1].trim();

					String element = Jsoup.parse(ele.toString()).text();
					System.out.println("Review: " + (++count) + "  " + rating
							+ "|" + element);
					print = rating + "|" + element;
					print = print.replace(" stars|", "|");
					pr.println(print);
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
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
