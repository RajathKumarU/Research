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
			BufferedReader reader = new BufferedReader(new FileReader(
					"src/files/semioutputs/all_bow_appended_2.txt"));
			ArrayList<String> docs = new ArrayList<String>();
			HashMap<String, String> docPairScore = new HashMap<String, String>();

			String line = "";
			while ((line = reader.readLine()) != null) {
				// String words[] = line.substring(0,
				// line.length()-1).split(",");
				docs.add(line);
			}

			for (int i = 0; i < docs.size() - 1; i++) {

				String words_i[] = docs.get(i)
						.substring(0, docs.get(i).length() - 1).split(",");

				for (int j = i + 1; j < docs.size(); j++) {

					String words_j[] = docs.get(j)
							.substring(0, docs.get(j).length() - 1).split(",");
					int score = 0;

					for (int k = 0; k < words_i.length; k++) {
						for (int l = 0; l < words_j.length; l++) {
							if (words_i[k].equals(words_j[l])) {
								score++;
							}
						}
					}

					docPairScore.put("D" + (i + 1) + " - D" + (j + 1),
							String.format("%04d", score));
				}
			}

			docPairScore = sortByValues(docPairScore);

			for (Map.Entry<String, String> entry : docPairScore.entrySet()) {
				String key = entry.getKey();
				int tab = Integer.parseInt(entry.getValue());

				if (tab > 1) {
					System.out.println(key + " - " + tab);
				}
			}

			reader.close();

			System.out.println("\n-----------------\n");

			clusterAssDoc(docs, docPairScore, 2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void clusterAssDoc(ArrayList<String> docs,
			HashMap<String, String> docPairScore, int numOfClusters) {
		ArrayList<String> clusteredDocs = new ArrayList<String>();
		ArrayList<ArrayList<String>> clusteredWords = new ArrayList<ArrayList<String>>();
		HashMap<String, Integer> markRead = new HashMap<String, Integer>();
		for (int i = 0; i < docs.size(); i++) {
			markRead.put(("D" + (i + 1)), new Integer(0));
		}

		for (Map.Entry<String, String> entry : docPairScore.entrySet()) {
			String key = entry.getKey();
			int tab = Integer.parseInt(entry.getValue());

			String d1 = key.split("-")[0].trim();
			String d2 = key.split("-")[1].trim();

			int found1 = -1;
			int found2 = -1;
			if (tab > 1) {

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
				if ((found1 == -1) && (found2 == -1) && (readD1 == 0)
						&& (readD2 == 0)) {

					clusteredDocs.add(d1 + "," + d2);
					// System.out.println(d1 + "," + d2);

					markRead.put(d1, new Integer(1));
					markRead.put(d2, new Integer(1));
				} else if ((found1 > -1) && (found2 == -1) && (readD2 == 0)
						&& (readD1 == 1)) {

					String val = clusteredDocs.get(found1);
					clusteredDocs.set(found1, val + "," + d2);
					// System.out.println(val + "," + d2);

					markRead.put(d2, new Integer(1));
				} else if ((found1 == -1) && (found2 > -1) && (readD1 == 0)
						&& (readD2 == 1)) {

					String val = clusteredDocs.get(found2);
					clusteredDocs.set(found2, val + "," + d1);
					// System.out.println(val + "," + d1);

					markRead.put(d1, new Integer(1));
				}
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
