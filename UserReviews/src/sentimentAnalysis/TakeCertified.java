package sentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class TakeCertified {
	public static void main(String[] args) {
		try {
			BufferedReader certif = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/New/Certified/crawledreview_certified.txt"));

			PrintWriter p = new PrintWriter(
					new FileWriter(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/New/cluster/mi4i_certified_cluster_dev_valid_no_zeros.arff"));
			// PrintWriter p2 = new PrintWriter(
			// new FileWriter(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/galaxy/certified/cert_reviews_949.txt"));

			String cer = "", rev = "", dat = "";

			int i = 0;
			while ((cer = certif.readLine()) != null) {
				System.out.println(++i);
				BufferedReader reviews = new BufferedReader(
						new FileReader(
								"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/New/mi4i_filtered_3828.txt"));
				BufferedReader data = new BufferedReader(
						new FileReader(
								"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/New/cluster/data_dev_valid.txt"));

				while ((rev = reviews.readLine()) != null
						&& (dat = data.readLine()) != null) {
					if (rev.contains(cer) && !dat.startsWith("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0")) {
						p.println(dat /*
									 * + "," + rev.charAt(2) + "," +
									 * rev.charAt(0)
									 */);
						// p2.println(rev.charAt(0) + "|" + cer);
						break;
					}
				}
				reviews.close();
				data.close();
			}

			p.close();
			// p2.close();
			certif.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
