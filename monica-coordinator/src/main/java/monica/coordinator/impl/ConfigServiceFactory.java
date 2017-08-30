package monica.coordinator.impl;

import monica.coordinator.ConfigService;
import monica.framework.extension.ExtensionProvider;
/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class ConfigServiceFactory {

	public static ConfigService getConfigService()
			throws InstantiationException, ClassNotFoundException, IllegalAccessException {
		ConfigService service = (ConfigService) ExtensionProvider.getDefaultExtension(ConfigService.class)
				.newInstance();
		return service;
	}
}
