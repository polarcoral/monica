package monica.registry.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.KeeperException;

import monica.configuration.context.ConfigurationContext;
import monica.registry.context.RegistryContext;



/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class ZookeeperMonicaClient {
	private final String ZK_NAMESPACE ="monica";
	private CuratorFramework zkClient;
	
	public void  start(){
		  CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		  zkClient = builder.connectString(getServerConnectionString()).namespace(ZK_NAMESPACE).retryPolicy(new RetryOneTime(1))
	        		.connectionTimeoutMs(1)
	        		.sessionTimeoutMs(140)
	        		.build();
		  zkClient.start();
		  RegistryContext.clientCache.putIfAbsent("curatorClient", zkClient);
	}
	
	
	private String getServerConnectionString(){
		return String.valueOf(ConfigurationContext.propMap.get("serverString"));
	}
	
	
    public CuratorFramework getZookeeperClient() throws KeeperException.ConnectionLossException{
    	if(zkClient.getZookeeperClient().isConnected()){
    		return this.zkClient;
    	}else{
    		throw   new KeeperException.ConnectionLossException();
    	}
    	
    }    
    

}
