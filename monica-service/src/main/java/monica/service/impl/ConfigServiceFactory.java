package monica.service.impl;

import monica.framework.extension.ExtensionProvider;
import monica.service.ConfigService;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class ConfigServiceFactory {
	
	public static ConfigService getConfigService() throws InstantiationException, ClassNotFoundException,  IllegalAccessException{
		ConfigService service = (ConfigService)ExtensionProvider.getDefaultExtension(ConfigService.class).newInstance();
		return service;
	}
}
