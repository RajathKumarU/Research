package stopwords;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StopWordsRemoval {
	public static void main(String[] args) {
		try {
			BufferedReader reader_input = new BufferedReader(new FileReader(
					"C:/Users/RajathMeghana/Downloads/feedback/yureka.txt"));
			BufferedReader reader_stop = new BufferedReader(new FileReader(
					"src/stopwords/stopwords.txt"));
			PrintWriter p = new PrintWriter(new FileWriter("C:/Users/RajathMeghana/Downloads/feedback/yureka_stop_removed.txt"));

			String stopwords = "";
			String line = "";

			while ((line = reader_stop.readLine()) != null) {
				stopwords += line + ",";
			}
			
			System.out.println(stopwords);
			
			while ((line = reader_input.readLine()) != null) {
				line = line.replace(".", " ").replace(",", " ").replace("!", " ");
				String[] words = line.split(" ");
				String outline = "";

				for (int i = 0; i < words.length; i++) {
					byte b[] = words[i].getBytes("ISO-8859-1");
					words[i] = new String(b);
					if (!(stopwords.toLowerCase().contains(words[i].toLowerCase().trim()))) {
						outline += words[i] + " ";
						System.out.println(i+""+stopwords.toLowerCase()+words[i].toLowerCase());
					}
				}
				p.println(outline);
			}

			p.close();
			reader_input.close();
			reader_stop.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
