package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadClass {
	FileInputStream fis;
	Properties prop;
	String path = "C:\\Users\\gsdra\\eclipse-workspace\\YatraFlight\\ConfigurationFiles\\config.properties";

	public ReadClass() {
		try {
			fis = new FileInputStream(path);
			prop = new Properties();
			prop.load(fis);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public String getUrl() {
		String url = prop.getProperty("url");
		return url;
	}
	
	public String getBrowser() {
		String brow = prop.getProperty("browser");
		return brow;
	}
}
