package pos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Process2 {

	public static void posTagging(String product) throws Exception {
		MaxentTagger tagger = new MaxentTagger(
				"english-left3words-distsim.tagger");
		String line = "";
		String tagged = "";
		BufferedReader br = new BufferedReader(new FileReader(product + ".txt"));
		PrintWriter p = new PrintWriter(new File(product + "_POS_tagged.txt"),
				"UTF-8");
		while ((line = br.readLine()) != null) {
			line = line.substring(2, line.length());
			if (line.contains(".")) {
				StringBuilder str = new StringBuilder(line);
				int indx = 0;
				while (indx < str.lastIndexOf(".")) {
					int pos = str.indexOf(".", indx);
					str.insert(pos + 1, ' ');
					indx = pos + 1;
				}
				line = str.toString();
				// System.out.println(line);
			}

			tagged = tagger.tagString(line);
			p.println(tagged.trim());
		}
		br.close();
		p.close();
	}

	public static void toLowerCase(String product) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(product
					+ "_POS_tagged.txt"));
			PrintWriter p = new PrintWriter(new File(product
					+ "_POS_tagged_lowercase.txt"), "UTF-8");
			String line = "";

			while ((line = reader.readLine()) != null) {
				p.println(line.toLowerCase());
			}

			p.close();
			reader.close();

			// to replace words with stemmed words
			reader = new BufferedReader(new FileReader(product
					+ "_POS_tagged_lowercase.txt"));
			BufferedReader reader2 = new BufferedReader(new FileReader(product
					+ "_noun_freq.txt"));
			p = new PrintWriter(new FileWriter(product
					+ "_POS_tagged_replaced.txt"));
			line = "";
			String nouns = "";

			while ((line = reader2.readLine()) != null) {
				nouns += line + ",";
			}
			String nouns_req[] = nouns.toLowerCase()
					.substring(0, nouns.length() - 1).split(",");
			for (int i = 0; i < nouns_req.length; i++)
				System.out.println(nouns_req[i]);
			Thread.sleep(10 * 1000);
			while ((line = reader.readLine()) != null) {
				String words[] = line.split(" ");
				for (int i = 0; i < words.length; i++) {
					for (int j = 0; j < nouns_req.length; j++) {
						String word = words[i].split("_")[0];
						if (word.contains(nouns_req[j])) {
							words[i] = nouns_req[j] + "_"
									+ words[i].split("_")[1];
							break;
						}
					}
				}
				for (int i = 0; i < words.length; i++) {
					p.print(words[i] + " ");
				}
				p.println();
			}

			reader.close();
			reader2.close();
			p.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ExtractOpinion(String product) {
		try {

			BufferedReader nouns = new BufferedReader(new FileReader(product
					+ "_noun_freq.txt"));
			BufferedReader reviews = new BufferedReader(new FileReader(product
					+ "_POS_tagged_replaced.txt"));
			PrintWriter p = new PrintWriter(new File(product
					+ "_opinions_4.txt"), "UTF-8");

			String nouns_req = "", line = "";

			while ((line = nouns.readLine()) != null) {
				nouns_req += line.toLowerCase() + ",";
				// nouns_req += line + ",";
			}
			nouns_req = nouns_req.substring(0, nouns_req.length() - 1);
			System.out.println(nouns_req);
			System.out.println();
			System.out.println();
			String noun_arr[] = nouns_req.split(",");

			while ((line = reviews.readLine()) != null) {
				line = line.trim();
				String words[] = line.split(" ");
				for (int i = 0; i < noun_arr.length; i++) {
					for (int j = 0; j < words.length; j++) {
						if ((words[j].contains(noun_arr[i]))
								&& (words[j].contains("_nn"))
								&& (!words[j].contains("http://"))
								&& (words[j].length() < 50)) {

							p.print("{");

							int start = j - 5, start2 = j + 1, end = j + 6;
							if (j - 5 < 0) {
								start = 0;
							} else {
								start = j - 5;
							}

							if (j == words.length - 1) {
								start2 = j;
								end = j;
							} else if (j + 6 >= words.length) {
								end = words.length;
							} else {
								start2 = j + 1;
								end = j + 6;
							}

							boolean adj_flag = false;

							LinkedList<String> l3 = new LinkedList<String>();
							LinkedList<String> l1 = new LinkedList<String>();
							LinkedList<String> l2 = new LinkedList<String>();

							l1: for (int k = j - 1; k >= start; k--) {
								System.out
										.println("1" + words[k].split("_")[0]);
								if (words[k].contains("_nn")
										&& nouns_req.contains(words[k]
												.split("_")[0])) {

									System.out.println("HELLO");
									break l1;
								} else if (words[k].contains("_nn")
										&& !nouns_req.contains(words[k]
												.split("_")[0]) && !adj_flag) {
									l3.addFirst(words[k].split("_")[0]);
									System.out.println(l3);
								} else if (words[k].contains("_nn")
										&& !nouns_req.contains(words[k]
												.split("_")[0]) && adj_flag) {
									l1.addFirst(words[k].split("_")[0]);
									System.out.println(l1);
								}

								if (words[k].contains("_jj")
										&& adj_flag == false) {
									adj_flag = true;

									int num = k;
									while (num != 0
											&& words[num - 1].contains("_jj")) {
										l2.addFirst(words[num - 1].split("_")[0]);
										num--;
									}
									while (num != 0
											&& words[num - 1].contains("_rb")) {
										l2.addFirst(words[num - 1].split("_")[0]);
										num--;
									}

									l2.addLast(words[k].split("_")[0]);

									num = k;
									while (words[num + 1].contains("_jj")) {
										l2.addLast(words[num - 1].split("_")[0]);
										num++;
									}
									while (words[num + 1].contains("_rb")) {
										l2.addLast(words[num + 1].split("_")[0]);
										num++;
									}
								}

							}
							while (!l1.isEmpty()) {
								String s = l1.removeFirst();
								p.print(s + ",");
								// System.out.print(s+",");
							}
							// System.out.println();
							while (!l2.isEmpty()) {
								String s = l2.removeFirst();
								p.print(s + ",");
								// System.out.print(s+",");
							}
							// System.out.println();
							while (!l3.isEmpty()) {
								String s = l3.removeFirst();
								p.print(s + ",");
								// System.out.print(s+",");
							}
							// System.out.println();

							p.print(words[j].split("_")[0]);// print NOUN
							// System.out.println(words[j].split("_")[0]);

							adj_flag = false;
							l2: for (int k = start2; k < end; k++) {

								if (words[k].contains("_nn")
										&& nouns_req.contains(words[k]
												.split("_")[0])) {
									System.out.println("HI");
									break l2;
								} else if (words[k].contains("_nn")
										&& !nouns_req.contains(words[k]
												.split("_")[0]) && !adj_flag) {
									l1.addLast(words[k].split("_")[0]);
									System.out.println(l1);
								} else if (words[k].contains("_nn")
										&& !nouns_req.contains(words[k]
												.split("_")[0]) && adj_flag) {
									l3.addLast(words[k].split("_")[0]);
									// System.out.println(l3);
								}

								if (words[k].contains("_jj")
										&& adj_flag == false) {
									adj_flag = true;

									int num = k;
									while (words[num - 1].contains("_jj")) {
										l2.addFirst(words[num - 1].split("_")[0]);
										num--;
									}
									while (words[num - 1].contains("_rb")) {
										l2.addFirst(words[num - 1].split("_")[0]);
										num--;
									}

									l2.addLast(words[k].split("_")[0]);

									num = k;
									while ((num != end - 1)
											&& words[num + 1].contains("_jj")) {
										l2.addLast(words[num - 1].split("_")[0]);
										num++;
									}
									while ((num != end - 1)
											&& words[num + 1].contains("_rb")) {
										l2.addLast(words[num + 1].split("_")[0]);
										num++;
									}
								}
							}
							while (!l1.isEmpty()) {
								String s = l1.removeFirst();
								p.print("," + s);
							}
							while (!l2.isEmpty()) {
								String s = l2.removeFirst();
								p.print("," + s);
							}
							while (!l3.isEmpty()) {
								String s = l3.removeFirst();
								p.print("," + s);
							}

							p.print("}");
						}
					}
				}
				p.println();
			}

			p.close();
			reviews.close();
			nouns.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static void removeRelatedNouns(String product)throws Exception {
	 * PrintWriter pw = new PrintWriter( new File(
	 * product+"_nouns.txt"),"UTF-8"); BufferedReader br = new BufferedReader(
	 * new FileReader(product+"_noun_freq.txt") ); String t = ""; while( (t=
	 * br.readLine())!=null) { if(!product.contains(t)) pw.println(t+"\n"); }
	 * pw.close(); }
	 */
	public static void getInstancesOnly(String product) throws Exception {

		PrintWriter pw = new PrintWriter(new File(product
				+ "_big_instances.arff"), "UTF-8");
		pw.println("@relation '" + product + "_big_cluster'\n");
		BufferedReader br = new BufferedReader(new FileReader(product
				+ "_noun_freq.txt"));
		BufferedReader reader = new BufferedReader(new FileReader(product
				+ "_big_cluster.txt"));
		// pw.println("@attributes");
		String ln = "";
		while ((ln = br.readLine()) != null) {
			pw.println("@attribute " + ln + " numeric");
		}
		pw.println("\n@data\n");
		while ((ln = reader.readLine()) != null)
			pw.println(ln.split("\\|")[1]);
		pw.close();
	}

	public static void convertFile(String product) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(product
				+ "_ecommerce_all.txt"));
		PrintWriter pw = new PrintWriter(new File(product + ".txt"), "UTF-8");
		String line = null;
		while ((line = br.readLine()) != null) {
			pw.println(line + "\n");
		}
		pw.close();
		br.close();
	}

	public static void main(String args[]) {
		String product = "macbook_pro";
		// String req = "Samsung Galaxy S6";
		System.out.println("Hello");
		try {
			// convertFile(product);
			/*
			 * System.out.println("Removing related nouns");
			 * Thread.sleep(1000*3); removeRelatedNouns(product);
			 */

			System.out.println("POS tagging");
			Thread.sleep(1000 * 3);
			posTagging(product);

			System.out.println("To Lower Case");
			Thread.sleep(1000 * 3);
			toLowerCase(product);

			System.out.println("Extracting opinion");
			Thread.sleep(1000 * 3);
			ExtractOpinion(product);

			System.out.println("generating arff");
			Thread.sleep(1000 * 3);
			sent.Sentiment.generateSentiment(product);

			System.out.println("Overall and score");
			score.main(new String[] { product });
			GenerateArff2.main(new String[] { product });
			/*
			 * System.out.println("Removing Zeros"); Thread.sleep(1000*3);
			 * RemoveZeros.removeZeros(product);
			 * 
			 * System.out.println("Clustering and sending top 10 to DB");
			 * Thread.sleep(1000*3); ClusterRating.Clustering(product);
			 * 
			 * System.out.println("FInding top Ten"); Thread.sleep(1000*3);
			 * FindTopTen.GenerateTopTen(product);
			 */
			//
			// getInstancesOnly(product);

			// ProsCons.prosCons( product , req);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
