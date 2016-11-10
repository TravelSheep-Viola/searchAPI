package searchmachine;

import java.io.File;
import java.util.ArrayList;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/filemanager")
public class FileManager {

	// set file path
	static String path = new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();
	//static String path = new File("").getAbsolutePath();

	// create file list with single files
	public static ArrayList<SingleFile> fileList = new ArrayList<SingleFile>();

	// add default single files to file list
	public static void addDefaultFilesToFileList() {
		// single file for Travelbird
		SingleFile travelbird = new SingleFile("Travelbird.csv.gz",
				"http://productdata.zanox.com/exportservice/v1/rest/37891892C362838618.csv?ticket=3A4079B7DB3C10AF1197344D5D169EFE&productIndustryId=2&columnDelimiter=,&textQualifier=DoubleQuote&nullOutputFormat=NullValue&dateFormat=yyyy-MM-dd'T'HH:mm:ss:SSS&decimalSeparator=period&id=&pg=&nb=&na=&pp=&po=&cy=&du=&ds=&dl=&tm=&mc=&c1=&c2=&c3=&ia=&im=&il=&df=&dt=&lk=&ss=&sa=&af=&sp=&sv=&x1=&x2=&x3=&x4=&x5=&x6=&x7=&x8=&x9=&zi=&fd=&to=&dn=&da=&dz=&dc=&dy=&dr=&dp=&do=&tu=&ti=&ta=&tr=&tt=&tp=&p3=&gZipCompress=yes",
				path);
		fileList.add(travelbird);
		
		// single file for Steigenberger
		SingleFile steigenbergerHotel = new SingleFile("Steigenberger.csv.gz",
				"http://productdata.zanox.com/exportservice/v1/rest/37946236C54142684.csv?ticket=3A4079B7DB3C10AF1197344D5D169EFE&productIndustryId=2&columnDelimiter=,&textQualifier=DoubleQuote&nullOutputFormat=NullValue&dateFormat=yyyy-MM-dd'T'HH:mm:ss:SSS&decimalSeparator=period&id=&pg=&nb=&na=&pp=&po=&cy=&du=&ds=&dl=&tm=&mc=&c1=&c2=&c3=&ia=&im=&il=&df=&dt=&lk=&ss=&sa=&af=&sp=&sv=&x1=&x2=&x3=&x4=&x5=&x6=&x7=&x8=&x9=&zi=&fd=&to=&dn=&da=&dz=&dc=&dy=&dr=&dp=&do=&tu=&ti=&ta=&tr=&tt=&tp=&p3=&gZipCompress=yes",
				path);
		fileList.add(steigenbergerHotel);
		
		// single file for Suncamp
		SingleFile suncamp = new SingleFile("Suncamp.csv.gz",
				"http://productdata.zanox.com/exportservice/v1/rest/38101340C142349509.csv?ticket=3A4079B7DB3C10AF1197344D5D169EFE&productIndustryId=2&columnDelimiter=,&textQualifier=DoubleQuote&nullOutputFormat=NullValue&dateFormat=yyyy-MM-dd'T'HH:mm:ss:SSS&decimalSeparator=period&id=&pg=&nb=&na=&pp=&po=&cy=&du=&ds=&dl=&tm=&mc=&c1=&c2=&c3=&ia=&im=&il=&df=&dt=&lk=&ss=&sa=&af=&sp=&sv=&x1=&x2=&x3=&x4=&x5=&x6=&x7=&x8=&x9=&zi=&fd=&to=&dn=&da=&dz=&dc=&dy=&dr=&dp=&do=&tu=&ti=&ta=&tr=&tt=&tp=&p3=&gZipCompress=yes",
				path);
		fileList.add(suncamp);
	}

	// download files when URL is called
	@Path("/updatefiles")
	@Produces("text/html")
	public static String downloadFiles() {
		addDefaultFilesToFileList();
		if (!fileList.isEmpty()) {
			for (SingleFile sf : fileList) {
				try {
					CsvUpdater.downloadCsvAndUnzip(sf);
					sf.setFileName(sf.getFileName().replace(".gz", ""));
					System.out.println("Downloaded and unzipped: " + sf.getFileName());

				} catch (Exception e) {
					return "Error downloading CSV files";
				}
			}
			return "Files successfully downloaded.";
		}
		return "Undefined error downloading CSV";
	}
}
