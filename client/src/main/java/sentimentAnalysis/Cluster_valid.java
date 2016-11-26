package sentimentAnalysis;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

public class Cluster_valid {

	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;

		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}

		return inputReader;
	}

	public static void main(String[] args) throws Exception {
		SimpleKMeans kmeans = new SimpleKMeans();

		kmeans.setSeed(10);
		int a = 0, b = 0, c = 0, d = 0, e = 0;

		// important parameter to set: preserver order, number of cluster.
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(2);

		BufferedReader datafile = readDataFile("C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/flipkart_12stars_data.arff");

		Instances data = new Instances(datafile);

		kmeans.buildClusterer(data);

		// This array returns the cluster number (starting with 0) for each
		// instance
		// The array has as many elements as the number of instances
		int[] assignments = kmeans.getAssignments();
		String line = "";
		datafile = readDataFile("C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/flipkart_12stars_data.arff");
		PrintWriter p = new PrintWriter(
				new FileWriter(
						"C:/Users/RajathMeghana/Downloads/feedback/Large Data/mi4i/mi4i_Flipkart/flipkart_12stars_clustered_data.arff"));

		int i=0;
		while((line=datafile.readLine())!=null){
			if(i==20)
				break;
			i++;
		}
		for (int clusterNum = 0; (clusterNum < assignments.length)
				&& ((line = datafile.readLine()) != null); clusterNum++,i++) {
			
			System.out.printf("Instance %d -> Cluster %d \n", (clusterNum + 1),
					assignments[clusterNum]);
			p.println(line + "," + assignments[clusterNum]);

		}
		
		p.close();
		// int i=0,count=0;
		// for(int clusterNum : assignments) {
		// System.out.printf("Instance %d -> Cluster %d \n", i, clusterNum);
		// if(clusterNum==0){a++;}
		// else if(clusterNum==1){b++;}
		// else if(clusterNum==2){c++;}
		// else if(clusterNum==3){d++;}
		// else if(clusterNum==4){e++;}
		// i++;
		// }
		//
		// ArrayList<Integer> ar=new ArrayList<Integer>();
		// ar.add(a);
		// ar.add(b);
		// ar.add(c);
		// ar.add(d);
		// ar.add(e);
		//
		// ar.sort(null);
		// int clnum=assignments[assignments.length-1];
		// for(int j=0;j<assignments.length;j++){
		// if(assignments[j]==clnum){
		//
		// count++;
		// }
		// }
		// System.out.println(clnum);
		// System.out.println(count);
		// System.out.println(ar);
		// if(count==ar.get(0)){
		// System.out.println("Review rate:- 1 ");
		// }
		// else if(count==ar.get(1)){
		// System.out.println("Review rate:- 2 ");
		// }
		// else if(count==ar.get(2)){
		// System.out.println("Review rate:- 3 ");
		// }
		// else if(count==ar.get(3)){
		// System.out.println("Review rate:- 4 ");
		// }
		// else if(count==ar.get(4)){
		// System.out.println("Review rate:- 5 ");
		// }
	}
}
