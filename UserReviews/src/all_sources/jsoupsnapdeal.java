package all_sources;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class jsoupsnapdeal {

	public static void main(String[] args) {
		try {
			for (int j = 1; j <= 100; j++) {
				// int j = 10;
				int count = 0;
				String reviewstr = "";
				String rating = "";
				int stars = 0;
				PrintWriter p = new PrintWriter(new FileWriter(
						"src/all_sources/tp_link_router/tp_link_router_snapdeal.txt", true));
				Document doc = null;
				Elements reviews = null;

				boolean flag = true;
				while (flag) {
					try {
						flag = false;
						doc = Jsoup
								.connect(
										"http://www.snapdeal.com/product/tplink-300mbps-wireless-n-router/640560/ratedreviews?page="+j+"&sortBy=HELPFUL&ratings=1,2,3,4,5#defRevPDP")
								.timeout(60 * 1000).get();
						reviews = doc.select("div.user-review");
					} catch (Exception e1) {
						flag = true;
						Thread.sleep(3 * 1000);
						System.out.println("wait");
						e1.printStackTrace();
					}
				}
				for (Element review : reviews) {
					if (count > 1) {
						rating = review.toString();
						stars = 0;
						Pattern pattern = Pattern
								.compile("\\bsd-icon sd-icon-star active");
						Matcher matcher = pattern.matcher(rating);
						while (matcher.find()) {
							stars++;
						}
						reviewstr = rating.split("</p>")[0].split("<p>")[1];
						p.println(stars + "|" + reviewstr);

					}
					count++;
				}
				System.out.println("Page: " + j + " , Count: " + (count - 2));
				p.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
