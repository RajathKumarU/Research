package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;


public class RemoveDupInLine {

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/files/semioutputs/all_bow_appended.txt"));
			String line = "";
			PrintWriter p = new PrintWriter(new FileWriter("src/files/semioutputs/all_bow_appended_2.txt"));
			
			while((line = reader.readLine())!=null){
				String words[] = line.substring(0, line.length()-1).split(",");
				ArrayList<String> list= new ArrayList<String>();
				for (int i = 0; i < words.length; i++) {
					if(!list.contains(words[i].trim())){
						list.add(words[i].trim());
					}
				}
				
				for (int i = 0; i < list.size(); i++) {
					p.print(list.get(i)+",");
				}
				p.println();
			}
			
			p.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
