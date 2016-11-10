package searchmachine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloadUtility {
	private static final int BUFFER_SIZE = 4096;

	// download a file from URL to server
	// fileURL: URL of the file to be downloaded
	// saveDir: path of the directory to save the file
	// fileName: name of the output file
	public static String downloadFile(String fileURL, String saveDir, String fileName) throws IOException {
		// set default return value
		String fileReturn = "File not downloaded";

		// open connection to file URL
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// check HTTP response code
		if (responseCode == HttpURLConnection.HTTP_OK) {
			// String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

			// check if file name is empty
			if (fileName == "") {
				if (disposition != null) {
					// get file name from header field
					int index = disposition.indexOf("filename=") - 1;
					if (index > 0) {
						fileName = disposition.substring(index + 10, disposition.length());
					}
				} else {
					// get file name from URL
					fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
				}
			}
			// get file info
			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);

			// open input stream from HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = saveDir + File.separator + fileName;

			// open output stream
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			// write file
			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			// close input & output stream
			outputStream.close();
			inputStream.close();

			// overwrite default return value
			fileReturn = fileName;
			System.out.println("File downloaded");

		} else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		}
		// close connection
		httpConn.disconnect();
		return fileReturn;
	}
}