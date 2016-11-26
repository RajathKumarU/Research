package sentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class AppendRating {

	public static void main(String[] args) {
		try {
			BufferedReader reader1 = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/New/cluster/mi4i_stars.txt"));
			BufferedReader reader2 = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/New/data.txt"));
			BufferedReader valid = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/New/mi4i_filtered_3828.txt"));
			PrintWriter p = new PrintWriter(
					new FileWriter(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/New/cluster/mi4i_cluster_dev_valid_no_zeros.arff"));
			// PrintWriter p2 = new PrintWriter(
			// new FileWriter(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/galaxy/certified/galaxy_val_inval_reviews_no_zeros.txt"));
			//
			String line1 = "", line2 = "", val = "";

			while ((line1 = reader1.readLine()) != null
					&& (line2 = reader2.readLine()) != null
					&& (val = valid.readLine()) != null) {
				if (!line2.contains("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0")) {
					// no of zeros changes for every product

					p.println(line2 + ","
							+ Math.abs(line1.charAt(2) - line1.charAt(0)) + ","
							+ val.charAt(0));
					// p2.println(line1);
				}
			}

			p.close();
			// p2.close();
			reader1.close();
			reader2.close();
			valid.close();

			// BufferedReader reader1 = new BufferedReader(
			// new FileReader(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/temp.txt"));
			// PrintWriter p = new PrintWriter(
			// new FileWriter(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/temp.arff"));
			//
			// String line1 = "";
			//
			// while ((line1 = reader1.readLine()) != null){
			// p.println(line1+",1");
			// }
			//
			// p.close();
			// reader1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
