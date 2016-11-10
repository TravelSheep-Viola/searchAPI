package searchmachine;

import java.util.ArrayList;

public class RelevanceCalculator {

	// calculate relevance value for products
	public static ArrayList<Product> calculateRelevance(ArrayList<Product> productlist, String[] searchArray) {
		boolean destinationFound;
		for (Product r : productlist) {
			destinationFound = false;
			for (int i = 0; i < searchArray.length; i++) {
				// name relevance
				if (r.getName().toLowerCase().contains(searchArray[i].toLowerCase())) {
					r.setRelevance(r.getRelevance() + 10.0);
				}
				try {
					// description relevance
					if (r.getDescription().toLowerCase().contains(searchArray[i].toLowerCase())
							|| r.getShortDescription().toLowerCase().contains(searchArray[i].toLowerCase())) {
						r.setRelevance(r.getRelevance() + 8.0);
					}
				} catch (NullPointerException npe) {
					System.out.println("Keine Description vorhanden!");
				}
				try {
					// destination name relevance
					if (r.getDestinationName().toLowerCase().contains(searchArray[i].toLowerCase())) {
						r.setRelevance(r.getRelevance() + 30.0);
						destinationFound = true;
					}
				} catch (NullPointerException npe) {
					System.out.println("Kein DestinationName vorhanden!");
				}
				try {
					// destination region relevance
					if (r.getDestinationRegion().toLowerCase().contains(searchArray[i].toLowerCase())) {
						if (destinationFound == true) {
							r.setRelevance(r.getRelevance() + 8.0);
						}
						if (destinationFound == false) {
							r.setRelevance(r.getRelevance() + 20.0);
						}
					}
				} catch (NullPointerException npe) {
					System.out.println("Keine DestinationRegion vorhanden!");
				}
				try {
					// destination country relevance
					if (r.getDestinationCountry().toLowerCase().contains(searchArray[i].toLowerCase())) {
						if (destinationFound == true) {
							r.setRelevance(r.getRelevance() + 4.0);
						}
						if (destinationFound == false) {
							r.setRelevance(r.getRelevance() + 10.0);
						}
					}
				} catch (NullPointerException npe) {
					System.out.println("Keine DestinationCountry vorhanden!");
				}

			}
		}

		return productlist;

	}
}
