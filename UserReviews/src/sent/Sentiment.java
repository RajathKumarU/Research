package sent;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Sentiment {
	static HashMap<String, Integer> ht;
	static String product = "samsunggalaxys6";
	public static void main(String[] args) {
		
		try {
			// extracting stemmed nouns to an array

			// GIVE THE PATH TO THE FILE HERE
			//Scanner k = new Scanner(new File("ipfile"));
			String nouns = new Scanner(
					new File(
							product+"_noun_freq.txt"),"UTF-8")
					.useDelimiter("\\Z").next();
			Scanner hnoun = new Scanner(
					new File(
							product+"_noun_freq.txt"),"UTF-8");
			String temp[] = nouns.split("\n");

			String narray[] = new String[temp.length];
			
			ht = new HashMap<String, Integer>();
			for (int i = 0; i < temp.length; i++)
			{
				narray[i] = hnoun.nextLine();
				//narray[i] = temp[i].split(" ")[0]; 
				ht.put(narray[i], 0);
				System.out.println(narray[i]);
			}
			Set n = ht.keySet();
			Iterator itr = n.iterator();
			while (itr.hasNext())
				System.out.println(ht.get(itr.next().toString()));

			// Reading the opinion file
			/*String opn = new Scanner(
					new File(
							"C:/Users/RajathMeghana/Downloads/feedback/latest/mi4i_opinions_4_old.txt"))
					.useDelimiter("\\Z").next();
			String opinion[] = opn.split("\n");*/
			
			Scanner sc = new Scanner(new File(product+"_opinions_4.txt"),"ANSI");
			// training the opinion classifier
			// String x[] ={"C:/Users/Aniruddha/Downloads/JARs"};/*Dont change
			// this!*/
			// PolarityBasic pb = new PolarityBasic(x);
			// pb.train();
			
			//String url = "http://sentiment.vivekn.com/api/text/"; // <<----Don't
																	// change
																	// this
			//Vivekn sa = new Vivekn(url);
			
			CustomSentiment cs = new CustomSentiment();
			// opening file to write
			FileOutputStream fos = new FileOutputStream(
					product+"_sentiment.arff",
					false);

			fos.write(new String("@relation 'user-"+product+"-reviews'\n\n")
					.getBytes());

			String writtenLine = "";
			Iterator itr2 = n.iterator();
			for(int i = 0 ; i<narray.length ; i++) {
				String noun = narray[i];
				writtenLine = writtenLine + "@attribute " +noun+" numeric\n";
			}
			writtenLine = writtenLine + "\n@data\n\n";
			writtenLine = writtenLine + "\n";
			fos.write(writtenLine.getBytes());
			
			//sc.skip(Pattern.compile("(?:.*\\r?\\n|\\r){1000}"));
			//sc.skip(Pattern.compile("(?:.*\\r?\\n|\\r){142}"));
			int count = 0;
			boolean flag= false;
			String opinion="";
			while (/*int i = 0; i < opinion.length; i++, count++*/sc.hasNext()) {
				try{
				// System.out.println("iteration "+i);
				if(!flag)
					opinion = sc.nextLine();
				//System.out.println(opinion);
				opinion = opinion.replace("}{", " ");
				opinion = opinion.replace("{", "");
				opinion = opinion.replace("}", "");
				String atts[] = opinion.split(" ");
				// System.out.println(atts[i]);

				for (int j = 0; j < atts.length; j++) {
					 //System.out.println(atts[j]);
					String inatts[] = atts[j].split(",");

					// find noun index
					int nindex = 0;
					String stemmedNoun = "";
					for (int k1 = 0; k1 < inatts.length; k1++)
						L: {
							Iterator itr1 = n.iterator();
							// System.out.println(inatts[k]);
							while (itr1.hasNext()) {
								String cur = itr1.next().toString();
								// System.out.println(cur);
								if (inatts[k1].contains(cur)) {
									nindex = k1;
									stemmedNoun = cur;
									break L;
								}

							}
						}
					try {
						int curVal = ht.get(stemmedNoun);
						System.out.println("be4 loop" + stemmedNoun + " "
								+ nindex );

						if (inatts.length != 1) {
							System.out.println(inatts.length);
							for( int k = 0 ; k < inatts.length ; ++k)
							{									
								if (k == nindex)
									continue;

									// System.out.println(inatts[k]);
									// curVal = curVal+pb.evaluate(inatts[k]);
									//curVal = curVal
									//		+ sa.getSentiment(inatts[k]);
									curVal = curVal+cs.getSentiment(inatts[k]);
							}	

						}
						System.out.println(stemmedNoun + " " + curVal);
						ht.put(stemmedNoun, curVal);
					} catch (Exception e) {
					//e.printStackTrace();
					}

					/*
					 * String s = new Scanner(System.in).next(); pb.evaluate(s);
					 */
					// System.out.println(ht.get("heat"));
					// Write to file here
				}

				writtenLine = "";
				itr2 = n.iterator();
				for( int l = 0 ; l<narray.length ; l++) {
					String noun = itr2.next().toString();
					writtenLine = writtenLine + ht.get(narray[l]) + ",";
				}
				writtenLine = writtenLine
						.substring(0, writtenLine.length() - 1);

				writtenLine =writtenLine+"\n";
				fos.write(writtenLine.getBytes());

				Iterator itr1 = n.iterator();
				while (itr1.hasNext())
					ht.put(itr1.next().toString(), 0);
				flag = false;
				}catch(Exception e)
				{
					e.printStackTrace();
					Iterator itr1 = n.iterator();
					while (itr1.hasNext())
						ht.put(itr1.next().toString(), 0);
					flag= true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void generateSentiment(String product)
	{	
		try {
			// extracting stemmed nouns to an array

			// GIVE THE PATH TO THE FILE HERE
			//Scanner k = new Scanner(new File("ipfile"));
			String nouns = new Scanner(
					new File(
							product+"_noun_freq.txt"),"UTF-8")
					.useDelimiter("\\Z").next();
			
			Scanner hnoun = new Scanner(
					new File(
							product+"_noun_freq.txt"),"UTF-8");
			String temp[] = nouns.split("\n");

			String narray[] = new String[temp.length];
			
			ht = new HashMap<String, Integer>();
			for (int i = 0; i < temp.length; i++)
			{
				narray[i] = hnoun.nextLine();
				//narray[i] = temp[i].split(" ")[0]; 
				ht.put(narray[i], 0);
				System.out.println(narray[i]);
			}
			
			Set n = ht.keySet();
			Iterator itr = n.iterator();
			while (itr.hasNext())
				System.out.println(ht.get(itr.next().toString()));

			// Reading the opinion file
			/*String opn = new Scanner(
					new File(
							"C:/Users/RajathMeghana/Downloads/feedback/latest/mi4i_opinions_4_old.txt"))
					.useDelimiter("\\Z").next();
			String opinion[] = opn.split("\n");*/
			
			Scanner sc = new Scanner(new File(product+"_opinions_4.txt"),"UTF-8");
			Scanner allrev = new Scanner( new File( product+".txt"),"UTF-8");
			// training the opinion classifier
			// String x[] ={"C:/Users/Aniruddha/Downloads/JARs"};/*Dont change
			// this!*/
			// PolarityBasic pb = new PolarityBasic(x);
			// pb.train();
			
			//String url = "http://sentiment.vivekn.com/api/text/"; // <<----Don't
																	// change
																	// this
			//Vivekn sa = new Vivekn(url);
			
			CustomSentiment cs = new CustomSentiment();
			// opening file to write
			FileOutputStream fos = new FileOutputStream(
					product+"_sentiment.arff",
					false);

			fos.write(new String("@relation 'user-"+product+"-reviews'\n\n")
					.getBytes());

			String writtenLine = "";
			Iterator itr2 = n.iterator();
			for(int i = 0 ; i<narray.length ; i++) {
				String noun = narray[i];
				writtenLine = writtenLine + "@attribute " + noun + " numeric\n";
			}
			writtenLine = writtenLine + "\n@data\n\n";
			writtenLine = writtenLine + "\n";
			fos.write(writtenLine.getBytes());
			
			//sc.skip(Pattern.compile("(?:.*\\r?\\n|\\r){1000}"));
			//sc.skip(Pattern.compile("(?:.*\\r?\\n|\\r){142}"));
			int count = 0;
			boolean flag= false;
			String opinion="";
			while (/*int i = 0; i < opinion.length; i++, count++*/sc.hasNext() && allrev.hasNext()) 
			{
				try{
				// System.out.println("iteration "+i);
				if(!flag)
				{
					opinion = sc.nextLine();
					allrev.nextLine();
				}
				System.out.println(opinion);
				opinion = opinion.replace("}{", " ");
				opinion = opinion.replace("{", "");
				opinion = opinion.replace("}", "");
				String atts[] = opinion.split(" ");
				// System.out.println(atts[i]);

				for (int j = 0; j < atts.length; j++) {
					// System.out.println(atts[j]);
					String inatts[] = atts[j].split(",");

					// find noun index
					int nindex = 0;
					String stemmedNoun = "";
					for (int k1 = 0; k1 < inatts.length; k1++)
						L: {
							Iterator itr1 = n.iterator();
							// System.out.println(inatts[k]);
							while (itr1.hasNext()) {
								String cur = itr1.next().toString();
								// System.out.println(cur);
								if (inatts[k1].contains(cur)) {
									nindex = k1;
									stemmedNoun = cur;
									break L;
								}

							}
						}
					try {
						int curVal = ht.get(stemmedNoun);
						System.out.println("be4 loop" + stemmedNoun + " "
								+ nindex );

						if (inatts.length != 1) {
							System.out.println(inatts.length);
							for( int k = 0 ; k < inatts.length ; ++k)
							{									
								if (k == nindex)
									continue;

									// System.out.println(inatts[k]);
									// curVal = curVal+pb.evaluate(inatts[k]);
									//curVal = curVal
									//		+ sa.getSentiment(inatts[k]);
									curVal = curVal+cs.getSentiment(inatts[k]);
							}	

						}
						System.out.println(stemmedNoun + " " + curVal);
						ht.put(stemmedNoun, curVal);
					} catch (Exception e) {
					}

					/*
					 * String s = new Scanner(System.in).next(); pb.evaluate(s);
					 */
					// System.out.println(ht.get("heat"));
					// Write to file here
				}

				writtenLine = "";
				itr2 = n.iterator();
				for( int l = 0 ; l<narray.length ; l++) {
					String noun = itr2.next().toString();
					writtenLine = writtenLine + ht.get(narray[l]) + ",";
				}
				writtenLine = writtenLine
						.substring(0, writtenLine.length() - 1);

				writtenLine =writtenLine+"\n";
				fos.write(writtenLine.getBytes());

				Iterator itr1 = n.iterator();
				while (itr1.hasNext())
					ht.put(itr1.next().toString(), 0);
				flag = false;
				}catch(Exception e)
				{
					e.printStackTrace();
					Iterator itr1 = n.iterator();
					while (itr1.hasNext())
						ht.put(itr1.next().toString(), 0);
					flag= true;
				}
				

			}
			
			if( allrev.hasNext() && !sc.hasNext())
			{
				while( allrev.hasNext() )
				{
					allrev.nextLine();
					String Zline="";
					for( int i = 0 ; i<narray.length ; i++)
						Zline = Zline+"0,";
					Zline = Zline.substring(0,Zline.length()-1);
					
					fos.write((Zline+"\n").getBytes());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

			
	}
	/*static void initializeHashTable() {
		Set n = ht.entrySet();
		Iterator itr = n.iterator();
		while (itr.hasNext())
			ht.put(itr.next().toString(), 0);
	}*/
}
