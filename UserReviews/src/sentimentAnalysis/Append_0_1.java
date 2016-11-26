package sentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Append_0_1 {

	public static void main(String[] args) {
		try {
			BufferedReader reader1 = new BufferedReader(new FileReader("C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/data.txt"));
			BufferedReader reader2 = new BufferedReader(new FileReader("C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_filtered_no_rating.txt"));
			BufferedReader reader3 = new BufferedReader(new FileReader("C:/Users/RajathMeghana/Downloads/mi4i_filtered_reviews_new.txt"));
			PrintWriter p = new PrintWriter(new FileWriter("valid_0_1.arff"));
			String line1="",line2="",line3="";
			
			while ((line1=reader1.readLine())!=null && (line2=reader2.readLine())!=null) {
				while((line3=reader3.readLine())!=null){
					if(line3.contains(line2)){
						p.println(line1+","+line3.charAt(0));
						break;
					}
				}
			}
			
			p.close();
			reader1.close();
			reader2.close();
			reader3.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
