package searchmachine;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CsvProductReader {
	// read Products from CSV to Collection
	public static Collection<Product> getCSVProducts(SingleFile sf, int maxPrice) throws IOException {
		ArrayList<Product> productsFromCsv = new ArrayList<Product>();
		Reader in = new FileReader(sf.getFolderName() + File.separator + sf.getFileName());
		Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
		for (CSVRecord record : records) {
			if (Double.parseDouble(record.get("ProductPrice")) < maxPrice || maxPrice == 0.0) {
				Product p = new Product();
				p.setName(record.get("ProductName"));
				p.setDescription(record.get("ProductLongDescription"));
				p.setPrice(Double.parseDouble(record.get("ProductPrice")));
				p.setShortDescription(record.get("ProductShortDescription"));
				p.setDestinationName(record.get("DestinationName"));
				p.setDestinationRegion(record.get("DestinationRegion"));
				p.setDestinationCountry(record.get("DestinationCountry"));
				p.setTrackingLink(record.get("ZanoxProductLink"));
				p.setImageURL(record.get("ImageLargeURL"));
				productsFromCsv.add(p);
			}
		}
		return productsFromCsv;
	}
}
