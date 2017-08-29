package monica.registry.centre;

import monica.registry.Registration;
import monica.registry.base.RegistryType;
import monica.registry.base.UriSpec;
import monica.registry.service.RegistrationFactory;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class RegistryCentre {

	public void start() throws Exception {
		Registration service = RegistrationFactory.getRegistryService();
		service.register(getUri(), getType());
	}

	public static boolean registryFinished() {
		return true;
	}

	private UriSpec uri;
	private RegistryType type;

	public UriSpec getUri() {
		return uri;
	}

	public RegistryCentre setUri(UriSpec uri) {
		this.uri = uri;
		return this;
	}

	public RegistryType getType() {
		return type;
	}

	public RegistryCentre setType(RegistryType type) {
		this.type = type;
		return this;
	}

	private void register() {

	}

}
