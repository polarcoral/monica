package monica.cluster;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import monica.cluster.router.parse.object.Operator;
import monica.cluster.router.parse.object.Rule;

/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class AbstractParser {
	
	
	public void routeToIps(Rule rule,  List<String> temp_providers){
		if(rule.getDestination().getOps().equals(Operator.INCLUDE)){
		   List<String> leftIps = new ArrayList<String>();
		   for(String s: temp_providers){
			   if(rule.getDestination().getIps().contains(s.substring(8))){
				  leftIps.add(s);
			   }
		   }
		   temp_providers.clear();	
		   temp_providers.addAll(leftIps);			
		}else{					
			 for(String s: temp_providers){
				   if(rule.getDestination().getIps().contains(s.substring(8))){
					   temp_providers.remove(s);
				   }
			   }
		}
	}
	
	public void rejectToIps(Rule rule,  List<String> temp_providers) throws UnknownHostException{
		 //String localIp =  InetAddress.getLocalHost().getHostAddress();
		if(rule.getDestination().getOps().equals(Operator.INCLUDE) ){
			 for(String s: temp_providers){
				   if(rule.getDestination().getIps().contains(s.substring(8))){
					   temp_providers.remove(s);
				   }
			   }			
		}else{
			  List<String> leftIps = new ArrayList<String>();
			   for(String s: temp_providers){
				   if(rule.getDestination().getIps().contains(s.substring(8))){
					  leftIps.add(s);
				   }
			   }
			   temp_providers.clear();	
			   temp_providers.addAll(leftIps);					
		}
	}
	
	public void routeToNetSegment(Rule rule, List<String> temp_providers){
		if(rule.getDestination().getOps().equals(Operator.INCLUDE)){
			   List<String> leftIps = new ArrayList<String>();
			   for(String s: temp_providers){
				   if(rule.getDestination().getIps().contains(s.substring(8))){
					  leftIps.add(s);
				   }
			   }
			   temp_providers.clear();	
			   temp_providers.addAll(leftIps);			
			}else{					
				 for(String s: temp_providers){
					   if(rule.getDestination().getIps().contains(s.substring(8))){
						   temp_providers.remove(s);
					   }
				   }
			}
	}
	
	public void rejectToNetSegment(Rule rule, List<String> temp_providers){
		if(rule.getDestination().getOps().equals(Operator.INCLUDE) ){
			 for(String s: temp_providers){
				   if(rule.getDestination().getIps().contains(s.substring(8))){
					   temp_providers.remove(s);
				   }
			   }			
		}else{
			  List<String> leftIps = new ArrayList<String>();
			   for(String s: temp_providers){
				   if(rule.getDestination().getIps().contains(s.substring(8))){
					  leftIps.add(s);
				   }
			   }
			   temp_providers.clear();	
			   temp_providers.addAll(leftIps);					
		}
	}
	
}
