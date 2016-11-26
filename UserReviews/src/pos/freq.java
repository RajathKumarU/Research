package pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class freq {
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"C:/Users/RajathMeghana/Downloads/feedback/transcendHDD_stemmed.txt"));
		String line = "";
		String[] words = new String[10000];
		int count = 0;
		while ((line = br.readLine()) != null) {
			words[++count] = line;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String w : words) {
			if (w != null) {
				Integer n = map.get(w);
				n = (n == null) ? 1 : ++n;
				map.put(w, n);
			}
		}
		for (@SuppressWarnings("rawtypes")
		Map.Entry m : map.entrySet()) {
			if (Integer.parseInt(m.getValue().toString()) > 4)
				System.out.println(m.getKey() + " " + m.getValue());
		}
		br.close();
	}
}
