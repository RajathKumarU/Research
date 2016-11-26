package sentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class take1000 {

	public static void main(String[] args) {
		try {
			BufferedReader reader1 = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/source_arff_data/tp_link_sentiment_noz.txt"));
			// BufferedReader reader2 = new BufferedReader(new
			// FileReader("C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/New/Scoring/mi4i_val_inval_reviews_no_zeros.txt"));
			PrintWriter p = new PrintWriter(
					new FileWriter(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/train_test_arffs/tp_link_sentiment_noz_train_680_680.arff"));
			PrintWriter p2 = new PrintWriter(
					new FileWriter(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/train_test_arffs/tp_link_sentiment_noz_test_68_68.arff"));
			// PrintWriter p2 = new PrintWriter(
			// new FileWriter(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/New/Scoring/mi4i_val_reviews_no_zeros.txt"));
			String line1 = "", line2 = "";
			int one_count = 0, zero_count = 0;

			while (((line1 = reader1.readLine()) != null)
			/* && ((line2 = reader2.readLine()) != null) */) {
				if (line1.endsWith("0")
						/*&& (!line1.startsWith("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0"))*/
						&& (zero_count < 748)) {
					
					if (zero_count < 680) {
						p.println(line1);
					} else {
						p2.println(line1);
					}
					// p.println(line1);
					// p2.println(line2);
					zero_count++;
				}
			}
			System.out.println(zero_count);
			// p.close();
			reader1.close();
			// reader2.close();

			// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------

			reader1 = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/source_arff_data/tp_link_sentiment_noz.txt"));
			// p = new PrintWriter(new
			// FileWriter("C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/flipkart_mi4i_total_validity_1040.arff",true));

			while ((line1 = reader1.readLine()) != null) {
				if (line1.endsWith("1")
						/*&& (!line1.startsWith("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0"))*/
						&& (one_count < 748)) {
					if (one_count < 680) {
						p.println(line1);
					} else {
						p2.println(line1);
					}
					// p.println(line1);
					one_count++;
				}
			}
			System.out.println(one_count);
			p.close();
			p2.close();
			reader1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
