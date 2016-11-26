package text;

import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.functions.SMO;
import weka.core.Instance;
import weka.core.Instances;

public class aaa {
	public static void main(String[] args) {
		try {
			BufferedReader inputReader = null;
			inputReader = new BufferedReader(new FileReader(
					"motog3_rated_extended_sentiment.arff"));
			// System.out.println(inputReader.readLine());

			SMO smo = new SMO();
			Instances data = new Instances(inputReader);
			data.setClassIndex(data.numAttributes() - 1);

			// SMO smo = new SMO(); smo.buildClassifier(data);
			Instance ins = null;
			//for( int i = 0 ; i< data.numAttributes(); i++)
				
			ins = data.lastInstance();
			System.out.println("Class attribute = " + smo.classifyInstance(ins));

			System.out.println("classifier built");

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
}
