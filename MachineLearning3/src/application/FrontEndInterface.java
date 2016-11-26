package application;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

class Front extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	String cityval = "Mysore", statusvalstr, area;
	int bedroomval;
	double areaval;
	int statusval;
	JLabel cityLabel, areaLabel, houseSizeLabel, bedRoomsLabel, statusLabel,
			predictedPriceLabel;
	JTextField areaField, houseSizeField, bedroomsField, predictedPriceField;
	JRadioButton newButton, resaleButton;
	ButtonGroup cityGroup, statusGroup;
	JButton predictButton, updateButton;
	JPanel north, center, parea, psize, pbedrooms, pstatus, pcity, south,
			south1;
	JComboBox<String> cityBox, areabox;

	Connection conn, conc = null;
	Statement stmt, stm = null;
	String a1 = "", a2 = "";
	float f1 = 0, f2 = 0;

	@SuppressWarnings("deprecation")
	public Front() {

		setSize(400, 400);
		setTitle("House Price Predictor");

		cityLabel = new JLabel("Select City");
		areaLabel = new JLabel("Select Area");
		houseSizeLabel = new JLabel("Enter House Size(sq.ft)");
		bedRoomsLabel = new JLabel("Select no. of Bedrooms");
		statusLabel = new JLabel("Select status");
		predictedPriceLabel = new JLabel("Price");

		areaField = new JTextField(20);
		houseSizeField = new JTextField(10);
		bedroomsField = new JTextField(10);
		predictedPriceField = new JTextField(20);
		predictedPriceField.setEditable(false);

		newButton = new JRadioButton("New");
		newButton.setSelected(true);

		resaleButton = new JRadioButton("Resale");

		statusGroup = new ButtonGroup();
		statusGroup.add(newButton);
		statusGroup.add(resaleButton);

		predictButton = new JButton("Predict");
		updateButton = new JButton("Update Data");

		cityBox = new JComboBox<String>();
		areabox = new JComboBox<String>();

		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println("Connecting to database...");
			conc = DriverManager.getConnection("jdbc:sqlite:test.db");
			stm = conc.createStatement();

			ResultSet buffer = stm.executeQuery("select * from mysore");
			while (buffer.next()) {
				a1 = buffer.getString("area");
				areabox.addItem(a1);
			}
		} catch (Exception e9) {
			e9.printStackTrace();
		}

		cityBox.addItem("Mysore");
		cityBox.addItem("Bangalore");
		cityBox.addItem("Chennai");
		cityBox.addItem("New-Delhi");
		cityBox.addItem("Hyderabad");
		cityBox.addItem("Kolkata");
		cityBox.addItem("Mumbai");
		cityBox.addItem("Pune");

		north = new JPanel();
		pcity = new JPanel();
		center = new JPanel();
		parea = new JPanel();
		psize = new JPanel();
		pbedrooms = new JPanel();
		pstatus = new JPanel();
		south = new JPanel();
		south1 = new JPanel();

		Container con = getContentPane();
		con.setLayout(new BorderLayout());

		south.setLayout(new BorderLayout());
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

		pcity.add(cityLabel);
		pcity.add(cityBox);
		north.add(pcity);
		parea.add(areaLabel);
		parea.add(areabox);
		center.add(parea);
		psize.add(houseSizeLabel);
		psize.add(houseSizeField);
		center.add(psize);
		pbedrooms.add(bedRoomsLabel);
		pbedrooms.add(bedroomsField);
		center.add(pbedrooms);
		pstatus.add(statusLabel);
		pstatus.add(newButton);
		pstatus.add(resaleButton);
		center.add(pstatus);
		center.add(predictButton);
		south1.add(predictedPriceLabel);
		south1.add(predictedPriceField);
		south.add(south1, BorderLayout.CENTER);
		south.add(updateButton, BorderLayout.EAST);

		con.add(north, BorderLayout.NORTH);
		con.add(center, BorderLayout.CENTER);
		con.add(south, BorderLayout.SOUTH);

		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				try {

					cityBox.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							System.out.println("coming");
							areabox.removeAllItems();

							cityval = cityBox.getSelectedItem().toString();
							System.out.println(cityval);
							try {
								Class.forName("org.sqlite.JDBC");
								System.out
										.println("Connectiing to daatabase...");
								conc = DriverManager
										.getConnection("jdbc:sqlite:test.db");
								stm = conc.createStatement();

								ResultSet buffer = stm
										.executeQuery("select * from "
												+ cityval.toLowerCase()
														.replaceAll("-", ""));
								while (buffer.next()) {
									a1 = buffer.getString("area");
									areabox.addItem(a1);
								}
							} catch (Exception e9) {
								e9.printStackTrace();
							}

						}
					});

				} catch (Exception e3) {
					e3.printStackTrace();
				}

			}

		};

		Thread th1 = new Thread(r1);
		th1.start();

		predictButton.addActionListener(this);
		updateButton.addActionListener(this);

		show();

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Predict")) {
			if (houseSizeField.getText().toString().equals("")
					|| bedroomsField.getText().toString().equals("")) {
				JOptionPane.showMessageDialog(rootPane,
						"please enter all the data!");
			} else {

				areaval = Float.parseFloat(houseSizeField.getText());
				bedroomval = Integer.parseInt(bedroomsField.getText());
				area = areabox.getSelectedItem().toString();
				if (newButton.isSelected()) {
					statusval = 1;
				} else {
					statusval = 0;
				}
				System.out.println(cityval + "," + area + "," + areaval + ","
						+ bedroomval + "," + statusval);
				double price = pricepredict(cityval, area, areaval, bedroomval,
						statusval);
				if (price == 0)
					JOptionPane.showMessageDialog(rootPane,
							"Entered Area does not exist");
				else if (price < 0)
					JOptionPane.showMessageDialog(rootPane,
							"Enter data correctly");
				else
					predictedPriceField.setText(price + "Lacs");
			}
		} else if (e.getActionCommand().equals("Update Data")) {
			int option = JOptionPane
					.showConfirmDialog(
							rootPane,
							"Updating data may consume lot of data \nand it may take hours to update.\nDo you want to continue?\n",
							"Update Data", JOptionPane.YES_NO_OPTION);

			if (option == JOptionPane.YES_OPTION) {
				Runnable r = new Runnable() {
					@Override
					public void run() {
						try {
							File f = new File("src/files1");
							File file[] = f.listFiles();
							for (int i = 0; i < file.length; i++) {
								file[i].delete();
							}

							DataExctractorImportIO.main(null);
							Preprocess.main(null);
							JOptionPane.showMessageDialog(rootPane,
									"updated succesfully!");
						} catch (Exception e3) {
							e3.printStackTrace();
						}
					}
				};
				Thread th = new Thread(r);
				th.start();
			}
		}
	}

	public double pricepredict(String city, String area, double size,
			int bedroom, int status) {
		double price = 0;
		try {
			f1 = 0;
			f2 = 0;
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			System.out.println("Connecting to database...");

			stmt = conn.createStatement();

			ResultSet buffer = stmt.executeQuery("select * from "
					+ city.toLowerCase().replaceAll("-", "") + " where area ='"
					+ area.toLowerCase() + "'");
			while (buffer.next()) {
				f1 = buffer.getFloat("lat");
				f2 = buffer.getFloat("lon");

				System.out.println(f1 + "\t\t" + f2);

			}
			if (f1 == 0 || f2 == 0)
				return 0;

			Instances data = new Instances(new BufferedReader(new FileReader(
					"src/files/d" + city.toLowerCase().replaceAll("-", "")
							+ ".arff")));
			data.setClassIndex(data.numAttributes() - 1);
			Attribute housesize = data.attribute(0);
			Attribute bedrooms = data.attribute(1);
			Attribute lattitude = data.attribute(2);
			Attribute longitude = data.attribute(3);
			Attribute stat = data.attribute(4);

			// build model
			LinearRegression model = new LinearRegression();
			String[] options = new String[2];
			options[0] = "-S";
			options[1] = "1";
			model.setOptions(options);
			model.buildClassifier(data); // the last instance with missing
			System.out.println(model);
			// classify the last instance
			Instance myHouse = new Instance(6);
			myHouse.setValue(housesize, size);
			myHouse.setValue(bedrooms, bedroom);
			myHouse.setValue(lattitude, f1);
			myHouse.setValue(longitude, f2);
			myHouse.setValue(stat, status);
			// myHouse.setValue(sellingprice,0);
			price = model.classifyInstance(myHouse);
			price = price / 100000;
			price = Math.round(price * 100.0) / 100.0;
			System.out.println("My house (" + myHouse + "): " + price);

		} catch (Exception e2) {
			e2.printStackTrace();
		}

		return price;
	}
}

public class FrontEndInterface {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Front f = new Front();
	}

}