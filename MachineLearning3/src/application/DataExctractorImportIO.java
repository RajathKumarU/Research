package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import com.importio.api.clientlite.ImportIO;
import com.importio.api.clientlite.MessageCallback;
import com.importio.api.clientlite.data.Progress;
import com.importio.api.clientlite.data.Query;
import com.importio.api.clientlite.data.QueryMessage;
import com.importio.api.clientlite.data.QueryMessage.MessageType;

public class DataExctractorImportIO {
	public static int count = 0;
	public static String city = "";
	public static boolean flag = false;
	public static int i = 2;

	public static void main(String[] args) throws IOException,
			InterruptedException {

		ImportIO client = new ImportIO(
				UUID.fromString("e29adbd9-cf69-42a0-a5a2-6a77405d39d5"),
				"LCYOuRa1fqBW3hYUTb7/xLcmaYwY/LYRT8/BqJ3Sd2MSU0ZP96IHG5NPghXNExB77HzPdcycdLJpA+fscrPcXQ==",
				"import.io");

		final CountDownLatch latch = new CountDownLatch(1);

		MessageCallback messageCallback = new MessageCallback() {

			@SuppressWarnings("unchecked")
			public void onMessage(Query query, QueryMessage message,
					Progress progress) {
				if (message.getType() == MessageType.MESSAGE) {
					HashMap<String, Object> resultMessage = (HashMap<String, Object>) message
							.getData();
					if (resultMessage.containsKey("errorType")) {
						System.err.println("Got an error!");
						System.err.println(message);
					} else {
						DataExctractorImportIO.count++;
						System.out.println(DataExctractorImportIO.count
								+ " Got data! of " + city);
						String results = resultMessage.get("results")
								.toString();
						System.out.println(results);
						if (results.contains("Call for Price")) {
							flag = true;
							results = "";
							i = 2;
						} else
							JsonFormatter.writeToFile(results, city);
					}
				}
				if (progress.isFinished()) {
					latch.countDown();
				}
			}
		};

		Map<String, Object> queryInput;
		Query query;
		List<UUID> connectorGuids;

		connectorGuids = Arrays.asList(UUID
				.fromString("da637108-d8bb-400e-b848-1636b81c01b8"));
		BufferedReader br = new BufferedReader(new FileReader(
				"src/files/Cities.txt"));

		while ((city = br.readLine()) != null) {
			Thread.sleep(10000);
			flag = false;
			System.out.println(city);
			abc: while (i < 5000) {
				client.connect();
				while (i < 5000) {
					if (flag) {
						// System.out.println(flag);
						break abc;

					}
					// System.out.println(flag);
					queryInput = new HashMap<String, Object>();
					queryInput
							.put("webpage/url",
									"http://www.magicbricks.com//property-for-sale/residential-real-estate?nsrSearchBar=N&bar_propertyType_R_new=10002_10003_10021_10022,10001_10017&bar_propertyType_new=10002_10003_10021_10022,10001_10017&tab1=property&proptype=Multistorey-Apartment,Builder-Floor-Apartment,Penthouse,Studio-Apartment,Residential-House,Villa&cityName="
											+ city + "/Page-" + i);
					query = new Query();
					query.setConnectorGuids(connectorGuids);
					query.setInput(queryInput);
					client.query(query, messageCallback);
					latch.await();
					i++;
					Thread.sleep(1000);
				}

				client.disconnect();
				Thread.sleep(1000);
			}
		}
		br.close();

		System.out.println("All data received:");
	}
}
