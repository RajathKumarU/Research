package all_sources;

import java.io.*;

public class ScoreOverall {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new FileReader(
						"C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/arffs/motog3_sentiment_20_non-spam - Copy.txt"));
		PrintWriter pr = new PrintWriter(
				new FileWriter(
						"C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/scoring/motog3_sentiment_noz_20_non-spam.arff"));
		PrintWriter pr2 = new PrintWriter(
				new FileWriter(
						"C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/scoring/motog3_overall_score_20_non-spam.arff"));

		String line;

		String score = "1.2081,1.3672,1.3062,1.2219,1.3839,1.2419,1.2995,1.3474,1.4455,1.3825,1.5396,1.3028,1.4745,1.3976,1.5969,1.7643,2.5571";

		String[] arr = new String[100];
		String[] scr = new String[100];
		scr = score.split(",");

		while ((line = br.readLine()) != null) {

			float sum = 0;
			int overall = 0;
			arr = line.split(",");
			for (int i = 0; i < arr.length; i++) {
				if (Integer.parseInt(arr[i]) >= 1) {
					sum = sum + Float.parseFloat(scr[i].toString().trim());
					overall++;
					pr.print("1,");
				} else if (Integer.parseInt(arr[i]) <= -1) {
					sum = sum - Float.parseFloat(scr[i].toString().trim());
					pr.print("-1,");
					overall--;
				} else {
					pr.print("0,");
				}
			}
			
			if (overall > 0) {
				pr2.println(sum + ",1");
			} else if (overall < 0) {
				pr2.println(sum + ",-1");
			} else {
				pr2.println(sum + ",0");
			}
			
			pr.println(sum);
			System.out.println(line + "," + sum);

		}
		br.close();
		pr.close();
		pr2.close();
	}
}