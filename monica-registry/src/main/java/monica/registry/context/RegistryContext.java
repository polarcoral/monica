package monica.registry.context;

import java.util.concurrent.ConcurrentHashMap;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class RegistryContext {
	// caheKey:  curatorClient  providers  server  usefulProviders
	public static ConcurrentHashMap<String, Object> clientCache = new ConcurrentHashMap<String,Object>();	
}
