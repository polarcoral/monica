package monica.framework.tools;

import java.io.File;

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

}
