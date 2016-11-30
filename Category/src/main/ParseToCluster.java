package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ParseToCluster {
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/files/input/ClusterInp.txt"));
			PrintWriter p = new PrintWriter(new FileWriter("src/files/output/ClusterOut.txt"));
			String line = "", filename = "";
			int count = 0;
			while ((line = reader.readLine()) != null) {
				count++;

				String bowLine = "", toWrite = "";

				if (count % 3 == 1) {
					filename = line.substring(0, line.indexOf("@")).trim();
				} else if (count % 3 == 2) {
					bowLine = line;

					toWrite = filename + "-->";
					String[] bowInp = bowLine.split(",");

					String bow[] = removeDuplicates(bowInp);

					for (int i = 0; i < bow.length; i++) {
						bow[i] = bow[i].replaceAll("\'", "").replaceAll("WIKI", "").replaceAll("]", "")
								.replaceAll("\\[", "").replaceAll("\"", "").trim();
						if (i == bow.length - 1 && !bow[i].equals("") && bow[i] != null) {
							toWrite += bow[i];
						} else {
							if (!bow[i].equals("") && bow[i] != null)
								toWrite += bow[i] + ",";
						}
					}
					System.out.println(count);
					p.println(toWrite);
				} else {
					filename = "";
				}
			}
			p.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String[] removeDuplicates(String[] bowInp) {

		ArrayList<String> phrases = new ArrayList<String>();

		for (int i = 0; i < bowInp.length; i++) {
			if (!phrases.contains(bowInp[i]))
				phrases.add(bowInp[i]);
		}

		return (String[]) phrases.toArray(new String[phrases.size()]);
	}
}
