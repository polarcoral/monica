package monica.container.client;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import monica.framework.Client;
import monica.registry.context.RegistryContext;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class ClientFactory {
	private final String SERVER_IP_CACHE = "server_ip";
	private final String SERVER_PORT_CACHE = "server_port";
	private String protocolString = "socket";
	private static ClientFactory factory = new ClientFactory();
	private String ip = (String) RegistryContext.clientCache.get(SERVER_IP_CACHE);
	private int port = Integer.valueOf((String) RegistryContext.clientCache.get(SERVER_PORT_CACHE));

	public static ClientFactory newFactory() {
		if (null != factory) {
			return factory;
		}
		return new ClientFactory();
	}

	public Client getConsumerClient()
			throws NotFoundException, InstantiationException, IllegalAccessException, CannotCompileException {
		String clientClass = protocolString.substring(0, 1).toUpperCase()
				+ protocolString.substring(1, protocolString.length()) + "Client";
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get("monica.files.socket." + clientClass);
		Object o = cc.toClass().newInstance();
		Client client = (Client) o;
		return client;
	}
}
