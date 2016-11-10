package searchmachine;

import java.net.URL;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProductAPIZanox {

	// load Zanox XML
	private static Document loadXMLDocument(String url) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		return factory.newDocumentBuilder().parse(new URL(url).openStream());
	}

	// Add searched Products from XML to ArrayList
	public ArrayList<Product> getXMLProductsFromURL(String searchEntered, String urlZanox) throws Exception {
		Document doc = loadXMLDocument(urlZanox);

		ArrayList<Product> zanoxList = new ArrayList<Product>();

		Node knoten = doc.getFirstChild().getLastChild();
		NodeList products = knoten.getChildNodes();

		Product reiseA = new Product();
		for (int i = 0; i < products.getLength() - 1; i++) {
			reiseA = new Product();
			NodeList nodelist1 = products.item(i).getChildNodes();
			for (int j = 0; j < nodelist1.getLength(); j++) {
				Node attribute = nodelist1.item(j);
				if (attribute.getNodeName().equals("name")) {
					reiseA.setName(attribute.getTextContent());
				}
				if (attribute.getNodeName().equals("description")) {
					reiseA.setDescription(attribute.getTextContent());
				}
				if (attribute.getNodeName().equals("price")) {
					reiseA.setPrice(Double.parseDouble(attribute.getTextContent()));
				}
				if (attribute.getNodeName().equals("trackingLinks")) {
					reiseA.setTrackingLink(attribute.getFirstChild().getLastChild().getTextContent());
				}
				if (attribute.getNodeName().equals("image")) {
					reiseA.setImageURL(attribute.getFirstChild().getLastChild().getTextContent());
				}
			}
			reiseA.setRelevance(0.0);
			zanoxList.add(reiseA);

		}
		return zanoxList;
	}

	// Add all Products from XML to ArrayList
	@GET
	@Produces("application/xml")
	public ArrayList<Product> generateXMLNotFiltered() throws Exception {

		Document doc = loadXMLDocument(
				"http://api.zanox.com/xml/2011-03-01/products?&items=5000&programs=11151,571,17141,1302,2033&connectid=47C99F744D09F5B1950E");

		ArrayList<Product> productlist = null;
		productlist = new ArrayList<Product>();

		Node knoten = doc.getFirstChild().getLastChild();
		NodeList products = knoten.getChildNodes();

		Product reiseA = new Product();
		for (int i = 0; i < products.getLength(); i++) {
			reiseA = new Product();
			NodeList nodelist1 = products.item(i).getChildNodes();
			for (int j = 0; j < nodelist1.getLength(); j++) {
				Node attribute = nodelist1.item(j);
				if (attribute.getNodeName().equals("name")) {
					reiseA.setName(attribute.getTextContent());
				}
				if (attribute.getNodeName().equals("description")) {
					reiseA.setDescription(attribute.getTextContent());
				}
				if (attribute.getNodeName().equals("price")) {
					reiseA.setPrice(Double.parseDouble(attribute.getTextContent()));
				}

				if (attribute.getNodeName().equals("trackingLinks")) {
					reiseA.setTrackingLink(attribute.getFirstChild().getLastChild().getTextContent());
				}

			}

			productlist.add(reiseA);

		}

		return productlist;

	}

}
