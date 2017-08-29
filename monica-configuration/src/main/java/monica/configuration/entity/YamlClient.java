package monica.configuration.entity;

import java.util.List;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class YamlClient {
	private String provider;
	private String loadbalance;
	private List<YamlRegistryServer> servers;

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getLoadbalance() {
		return loadbalance;
	}

	public void setLoadbalance(String loadbalance) {
		this.loadbalance = loadbalance;
	}

	public List<YamlRegistryServer> getServers() {
		return servers;
	}

	public void setServers(List<YamlRegistryServer> servers) {
		this.servers = servers;
	}

	public String toServerString() {
		String serverListString = "";
		for (YamlRegistryServer s : servers) {
			serverListString = serverListString + s.toString();
			if (servers.indexOf(s) != (servers.size() - 1)) {
				serverListString = serverListString + ",";
			}
		}
		return serverListString;
	}
}
