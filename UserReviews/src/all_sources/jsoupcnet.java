package all_sources;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class jsoupcnet {

	public static void main(String[] args) {
		try {
			String title1 = "", overallstar = "", good = "", bad = "", bottomline = "", mainreview = "", subrating = "";

			PrintWriter p = new PrintWriter(new FileWriter(
					"src/all_sources/one_plus_x.txt", true));
			Document doc = null;
			Elements title = null, rating = null, goode = null, bade = null, bottomlinee = null, review = null, subratinge = null;
			StringBuilder sb = new StringBuilder();
			StringBuilder sb1 = new StringBuilder();
			boolean flag = true;
			while (flag) {
				try {
					flag = false;
					doc = Jsoup
							.connect("http://www.cnet.com/products/oneplus-x/")
							.timeout(60 * 1000).get();
					title = doc.select("h1.headline");
					rating = doc.select("div.editorStarRating");
					goode = doc.select("p.theGood");
					bade = doc.select("p.theBad");
					bottomlinee = doc.select("p.theBottomLine");
					review = doc.select("div.description > p");
					subratinge = doc.select("div.categoryWrap");
					title1 = Jsoup.parse(title.toString()).text();
					overallstar = Jsoup.parse(rating.toString()).text();
					good = Jsoup.parse(goode.toString()).text();
					bad = Jsoup.parse(bade.toString()).text();
					bottomline = Jsoup.parse(bottomlinee.toString()).text();
					for (Element element : review) {
						sb.append(element.text()).append('\n');
					}
					mainreview = sb.toString().trim();
					for (Element element : subratinge) {
						sb1.append(element.text()).append('\n');
					}
					subrating = sb1.toString().trim();
				} catch (Exception e1) {
					flag = true;
					Thread.sleep(3 * 1000);
					System.out.println("wait");
					e1.printStackTrace();
				}
			}

			good = "The Good : " + good.split("The Good ")[1];
			bad = "The Bad : " + bad.split("The Bad ")[1];
			bottomline = "The Bottom Line : "
					+ bottomline.split("The Bottom Line ")[1];
			p.print(title1 + "\n\n" + overallstar + "\n\nSubratings:\n"
					+ subrating + "\n\n\n" + good + "\n\n" + bad + "\n\n"
					+ bottomline + "\n\n\nReview:\n\n" + mainreview);
			p.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
