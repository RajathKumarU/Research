package sent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class Vivekn {
	String url;
	public Vivekn(String url)
	{
		this.url = url;
	}
	
	public int getSentiment(String text) throws Exception
	{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("POST");
		String urlParameters = "txt="+text;
		
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
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
		System.out.println(sentiment);
		if(sentiment.equals("Positive"))
			return 1;
		else if( sentiment.equals("Neutral"))
			return 0;
		else return -1;
	}
}
