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

public class TitleCluster {

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

//			String clusteredFiles = ClusterIteratively(docs, docPairScore, 10);
//
//			// Clear docPairScore to read again with new threshold
//			titleReader = new BufferedReader(new FileReader("src/files/input/TitleClusterInp.txt"));
//			docPairScore = new HashMap<String, String>();
//
//			while ((line = titleReader.readLine()) != null) {
//				String key = line.split("==")[0];
//				int val = Integer.parseInt(line.split("==")[1]);
//				if (val > 50) {
//					docPairScore.put(key, String.format("%04d", val));
//					// System.out.println(key + "," + docPairScore.get(key));
//				}
//			}
//			titleReader.close();
//
//			// Remove clustered pairs and cluster Iteratively again
//			String keyToRemove = "";
//			String clFiles[] = clusteredFiles.split(",");
//			for (Map.Entry<String, String> entry : docPairScore.entrySet()) {
//
//				String key = entry.getKey();
//				int tab = Integer.parseInt(entry.getValue());
//
//				for (int i = 0; i < clFiles.length; i++) {
//					if (key.contains(clFiles[i])) {
//						keyToRemove += key + "@";
//						break;
//					}
//				}
//			}
//			String keys[] = keyToRemove.substring(0, keyToRemove.length() - 1).split("@");
//			for (int i = 0; i < keys.length; i++) {
//				docPairScore.remove(keys[i]);
//			}
//
//			ClusterIteratively(docs, docPairScore, 10);

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

	private static void clusterAssDoc(ArrayList<String> docs, HashMap<String, String> docPairScore, int numOfClusters) {

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
			if (tab > 0) {

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
		for (int i = 0; i < clusteredDocs.size(); i++) {
			System.out.println("Cluster-" + i + " " + clusteredDocs.get(i));
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

	public static String ClusterIteratively(ArrayList<String> docs, HashMap<String, String> docPairScore,
			int numOfClusters) {

		ArrayList<String> clusteredDocs = new ArrayList<String>();
		// ArrayList<ArrayList<String>> clusteredWords = new
		// ArrayList<ArrayList<String>>();
		HashMap<String, Integer> markRead = new HashMap<String, Integer>();
		// HashMap<String, Integer> markReadUnCl = new HashMap<String,
		// Integer>();

		for (int i = 0; i < numOfClusters; i++) {

			int isFirst = -1;
			for (Map.Entry<String, String> entry : docPairScore.entrySet()) {

				int first = -1;
				int second = -1;

				String key = entry.getKey();
				int tab = Integer.parseInt(entry.getValue());

				// Use this only when calling from Cluster1.java
//				if (tab < 20)
//					continue;

				String d1 = key.split(",")[0].trim();
				String d2 = key.split(",")[1].trim();

				for (int j = 0; j < clusteredDocs.size(); j++) {
					String val = clusteredDocs.get(j);
					// System.out.println(val);
					if (val.contains(d1))
						first = j;
					if (val.contains(d2))
						second = j;

				}
				// System.out.println("First:" + first + ",Second:" + second +
				// ",D1:" + d1 + ",D2:" + d2);

				if ((first == -1) && (second == -1) && (isFirst == -1) && (i % 2 == 0)) {
					isFirst = 0;
					clusteredDocs.add(i / 2, key);
					// System.out.println("Key:" + key);
				} else if ((first == -1) && (second != -1)) {
					String val = clusteredDocs.get(second);
					clusteredDocs.set(second, val + "," + d1);
					// System.out.println("D1:" + d1);
				} else if ((first != -1) && (second == -1)) {
					String val = clusteredDocs.get(first);
					clusteredDocs.set(first, val + "," + d2);
					// System.out.println("D2:" + d2);
				}

			}
		}

		String clusteredDocsToReturn = "";
		for (int i = 0; i < numOfClusters && i < clusteredDocs.size(); i++) {
			System.out.print("Cluster - " + (i + 1) + " = "
					+ (clusteredDocs.get(i).length() - clusteredDocs.get(i).replaceAll(",", "").length() + 1));
			System.out.println(" = " + clusteredDocs.get(i));
			clusteredDocsToReturn += clusteredDocs.get(i) + ",";
		}

		if (clusteredDocsToReturn.length() > 0)
			return clusteredDocsToReturn.substring(0, clusteredDocsToReturn.length() - 1);
		else
			return "";
	}

}
