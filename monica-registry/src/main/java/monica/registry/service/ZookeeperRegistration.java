package monica.registry.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;

import monica.registry.Registration;
import monica.registry.base.RegistryType;
import monica.registry.base.UriSpec;
import monica.registry.context.RegistryContext;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class ZookeeperRegistration implements Registration {
	public final static String NAME = "zookeeper";
	public final String ZK_NAMESPACE = "monica";

	public void register(UriSpec uri, RegistryType type) throws Exception {
		CuratorFramework client = (CuratorFramework) RegistryContext.clientCache.get("curatorClient");
		try {
			CuratorWatcher watcher = new CuratorWatcher() {
				public void process(WatchedEvent event) {
					handleChildrenChange("", event.getPath());
				}
			};
			String s = URLEncoder.encode(uri.toString(), "UTF-8");
			if (null == client.checkExists().forPath("/" + type.toString())) {
				client.create().forPath("/" + type.toString());
			}
			client.getChildren().usingWatcher(watcher).forPath("/SERVER");
			if (null == client.checkExists().forPath("/SERVER/" + s)) {
				client.create().withMode(CreateMode.EPHEMERAL).forPath("/SERVER/" + s);
			}

			/*
			 * List<String> batch = batchCreate(); for (String ss : batch) { if
			 * (null == client.checkExists().forPath("/SERVER/" + ss)) {
			 * client.create().forPath("/SERVER/" + ss); } }
			 */

			client.getChildren().usingWatcher(new CuratorWatcher() {

				public void process(WatchedEvent event) throws Exception {

					handleRoutersChildrenChange("", event.getType());
				}

			}).forPath("/SERVER");

			// Assert.assertEquals("", "/SERVER");
		} finally {
			// CloseableUtils.closeQuietly(client);

		}
	}

	private void handleChildrenChange(String name, Object value) {
		log.info("/socket/SERVER  NodeChildren changed !");
		// ServiceContext.hashmap.put(name, value);
	}

	private void handleRoutersChildrenChange(String name, Object value) {

	}

	public boolean done() {
		return true;
	}

	public List<String> batchCreate() {
		List<String> batchUri = new ArrayList();
		for (int i = 0; i < 2; i++) {
			String b = "monica://192.168.1.10";
			String e = ":8023";
			try {
				batchUri.add(URLEncoder.encode(b + i + e, "UTF-8"));
				System.out.println("batchString[" + i + "] ----   " + (b + i + e));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return batchUri;
	}
}
