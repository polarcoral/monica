package monica.registry.service;

import monica.framework.extension.ExtensionProvider;
import monica.registry.Registration;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */

public class RegistrationFactory {

	public static Registration getRegistryService() throws InstantiationException, ClassNotFoundException,  IllegalAccessException{
		Registration service = (Registration)ExtensionProvider.getDefaultExtension(Registration.class).newInstance();
		
		return service;   	
	}

}
