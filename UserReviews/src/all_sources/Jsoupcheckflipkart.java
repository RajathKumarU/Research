package all_sources;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Jsoupcheckflipkart {

	public static void main(String[] args) {
		try {
			for (int j = 0; j <= 80; j++) {
				// int j = 10;
				int count = 0;
				String linkstr = "";
				PrintWriter p = new PrintWriter(
						new FileWriter(
								"src/all_sources/tp_link_router/tp_link_router_flipkart_temp.txt", true));
				Document doc = null;
				Elements links = null;

				boolean flag = true;
				while (flag) {
					try {
						flag = false;
						doc = Jsoup
								.connect(
										"http://www.flipkart.com/tp-link-tl-wr841n-300mbps-wireless-n-router/product-reviews/ITMD7HN9CW5Y3H3K?pid=RTRD7HN3JJYF6WN2&rating=1,2,3,4,5&reviewers=all&type=top&sort=most_helpful&start="
												+ j * 10).timeout(60 * 1000)
								.get();
						links = doc.select("a[href]");
					} catch (Exception e1) {
						flag = true;
						Thread.sleep(3 * 1000);
						System.out.println("wait");
						e1.printStackTrace();
					}
				}
				for (Element link : links) {
					// System.out.println("\nlink : " + link.attr("href"));
					linkstr = link.attr("href");

					if (linkstr.startsWith("/reviews/")) {

						p.println("http://www.flipkart.com" + linkstr);
						count++;

					}

					// System.out.println(linkstr);
				}
				System.out.println("Page: " + j + " , Count: " + count);
				p.close();
				Jsoupreviewflipkart.main(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
