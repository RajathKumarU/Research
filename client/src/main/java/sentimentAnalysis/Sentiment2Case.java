package sentimentAnalysis;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.parameters.SentimentParams;

public class Sentiment2Case {

	public static void main(String args[]) throws Exception {
		String product = "galaxy_tab4_spam100_reviews";
		try {
			FileOutputStream fos2 = new FileOutputStream("C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/semantic_extended/"
					+ product + "_filtered_2.txt", false);
			FileOutputStream fos1 = new FileOutputStream("C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/semantic_extended/"
					+ product + "_filtered.txt", false);
			// Scanner k= new Scanner(new
			// File("C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/semantic_extended/"+product+"_ecommerce_all.txt"));
			Scanner k = new Scanner(new File("C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/semantic_extended/" + product + ".txt"));
			// String rev[] = file.split("\n");
			TextAPIClient client[] = new TextAPIClient[6];
			client[0] = new TextAPIClient("ba34697c",
					"2e8ee6ebae9c87dbf8640001a0c40fd4");// -->Ani
			client[1] = new TextAPIClient("f090477c",
					"1e5a83402308292e4332994fa81f5b5f");// --->rajath
			client[2] = new TextAPIClient("1ed17b9c",
					"83c5ee5d97e3d2790b3f5376191d7eaf");// -->anil
			client[3] = new TextAPIClient("f2601798",
					"78ddaea3aa5f749c338654fcbf5cef22");// --->anand
			client[4] = new TextAPIClient("e853721a",
					"38928d046f124744ae9e71c812ac964e"); // --->brad_cooper
			client[5] = new TextAPIClient("993a9f88",
					"cb2d1a9ae91207cd9dadc8affa5326b8");// -->rajath2
			int index = 0;
			boolean flag = false;
			String rev = "";
			int i = 0;
			/*
			 * for(int a = 1 ; a<=3988 ; a++) k.nextLine();
			 * /*k.skip(Pattern.compile("(?:.*\\r?\\n|\\r){1000}"));
			 * k.skip(Pattern.compile("(?:.*\\r?\\n|\\r){1000}"));
			 * k.skip(Pattern
			 * .compile("(?:.*\\r?\\n|\\r){1000}"));k.skip(Pattern.
			 * compile("(?:.*\\r?\\n|\\r){1000}"));
			 * k.skip(Pattern.compile("(?:.*\\r?\\n|\\r){1000}"));
			 * k.skip(Pattern.compile("(?:.*\\r?\\n|\\r){466}"));
			 */
			int count = 0;
			while (k.hasNext()) {
				i++;
				if (!flag)
					rev = k.nextLine();
				try {
					String r[] = rev.split("\\|");
					int p_rating = (int) Double.parseDouble(r[0].trim());
					SentimentParams.Builder builder = SentimentParams
							.newBuilder();
					builder.setText(r[1]);
					builder.setUrl(null);
					builder.setMode("document");
					SentimentParams sp = builder.build();

					com.aylien.textapi.responses.Sentiment sent = client[index]
							.sentiment(sp);
					System.out.println("here" + r[1] + " " + r[0]);
					System.out.println("Sentiment = " + sent.getPolarity()
							+ "\tPolarity" + sent.getPolarityConfidence());
					double pol = sent.getPolarityConfidence();
					String polarity = sent.getPolarity();
					int stars;
					if (polarity.equals("positive")
							|| polarity.equals("neutral"))
						stars = (int) Math.round(pol * 5);
					else {
						stars = (int) Math.round((1 - pol) * 5);
						if (stars == 0)
							stars = 1;
					}
					// System.out.println("For instance "+(i+1)+", the given stars -->"+stars+"\n");
					// int valid = 1;
					// Case1 ---> less than 3
					// Case2---->less than 2
					if (Math.abs(p_rating - stars) < 3)
						fos1.write((rev.substring(2) + "\n").getBytes());
					if (Math.abs(p_rating - stars) < 2)
						fos2.write((rev.substring(2) + "\n").getBytes());
					// valid = 0;
					/*
					 * else valid =1;
					 */
					// fos.write((stars+"|"+rev+"|"+"\n").getBytes());
					// System.out.println("instance "+i+" user's rating "+p_rating+" value obtained "+stars);
					flag = false;
					count = 0;
				} catch (NumberFormatException e) {
					System.out.println("empty line");
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("empty line");
				} catch (Exception e) {
					count++;
					if (count >= 10) {
						index = (index + 1) % client.length;
						count = 0;
					}
					e.printStackTrace();
					Thread.sleep(1000 * 5);
					System.out.println("Timed out!");
					flag = true;
					i--;
				}
			}
			System.out.println(k.hasNextLine());
			k.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
