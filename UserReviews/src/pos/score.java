package pos;
import java.io.*;

public class score {
public static void main(String[] args) throws Exception{
	String product = args[0];
	BufferedReader br=new BufferedReader(new FileReader(product+"_sentiment.arff"));
	while( !br.readLine().equals("@data"));
	br.readLine();
	br.readLine();
	PrintWriter pr=new PrintWriter(new FileWriter(product+"_temp_score.txt"));
	String line;
	BufferedReader weights = new BufferedReader( new FileReader(product+"_noun_weights"));
	String score = "";
	String ln;
	while( (ln = weights.readLine())!=null)
	{
		score = score+","+ln.split(" ")[1];
	}
	score= score.substring(1);
	
	//String score="1.2081,1.3672,1.3062,1.2219,1.3839,1.2419,1.2995,1.3474,1.4455,1.3825,1.5396,1.3028,1.4745,1.3976,1.5969,1.7643,2.5571";
			
	String[] arr=new String[100];
	String[] scr=new String[100];
	scr=score.split(",");
	
	while((line=br.readLine())!=null){
		
		
			float sum=0;
			arr=line.split(",");
			for(int i=0;i<arr.length;i++){
				int val = Integer.parseInt(arr[i]);
			if(val>0){
				sum=sum+Float.parseFloat(scr[i].toString().trim());
			}
			if(val<0){
				sum=sum-Float.parseFloat(scr[i].toString().trim());
			}
			
			}
			pr.println(line+","+sum);
			System.out.println(line+","+sum);
			
		
		
	}
	System.out.println(score);
	pr.close();
}
}
