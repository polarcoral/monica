package monica.cluster.router.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;

import monica.configuration.context.ConfigurationContext;
import monica.registry.context.RegistryContext;
import monica.registry.service.ZookeeperMonicaClient;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class UpdateRulesMain {
	private final CuratorFramework zkClient = (CuratorFramework)RegistryContext.clientCache.get("curatorClient");
	private final String RULE_PATH="/routers";
	/**
	 * The only  entrance for  update routing rules
	 * step 1) delete all rule nodes from zk
	 * step 2) create all rule nodes on zk 
	 */
	public static void main(String args[]){		
		try {
			ConfigurationContext.loadYamlClientConfig();
			new ZookeeperMonicaClient().start();
			ConfigurationContext.loadYamlClientConfig();
			UpdateRulesMain main = new UpdateRulesMain();
			main.deleteRules();
			main.storeRules();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private void storeRules() throws Exception{		
		List<String> rules = new ArrayList<String>();
		try {
			rules = ConfigurationContext.loadYamlRouterConfig();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null == zkClient.checkExists().forPath(RULE_PATH)){
			zkClient.create().forPath(RULE_PATH);
		}
		for(String rule: rules){
			if(null == zkClient.checkExists().forPath(RULE_PATH+"/"+rule)){
				zkClient.create().forPath(RULE_PATH+"/"+URLEncoder.encode(rule,"UTF-8"));
			}
		}
	}
	
	private void deleteRules() throws Exception{
		if(null != zkClient.getChildren().forPath(RULE_PATH)){
			zkClient.delete().deletingChildrenIfNeeded().forPath(RULE_PATH);
		}
	}
}
