package semantic;

import java.io.BufferedReader;
import java.io.FileReader;

public class OverallSentScore {
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/New/data.txt"));

			double new_review[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			double ovr_score[] = { 15.03465347, 14.5170068, 8.847938144,
					10.49090909, 15.58333333, 12.38223938, 15.06097561,
					11.72413793, 14.99103139, 13.83695652, 13.03267974,
					13.30344828, 8.701492537, 13.1484375, 17.8 };
			double ovr_senti[] = new double[15];
			double ovr_senti_count[] = new double[15];

			String line = "";

			while ((line = reader.readLine()) != null) {
				String vals[] = line.split(",");
				for (int i = 0; i < vals.length; i++) {
					if (Integer.parseInt(vals[i]) > 0) {
						ovr_senti[i]++;
						ovr_senti_count[i]++;
					} else if (Integer.parseInt(vals[i]) < 0) {
						ovr_senti[i]--;
						ovr_senti_count[i]++;
					}
				}
			}

			for (int i = 0; i < ovr_senti_count.length; i++) {
				System.out.println(ovr_senti[i] + ","
						+ (ovr_senti[i] / ovr_senti_count[i]) * ovr_score[i]);
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
