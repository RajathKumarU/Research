package after_rating;

import java.io.BufferedReader;
import java.io.FileReader;

public class ProsCons {
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(
							"C:/Users/RajathMeghana/Downloads/samsunggalaxys6_big_instances.arff"));
			String line = "", file_contents = "";

			while ((line = reader.readLine()) != null) {
				file_contents += line + "\n";
			}

			String data[] = file_contents.split("\n");
			String attrString = "";
			int line_with_data = 0;

			for (int i = 0; i < data.length; i++) {
				if (data[i].contains("@attribute")) {
					attrString += (data[i].split(" ")[1]) + ",";
				}
				if (data[i].contains("@data")) {
					line_with_data = i + 1;
					break;
				}
			}
			String attributes[] = attrString.split(",");
			System.out.println("Attributes:");
			for (String string : attributes) {
				System.out.print(string + ",");
			}
			System.out.println("\n");

			String actual_dataString = "";

			for (int i = line_with_data; i < data.length; i++) {
				if (data[i].trim().length() > 0)
					actual_dataString += data[i] + "\n";
			}
			String actual_data[] = actual_dataString.split("\n");

			int value_count[] = new int[attributes.length];
			int pos_neg_count[] = new int[attributes.length];

			for (int i = 0; i < actual_data.length; i++) {
				String vals[] = actual_data[i].split(",");

				for (int k = 0; k < vals.length; k++) {
					if (Integer.parseInt(vals[k]) > 0) {
						value_count[k]++;
						pos_neg_count[k]++;
					} else if (Integer.parseInt(vals[k]) < 0) {
						value_count[k]++;
						pos_neg_count[k]--;
					}
				}
			}

			System.out.println("positive and negative deviation");
			for (int i : pos_neg_count) {
				System.out.print(i + ",");
			}
			System.out.println("\n");

			System.out.println("Total mentioned count");
			for (int i : value_count) {
				System.out.print(i + ",");
			}
			System.out.println("\n");

			System.out.println("pos_neg_count / total_mentioned_count");
			for (int i = 0; i < pos_neg_count.length; i++) {
				System.out.print(pos_neg_count[i] / (double) value_count[i]
						+ ",");
			}
			System.out.println("\n");

			int top1indx = -1;
			int top2indx = -1;
			int top3indx = -1;

			int bottom1indx = 9999;
			int bottom2indx = 9999;
			int bottom3indx = 9999;

			// for getting Pros indexes
			boolean flag = false;
			for (int i = 0; i < pos_neg_count.length; i++) {
				double val = (pos_neg_count[i] / (double) value_count[i]);
				if (pos_neg_count[i] == 0 && value_count[i] == 0) {
					val = 0;
				}

				if (val <= 0.5d && !flag) {
					continue;
				}
				if (!flag) {
					top1indx = i;
					flag = true;
					continue;
				}

				double val_at_top1indx = (pos_neg_count[top1indx] / (double) value_count[top1indx]);
				if (pos_neg_count[top1indx] == 0 && value_count[top1indx] == 0) {
					val_at_top1indx = 0;
				}

				if (val > 0.5 && val >= val_at_top1indx) {
					top1indx = i;
				}
			}

			flag = false;
			for (int i = 0; i < pos_neg_count.length; i++) {
				if (i != top1indx) {
					double val = (pos_neg_count[i] / (double) value_count[i]);
					if (pos_neg_count[i] == 0 && value_count[i] == 0) {
						val = 0;
					}

					if (val <= 0.5d && !flag) {
						continue;
					}
					if (!flag) {
						top2indx = i;
						flag = true;
						continue;
					}

					double val_at_top2indx = (pos_neg_count[top2indx] / (double) value_count[top2indx]);
					if (pos_neg_count[top2indx] == 0
							&& value_count[top2indx] == 0) {
						val_at_top2indx = 0;
					}

					if (val > 0.5 && val >= val_at_top2indx) {
						top2indx = i;
					}
				}
			}

			flag = false;
			for (int i = 0; i < pos_neg_count.length; i++) {
				if ((i != top1indx) && (i != top2indx)) {
					double val = (pos_neg_count[i] / (double) value_count[i]);
					if (pos_neg_count[i] == 0 && value_count[i] == 0) {
						val = 0;
					}

					if (val <= 0.5d && !flag) {
						continue;
					}
					if (!flag) {
						top3indx = i;
						flag = true;
						continue;
					}

					double val_at_top3indx = (pos_neg_count[top3indx] / (double) value_count[top3indx]);
					if (pos_neg_count[top3indx] == 0
							&& value_count[top3indx] == 0) {
						val_at_top3indx = 0;
					}

					if (val > 0.5 && val >= val_at_top3indx) {
						top3indx = i;
					}
				}
			}

			// for getting indexed of cons
			flag = false;
			for (int i = 0; i < pos_neg_count.length; i++) {
				double val = (pos_neg_count[i] / (double) value_count[i]);
				if (pos_neg_count[i] == 0 && value_count[i] == 0) {
					val = 0;
				}

				if (val >= -0.5d && !flag) {
					continue;
				}
				if (!flag) {
					bottom1indx = i;
					flag = true;
					continue;
				}

				double val_at_bottom1indx = (pos_neg_count[bottom1indx] / (double) value_count[bottom1indx]);
				if (pos_neg_count[bottom1indx] == 0
						&& value_count[bottom1indx] == 0) {
					val_at_bottom1indx = 0;
				}

				if ((val < -0.5d) && (val <= val_at_bottom1indx)) {
					bottom1indx = i;
				}
			}

			flag = false;
			for (int i = 0; i < pos_neg_count.length; i++) {
				if (i != bottom1indx) {
					double val = (pos_neg_count[i] / (double) value_count[i]);
					if (pos_neg_count[i] == 0 && value_count[i] == 0) {
						val = 0;
					}

					if (val >= -0.5d && !flag) {
						continue;
					}
					if (!flag) {
						bottom2indx = i;
						flag = true;
						continue;
					}

					double val_at_bottom2indx = (pos_neg_count[bottom2indx] / (double) value_count[bottom2indx]);
					if (pos_neg_count[bottom2indx] == 0
							&& value_count[bottom2indx] == 0) {
						val_at_bottom2indx = 0;
					}

					if (val < -0.5d && val <= val_at_bottom2indx) {
						bottom2indx = i;
					}
				}
			}

			flag = false;
			for (int i = 0; i < pos_neg_count.length; i++) {
				if ((i != bottom1indx) && (i != bottom2indx)) {
					double val = (pos_neg_count[i] / (double) value_count[i]);
					if (pos_neg_count[i] == 0 && value_count[i] == 0) {
						val = 0;
					}

					if (val >= -0.5d && !flag) {
						continue;
					}
					if (!flag) {
						bottom3indx = i;
						flag = true;
						continue;
					}

					double val_at_bottom3indx = (pos_neg_count[bottom3indx] / (double) value_count[bottom3indx]);
					if (pos_neg_count[bottom3indx] == 0
							&& value_count[bottom3indx] == 0) {
						val_at_bottom3indx = 0;
					}

					if (val < -0.5d && val <= val_at_bottom3indx) {
						bottom3indx = i;
					}
				}
			}

			// Print Pros and Cons
			if (top1indx != -1)
				System.out.println("Pros:\n" + attributes[top1indx]);
			if (top2indx != -1)
				System.out.println(attributes[top2indx]);
			if (top3indx != -1)
				System.out.println(attributes[top3indx]);

			if (bottom1indx != 9999)
				System.out.println("\n\nCons:\n" + attributes[bottom1indx]);
			if (bottom2indx != 9999)
				System.out.println(attributes[bottom2indx]);
			if (bottom3indx != 9999)
				System.out.println(attributes[bottom3indx]);

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
