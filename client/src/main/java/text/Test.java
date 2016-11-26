package text;

import java.io.File;
import java.util.Scanner;

public class Test {
	public static void main( String args[]) throws Exception
	{
		
		Scanner k = new Scanner(new File("obtained_reviews.txt"));
		while( k.hasNext() )
			System.out.println(k.nextLine());
	}
}
