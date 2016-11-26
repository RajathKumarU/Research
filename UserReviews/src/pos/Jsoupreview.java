package pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Jsoupreview {
	public static void main(String[] args) throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					"mi4i_links.txt"));
			PrintWriter pr = new PrintWriter(new FileWriter(
					"crawledreview.txt", true));
			String line;
			int count = 0;

			while ((line = br.readLine()) != null) {
				//Thread.sleep(1000 * 20);
				Document doc = Jsoup.connect(line).get();
				Elements ele = doc.select("div.reviewText");
				Element ele1 = doc.select("li.rating").first();
				String rating = ele1.toString().split("out of 5 stars")[0]
						.split("alt=\"")[1].trim();
				// System.out.println(rating);
				String element = Jsoup.parse(ele.toString()).text();
				System.out.println("Review: " + (++count) + "  " + rating + "|"
						+ element);
				pr.println(rating + "|" + element);
			}
			br.close();
			pr.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
