import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Test {
	public static void main(String[] args) {
		try {
			MaxentTagger tagger = new MaxentTagger(
					"src/files/english-left3words-distsim.tagger");

			BufferedReader br = new BufferedReader(new FileReader(
					"src/files/semioutputs/outputcs.txt"));
			PrintWriter p = new PrintWriter(new FileWriter(
					"src/files/semioutputs/outputcs1.txt"));

			String line = "";
			while ((line = br.readLine()) != null) {
				String[] linesplit = line.split(" ");
				int i = 0;
				while (i < linesplit.length) {
					if (linesplit[i].contains("_NN")) {
						if (i < (linesplit.length - 1)
								&& linesplit[i + 1].contains("_NN")) {
							if (i < (linesplit.length - 3)
									&& linesplit[i + 2].contains("_NN")) {
								p.println(linesplit[i].split("_")[0] + " "
										+ linesplit[i + 1].split("_")[0] + " "
										+ linesplit[i + 2].split("_")[0]);
								i += 3;
							} else if (i < (linesplit.length - 2)) {
								p.println(linesplit[i].split("_")[0] + " "
										+ linesplit[i + 1].split("_")[0]);
								i += 2;
							}
						}
						if (i > 2 && linesplit[i - 1].contains("_JJ")) {
							p.println(linesplit[i - 1].split("_")[0] + " "
									+ linesplit[i].split("_")[0]);
						} else if (i > 2 && linesplit[i - 2].contains("_JJ")) {
							p.println(linesplit[i - 2].split("_")[0] + " "
									+ linesplit[i].split("_")[0]);
						}
					}
					i++;
				}
			}
			br.close();
			p.close();

			// Reduce function is called here
			try {
				reduce(args[0]);
			} catch (ArrayIndexOutOfBoundsException e) {
				reduce("");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void reduce(String inputFileName) {
		try {
			BufferedReader brd = new BufferedReader(new FileReader(
					"src/files/semioutputs/output1.txt"));
			HashMap<String, String> freq = new HashMap<String, String>();
			BufferedReader br1 = new BufferedReader(new FileReader(
					"src/files/semioutputs/outputcs1.txt"));
			File f1 = new File("src/files/semioutputs/output1.txt");
			String line1 = "";
			String[] keywrds = new String[5000];
			int i = 0;
			while ((line1 = br1.readLine()) != null) {
				keywrds[i] = line1;
				i++;
			}
			String[] dup = new String[1000];
			for (int j = 0; j < 1000; j++) {
				dup[j] = "";
			}
			int m = 0;

			write: for (int j = 0; j < i; j++) {
				for (int k = 0; k < i; k++) {
					int c = 0;
					if (keywrds[j].contains(keywrds[k]) && j != k) {
						for (int l = 0; l < dup.length; l++) {
							if (dup.length != 0) {
								if (dup[l].equalsIgnoreCase(keywrds[k])) {
									c = 1;
									break;
								}
							}
						}
						if (c == 0) {
							dup[m] = keywrds[k].toLowerCase();
							// System.out.println(dup[m]);
							m++;
						}

						continue write;
					}
				}
			}
			String linerp = "";
			String title = "", abs = "", keywords = "", conc = "", ref = "", intro = "";
			String whole = FileUtils.readFileToString(f1, "UTF-8");
			whole = whole.toLowerCase();
			title = whole.split("titlerp")[0].trim();// fr=1
			abs = whole.split("abstractrp")[0].split("titlerp")[1].trim();// fr=5
			intro = whole.split("abstractrp")[1].split("introductionrp")[0]
					.trim();
			keywords = whole.split("keywordsrp")[0].split("abstractrp")[1]
					.trim();// fr=2
			conc = whole.split("conclusionrp")[0].split("introductionrp")[1]
					.trim();// fr=4
			ref = whole.split("referencesrp")[0].split("conclusionrp")[1]
					.trim();// fr=3
			for (int j = 0; j < dup.length; j++) {
				int fr = 0;
				if (title.contains(dup[j]) && dup[j] != "") {
					fr = fr + 1;
				}
				if (abs.contains(dup[j]) && dup[j] != "") {
					fr = fr + 5;
				}
				if (intro.contains(dup[j]) && dup[j] != "") {
					fr = fr + 5;
				}
				if (keywords.contains(dup[j]) && dup[j] != "") {
					fr = fr + 2;
				}
				if (conc.contains(dup[j]) && dup[j] != "") {
					fr = fr + 4;
				}
				if (ref.contains(dup[j]) && dup[j] != "") {
					fr = fr + 3;
				}
				freq.put(dup[j], String.format("%04d", fr));
			}
			if (freq.containsKey(""))
				freq.remove("");

			int[][] arr = new int[freq.size()][6];
			int cnt = 0;
			for (int o = 0; o < freq.size(); o++) {
				for (int j = 0; j < 6; j++) {
					arr[o][j] = 0;
				}
			}

			for (Map.Entry<String, String> entry : freq.entrySet()) {

				String key = entry.getKey();
				// int tab = Integer.parseInt(entry.getValue());
				String lined = "";
				int sum = 0;
				if (title.contains(key)) {
					arr[cnt][0] = 1;
				}
				if (abs.contains(key)) {
					arr[cnt][1] = 5;
				}
				if (keywords.contains(key)) {
					arr[cnt][2] = 2;
				}
				if (intro.contains(key)) {
					arr[cnt][3] = 5;
				}
				if (conc.contains(key)) {
					arr[cnt][4] = 4;
				}
				if (ref.contains(key)) {
					arr[cnt][5] = 3;
				}

				cnt = cnt + 1;
			}

			int c = 0;
			for (Map.Entry<String, String> entry : freq.entrySet()) {
				for (int j = 0; j < 6; j++) {
					System.out.print(arr[c][j] + " ");
				}
				System.out.println(entry.getKey() + " ");
				System.out.println();
				c++;
			}

			int[] a = new int[100];
			HashMap<String, String> ass = new HashMap<String, String>();

			int p = 0;
			for (Map.Entry<String, String> entry : freq.entrySet()) {
				if (p == freq.size() - 1) {
					break;
				}

				String key = entry.getKey();
				int q = 0;
				for (Map.Entry<String, String> entry2 : freq.entrySet()) {
					if (q <= p) {
						q++;
						continue;
					}
					String key2 = entry2.getKey();
					int sum = 0;
					for (int j = 0; j < 6; j++) {
						if (arr[p][j] > 0 && arr[q][j] > 0) {
							// System.out.println(dup[p]+","+dup[q]+"---"+arr[p][j]+","+arr[q][j]);
							sum = sum + arr[p][j];
						}
					}
					ass.put(key + "," + key2, String.format("%04d", sum));
					q++;
				}
				p++;
			}

			HashMap<String, String> topN = getTopNvalues(freq, 6); // new method
																	// added
			freq = sortByValues(freq);
			for (Map.Entry<String, String> entry : freq.entrySet()) {
				String key = entry.getKey();
				int tab = Integer.parseInt(entry.getValue());
				if (key != "") {
					System.out.println(key + " - " + tab);
				}

			}

			System.out
					.println("------------------------------------------------");

			int total_freq = 0;

			// To store words and their frequency of all associations containing
			// that word
			HashMap<String, String> word_freq = new HashMap<String, String>();
			HashMap<String, String> support = new HashMap<String, String>();
			HashMap<String, String> confidence = new HashMap<String, String>();

			ass = sortByValues(ass);
			for (Map.Entry<String, String> entry : ass.entrySet()) {
				String key = entry.getKey();
				int tab = Integer.parseInt(entry.getValue());
				if (key != "") {
					// System.out.println(key + " - " + tab);
					total_freq += tab;
				}

				String word1 = key.split(",")[0].trim();
				String word2 = key.split(",")[1].trim();
				if (word_freq.containsKey(word1)) {
					int val = Integer.parseInt(word_freq.get(word1));
					word_freq
							.replace(word1, String.format("%04d", (val + tab)));
				} else {
					word_freq.put(word1, String.format("%04d", tab));
				}

				if (word_freq.containsKey(word2)) {
					int val = Integer.parseInt(word_freq.get(word2));
					word_freq
							.replace(word2, String.format("%04d", (val + tab)));
				} else {
					word_freq.put(word2, String.format("%04d", tab));
				}
			}

			for (Map.Entry<String, String> entry : ass.entrySet()) {
				String key = entry.getKey();
				int tab = Integer.parseInt(entry.getValue());
				int val1 = Integer.parseInt(word_freq.get(key.split(",")[0]
						.trim()));
				int val2 = Integer.parseInt(word_freq.get(key.split(",")[1]
						.trim()));

				if (key != "") {
					System.out.println(key + " - " + tab);
					support.put(key,
							String.format("%.6f", ((double) tab / total_freq)));
					confidence.put(key, String.format("%.6f",
							((double) tab / (val1 + val2))));
				}
			}

			// System.out
			// .println("\n------------------------------------------------------\n\nSUPPORT\n\n");
			// support = sortByValues(support);
			// for (Map.Entry<String, String> entry : support.entrySet()) {
			// String key = entry.getKey();
			// double tab = Double.parseDouble(entry.getValue());
			//
			// System.out.println(key + " - " + tab);
			// }

			System.out
					.println("\n------------------------------------------------------\n\nCONFIDENCE\n\n");
			confidence = sortByValues(confidence);
			for (Map.Entry<String, String> entry : confidence.entrySet()) {
				String key = entry.getKey();
				double tab = Double.parseDouble(entry.getValue());

				System.out.println(key + " - " + tab);
			}

			br1.close();

			// calling reduceAgain() to reduce words to 4-5 words
			reduceAgain(inputFileName,title, ass);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private static void reduceAgain(String inputFileName,String title,
			HashMap<String, String> associatedWords) throws IOException {
		associatedWords = sortByValues(associatedWords);
		ArrayList<String> bow = new ArrayList<String>();

		PrintWriter p = new PrintWriter(new FileWriter(
				"src/files/semioutputs/allBOW.txt", true));

		for (Map.Entry<String, String> entry : associatedWords.entrySet()) {
			String key = entry.getKey();
			int val = Integer.parseInt(entry.getValue());
			String w1 = key.split(",")[0];
			String w2 = key.split(",")[1];

			if (!bow.contains(w1))
				bow.add(w1);
			if (!bow.contains(w2))
				bow.add(w2);
			if (bow.size() >= 4)
				break;
		}

		//p.print(inputFileName + "->");
		p.print(title.replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("\t", " ").trim() + "->");
		for (int i = 0; i < bow.size(); i++) {
			p.print(bow.get(i) + ",");
		}
		p.println();
		p.close();

	}

	public static HashMap<String, String> getTopNvalues(
			HashMap<String, String> map, int n) {
		map = sortByValues(map);
		String removeKeys = "";

		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			int val = Integer.parseInt(entry.getValue());
			if (val <= 3) {
				removeKeys += key + ",";
			}
		}

		if (removeKeys.length() > 0) {
			String keys[] = removeKeys.substring(0, removeKeys.length() - 1)
					.split(",");
			for (int i = 0; i < keys.length; i++) {
				map.remove(keys[i]);
			}
		}

		if (map.size() == 0) {
			System.err.println("Warning!: No words with frequrncy > 3");
			return null;
		}

		System.out.println("\n\n");
		if (map.size() < n) {
			String key = "";
			String firstKey = "";
			int val = 0;
			int count = 0;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				key = entry.getKey();
				if (count == 0) {
					firstKey = key;
				}

				val = Integer.parseInt(entry.getValue());

				System.out.print("\"" + key + "\",");
				count++;
			}
			while (count < n) {
				System.out.print("\"" + firstKey + "\",");
				count++;
			}
			System.out.println("\n\n\n\n");
		} else {
			int count = 0;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				int val = Integer.parseInt(entry.getValue());

				System.out.print("\"" + key + "\",");
				count++;
				if (count == n)
					break;
			}
			System.out.println("\n\n\n\n");
		}

		return map;
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
