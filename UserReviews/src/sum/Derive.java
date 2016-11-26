package sum;

import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

public class Derive {
	public static void main(String[] args) {
		String file = new Scanner(
				"C:/Users/RajathMeghana/Downloads/feedback/latest/mi4i_old.arff")
				.useDelimiter("\\Z").next();
		String atts = "";
		int index;
		while ((index = file.indexOf("@attribute")) != -1) {
			int t = file.indexOf(" ", index);
			atts = atts + file.substring(t + 1, file.indexOf(" ", t + 1)) + ",";
		}
		String att[] = atts.split(",");
		HashMap<String, Integer> ht = new HashMap<String, Integer>();
		for (int i = 0; i < att.length - 1; i++)
			ht.put(att[i], 0);
		String data = file.split("@data\n")[1];
		String da[] = data.split("\n");
		for (int i = 0; i < da.length; i++) {

			String x[] = da[i].split(",");
			System.out.println("Doing line i");
			/*
			 * int sum = 0; for( int j = 0 ; j<x.length-1 ; j++) sum = sum +
			 * Integer.parseInt(x[j]);
			 */
			for (int j = 0; j < att.length - 1; j++)
				ht.put(att[j], ht.get(att[j]) + Integer.parseInt(x[j]));
		}
	}
}
