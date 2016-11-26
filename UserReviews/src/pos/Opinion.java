//All adjectives and adverbs with window size 5 taken

package pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Opinion {
	public static void main(String[] args) {
		try {

			BufferedReader nouns = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/yureka_noun_freq.txt"));
			BufferedReader reviews = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/yureka_POS_tagged.txt"));
			PrintWriter p = new PrintWriter(
					new FileWriter(
							"C:/Users/RajathMeghana/Downloads/feedback/yureka_opinions.txt"));

			String nouns_req = "", line = "";

			while ((line = nouns.readLine()) != null) {
				nouns_req += line.split(" ")[0] + ",";
			}
			nouns_req = nouns_req.substring(0, nouns_req.length() - 1);

			String noun_arr[] = nouns_req.split(",");

			while ((line = reviews.readLine()) != null) {
				line = line.trim();
				String words[] = line.split(" ");
				for (int i = 0; i < noun_arr.length; i++) {
					for (int j = 0; j < words.length; j++) {
						if ((words[j].contains(noun_arr[i]))
								&& (words[j].contains("_NN"))
								&& (!words[j].contains("http://"))
								&& (words[j].length() < 50)) {
							int start = j - 5, start2 = j + 1, end = j + 6;

							p.print("{");
							System.out.print("{");

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

							for (int k = start; k < j; k++) {
								if (words[k].contains("_JJ")
										|| words[k].contains("_RB")) {

									p.print(words[k].split("_")[0] + ",");
									System.out.print(words[k].split("_")[0]
											+ ",");
								}
							}
							p.print(words[j].split("_")[0]);
							System.out.print(words[j].split("_")[0]);
							for (int k = start2; k < end; k++) {

								if (words[k].contains("_JJ")
										|| words[k].contains("_RB")) {

									p.print("," + words[k].split("_")[0]);
									System.out.print(","
											+ words[k].split("_")[0]);
								}
							}
							p.print("}");
							System.out.print("}");
						}
					}
				}
				p.println();
				System.out.println();
			}

			p.close();
			reviews.close();
			nouns.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
