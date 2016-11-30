package main;

import java.io.BufferedReader;
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

public class TitleCluster2 {

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/files/output/ClusterOut.txt"));

			ArrayList<String> docs = new ArrayList<String>();

			String line = "";
			while ((line = reader.readLine()) != null) {
				docs.add(line.toLowerCase());
			}
			reader.close();

			BufferedReader titleReader = new BufferedReader(new FileReader("src/files/input/TitleClusterInp.txt"));
			HashMap<String, String> docPairScore = new HashMap<String, String>();

			while ((line = titleReader.readLine()) != null) {
				String key = line.split("==")[0];
				int val = Integer.parseInt(line.split("==")[1]);
				if (val > 0) {
					docPairScore.put(key, String.format("%04d", val));
					// System.out.println(key + "," + docPairScore.get(key));
				}
			}
			titleReader.close();

			docPairScore = sortByValues(docPairScore);

			for (Map.Entry<String, String> entry : docPairScore.entrySet()) {

				String key = entry.getKey();
				int tab = Integer.parseInt(entry.getValue());

				if (tab > 0) {
					System.out.println(key + "==" + tab);
				}
			}

			clusterAssDoc(docs, docPairScore, 2);

		} catch (Exception e) {
			e.printStackTrace();
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

	private static void clusterAssDoc(ArrayList<String> docs, HashMap<String, String> docPairScore, int numOfClusters)
			throws IOException {

		ArrayList<String> clusteredDocs = new ArrayList<String>();
		ArrayList<ArrayList<String>> clusteredWords = new ArrayList<ArrayList<String>>();
		HashMap<String, Integer> markRead = new HashMap<String, Integer>();
		HashMap<String, Integer> markReadUnCl = new HashMap<String, Integer>();
		// for (int i = 0; i < docs.size(); i++) {
		// markRead.put(docs.get(i).split("-->")[0].trim(), new Integer(0));
		//
		// }

		for (Map.Entry<String, String> entry : docPairScore.entrySet()) {

			String key = entry.getKey();
			int tab = Integer.parseInt(entry.getValue());

			String d1 = key.split(",")[0].trim();
			String d2 = key.split(",")[1].trim();

			int found1 = -1;
			int found2 = -1;
			if (tab > 10) {

				for (int i = 0; i < clusteredDocs.size(); i++) {
					if (clusteredDocs.get(i).contains(d1)) {
						found1 = i;
					}
					if (clusteredDocs.get(i).contains(d2)) {
						found2 = i;
					}
				}

				int readD1 = 0;
				if (markRead.containsKey(d1))
					readD1 = 1;

				int readD2 = 0;
				if (markRead.containsKey(d2))
					readD2 = 1;

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

			} else {
				if (!markReadUnCl.containsKey(d1))
					markReadUnCl.put(d1, new Integer(0));
				if (!markReadUnCl.containsKey(d2))
					markReadUnCl.put(d2, new Integer(0));
			}

		}

		System.out.println("\n");

		// To write clusters as well as bow associated to docs present in
		// clusters
		PrintWriter writer = new PrintWriter(new FileWriter("src/files/output/cluDocBOW.txt"));
		for (int i = 0; i < clusteredDocs.size(); i++) {
			String toWrite = "";
			System.out.println("Cluster-" + i + " " + clusteredDocs.get(i) + " ");
			toWrite += "Cluster-" + i + "@";
			String docNames[] = clusteredDocs.get(i).split(",");
			for (int j = 0; j < docNames.length; j++) {
				if (j < docNames.length - 1) {
					toWrite += docNames[j] + "|";
				} else {
					toWrite += docNames[j] + " ";
				}
			}
			for (int j = 0; j < docNames.length; j++) {
				// toWrite += docNames[j] + "@";
				BufferedReader reader = new BufferedReader(new FileReader("src/files/input/TitleClusterBOW.txt"));
				String line = "";
				while ((line = reader.readLine()) != null) {
					if (line.toLowerCase().startsWith(docNames[j].toLowerCase())) {
						if (j < docNames.length - 1) {
							String bows[] = line.split("@")[1].split(" ");
							for (int k = 0; k < bows.length; k++) {
								if (bows[k].length() > 2 && !toWrite.contains(" " + bows[k] + " "))
									toWrite += bows[k] + " ";
							}
							continue;
						} else {
							String bows[] = line.split("@")[1].split(" ");
							for (int k = 0; k < bows.length; k++) {
								if (bows[k].length() > 2 && !toWrite.contains(" " + bows[k] + " "))
									if (k < bows.length - 1)
										toWrite += bows[k] + " ";
									else
										toWrite += bows[k];

							}
							continue;
						}
					}
				}

				// toWrite += ",";
				reader.close();
			}
			writer.println(toWrite);
		}
		writer.close();

		if (clusteredDocs.size() < numOfClusters) {
			System.err.println("Clusters formed are less than expected. Check threshold");
		} else {
			// Call a method that forms required cluster heads using
			// distance and connectivity and merges the remaining clusters to
			// these clusters.

			formClusters(numOfClusters);

		}

		System.out.println();
		System.out.print("Unclustered - ");
		int count = 0;
		for (Map.Entry<String, Integer> entry : markReadUnCl.entrySet()) {
			if (!markRead.containsKey(entry.getKey())) {
				count++;
				System.out.print(entry.getKey() + ",");
			}
		}
		System.out.println();

		System.out.println("CLustered count - " + markRead.size());
		System.out.println("Total Unclustered - " + count);

	}

	public static void formClusters(int numOfClusters) {
		// Form cluster heads here

		mergeClusters();
	}

	public static void mergeClusters() {

	}

}
