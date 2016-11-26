package pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class ReviewToFiles {

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mypassportHD/crawledreviews.txt"));
			String line = "";
			//PrintWriter p = new PrintWriter(new FileWriter("C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_filtered_no_rating.txt"));
			int i = 0;

			while ((line = reader.readLine()) != null) {
				PrintWriter p = new PrintWriter(new FileWriter("C:/Users/RajathMeghana/Downloads/feedback/Large Data/mypassportHD/input/input" + (++i) + ".txt"));
				p.println(line.substring(2, line.length()));
				p.close();
			}
			//p.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
