package monica.starter.server;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javassist.ClassPool;
import javassist.CtClass;
import monica.configuration.context.ConfigurationContext;
import monica.configuration.entity.YamlMonica;
import monica.coordinator.config.ConfigServiceInit;
import monica.framework.Server;
import monica.registry.base.RegistryType;
import monica.registry.base.UriSpec;
import monica.registry.centre.RegistryCentre;
import monica.registry.service.ZookeeperMonicaClient;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class ServerContainer {

	private final Logger log = LoggerFactory.getLogger(ServerContainer.class);
	private final String protocolPrefix = "monica.files";

	public void start() throws Exception {
		// take note of the number arg for CountDownLatch() constructor.
		// CountDownLatch instance will wait the countDown number which is
		// specified here.
		final CountDownLatch zkSignal = new CountDownLatch(1);
		final CountDownLatch nettySignal = new CountDownLatch(1);
		ConfigurationContext.loadYamlServerConfig();
		YamlMonica monica = (YamlMonica) ConfigurationContext.propMap.get("yamlMonica");
		String protocolString = monica.getProtocol();
		String className = protocolString.substring(0, 1).toUpperCase()
				+ protocolString.substring(1, protocolString.length()) + "Server";
		String protocolPackageName = protocolPrefix + "." + monica.getProtocol();
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get(protocolPackageName + "." + className);
		Object o = cc.toClass().newInstance();
		final Server server = (Server) o;
		Executors.newSingleThreadExecutor().execute(new NettyRunnable(zkSignal, server));
		Executors.newSingleThreadExecutor().execute(new ZookeeperRunnable(zkSignal));
		for (;;) {
			// the Server isStrated filed is visible to multi-thread by volatile
			// modifier
			if (server.isStarted()) {
				nettySignal.countDown();
				break;
			}
		}
		nettySignal.await();
		ConfigServiceInit.init();
		new RegistryCentre().setUri(createUri(server)).setType(RegistryType.SERVER).start();

		if (RegistryCentre.registryFinished()) {
			log.info("monica server start successfully!");
		}
		log.warn("monica server start successfully!");
	}

	private UriSpec createUri(Server server) throws Exception {
		UriSpec uriSpec = new UriSpec();
		uriSpec.setIp(server.getHostIp());
		uriSpec.setPort(server.getPort() + "");
		return uriSpec;
	}

	public static void main(String args[]) {
		try {
			ServerContainer container = new ServerContainer();
			container.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	class ZookeeperRunnable implements Runnable {
		private CountDownLatch zkCountDown;

		public ZookeeperRunnable(CountDownLatch latch) {
			this.zkCountDown = latch;
		}

		public void run() {
			new ZookeeperMonicaClient().start();
			zkCountDown.countDown();
		}
	}

	class NettyRunnable implements Runnable {
		private final CountDownLatch zkSignal;
		private Server server;

		public NettyRunnable(CountDownLatch zkLatch, Server monicaServer) {
			this.zkSignal = zkLatch;
			server = monicaServer;
		}

		public void run() {
			try {
				zkSignal.await();
				server.start();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
