package sentimentAnalysis;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

import org.json.JSONObject;

public class Vivekn_mine {
	public static int getSentiment(String text) throws Exception {
		URL obj = new URL("http://sentiment.vivekn.com/api/text/");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		String urlParameters = "txt=" + text;

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		String jsonResp = response.toString();
		JSONObject reader = new JSONObject(jsonResp);
		JSONObject result = reader.getJSONObject("result");
		String sentiment = result.getString("sentiment");
		if (sentiment.equals("Positive"))
			return 1;
		else if (sentiment.equals("Neutral"))
			return 0;
		else
			return -1;
	}

	public static void main(String[] args) {
		try {
			BufferedReader opinions = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Rapid/rapid_mi4_opinions_4.txt"));
			BufferedReader nouns = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/feedback/Rapid/rapid_mi4_noun_freq.txt"));
			PrintWriter p = new PrintWriter(
					new FileWriter(
							"C:/Users/RajathMeghana/Downloads/feedback/Rapid/rapid_mi4_sentiments_mine.txt"));

			Hashtable<String, Integer> h = new Hashtable<String, Integer>();

			String line = "";

			while ((line = nouns.readLine()) != null) {
				h.put(line.split(" ")[0], 0);
			}

			while ((line = opinions.readLine()) != null) {
				if (line == "") {
					p.println();
					break;
				} else {
					line = line.replaceAll("\\}\\{", " ");
					line = line.replaceAll("\\{", "");
					line = line.replaceAll("\\}", "");
					String attrs[] = line.split(" ");

					for (int i = 0; i < attrs.length; i++) {
						String vals[] = attrs[i].split(",");
						for (int j = 0; j < vals.length; j++) {
							if (h.containsKey(vals[j])) {
								if (getSentiment(attrs[i].replaceAll(",", " ")) == 1){
									h.put(vals[j], h.get(vals[j]) + 1);
								} else if (getSentiment(attrs[i].replaceAll(",", " ")) == -1){
									h.put(vals[j], h.get(vals[j]) - 1);
								}
							}
						}
					}
					Enumeration<String> e = h.keys();
					while (e.hasMoreElements()) {
						String val = e.nextElement();
						p.print("{" + val + "," + h.get(val) + "}");
						System.out.print("{" + val + "," + h.get(val) + "}");
					}
					p.println();
					System.out.println();
					
					BufferedReader nouns2 = new BufferedReader(
							new FileReader(
									"C:/Users/RajathMeghana/Downloads/feedback/Rapid/rapid_mi4_noun_freq.txt"));
					String line2 = "";
					while ((line2 = nouns2.readLine()) != null) {
						h.put(line2.split(" ")[0], 0);
					}
					nouns2.close();
				}
			}

			p.close();
			nouns.close();
			opinions.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
