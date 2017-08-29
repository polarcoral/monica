package monica.registry;

import org.apache.log4j.Logger;

import monica.framework.SPI;
import monica.registry.base.RegistryType;
import monica.registry.base.UriSpec;
import monica.registry.service.ZookeeperRegistration;

/**
 * 
 * plugin point for user to extend the registry-discovery way using the
 * different tech framework.
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */

@SPI(ZookeeperRegistration.NAME)
public interface Registration {
	final Logger log = Logger.getLogger(Registration.class);

	public void register(UriSpec uri, RegistryType type) throws Exception;

	public boolean done();

}
