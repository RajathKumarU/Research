package all_sources;

import java.io.FileOutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AmazonIn {
	public static void main(String args[]) throws Exception {
		String url = "http://www.amazon.in/Seagate-Backup-Portable-External-Storage/product-reviews/B00GASLJK6/ref=cm_cr_pr_btm_link_3?showViewpoints=1&pageNumber=";
		FileOutputStream fos = new FileOutputStream(
				"src/all_sources/seagate_HDD.txt", true);
		for (int j = 1; j <= 700; j++) {
			try {
				String url1 = url + j;
				Document doc = Jsoup.connect(url1).timeout(3000).get();
				Elements ele_maindiv = doc.select("div.review");

				for (int i = 0; i < ele_maindiv.size(); i++) {
					Element ele_review = ele_maindiv.get(i)
							.select("span.review-text").first();
					Element ele_rating = ele_maindiv.get(i)
							.select("span.a-icon-alt").first();
					/*
					 * if( ele_review == null) System.out.println("no review");
					 * else if ( ele_rating == null )
					 * System.out.println("no rating");
					 */
					String review = Jsoup.parse(ele_review.toString()).text();
					String rating = Jsoup.parse(ele_rating.toString()).text();
					System.out.println(rating.substring(0, 1) + "|" + review);
					fos.write((rating.substring(0, 1) + "|" + review + "\n")
							.getBytes());
				}
			} catch (Exception e) {
				e.printStackTrace();
				j--;
			}
		}

	}
}
