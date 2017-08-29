package monica.cluster.router;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.curator.framework.CuratorFramework;

import monica.cluster.router.parse.IpParser;
import monica.cluster.router.parse.StringToObjectParser;
import monica.cluster.router.parse.object.Operator;
import monica.cluster.router.parse.object.Rule;
import monica.configuration.context.ConfigurationContext;
import monica.registry.context.RegistryContext;
import monica.registry.service.ZookeeperMonicaClient;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class RouteHandler {
	private List<Rule> rulesList = new CopyOnWriteArrayList<Rule>() ;
    private  final  String RULES_PATH="/routers";
	public RouteHandler(){
		super();
		rulesInit();
	}
	
	private void rulesInit(){
		 final CuratorFramework zkClient = (CuratorFramework)RegistryContext.clientCache.get("curatorClient");
		 try {
			 if(null != zkClient.checkExists().forPath(RULES_PATH)){
				 List<String> childList = zkClient.getChildren().forPath(RULES_PATH);	
				 for(String rule:childList){
					 StringToObjectParser  stringParser = new StringToObjectParser();
					 rulesList.add(stringParser.parseRuleStringToRule(URLDecoder.decode(rule, "UTF-8")));
					 System.out.println("rule---------    "+URLDecoder.decode(rule, "UTF-8"));
				 }
			 }	
			 filterRulesForConsumer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handle(){
		rulesList.sort(new Comparator(){
			public int compare(Object o1, Object o2) {				
				return ((Rule)o1).compareTo(((Rule)o2));
			}
			
		});
		for(Rule s: rulesList){		
				try {
					IpParser.class.getMethod(s.getSource().getOps().name().toLowerCase()+"Consumer", s.getClass());
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
				
	}
	
	
	
	
	
	/**
	 * conditions:   localhost.IP,  localhost.ip.net.* ,   ,  127.0.0.1
	 */
	
	private void filterRulesForConsumer()  throws  UnknownHostException{	
			for(Rule rule: rulesList){				
				if(!isMatched(rule)){
					rulesList.remove(rule);
				}
			}
	}
	
	private boolean isMatched(Rule rule) throws  UnknownHostException{
		String  localIp = InetAddress.getLocalHost().getHostAddress();
		String  netSegment  = localIp.substring(0,localIp.lastIndexOf("."))+".*";
		//not allowed loopback address
		String  loopbackIp = "127.0.0.1";
		String  emptyIsAll  = " ";
		if(null !=rule ){
			String srcString = rule.getSource().toString();//.substring(rule.indexOf("'")+1,rule.indexOf("=>"));
			//System.out.println("srcString ------------  "+srcString+"    "+netSegment);
			if(srcString.contains(localIp) || srcString.contains(netSegment) || srcString.contains(loopbackIp) || srcString.contains(emptyIsAll)){
				return true;
			}
		}		
		return false;
	}
	
	public static void main(String args[]){
		try {
			ConfigurationContext.loadYamlClientConfig();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ZookeeperMonicaClient().start();
		RouteHandler router = new RouteHandler();
		router.handle();
		System.out.println("rules.size ------------  "+router.rulesList.size());
	}
}
