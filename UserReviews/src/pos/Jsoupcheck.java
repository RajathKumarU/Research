package pos;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Jsoupcheck {

	public static void main(String[] args) {
		try {
			for (int j = 24; j <= 25; j++) {
				// int j = 10;
				int count = 0;
				String linkstr = "";
				ArrayList<String> al = new ArrayList<String>();
				PrintWriter p = new PrintWriter(
						new FileWriter("mi4i_links.txt"));
				Document doc = Jsoup
						.connect(
								"http://www.amazon.in/Mi-MZB4300IN-4i-16GB-Grey/product-reviews/B0117H62QK/ref=cm_cr_pr_btm_link_2?showViewpoints=1&pageNumber="
										+ j).get();
				Elements links = doc.select("a[href]");
				for (Element link : links) {
					// System.out.println("\nlink : " + link.attr("href"));
					linkstr = link.attr("href");
					if (linkstr.contains("/gp/customer-reviews")) {
						if ((!al.contains(linkstr))
								&& (!linkstr.contains("#R14MC9LAD8STZZ"))
								&& (!linkstr.contains("#R2FHWWOD92OEII"))) {
							al.add(linkstr);
							p.println("http://www.amazon.in" + linkstr);
							count++;
						}
					}
					// System.out.println("text : " + link.text());
				}
				System.out.println("Page: " + j + " , Count: " + count);
				p.close();
				Jsoupreview.main(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
