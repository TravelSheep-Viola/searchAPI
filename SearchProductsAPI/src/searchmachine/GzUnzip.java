package searchmachine;

import java.io.*;
import java.util.zip.*;

public class GzUnzip {

	// unzip *.gz
	// fileIn: file to unzip
	// fileOut: unzipped file
	public static void gzUnzip(String fileIn, String fileOut) {
		GZIPInputStream gis = null;
		BufferedOutputStream bos = null;

		// open Input & Output Stream
		try {
			String fileName = fileIn;
			gis = new GZIPInputStream(new FileInputStream(fileName));
			bos = new BufferedOutputStream(new FileOutputStream(fileOut));

			int ch;

			// write Output Stream from Input Stream
			while ((ch = gis.read()) != -1) {
				bos.write(ch);
			}
		} catch (IOException e) {
			System.out.println("Error at unzipping" + fileIn);
		} finally {

			// close Input & Output Stream
			try {
				if (bos != null)
					bos.close();
				if (gis != null)
					gis.close();
			} catch (Exception ex) {
			}
		}
	}
}