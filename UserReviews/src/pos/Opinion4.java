//First occurence of adjectives with window size 5 taken and 
//adverbs if present around adjectives with window size 1 and 
//also the nouns that are not attributes are considered.

package pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Opinion4 {
	public static void main(String[] args) {
		try {

			BufferedReader nouns = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mypassportHD/flipkart_mypassportHD_noun_freq.txt"));
			BufferedReader reviews = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mypassportHD/flipkart_mypassportHD_POS_tagged_replaced.txt"));
			PrintWriter p = new PrintWriter(
					new FileWriter(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mypassportHD/flipkart_mypassportHD_opinions_4.txt"));

			String nouns_req = "", line = "";

			while ((line = nouns.readLine()) != null) {
				nouns_req += line.split(" ")[0].toLowerCase() + ",";
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
								System.out.println("1"+words[k].split("_")[0]);
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
}
