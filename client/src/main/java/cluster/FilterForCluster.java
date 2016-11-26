package cluster;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.parameters.SentimentParams;

public class FilterForCluster {
	
	public static void main( String args[])throws Exception
	{
		try{
		FileOutputStream oparff = new FileOutputStream("flipkart_12stars_review_filtered.arff",false);
		FileOutputStream fos = new FileOutputStream("flipkart_12stars_review_filtered.txt",false);
		Scanner k= new Scanner(new File("flipkart_12stars_review.txt"));
		Scanner arff = new Scanner( new File("flipkart_12stars_data.arff"));
		//String rev[] = file.split("\n");
		arff.skip(Pattern.compile("(?:.*\\r?\\n|\\r){21}"));
		TextAPIClient client[] = new TextAPIClient[4];
		 client[3] = new TextAPIClient("ba34697c","2e8ee6ebae9c87dbf8640001a0c40fd4");//-->Ani
		 client[2] = new TextAPIClient("f090477c","1e5a83402308292e4332994fa81f5b5f");//--->rajath
		 client[1] = new TextAPIClient("1ed17b9c","83c5ee5d97e3d2790b3f5376191d7eaf");//-->anil
		 client[0] = new TextAPIClient("f2601798","78ddaea3aa5f749c338654fcbf5cef22");//--->anand
		 int index = 0;
		boolean flag = false;
		String rev="";
		String dat ="";
		int i =0;
		//k.skip(Pattern.compile("(?:.*\\r?\\n|\\r){1000}"));
		//k.skip(Pattern.compile("(?:.*\\r?\\n|\\r){1000}"));
		//k.skip(Pattern.compile("(?:.*\\r?\\n|\\r){34}"));
		int count = 0;
		while(k.hasNext()&&arff.hasNext())
		{
			i++;
			if( !flag )
			{
				rev = k.nextLine();
				dat = arff.nextLine();
			}
			try{
			String r[] = rev.split("\\|");
			int p_rating= (int)Double.parseDouble(r[0].trim());
			SentimentParams.Builder builder = SentimentParams.newBuilder();
			builder.setText(r[1]);
			builder.setUrl(null);
			builder.setMode("document");
			SentimentParams sp = builder.build();
			
			com.aylien.textapi.responses.Sentiment sent = client[index].sentiment(sp);
			System.out.println("here"+r[1]+" "+r[0]);
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
			//System.out.println("For instance "+(i+1)+", the given stars -->"+stars+"\n");
			//int valid = 1;
			if(Math.abs(p_rating-stars)<3)
			{
				fos.write((rev+"\n").getBytes());
				oparff.write((dat+"\n").getBytes());
			}
			//	valid = 0;
			//else
			//	valid =1;
			//fos.write((valid+"|"+rev+"\n").getBytes());
				//System.out.println("instance "+i+" user's rating "+p_rating+" value obtained "+stars);
			flag = false;
			count =0;
			}catch(Exception e)
			{
				count++;
				if(count>=15)
				{
					index = (index+1)%4;
					count= 0 ;
				}
				e.printStackTrace();
				Thread.sleep(1000*5);
				System.out.println("Timed out!");
				flag = true;
				i--;
			}
		}
		System.out.println(k.hasNextLine());
		k.close();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	}
}
