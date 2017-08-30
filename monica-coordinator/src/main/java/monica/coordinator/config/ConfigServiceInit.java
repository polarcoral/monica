package monica.coordinator.config;

import monica.coordinator.ConfigService;
import monica.framework.extension.ExtensionProvider;

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
