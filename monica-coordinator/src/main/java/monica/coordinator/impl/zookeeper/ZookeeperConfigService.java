package monica.coordinator.impl.zookeeper;

import java.net.URLEncoder;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

import monica.configuration.context.ConfigurationContext;
import monica.coordinator.ConfigService;
import monica.registry.context.RegistryContext;
/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class ZookeeperConfigService implements ConfigService {
	public final static String NAME = "zookeeper";
	private CuratorFramework zkClient = (CuratorFramework) RegistryContext.clientCache.get("curatorClient");
	private String rulesBasePath = "/routers";
	private String CACHE_PROVIDER = "providers";
	private String CACHE_TEMP_PROVIDERS = "temp_providers";
	private String PROVIDERS_PATH = "/SERVER";

	public void initService() throws Exception {
		if (null != zkClient.checkExists().forPath(PROVIDERS_PATH)) {
			RegistryContext.clientCache.putIfAbsent(CACHE_PROVIDER, zkClient.getChildren().forPath(PROVIDERS_PATH));
			RegistryContext.clientCache.putIfAbsent(CACHE_TEMP_PROVIDERS,
					zkClient.getChildren().forPath(PROVIDERS_PATH));
		}
	}

	public void rulesStore() throws Exception {
		rulesStore(ConfigurationContext.loadYamlRouterConfig());
	}

	public void rulesStore(List<String> rules) throws Exception {
		for (String s : rules) {
			rulesStore(URLEncoder.encode(s, "UTF-8"));
		}

	}

	public void rulesStore(String ruleString) throws Exception {
		storeConfig(ruleString, rulesBasePath);
	}

	private ZookeeperConfigService storeConfig(String path, String basePath) throws Exception {
		if (null == zkClient.checkExists().forPath(basePath)) {
			zkClient.create().forPath(basePath);
		}
		if (null == zkClient.checkExists().forPath(basePath + "/" + path)) {
			zkClient.create().forPath(basePath + "/" + path);
		}
		return this;
	}

	private ZookeeperConfigService addWatcher(String path) throws Exception {
		zkClient.getChildren().usingWatcher(new CuratorWatcher() {
			public void process(WatchedEvent event) throws Exception {
				// updateLocalRules();
			}

		}).forPath(path);
		return this;
	}

}
