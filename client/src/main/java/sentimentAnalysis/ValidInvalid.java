package sentimentAnalysis;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class ValidInvalid {
	public static void main( String args[])
	{
		String product = "macbook_pro_spam";
		try{
			Scanner brfil = new Scanner( new FileInputStream("C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/semantic_extended/"+product+"_filtered.txt"));
			Scanner brunfil = new Scanner( new FileInputStream("C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/semantic_extended/"+product+".txt"));
			PrintWriter pw = new PrintWriter( new FileOutputStream("C:/Users/RajathMeghana/Downloads/feedback/Large Data/New5Products/2nd_case_files/semantic_extended/"+product+"_val_inval.txt",false));
			String fil = brfil.nextLine();
			String unfil = brunfil.nextLine();
			//while((unfil = brunfil.readLine())!=null)
			String unfil1 = "";
			//while( brunfil.hasNext() )
			do
			{
				unfil1 = unfil.substring(2);
				System.out.println(unfil1);
				if(unfil1.equals(fil))
				{
					pw.println("0|"+unfil1);
					unfil = brunfil.nextLine();
					fil = brfil.nextLine();
				}
				else
				{
					pw.println("1|"+unfil1);
					unfil = brunfil.nextLine();
				}
						
				
			}while( brunfil.hasNext() && brfil.hasNext());
			//brunfil.nextLine();
			unfil1 = unfil.substring(2);
			System.out.println(unfil1);
			if(unfil1.equals(fil))
			{
				pw.println("0|"+unfil1);
				//unfil = brunfil.nextLine();
				//fil = brfil.nextLine();
			}
			else
			{
				pw.println("1|"+unfil1);
				//unfil = brunfil.nextLine();
			}
			while( brunfil.hasNext() )
			{
				pw.println("1|"+brunfil.nextLine().substring(2));
			}
			pw.close();
			brfil.close();
			brunfil.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
