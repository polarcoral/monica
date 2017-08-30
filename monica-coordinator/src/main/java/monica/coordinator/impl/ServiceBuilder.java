package monica.coordinator.impl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;

import monica.cluster.loadbalance.LoadBalanceHandler;
import monica.cluster.router.RouteHandler;
import monica.registry.context.RegistryContext;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class ServiceBuilder {
	private final String TEMP_PROVIDERS = "temp_providers";
	private final String PROVIDER_CACHE = "providers";
	private final String SERVER_IP_CACHE = "server_ip";
	private final String SERVER_PORT_CACHE = "server_port";

	public ServiceBuilder servicesInit() throws Exception {
		final CuratorFramework zkClient = (CuratorFramework) RegistryContext.clientCache.get("curatorClient");
		final String PROVIDER_PATH = "/SERVER";
		List<String> providers = new ArrayList<String>();
		List<String> services = zkClient.getChildren().forPath(PROVIDER_PATH);
		for (String s : services) {
			providers.add(URLDecoder.decode(s, "UTF-8"));
		}
		List<String> tempProviders = new ArrayList<String>();
		tempProviders.addAll(providers);
		RegistryContext.clientCache.putIfAbsent(PROVIDER_CACHE, providers);
		RegistryContext.clientCache.putIfAbsent(TEMP_PROVIDERS, tempProviders);
		return this;
	}

	public ServiceBuilder route() {
		RouteHandler handler = new RouteHandler();
		handler.handle();
		return this;
	}

	public ServiceBuilder loadbalance() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		LoadBalanceHandler handler = new LoadBalanceHandler();
		handler.handle();
		return this;
	}

	public void build() {
		List<String> tempList = (List<String>) RegistryContext.clientCache.get(TEMP_PROVIDERS);
		if (tempList.size() > 0) {
			String serverUrl = (String) tempList.get(0);
			String serverString = serverUrl.substring(serverUrl.indexOf("//") + 2);
			String serverIp = serverString.substring(0, serverString.indexOf(":"));
			String serverPort = serverString.substring(serverString.indexOf(":") + 1);
			RegistryContext.clientCache.putIfAbsent(SERVER_IP_CACHE, serverIp);
			RegistryContext.clientCache.putIfAbsent(SERVER_PORT_CACHE, serverPort);
		} else {

		}

	}

}
