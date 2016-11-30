package main;

import java.io.*;

import edu.smu.tspell.wordnet.*;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Syno {
	public static void main(String[] args) {
		try {
			// System.setProperty("wordnet.database.dir",
			// "C:/Program Files (x86)/WordNet/2.1/dict/");
			// NounSynset nounSynset;
			// NounSynset[] hyponyms;

			// WordNetDatabase database = WordNetDatabase.getFileInstance();
			// Synset[] synsets = database.getSynsets("reviews",
			// SynsetType.NOUN);

			File papers = new File("src/files/input/papers");
			String input_papers[] = papers.list();

			for (int i = 0; i < input_papers.length; i++) {

				BufferedReader paper_reader = new BufferedReader(
						new FileReader("src/files/input/papers/"
								+ input_papers[i]));
				PrintWriter writer = new PrintWriter(new FileWriter(
						"src/files/semioutputs/output1.txt"));
				String line = "";

				while ((line = paper_reader.readLine()) != null) {
					writer.println(line);
				}
				writer.close();

				MaxentTagger tagger = new MaxentTagger(
						"src/files/english-left3words-distsim.tagger");
				line = "";
				String tagged = "";
				BufferedReader br = new BufferedReader(new FileReader(
						"src/files/semioutputs/output1.txt"));
				// output1-> abstract,intro,conclusion
				PrintWriter p = new PrintWriter(new FileWriter(
						"src/files/semioutputs/outputcs.txt"));
				while ((line = br.readLine()) != null) {
					tagged = tagger.tagString(line);
					p.println(tagged);
				}
				br.close();
				p.close();

				String filename[] = new String[1];
				filename[0] = input_papers[i];
				Test.main(filename);

			}

			// for (int i = 0; i < synsets.length; i++) {

			// nounSynset = (NounSynset)(synsets[i]);
			// System.out.println(synsets[i]);
			// System.out.println(nounSynset.getWordForms()[0]);
			// System.out.println(nounSynset.getHypernyms()[0]);
			// hyponyms = nounSynset.getHyponyms();
			// System.err.println(nounSynset.getWordForms()[0] +
			// ": " + nounSynset.getDefinition() + ") has " + hyponyms.length +
			// " hyponyms");
			// }
			// Posop.main(null);
		} catch (Exception e) {
		}
	}
}
