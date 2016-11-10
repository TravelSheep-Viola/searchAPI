package searchmachine;

import java.util.Comparator;

public class RelevanceComparator implements Comparator<Product> {

	// override compare method to sort products by relevance
	@Override
	public int compare(Product product1, Product product2) {
		return Double.compare(product2.getRelevance(), product1.getRelevance());
	}
}