package all_sources;

import java.io.*;

public class Scoreavg {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new FileReader(
						"C:/Users/ANIL BALAJI/Desktop/flipkart_galaxy_score_overall.arff"));
		String line;
		float sum_p1 = 0;
		float avg_p1 = 0;
		int c_p1 = 0;
		float sum_0 = 0;
		float avg_0 = 0;
		int c_0 = 0;
		float sum_n1 = 0;
		float avg_n1 = 0;
		int c_n1 = 0;
		String arr[] = new String[10];
		while ((line = br.readLine()) != null) {
			arr = line.split(",");
			if (arr[1].equals("1".trim())) {
				sum_p1 = sum_p1 + Float.parseFloat(arr[0].trim());
				c_p1++;
			} else if (arr[1].equals("0".trim())) {
				sum_0 = sum_0 + Float.parseFloat(arr[0].trim());
				c_0++;
			} else if (arr[1].equals("-1".trim())) {
				sum_n1 = sum_n1 + Float.parseFloat(arr[0].trim());
				c_n1++;
			}
		}
		avg_p1 = sum_p1 / c_p1;
		avg_n1 = sum_n1 / c_n1;
		avg_0 = sum_0 / c_0;
		System.out.println("avg_p1= " + avg_p1 + ", avg_n1= " + avg_n1
				+ ", avg_0= " + avg_0);
	}
}