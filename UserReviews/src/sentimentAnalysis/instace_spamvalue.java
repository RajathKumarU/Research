package sentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class instace_spamvalue {

	public static void main(String[] args) {
		
			BufferedReader data;
			try {
				data = new BufferedReader(new FileReader(
						"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/1_2_stars/flipkart_12stars_filt_data.txt"));
			PrintWriter pw =new PrintWriter(new FileWriter("C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/1_2_stars/flipkart_12stars_spamval.txt"));
			String line = "";
			double weight[] = { 0.619565217, 0.484184915, 0.748251748,
					0.881040892, 0.373040752, 0.452914798, 0, 0.090909091, 0.5,
					0.456453305, 0.457286432, 0.089430894, -0.05, 0.618691589,
					0.232876712 };
			double sum,min=0,min2=0;
			int invalid=0,c=0;
			while ((line = data.readLine()) != null) {
				String val[] = line.split(",");
				sum=0;
				int senti_count=0,mismatch_count=0;
				for (int i = 0; i < val.length ; i++) {
					
					int num = Integer.parseInt(val[i]);
					
					
					if(num!=0){
						senti_count++;
						sum+=(num/Math.abs(num))*weight[i];
//						if(num*weight[i]<0){
//							mismatch_count++;
//						}
						System.out.println(String.format("%.2f", sum));
					}
					
				}
				if(sum<min && senti_count>2){
					min2=min;
					min=sum;
				}
//				if(sum<0 && senti_count>2/* && ((senti_count/2)<mismatch_count)*/){
//					invalid++;
//				}
				
				if((sum<0) && (senti_count>2)){
					invalid++;
					line=line+","+String.format("%.2f", sum);
					pw.println(line+","+senti_count+",1");
				}
				else{
					line=line+","+String.format("%.2f", sum);
					pw.println(line+","+senti_count+",0");
				}
				c++;
			}
			System.out.println(min);
			System.out.println(min2);
			System.out.println(c);
			System.out.println(invalid);
			pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}