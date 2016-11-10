package searchmachine;


// 
public class SingleFile {

	// create variables
	private String fileName = "";
	private String folderName = "";
	private String fileSourceUrl;

	// constructor
	public SingleFile(String fileName, String fileSourceUrl, String folderName) {
		this.setFileSourceUrl(fileSourceUrl);
		this.setFileName(fileName);
		this.setFolderName(folderName);
	}
	
	// check if file is *.gz
	public boolean isArchive() {
		return fileName.endsWith(".gz");
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSourceUrl() {
		return fileSourceUrl;
	}

	public void setFileSourceUrl(String fileSourceUrl) {
		this.fileSourceUrl = fileSourceUrl;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
}
