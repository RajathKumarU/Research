import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class Posop {
	public static void main(String[] args) throws Exception {

		// BufferedReader br = new BufferedReader(new FileReader(
		// "src/files/semioutputs/output2.txt"));
		// PrintWriter pw = new PrintWriter(new FileWriter(
		// "src/files/semioutputs/nouns.txt"), true);
		// String line = "", word = "";
		// while ((line = br.readLine()) != null) {
		// String[] linesplit = line.split(" ");
		//
		// for (int i = 0; i < linesplit.length; i++) {
		// if (linesplit[i].contains("_NN")) {
		// word = linesplit[i].split("_")[0].toLowerCase();
		// pw.println(word);
		// }
		// }
		//
		// }
		// br.close();
		// pw.close();

		BufferedReader br2 = new BufferedReader(new FileReader(
				"src/files/semioutputs/nouns.txt"));

		HashMap<String, String> hypernyms_freq = new HashMap<String, String>();

		System.setProperty("wordnet.database.dir",
				"C:/Program Files (x86)/WordNet/2.1/dict/");
		NounSynset nounSynset;
		// NounSynset[] hyponyms;

		WordNetDatabase database = WordNetDatabase.getFileInstance();
		String noun = "";
		int count = 0;

		read: while ((noun = br2.readLine()) != null) {
			++count;
			Synset[] synsets = database
					.getSynsets(noun.trim(), SynsetType.NOUN);
			// System.out.println(count + "-" + noun);

			for (int i = 0; i < synsets.length; i++) {
				nounSynset = (NounSynset) (synsets[i]);
				String hypernyms[] = null;

				try {
					hypernyms = nounSynset.getHypernyms()[0].getWordForms();
				} catch (ArrayIndexOutOfBoundsException a) {
					continue read;
				}

				String[] synonyms = nounSynset.getWordForms();

				for (int j = 0; j < hypernyms.length; j++) {
					if (hypernyms_freq.containsKey(hypernyms[j])) {
						String value = hypernyms_freq.get(hypernyms[j]);
						value = (String.format("%04d",
								(Integer.parseInt(value.split(",")[0]) + 1)))
								+ value.substring(value.indexOf(","));

						if (!value.contains(noun)) {
							value += "," + noun;
						}

						hypernyms_freq.put(hypernyms[j], value);
					} else {
						String value = String.format("%04d", 1) + "," + noun;
						hypernyms_freq.put(hypernyms[j], value);
					}

				}

			}

		}
		hypernyms_freq = sortByValues(hypernyms_freq);
		for (String key : hypernyms_freq.keySet()) {
			System.out.println(key + " - " + hypernyms_freq.get(key));
		}

		br2.close();

	}

	public static HashMap<String, String> sortByValues(
			HashMap<String, String> map) {
		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o2, Object o1) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}
}
