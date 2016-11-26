package sent;

import java.io.File;
import java.util.Scanner;

public class CustomSentiment {
	
	static String positiveFile = "positive-words.txt";
	static String negativeFile = "negative-words.txt";
	
	int getSentiment( String word )throws Exception
	{
		String posstr = new Scanner(new File(positiveFile)).useDelimiter("\\Z").next();
		String negstr = new Scanner(new File(negativeFile)).useDelimiter("\\Z").next();
		if( posstr.contains("\n"+word+"\n"))
			return 1;
		else if( negstr.contains("\n"+word+"\n"))
			return -1;
		else
			return 0;
	}

}
