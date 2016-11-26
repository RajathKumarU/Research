package all_sources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class AttributeExtraction {

	public static HashMap<String, Integer> sortHashMapByValuesD(
			HashMap<String, Integer> passedMap) {
		List mapKeys = new ArrayList(passedMap.keySet());
		List mapValues = new ArrayList(passedMap.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap sortedMap = new LinkedHashMap();

		Iterator valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = passedMap.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)) {
					passedMap.remove(key);
					mapKeys.remove(key);
					sortedMap.put((String) key, (Integer) val);
					break;
				}

			}

		}
		return sortedMap;
	}

	public static void main(String[] args) throws IOException {

		// Initialize the
		MaxentTagger tagger = new MaxentTagger(
				"src/files/english-left3words-distsim.tagger");
		String line = "";
		String tagged = "";
		File fpw = new File(
				"C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/review_postagged1.txt");
		File fbr = new File(
				"C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/source_reviews/seagateHDD.txt");
		File fstem = new File(
				"C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/stemmed.txt");
		BufferedReader br = new BufferedReader(new FileReader(fbr));
		PrintWriter p = new PrintWriter(new FileWriter(fpw));

		while ((line = br.readLine()) != null) {
			// line = line.split("\\|")[1];
			//System.out.println(line);
			tagged = tagger.tagString(line);
			tagged = tagged.toLowerCase().replaceAll("#", "");
			tagged = tagged.replaceAll("_[^]_nn]", "");
			String[] taged = tagged.split("[ \t]+|\\.");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < taged.length; i++) {
				if (taged[i].contains("_")) {
					sb.append(taged[i] + " ");
				}
			}
			tagged = sb.toString().replaceAll("_nns|_nnps|_nnp|_nn", "");
			p.println(tagged.trim());
		}
		p.close();
		Stemmer.main(fpw, fstem);
		br.close();
		HashMap<String, Integer> map = new HashMap<>();
		HashMap<String, Integer> map1 = new HashMap<>();

		BufferedReader br1 = new BufferedReader(new FileReader(fstem));
		String perline = "";
		while ((perline = br1.readLine()) != null) {
			String[] taged1 = perline.split(" ");
			Set<String> result = new HashSet<String>(
					java.util.Arrays.asList(taged1));
			for (String w : result) {
				if (map.containsKey(w)) {
					Integer n = map.get(w);
					n = (n == null) ? 1 : ++n;
					map.put(w, n);
				} else {
					map.put(w, 1);
				}
			}
			for (String w : taged1) {
				if (map1.containsKey(w)) {
					Integer n = map1.get(w);
					n = (n == null) ? 1 : ++n;
					map1.put(w, n);
				} else {
					map1.put(w, 1);
				}
			}
		}
		
		for (String val : map.keySet()) {
			if (val == "" || val == null){
				System.out.println("111");
				continue;
			}
			int df = map.get(val);
			int tf = map1.get(val);
			System.out.println(val + " = " + (double) tf / df);
		}
//		
//		HashMap<String, Integer> sortmap = new HashMap<>();
//		sortmap = sortHashMapByValuesD(map);
//		System.out.println(sortmap);
//		sortmap = sortHashMapByValuesD(map1);
//		System.out.println(sortmap);

		
	}
}