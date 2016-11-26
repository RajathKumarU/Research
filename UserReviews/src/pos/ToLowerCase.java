package pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class ToLowerCase {
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mypassportHD/flipkart_mypassportHD_POS_tagged.txt"));
			PrintWriter p = new PrintWriter(
					new FileWriter(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mypassportHD/flipkart_mypassportHD_POS_tagged_lowercase.txt"));
			String line = "";

			while ((line = reader.readLine()) != null) {
				p.println(line.toLowerCase());
			}

			p.close();
			reader.close();
			
			
			// to replace words with stemmed words
			reader = new BufferedReader(new FileReader("C:/Users/RajathMeghana/Downloads/feedback/Large Data/mypassportHD/flipkart_mypassportHD_POS_tagged_lowercase.txt"));
			BufferedReader reader2 = new BufferedReader(new FileReader("C:/Users/RajathMeghana/Downloads/feedback/Large Data/mypassportHD/flipkart_mypassportHD_noun_freq.txt"));
			p = new PrintWriter(
					new FileWriter(
							"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mypassportHD/flipkart_mypassportHD_POS_tagged_replaced.txt"));
			line = "";
			String nouns="";
			
			while((line=reader2.readLine())!=null){
				nouns+=line.split(" ")[0]+",";
			}
			String nouns_req[]=nouns.toLowerCase().substring(0, nouns.length()-1).split(",");
			
			while((line=reader.readLine())!=null){
				String words[]=line.split(" ");
				for(int i=0;i<words.length;i++){
					for(int j=0;j<nouns_req.length;j++){
						String word=words[i].split("_")[0];
						if(word.contains(nouns_req[j])){
							words[i] = nouns_req[j]+"_"+words[i].split("_")[1];
							break;
						}
					}
				}
				for(int i=0;i<words.length;i++){
					p.print(words[i]+" ");
				}
				p.println();
			}
			
			reader.close();
			reader2.close();
			p.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
