package monica.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import monica.configuration.api.Config;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class PropertyConfig implements Config {

	public void load(File file) throws Exception {
		// TODO Auto-generated method stub
		Properties prop = new Properties();
		prop.load(new FileInputStream(file));

	}

	public void reLoad(File file) throws Exception {
		// TODO Auto-generated method stub

	}

}
