package sentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;

public class Get_Overall_certif_value {
	public static void main(String[] args) {
		try {
			BufferedReader data = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/Certified/certified_arff_data.txt"));
			String line = "";
			int positive[] = new int[15];
			int negetive[] = new int[15];
			int neutral[] = new int[15];
			for (int i = 0; i < positive.length; i++) {
				positive[i] = 0;
			}

			while ((line = data.readLine()) != null) {
				String val[] = line.split(",");
				for (int i = 0; i < val.length - 1; i++) {
					// positive[i] += Integer.parseInt(val[i]);
					int num = Integer.parseInt(val[i]);
					if (num < 0)
						negetive[i]++;
					else if (num > 0)
						positive[i]++;
					else
						neutral[i]++;
				}
			}
			
			for (int i = 0; i < positive.length; i++) {
				// if (positive[i] < 0)
				// System.out.print(positive[i] + " ");
				// else if (positive[i] > 0)
				// System.out.print(positive[i] + " ");
				// else
				// System.out.print("0 ");
				System.out.printf("%5d", positive[i]);
			}
			System.out.println();
			for (int i = 0; i < negetive.length; i++) {
				System.out.printf("%5d", negetive[i]);
			}
			System.out.println();
			for (int i = 0; i < neutral.length; i++) {
				System.out.printf("%5d", neutral[i]);
			}

			data.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
