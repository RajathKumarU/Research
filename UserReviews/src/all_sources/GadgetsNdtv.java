package all_sources;

import java.io.FileOutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GadgetsNdtv {
	public static void main(String args[]) throws Exception {

		try {
			FileOutputStream fos = new FileOutputStream("s6.txt", true);
			String url = "http://gadgets.ndtv.com/samsung-galaxy-s6-2443/user-reviews";
			Document doc = Jsoup.connect(url).get();
			Element el_main = doc.select("div[class=other_reviews").first();
			Elements el_star = el_main.select("span[itemprop=ratingValue]");
			Elements el_review = el_main.select("span[itemprop=description]");
			// System.out.println();
			for (int i = 0; i < el_star.size(); i++) {
				String star = Jsoup.parse(el_star.get(i).toString()).text();
				String review = Jsoup.parse(el_review.get(i).toString()).text();
				System.out.println(star + "|" + review);
				fos.write((star + "|" + review + "\n").getBytes());
			}
			// System.out.println(el_star.size()+"|"+el_review.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
