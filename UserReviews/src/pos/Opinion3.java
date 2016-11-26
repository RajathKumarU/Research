//First occurence of adjectives with window size 5 taken and adverbs if present around adjectives with window size 1

package pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Opinion3 {
	public static void main(String[] args) {
		try {

			BufferedReader nouns = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Rapid/rapid_mi4_noun_freq.txt"));
			BufferedReader reviews = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/mi4_POS_tagged_lowercase.txt"));
			PrintWriter p = new PrintWriter(
					new FileWriter(
							"C:/Users/RajathMeghana/Downloads/feedback/Rapid/rapid_mi4_opinions_3.txt"));

			String nouns_req = "", line = "";

			while ((line = nouns.readLine()) != null) {
				nouns_req += line.split(" ")[0].toLowerCase() + ",";
			}
			nouns_req = nouns_req.substring(0, nouns_req.length() - 1);

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

							boolean adj_flag = false, avb_flag = false;
							// String s[] = new String[3];
							// s[0] = "";
							// s[1] = "";
							// int ndx = 0;
							for (int k = j - 1; k >= start; k--) {
								if (words[k].contains("_jj")
										&& adj_flag == false) {
									adj_flag = true;

									if (words[k + 1].contains("_rb")
											&& words[k - 1].contains("_rb")
											&& k != 0 && avb_flag == false) {
										avb_flag = true;
										p.print(words[k - 1].split("_")[0]
												+ " " + words[k].split("_")[0]
												+ " "
												+ words[k + 1].split("_")[0]
												+ ",");
										// s[ndx++] = words[k].split("_")[0];
										// s[ndx++] = words[k +
										// 1].split("_")[0];
									} else if (k != 0
											&& words[k - 1].contains("_rb")
											&& avb_flag == false) {
										avb_flag = true;
										p.print(words[k - 1].split("_")[0]
												+ " " + words[k].split("_")[0]
												+ ",");
										// s[ndx++] = words[k -
										// 1].split("_")[0];
										// s[ndx++] = words[k].split("_")[0];
									} else if (words[k + 1].contains("_rb")
											&& avb_flag == false) {
										avb_flag = true;
										p.print(words[k].split("_")[0] + " "
												+ words[k + 1].split("_")[0]
												+ ",");
									} else {
										p.print(words[k].split("_")[0] + ",");
										// s[ndx++] = words[k].split("_")[0];
									}
								}

							}
							// String val = "";
							// if (ndx == 3) {
							// val = s[2] + " " + s[1] + " " + s[0] + ",";
							// } else if (ndx == 2) {
							// val = s[1] + " " + s[0] + ",";
							// } else if (ndx == 1) {
							// val = s[0] + ",";
							// }
							// p.print(val);

							p.print(words[j].split("_")[0]);// print NOUN

							adj_flag = false;
							avb_flag = false;
							for (int k = start2; k < end; k++) {

								if (words[k].contains("_jj")
										&& adj_flag == false) {
									adj_flag = true;

									if (words[k - 1].contains("_rb")
											&& (k != end - 1)
											&& words[k + 1].contains("_rb")
											&& avb_flag == false) {
										avb_flag = true;
										p.print(","
												+ words[k - 1].split("_")[0]
												+ " " + words[k].split("_")[0]
												+ " "
												+ words[k + 1].split("_")[0]);
									} else if ((k != end - 1)
											&& words[k + 1].contains("_rb")
											&& avb_flag == false) {
										avb_flag = true;
										p.print("," + words[k].split("_")[0]
												+ " "
												+ words[k + 1].split("_")[0]);
									} else if (words[k - 1].contains("_rb")
											&& avb_flag == false) {
										avb_flag = true;
										p.print(","
												+ words[k - 1].split("_")[0]
												+ " " + words[k].split("_")[0]);
									} else {
										p.print("," + words[k].split("_")[0]);
									}
								}

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
