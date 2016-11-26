package pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class ToSingleLine {
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("C:/Users/RajathMeghana/Downloads/feedback/latest/mi4i_4stars.txt"));
			PrintWriter p = new PrintWriter(new FileWriter("C:/Users/RajathMeghana/Downloads/feedback/latest/mi4i_4stars_temp.txt"));
			String line = "";

			while ((line = reader.readLine()) != null) {
				if (line.contains("-review-")) {
					p.println();
					continue;
				}
				if (line.trim() != "") {
					p.print(line.trim() + " ");
				}
			}

			p.close();
			reader.close();
			
			reader = new BufferedReader(new FileReader("C:/Users/RajathMeghana/Downloads/feedback/latest/mi4i_4stars_temp.txt"));
			p = new PrintWriter(new FileWriter("C:/Users/RajathMeghana/Downloads/feedback/latest/mi4i_4stars.txt"));
			line = "";

			while ((line = reader.readLine()) != null) {
					p.println(line.trim());
			}

			p.close();
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
