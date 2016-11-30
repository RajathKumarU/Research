package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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

public class Cluster1 {

	public static void main(String[] args) {

		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/files/output/ClusterOut.txt"));
			PrintWriter formattedOut = new PrintWriter(new FileWriter("src/files/output/formattedMatchingOut.txt"));
			ArrayList<String> docs = new ArrayList<String>();
			HashMap<String, String> docPairScore = new HashMap<String, String>();
			HashMap<String, String> docsMatScore = new HashMap<String, String>();

			String line = "";
			while ((line = reader.readLine()) != null) {
				// String words[] = line.substring(0,
				// line.length()-1).split(",");

				docs.add(line.toLowerCase());
			}

			for (int i = 0; i < docs.size() - 1; i++) {

				if (docs.get(i).endsWith("-->"))
					continue;

				String words_i[] = docs.get(i).split("-->")[1].split(",");

				for (int j = i + 1; j < docs.size(); j++) {

					if (docs.get(j).endsWith("-->"))
						continue;

					String words_j[] = docs.get(j).split("-->")[1].split(",");
					int score = 0;

					ArrayList<String> matchedWords = new ArrayList<String>();
					for (int k = 0; k < words_i.length; k++) {
						if (words_i[k].length() <= 3)
							continue;
						for (int l = 0; l < words_j.length; l++) {
							if (words_j[l].length() <= 3)
								continue;
							if (words_i[k].equals(words_j[l])) {
								if (!matchedWords.contains(words_j[l]))
									matchedWords.add(words_j[l]);
								score++;
							}
						}
					}

					// docPairScore.put(String.format("%04d", (i + 1)) + " - " +
					// String.format("%04d", (j + 1)),
					// String.format("%04d", score));

					docPairScore.put(docs.get(i).split("-->")[0].trim() + "," + docs.get(j).split("-->")[0].trim(),
							String.format("%04d", score));

					// To print all pair match with score and bow
					// formattedOut.print(docs.get(i).split("-->")[0].trim() +
					// "," + docs.get(j).split("-->")[0].trim()
					// + "-->" + score);

					for (int k = 0; k < matchedWords.size(); k++) {
						// formattedOut.print("," + matchedWords.get(k));
					}
					matchedWords.clear();

					// // To print words of doc1 and doc2
					// formattedOut.print("@");
					// for (int k = 0; k < words_i.length; k++) {
					// if (k == words_i.length - 1)
					// formattedOut.print(words_i[k]);
					// else
					// formattedOut.print(words_i[k] + ",");
					// }
					// formattedOut.print("@");
					// for (int k = 0; k < words_j.length; k++) {
					// if (k == words_j.length - 1)
					// formattedOut.print(words_j[k]);
					// else
					// formattedOut.print(words_j[k] + ",");
					// }

					// formattedOut.println();

				}
			}

			// // To get the score of doc matching count for all docs
			// for (int i = 0; i < docs.size(); i++) {
			// String docName = docs.get(i).split("-->")[0].trim();
			// int count = 0;
			// for (Map.Entry<String, String> entry : docPairScore.entrySet()) {
			// String key = entry.getKey();
			// int tab = Integer.parseInt(entry.getValue());
			//
			// if (tab > 0 && key.contains(docName)) {
			// count++;
			// }
			// }
			// docsMatScore.put(docName, String.format("%04d", count));
			// }
			//
			// // To add docPairScore and docsMatScore
			// for (Map.Entry<String, String> entry : docPairScore.entrySet()) {
			// String key = entry.getKey();
			// int tab = Integer.parseInt(entry.getValue());
			//
			// String d1 = key.split(",")[0].trim();
			// String d2 = key.split(",")[1].trim();
			//
			// int newScore = tab + Integer.parseInt(docsMatScore.get(d1)) +
			// Integer.parseInt(docsMatScore.get(d2));
			//
			// docPairScore.put(key, String.format("%04d", newScore));
			// }

			// To sort in desc order
			docPairScore = sortByValues(docPairScore);

			// To print all the pairs and their scores
			for (Map.Entry<String, String> entry : docPairScore.entrySet()) {
				String key = entry.getKey();
				int tab = Integer.parseInt(entry.getValue());

				if (tab > 0) {
					// System.out.println(key.split(",")[0].trim() +","+
					// key.split(",")[1].trim() + "==" + tab);
					formattedOut.println(key + "-->" + tab);
				}
			}

			reader.close();
			formattedOut.close();

			System.out.println("\n-----------------\n");

			// Call Cluster Algorithm
			// clusterAssDoc(docs, docPairScore, 3);

			// Call TitleClusterAlgo 2nd method
			// TitleCluster.ClusterIteratively(docs, docPairScore, 20);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void clusterAssDoc(ArrayList<String> docs, HashMap<String, String> docPairScore, int numOfClusters) {
		ArrayList<String> clusteredDocs = new ArrayList<String>();
		ArrayList<ArrayList<String>> clusteredWords = new ArrayList<ArrayList<String>>();
		HashMap<String, Integer> markRead = new HashMap<String, Integer>();
		for (int i = 0; i < docs.size(); i++) {
			markRead.put(docs.get(i).split("-->")[0].trim(), new Integer(0));
		}

		for (Map.Entry<String, String> entry : docPairScore.entrySet()) {
			String key = entry.getKey();
			int tab = Integer.parseInt(entry.getValue());

			String d1 = key.split(",")[0].trim();
			String d2 = key.split(",")[1].trim();

			int found1 = -1;
			int found2 = -1;
			if (tab > 5) {

				for (int i = 0; i < clusteredDocs.size(); i++) {
					if (clusteredDocs.get(i).contains(d1)) {
						found1 = i;
					}
					if (clusteredDocs.get(i).contains(d2)) {
						found2 = i;
					}
				}

				int readD1 = (int) markRead.get(d1);
				int readD2 = (int) markRead.get(d2);
				if ((found1 == -1) && (found2 == -1) && (readD1 == 0) && (readD2 == 0)) {

					clusteredDocs.add(d1 + "," + d2);
					// System.out.println(d1 + "," + d2);

					markRead.put(d1, new Integer(1));
					markRead.put(d2, new Integer(1));
				} else if ((found1 > -1) && (found2 == -1) && (readD2 == 0) && (readD1 == 1)) {

					String val = clusteredDocs.get(found1);
					clusteredDocs.set(found1, val + "," + d2);
					// System.out.println(val + "," + d2);

					markRead.put(d2, new Integer(1));
				} else if ((found1 == -1) && (found2 > -1) && (readD1 == 0) && (readD2 == 1)) {

					String val = clusteredDocs.get(found2);
					clusteredDocs.set(found2, val + "," + d1);
					// System.out.println(val + "," + d1);

					markRead.put(d1, new Integer(1));
				}
				// else {
				// if ((found1 != found2) && (clusteredDocs.size() >
				// numOfClusters)) {
				// clusteredDocs.set(found1, clusteredDocs.get(found1) + "," +
				// clusteredDocs.get(found2));
				// clusteredDocs.remove(found2);
				// }
				// }
			}
		}

		System.out.println("\n");
		for (int i = 0; i < clusteredDocs.size(); i++) {
			System.out.println("Cluster-" + i + " " + clusteredDocs.get(i));
		}

		System.out.println();
		System.out.print("Unclustered - ");
		for (Map.Entry<String, Integer> entry : markRead.entrySet()) {
			if ((int) entry.getValue() == 0) {
				System.out.print(entry.getKey() + ",");
			}
		}

	}

	public static HashMap<String, String> sortByValues(HashMap<String, String> map) {

		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o2, Object o1) {
				return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
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
