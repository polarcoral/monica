package monica.configuration.entity;

import java.util.List;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class YamlMonica {
	private String protocol;
	private String configCentral;
	private String registry;
	private List<YamlRegistryServer> servers;
	private String storage;
	

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getConfigCentral() {
		return configCentral;
	}

	public void setConfigCentral(String configCentral) {
		this.configCentral = configCentral;
	}

	public String getRegistry() {
		return registry;
	}

	public void setRegistry(String registry) {
		this.registry = registry;
	}

	public List<YamlRegistryServer> getServers() {
		return servers;
	}

	public void setServers(List<YamlRegistryServer> servers) {
		this.servers = servers;
	}
	

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	/**
	 * for subclass to extend the method. For instance a subclass of Nexflix
	 * Eurake
	 * 
	 * @return
	 */
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
