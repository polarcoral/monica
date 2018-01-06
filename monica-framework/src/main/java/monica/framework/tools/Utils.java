package monica.framework.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Utils {
	public static File getPropertiesPath(String propertiesName) {
		String[] pathArray = System.getProperty("java.class.path").split(";");
		for (String s : pathArray) {
			File newFile = new File(s + File.separator + propertiesName);
			if (newFile.exists()) {
				return newFile;
			}			
		}
		return null;
	}
	
	public static URL loadResources(String propertiesName) throws IOException {
		String[] pathArray = System.getProperty("java.class.path").split(";");
		URL  inURL = Utils.class.getClassLoader().getResource(propertiesName);
		if (null !=inURL) {
			return inURL;
		}			
		return null;
	}

}
