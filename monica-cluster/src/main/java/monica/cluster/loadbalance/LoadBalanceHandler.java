package monica.cluster.loadbalance;

import java.util.List;

import monica.cluster.LoadBalance;
import monica.cluster.loadbalance.impl.RandomLoadBalance;
import monica.configuration.context.ConfigurationContext;
import monica.configuration.entity.YamlClient;
import monica.registry.context.RegistryContext;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class LoadBalanceHandler {
	private String TEMP_PROVIDER_CACHE = "temp_providers";
	private String SERVER_STRING = "server_string";

	private LoadBalance defaultAlgorithm() {
		return new RandomLoadBalance();
	}

	public void handle() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<String> providers = (List) RegistryContext.clientCache.get(TEMP_PROVIDER_CACHE);
		final String CLIENT_KEY = "client";
		YamlClient client = (YamlClient) ConfigurationContext.propMap.get(CLIENT_KEY);
		String loadBalanceName = client.getLoadbalance();
		LoadBalance balance = (LoadBalance) this.getClass().getClassLoader().loadClass(loadBalanceName).newInstance();
		int index = balance.doSelect(providers);
		String selectedString = providers.get(index);
		// providers.clear();
		// providers.add(selectedString);
	}
}
