package monica.registry.service;

import org.apache.curator.framework.CuratorFramework;

import monica.registry.ServerDiscovery;
import monica.registry.context.RegistryContext;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class ZookeeperServerDiscovery implements ServerDiscovery {
	private CuratorFramework zkClient = (CuratorFramework)RegistryContext.clientCache.get("curatorClient");

	public void discover() {
		zkClient.getChildren();
		
	}
}
