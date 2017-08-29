package monica.container.client;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javassist.ClassPool;
import javassist.CtClass;
import monica.configuration.context.ConfigurationContext;
import monica.framework.Client;
import monica.framework.Server;
import monica.registry.base.RegistryType;
import monica.registry.base.UriSpec;
import monica.registry.centre.RegistryCentre;
import monica.registry.context.RegistryContext;
import monica.registry.service.ZookeeperMonicaClient;
import monica.service.impl.ServiceBuilder;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class ClientContainer {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String SERVER_IP_CACHE="server_ip";
	private final String SERVER_PORT_CACHE="server_port";
	
	
	public void start() throws Exception{
		final CountDownLatch signal = new CountDownLatch(1);
		ConfigurationContext.loadYamlClientConfig();
	    new ZookeeperMonicaClient().start();
		new ServiceBuilder()
		.servicesInit()
		.route()
		.loadbalance()
		.build();	
		
		signal.wait();
		String ip = (String)RegistryContext.clientCache.get(SERVER_IP_CACHE);
		int port = Integer.valueOf((String)RegistryContext.clientCache.get(SERVER_PORT_CACHE));
		ClientFactory.newFactory()
		.getConsumerClient()
		.start(ip,port);
		
		
	    new RegistryCentre()
	    	.setUri(createUri())
	    	.setType(RegistryType.CLIENT)
	    	.start();
	    if(RegistryCentre.registryFinished()){
	    	log.info("monica client start successfully!");
	    }
	}
	
	
	//register the client consumer
	private UriSpec createUri(){
		return new UriSpec();
	}
	
	
	public static void main(String args[]){
		ClientContainer container = new ClientContainer();
		try {
			container.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
