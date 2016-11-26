package sentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.TextAPIException;
import com.aylien.textapi.parameters.SentimentParams;

class Sentiment {
  public static void main(String[] args) throws Exception {
	
	//TextAPIClient client = new TextAPIClient("f090477c","1e5a83402308292e4332994fa81f5b5f");
	
	String filename = "reviews/input";
	FileOutputStream fos = new FileOutputStream("mi4i_semantic.txt",false);
	
	for(int i=0 ; i<186 ; i++)
	{try{
	TextAPIClient client = new TextAPIClient("f090477c","1e5a83402308292e4332994fa81f5b5f");
		FileReader in = new FileReader(filename+(i+1)+".txt");
	    BufferedReader br = new BufferedReader(in);
	    String line;
	    StringBuilder sb = new StringBuilder();
	    while ((line = br.readLine()) != null) {
	        sb.append(line);
	    }
	    String file = sb.toString();
	//Scanner k = new Scanner(new File(filename+(i+1)+".txt"));
	//String file = k.useDelimiter("\\Z").next();
	SentimentParams.Builder builder = SentimentParams.newBuilder();
	builder.setText(file);
	builder.setUrl(null);
	builder.setMode("document");
	SentimentParams sp = builder.build();
	//System.out.println("parameters built");
	
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
	
	//Writing to file
	fos.write((pol+","+stars+"\n").getBytes());
	//k.close();
	}catch(TextAPIException e)
	{
		System.out.println("Connection failed!!");
		Thread.sleep(1000);
		i--;
	}
	}
	fos.close();
	
}
}