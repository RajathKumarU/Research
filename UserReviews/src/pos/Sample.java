package pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Sample {
	public static void main(String[] args) throws IOException {

		// Initialize the
		MaxentTagger tagger = new MaxentTagger(
				"src/files/english-left3words-distsim.tagger");
		String line = "";
		String tagged = "";
		BufferedReader br = new BufferedReader(new FileReader(
				"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mypassportHD/crawledreviews.txt"));
		PrintWriter p = new PrintWriter(
				new FileWriter(
						"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mypassportHD/flipkart_mypassportHD_POS_tagged.txt"));
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
}
