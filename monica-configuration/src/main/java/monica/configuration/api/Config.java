package monica.configuration.api;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public interface Config {
	public  void load(File file) throws Exception;
	public  void reLoad(File file) throws Exception;
}
