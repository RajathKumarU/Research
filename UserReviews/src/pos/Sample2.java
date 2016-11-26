package pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Sample2 {
	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(
				"C:/Users/RajathMeghana/Downloads/feedback/transcendHDD_POS_tagged.txt"));
		PrintWriter pw = new PrintWriter(new FileWriter(
				"C:/Users/RajathMeghana/Downloads/feedback/transcendHDD_NOUNS.txt"), true);
		String line = "", word = "";
		while ((line = br.readLine()) != null) {
			String[] linesplit = line.split(" ");

			for (int i = 0; i < linesplit.length; i++) {
				if (linesplit[i].contains("_NN")) {
					word = linesplit[i].split("_")[0].toLowerCase();
					pw.println(word);
				}
			}

		}
		br.close();
		pw.close();
	}
}
