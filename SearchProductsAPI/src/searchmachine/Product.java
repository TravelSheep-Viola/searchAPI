package searchmachine;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.xml.bind.annotation.XmlRootElement;

// 'main' class
@Path("/freitextsuche")
@XmlRootElement(name = "Reiseangebot")
public class Product {

	// Create Variables
	private String name;
	private double price;
	private String currency;
	private String description;
	private String shortDescription;
	private String imageURL;
	private String category;
	private String destinationName;
	private String destinationRegion;
	private String destinationCountry;
	private String trackingLink;
	private String stars;
	private double relevance;

	// Word Filters
	static List<String> pricePrep = Arrays.asList("unter", "maximal", "max", "bis", "bis zu", "höchstens");
	static List<String> euroCurrency = Arrays.asList("€", "euro", "eur");
	static List<String> fillWords = Arrays.asList("ich", "möchte", "will");
	static List<String> hotelStarsIdentifier = Arrays.asList(" stern", "-stern", "*");

	// 'Main' Method
	@Path("{suche}")
	@GET
	@Produces("application/xml")
	// Read Parameters
	// suche: search query read from URL path
	// size: maximum number of results, read from URL parameter (currently not used)
	public List<Product> generateXMLForSearch(@PathParam("suche") String suche, @QueryParam("size") int size)
			throws Exception {
		String searchQuery = suche;

		// API URLs + Searchquery
		// urlZanox currently not used
		String urlZanox = "http://api.zanox.com/xml/2011-03-01/products?q=" + searchQuery
				+ "&items=500&programs=11151,571,17141,1302,2033&connectid=47C99F744D09F5B1950E";
		// Affili.net API delivers JSON results
		String affiliURL = "https://product-api.affili.net/V3/productservice.svc/JSON/SearchProducts?PublisherId=769765&Password=ccBsWK2JGURqxU6zxNWu&ImageScales=Image180&Query="
				+ searchQuery;

		// split searchQuery into array
		String[] searchArray = searchQuery.split("\\+");

		// Create ArrayList for collecting Products
		ArrayList<Product> productlist = null;
		productlist = new ArrayList<Product>();

		// check if maxPrize is set from user input
		double doubleprice = 0.0;
		for (int i = 0; i < searchArray.length; i++) {
			if (i > 0) {
				String doublestring = searchArray[i].replace(',', '.');
				if (ProductHandler.isDouble(doublestring)) {
					if ((searchArray[i + 1]) != null) {
						//check if number between pricePrep and currency word filter
						if (euroCurrency.contains(searchArray[i + 1])) {
							if (pricePrep.contains(searchArray[i - 1])) {
								doubleprice = Double.parseDouble(doublestring);
							}
						}
					}
				}
			}
		}
		
		// set maxPrice Parameter in Zanox/Affili.net URL
		int maxPrice = (int) doubleprice;
		if (maxPrice > 0) {
			urlZanox = urlZanox + "&maxprice=" + maxPrice;
			affiliURL = affiliURL + "&MaximumPrice=" + maxPrice;
		}

		// Add products from Zanox API via XML (exchanged with CSV -> more data)
		// productlist.addAll(getXMLProductsFromURL(suchanfrage, urlZanox));

		// Add products from Affili.net API via JSON
		try {
			productlist.addAll(JsonProductReader.getJsonProductsFromUrl(searchQuery, affiliURL));
		} catch (UnknownHostException e) {
			System.out.println("affili Fail");
		}

		// Add CSV Files from Zanox
		// Download of files is handled by Admin.java
		try {
			for (SingleFile sf : FileManager.fileList) {
				productlist.addAll(CsvProductReader.getCSVProducts(sf, maxPrice));
			}
		} catch (Exception e) {
			System.out.println("Zanox Fail");
		}

		// method call to get hotel stars
		ProductHandler.readStars(productlist);

		// method call to get sorted results
		List<Product> resultList = RelevanceCalculator.calculateRelevance(productlist, searchArray);

		// sort products by relevance
		Collections.sort(resultList, new RelevanceComparator());

		
		// return search results
		return resultList;
		// .subList(0, size);
	}

	// Default Constructor
	public Product() {

	}

	// getter & setter
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}

	public String getName() {
		return name;
	}

	public void setName(String l) {
		this.name = l;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTrackingLink() {
		return trackingLink;
	}

	public void setTrackingLink(String trackingLink) {
		this.trackingLink = trackingLink;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public double getRelevance() {
		return relevance;
	}

	public void setRelevance(double relevance) {
		this.relevance = relevance;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationCountry() {
		return destinationCountry;
	}

	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	public String getDestinationRegion() {
		return destinationRegion;
	}

	public void setDestinationRegion(String destinationRegion) {
		this.destinationRegion = destinationRegion;
	}

	public static List<String> getPricePrep() {
		return pricePrep;
	}

	public static void setPricePrep(List<String> pricePrep) {
		Product.pricePrep = pricePrep;
	}

	public static List<String> getEuroCurrency() {
		return euroCurrency;
	}

	public static void setEuroCurrency(List<String> euroCurrency) {
		Product.euroCurrency = euroCurrency;
	}

	public static List<String> getFillWords() {
		return fillWords;
	}

	public static void setFillWords(List<String> fillWords) {
		Product.fillWords = fillWords;
	}

	public static List<String> getHotelStarsIdentifier() {
		return hotelStarsIdentifier;
	}

	public static void setHotelStarsIdentifier(List<String> hotelStarsIdentifier) {
		Product.hotelStarsIdentifier = hotelStarsIdentifier;
	}

}