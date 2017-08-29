package monica.service.config;

import monica.framework.extension.ExtensionProvider;
import monica.service.ConfigService;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class ConfigServiceInit {

	public static void init() {
		try {
			ConfigService configService = (ConfigService) ExtensionProvider.getDefaultExtension(ConfigService.class)
					.newInstance();
			try {
				configService.rulesStore();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
