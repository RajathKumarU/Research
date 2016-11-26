package sentimentAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class MainClass {
	static HashMap<String, Integer> ht;

	public static void main(String[] args) {
		try {
			// extracting stemmed nouns to an array

			// GIVE THE PATH TO THE FILE HERE
			// Scanner k = new Scanner(new File("ipfile"));
			String nouns = new Scanner(
					new File(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/New/mi4i_noun_freq.txt"))
					.useDelimiter("\\Z").next();

			String temp[] = nouns.split("\n");

			ht = new HashMap<String, Integer>();
			for (int i = 0; i < temp.length; i++)
				ht.put(temp[i].split(" ")[0], 0);
			Set n = ht.keySet();
			Iterator itr = n.iterator();
			while (itr.hasNext())
				System.out.println(ht.get(itr.next().toString()));

			// Reading the opinion file
			/*
			 * String opn = new Scanner( new File(
			 * "C:/Users/RajathMeghana/Downloads/feedback/latest/mi4i_opinions_4_old.txt"
			 * )) .useDelimiter("\\Z").next(); String opinion[] =
			 * opn.split("\n");
			 */
			Scanner sc = new Scanner(
					new File(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/Old_Wrong/test/test_review_opinions_4.txt"));
			// training the opinion classifier
			// String x[] ={"C:/Users/Aniruddha/Downloads/JARs"};/*Dont change
			// this!*/
			// PolarityBasic pb = new PolarityBasic(x);
			// pb.train();
			String url = "http://sentiment.vivekn.com/api/text/"; // <<----Don't
																	// change
																	// this
			Vivekn sa = new Vivekn(url);
			// opening file to write
			FileOutputStream fos = new FileOutputStream("flipkart_mi4i_1-950.arff", false);

			fos.write(new String("@relation 'user-reviews'\n\n").getBytes());

			// BufferedReader reader = new BufferedReader(
			// new FileReader(
			// "C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_filtered_reviews_test.txt"));

			String writtenLine = "";
			Iterator itr2 = n.iterator();
			while (itr2.hasNext()) {
				String noun = itr2.next().toString();
				writtenLine = writtenLine + "@attribute " + noun + " numeric\n";
			}
			writtenLine = writtenLine
					+ "@attribute rating {1,2,3,4,5}\n\n@data\n\n";
			writtenLine = writtenLine + "\n";
			fos.write(writtenLine.getBytes());

			int count = 0;
			boolean flag = false;
			String opinion = "";
			while (sc.hasNext()) {
				// while (/* int i = 0; i < opinion.length; i++, count++ */sc
				// .hasNext() && ((line = reader.readLine()) != null)) {
				try {
					// System.out.println("iteration "+i);
					if (!flag)
						opinion = sc.nextLine();
					System.out.println(opinion);
					opinion = opinion.replace("}{", " ");
					opinion = opinion.replace("{", "");
					opinion = opinion.replace("}", "");
					String atts[] = opinion.split(" ");
					// System.out.println(atts[i]);

					for (int j = 0; j < atts.length; j++) {
						// System.out.println(atts[j]);
						String inatts[] = atts[j].split(",");

						// find noun index
						int nindex = 0;
						String stemmedNoun = "";
						for (int k1 = 0; k1 < inatts.length; k1++)
							L: {
								Iterator itr1 = n.iterator();
								// System.out.println(inatts[k]);
								while (itr1.hasNext()) {
									String cur = itr1.next().toString();
									// System.out.println(cur);
									if (inatts[k1].contains(cur)) {
										nindex = k1;
										stemmedNoun = cur;
										break L;
									}

								}
							}
						try {
							int curVal = ht.get(stemmedNoun);
							System.out.println("be4 loop" + stemmedNoun + " "
									+ nindex);

							if (inatts.length != 1) {

								for (int k = 0; k < inatts.length; k++)
									if (k == nindex)
										continue;
									else {

										// System.out.println(inatts[k]);
										// curVal =
										// curVal+pb.evaluate(inatts[k]);
										curVal = curVal
												+ sa.getSentiment(inatts[k]);
									}

							}
							System.out.println(stemmedNoun + " " + curVal);
							ht.put(stemmedNoun, curVal);
						} catch (Exception e) {
						}

						/*
						 * String s = new Scanner(System.in).next();
						 * pb.evaluate(s);
						 */
						// System.out.println(ht.get("heat"));
						// Write to file here
					}

					writtenLine = "";
					itr2 = n.iterator();
					while (itr2.hasNext()) {
						String noun = itr2.next().toString();
						writtenLine = writtenLine + ht.get(noun) + ",";
					}
					writtenLine = writtenLine.substring(0,
							writtenLine.length() - 1);
					writtenLine = writtenLine + "\n";
					// if (count < 99) {
					// writtenLine = writtenLine + ",5\n";
					// } else if (count < 170) {
					// writtenLine = writtenLine + ",4\n";
					// } else {
					// writtenLine = writtenLine + ",3\n";
					// }

					fos.write(writtenLine.getBytes());

					Iterator itr1 = n.iterator();
					while (itr1.hasNext())
						ht.put(itr1.next().toString(), 0);
					
					flag = false;
				} catch (Exception e) {
					e.printStackTrace();
					flag = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static void initializeHashTable() {
		Set n = ht.entrySet();
		Iterator itr = n.iterator();
		while (itr.hasNext())
			ht.put(itr.next().toString(), 0);
	}
}
