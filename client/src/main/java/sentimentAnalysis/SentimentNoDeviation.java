package sentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.parameters.SentimentParams;

public class SentimentNoDeviation {

	public static void main(String args[]) {
		// System.out.println("hello");
		TextAPIClient client = new TextAPIClient("e853721a",
				"38928d046f124744ae9e71c812ac964e");
		boolean flag = true;
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/semantic_extended/tplink_spam100_reviews.txt"));
			PrintWriter pw = new PrintWriter(
					new FileWriter(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/semantic_extended/tplink_spam100_reviews_rated_extended.txt"));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				int star_val = (int) Integer.parseInt(String.valueOf(line
						.split("\\|")[0].trim()));
				line = line.split("\\|")[1];

				flag = true;
				while (flag) {
					flag = false;
					try {
						SentimentParams.Builder builder = SentimentParams
								.newBuilder();
						builder.setText(line);
						builder.setUrl(null);
						builder.setMode("document");
						SentimentParams sp = builder.build();

						com.aylien.textapi.responses.Sentiment sent = client
								.sentiment(sp);
						String polarity = sent.getPolarity();
						double pol = sent.getPolarityConfidence();

						int stars;
						if (polarity.equals("positive")
								|| polarity.equals("neutral"))
							stars = (int) Math.round(pol * 5);
						else {
							stars = (int) Math.round((1 - pol) * 5);
							if (stars == 0)
								stars = 1;
						}

						if (Math.abs(star_val - stars) < 3) {
							if (checkIFNounPresent(line.toLowerCase())) {
								pw.println((5-Math.abs(star_val - stars)) + "|" + line);
							} else {
								pw.println("2|" + line);
							}
						} else {
							pw.println((5-Math.abs(star_val - stars)) + "|" + line);
						}

					} catch (Exception e) {
						flag = true;
						e.printStackTrace();
					}
				}
				System.out.println(line);
			}
			br.close();
			pw.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}

	public static com.aylien.textapi.responses.Sentiment getSentiment(String str) {
		System.out.println("Hello");
		try {
			TextAPIClient client = new TextAPIClient("e853721a",
					"38928d046f124744ae9e71c812ac964e");
			SentimentParams.Builder builder = SentimentParams.newBuilder();
			builder.setText(str);
			builder.setUrl(null);
			builder.setMode("document");
			SentimentParams sp = builder.build();

			com.aylien.textapi.responses.Sentiment sent = client.sentiment(sp);
			return sent;
		} catch (Exception e) {
			return new com.aylien.textapi.responses.Sentiment();
		}
	}

	public static boolean checkIFNounPresent(String review) {
		String nouns = "featur,qualit,firmwar,perform,installat,coverag,tp-link,strength,servic,antenna,network,issu,setup,internet,problem,devic,connect,speed,wifi,pric,signal,rang,product,router";
		String noun_arr[] = nouns.split(",");

		for (int i = 0; i < noun_arr.length; i++) {
			if (review.contains(noun_arr[i])) {
				return true;
			}
		}
		return false;
	}

}