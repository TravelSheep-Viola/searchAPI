package searchmachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JsonProductReader {

	// read Products from Affili.net JSON API to ArrayList
	public static ArrayList<Product> getJsonProductsFromUrl(String searchEntered, String affiliURL)
			throws JsonIOException, JsonSyntaxException, IOException {

		// open input stream
		BufferedReader input = new BufferedReader(new InputStreamReader(new URL(affiliURL).openStream(), "UTF-8"));

		// open connection to API
		URL urlJSON = new URL(affiliURL);
		HttpURLConnection request = (HttpURLConnection) urlJSON.openConnection();
		request.connect();

		// parse JSON to ArrayList
		JsonParser jparser = new JsonParser();
		JsonElement jsonFile = jparser.parse(input);
		JsonObject obj = jsonFile.getAsJsonObject();
		JsonArray jsonarr = obj.get("Products").getAsJsonArray();
		ArrayList<Product> jsonlist = new ArrayList<Product>();

		// read product information
		for (int i = 0; i < jsonarr.size(); i++) {
			Product p1 = new Product();
			if (!jsonarr.get(i).getAsJsonObject().get("ProductName").isJsonNull()) {
				p1.setName(jsonarr.get(i).getAsJsonObject().get("ProductName").getAsString());
			}
			p1.setPrice(Double.parseDouble((jsonarr.get(i).getAsJsonObject().get("PriceInformation").getAsJsonObject()
					.get("PriceDetails").getAsJsonObject().get("Price").getAsString())));
			if (!jsonarr.get(i).getAsJsonObject().get("Description").isJsonNull()) {
				p1.setDescription(jsonarr.get(i).getAsJsonObject().get("Description").getAsString());
			}
			if (!jsonarr.get(i).getAsJsonObject().get("DescriptionShort").isJsonNull()) {
				p1.setShortDescription(jsonarr.get(i).getAsJsonObject().get("DescriptionShort").getAsString());
			}
			if (!jsonarr.get(i).getAsJsonObject().get("AffilinetCategoryPath").isJsonNull()) {
				p1.setCategory(jsonarr.get(i).getAsJsonObject().get("AffilinetCategoryPath").getAsString());
			}

			try {
				p1.setDestinationName(jsonarr.get(i).getAsJsonObject().get("Properties").getAsJsonArray().get(0)
						.getAsJsonObject().get("PropertyValue").getAsString());
			} catch (Exception jsonException) {
				System.out.println("Json: Could not read DestinationName");
			}
			try {
				p1.setDestinationCountry(jsonarr.get(i).getAsJsonObject().get("Properties").getAsJsonArray().get(1)
						.getAsJsonObject().get("PropertyValue").getAsString());
			} catch (Exception jsonException) {
				System.out.println("Json: Could not read DestinationCountry");
			}
			try {
				p1.setDestinationRegion(jsonarr.get(i).getAsJsonObject().get("Properties").getAsJsonArray().get(2)
						.getAsJsonObject().get("PropertyValue").getAsString());
			} catch (Exception jsonException) {
				System.out.println("Json: Could not read DestinationCountry");
			}

			try{
			p1.setTrackingLink(jsonarr.get(i).getAsJsonObject().get("Deeplink1").getAsString());
			}  catch (Exception jsonException){
				System.out.println("Json: Could not read TrackingLink");
			}
			
			try{
			p1.setImageURL(jsonarr.get(i).getAsJsonObject().get("Images").getAsJsonArray().get(0).getAsJsonArray().get(0).getAsJsonObject().get("URL").getAsString());
			}  catch (Exception jsonException){
				System.out.println("Json: Could not read ImageURL");
			}
			
			jsonlist.add(p1);
		}

		return jsonlist;

	}

}
