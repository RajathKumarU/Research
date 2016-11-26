package sentimentAnalysis;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.parameters.SentimentParams;

public class SentimentTest {

	public static void main(String[] args)throws Exception {
		FileOutputStream fos = new FileOutputStream("mi4i_semantic_test.txt",false);
		String file= new Scanner(new File("TestSample.txt")).useDelimiter("\\Z").next();
		String rev[] = file.split("\n");
		TextAPIClient client = new TextAPIClient("f090477c","1e5a83402308292e4332994fa81f5b5f");
		for( int i = 0 ; i<rev.length; i++)
		{
			SentimentParams.Builder builder = SentimentParams.newBuilder();
			builder.setText(rev[i]);
			builder.setUrl(null);
			builder.setMode("document");
			SentimentParams sp = builder.build();
			
			com.aylien.textapi.responses.Sentiment sent = client.sentiment(sp);
			System.out.println("Sentiment = "+sent.getPolarity()+"\tPolarity"+sent.getPolarityConfidence());
			double pol = sent.getPolarityConfidence();
			String polarity = sent.getPolarity();
			int stars;
			if( polarity.equals("positive")||polarity.equals("neutral"))
				stars = (int)Math.round(pol*5);
			else
			{
				stars =(int)Math.round((1-pol)*5);
				if( stars == 0)
					stars =1;
			}
			System.out.println("For instance "+(i+1)+", the given stars -->"+stars+"\n");
			
			fos.write((pol+","+stars+"\n").getBytes());
		}

	}

}
