package searchmachine;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

// Admin Tooll
@Path("/admin")
public class Admin {

	// download Files manually
	@Path("/updatecsvfiles")
	@Produces("text/html")
	public String csvUpload() {
		FileManager.addDefaultFilesToFileList();
		FileManager.downloadFiles();
		return "<html><body><p>Dateien erfolgreich heruntergeladen.</p></body></html>";
	}

}
