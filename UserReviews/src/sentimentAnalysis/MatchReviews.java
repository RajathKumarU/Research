package sentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class MatchReviews {
	public static void main(String[] args) {
		try {

			// BufferedReader cert_reviews = new BufferedReader(
			// new FileReader(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/Certified/crawledreview_certified.txt"));
			//
			// PrintWriter p = new PrintWriter(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/Certified/certified_12stars_arff_data.txt");
			// PrintWriter p2 = new PrintWriter(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/Certified/certified_filt_12stars_reviews.txt");
			//
			// String rev = "", cert_rev = "", dat = "";
			//
			// while ((cert_rev = cert_reviews.readLine()) != null) {
			// BufferedReader reviews = new BufferedReader(
			// new FileReader(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/mi4i_filtered_3828.txt"));
			// BufferedReader data = new BufferedReader(
			// new FileReader(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/data.txt"));
			//
			// while ((rev = reviews.readLine()) != null
			// && (dat = data.readLine()) != null) {
			// if (rev.contains(cert_rev)) {
			// if (((rev.startsWith("0"))
			// && (rev.charAt(2) == '1' || rev.charAt(2) == '2') && (!dat
			// .startsWith("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0")))) {
			// p.println(dat);
			// p2.println(rev);
			// break;
			// }
			// }
			// }
			// reviews.close();
			// data.close();
			// }
			//
			// cert_reviews.close();
			// p.close();
			// p2.close();

			//-----------------------------------------------------------------------------------------------------------------------------------------------
			// BufferedReader filt_reviews = new BufferedReader(
			// new FileReader(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/mi4i_filtered_3828.txt"));
			// BufferedReader data = new BufferedReader(
			// new FileReader(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/data.txt"));
			//
			// PrintWriter p1 = new PrintWriter(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/flipkart_12stars_filt_review.txt");
			//
			// PrintWriter p2 = new PrintWriter(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/flipkart_12stars_filt_data.arff");
			// String line1 = "", line2 = "";
			//
			// while ((line1 = filt_reviews.readLine()) != null
			// && (line2 = data.readLine()) != null) {
			// if (((line1.startsWith("0"))
			// && (line1.charAt(2) == '1' || line1.charAt(2) == '2') && (!line2
			// .startsWith("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0")))) {
			// p1.println(line1);
			// p2.println(line2);
			// }
			// }
			//
			// filt_reviews.close();
			// data.close();
			// p1.close();
			// p2.close();
//-----------------------------------------------------------------------------------------------------------------------------------------------
			BufferedReader data = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/1_2_stars/flipkart_12stars_spamval.txt"));

			int pos[] = new int[15];
			int neg[] = new int[15];
			int zero[] = new int[15];
			String line = "";

			for (int i = 0; i < zero.length; i++) {
				zero[i] = 0;
				pos[i] = 0;
				neg[i] = 0;
			}

			while ((line = data.readLine()) != null) {
				if(!line.endsWith("1")){
					continue;
				}
				String val[] = line.split(",");

				for (int j = 0; j < 15; j++) {
					int k = Integer.parseInt(val[j]);

					if (k > 0) {
						pos[j]++;
					} else if (k < 0) {
						neg[j]++;
					} else {
						zero[j]++;
					}
				}
			}
			for (int i = 0; i < pos.length; i++) {
				System.out.printf("%5d",pos[i]);
			}
			System.out.println();
			
			for (int i = 0; i < neg.length; i++) {
				System.out.printf("%5d",neg[i]);
			}
			System.out.println();
			
			for (int i = 0; i < zero.length; i++) {
				System.out.printf("%5d",zero[i]);
			}
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
