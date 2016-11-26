package pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class GenerateArff2 {
	public static void main( String args[])
	{
		try{
		String product = args[0];
		BufferedReader br=new BufferedReader(new FileReader(product+"_temp_score.txt"));
		PrintWriter pr=new PrintWriter(new FileWriter(product+"_score_overall.arff"));
		pr.println("@relation 'user-"+product+"reviews'");
		pr.println("@attribute score numeric\n@attribute overall {-1,0,1}");
		pr.println("\n@data\n\n");
		String line = "";
		while((line = br.readLine())!=null)
		{
			String atts[] = line.split(",");
			int oall=0;
			for(int i =0 ; i<atts.length-1 ; i++)
				oall = oall+Integer.parseInt(atts[i]);
			if( oall>0)
			pr.println(atts[atts.length-1]+",1");
			if( oall<0)
				pr.println(atts[atts.length-1]+",-1");
			if(oall==0)
				pr.println(atts[atts.length-1]+",0");
		}
		
		pr.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
