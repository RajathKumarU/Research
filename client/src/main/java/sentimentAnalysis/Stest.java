package sentimentAnalysis;

import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.parameters.SentimentParams;

public class Stest {

	public static void main(String args[])
	{
		System.out.println("hello");
	}
	public static String getSentiment(String str)
	{
		try{
		TextAPIClient client = new TextAPIClient("e853721a","38928d046f124744ae9e71c812ac964e");
		SentimentParams.Builder builder = SentimentParams.newBuilder();
		builder.setText(str);
		builder.setUrl(null);
		builder.setMode("document");
		SentimentParams sp = builder.build();
		
		com.aylien.textapi.responses.Sentiment sent = client.sentiment(sp);
		return sent.getPolarity();
		}catch(Exception e)
		{
			return "error";
		}
	}
	
}
