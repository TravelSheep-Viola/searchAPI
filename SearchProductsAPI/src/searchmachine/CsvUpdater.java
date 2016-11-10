package searchmachine;

import java.io.File;
import java.io.IOException;

public class CsvUpdater {

	// download and unzip
	public static void downloadCsvAndUnzip(SingleFile sf) throws IOException {
		try {
			downloadNewCSV(sf);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		unzipGZ(sf);
	}

	// download file
	public static void downloadNewCSV(SingleFile sf) throws IOException {
		HttpDownloadUtility.downloadFile(sf.getFileSourceUrl(), sf.getFolderName(), sf.getFileName());
	}

	// use gzUnzip method and rename single file
	public static String unzipGZ(SingleFile sf) throws IOException {
		String targetName = sf.getFileName().replace(".gz", "");
		GzUnzip.gzUnzip(sf.getFolderName() + File.separator + sf.getFileName(), targetName);
		sf.setFileName(sf.getFileName().replace(".gz", ""));
		return "Erfolgreich entpackt.";
	}

}
