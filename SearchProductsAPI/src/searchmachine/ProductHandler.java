package searchmachine;

import java.util.ArrayList;

// outsourced Product methods
public class ProductHandler {

	// check if description contains 'stars'
	public static void readStars(ArrayList<Product> productStars) {
		for (Product p : productStars) {
			String s = "";
			for (String starsIdentifier : Product.hotelStarsIdentifier) {
				if (p.getDescription() != null) {
					if (p.getDescription().toLowerCase().contains(starsIdentifier)) {
						int position = p.getDescription().toLowerCase().indexOf(starsIdentifier);
						if (position != 0) {
							if (p.getDescription().charAt(position - 1) > '0'
									&& p.getDescription().charAt(position - 1) < '9') {
								s = "" + p.getDescription().charAt(position - 1);
								
								if ((p.getDescription().charAt(position - 2) == ',')
										&& p.getDescription().charAt(position - 3) > '0'
										&& p.getDescription().charAt(position - 3) < '9') {
									s = "" + p.getDescription().charAt(position-3) + ',' +p.getDescription().charAt(position -1);
								}
								p.setStars(s);
							} else if (p.getDescription().charAt(position - 2) > '0'
									&& p.getDescription().charAt(position - 2) < '9') {
								if (position >= 2) {
									s = "" + p.getDescription().charAt(position - 2);
									if ((p.getDescription().charAt(position - 3) == ',')
											&& p.getDescription().charAt(position - 4) > '0'
											&& p.getDescription().charAt(position - 4) < '9') {
										s = "" + p.getDescription().charAt(position-4) + ',' +p.getDescription().charAt(position -2);
									}
									
									p.setStars(s);

								}
							}
						}
					}
				}
			}
		}
	}

	// check if String is convertible to Double
	public static boolean isDouble(String isnumber) {
		try {
			Double.parseDouble(isnumber);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
