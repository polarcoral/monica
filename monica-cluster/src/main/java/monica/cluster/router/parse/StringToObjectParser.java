package monica.cluster.router.parse;

import java.util.Arrays;

import monica.cluster.router.parse.object.Host;
import monica.cluster.router.parse.object.Operator;
import monica.cluster.router.parse.object.Rule;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class StringToObjectParser {
	
		public Rule parseRuleStringToRule(String rule){
			int index = rule.lastIndexOf(",");			
			int priorityIndex = rule.lastIndexOf("=");	
			String hostString = rule.trim().substring(rule.indexOf("['")+2,index-1).trim();
			String[] hosts = hostString.split("=>");
			Rule r = new Rule();
			System.out.println("destination host --------  "+ hostString.trim().indexOf("=>")+"     "+hosts[0]);
			if(hostString.trim().indexOf("=>") ==0 && hosts.length >0){
				r.setDestination(this.parseHostStringToHost(hosts[0]));
			}else if( hostString.trim().indexOf("=>") == (hostString.trim().length()-2) && hosts.length >0){
				 r.setSource(this.parseHostStringToHost(hosts[0]));
			}else{
				 r.setSource(this.parseHostStringToHost(hosts[0]));
				 r.setSource(this.parseHostStringToHost(hosts[1]));
			}
		   
		   // System.out.println("destination host --------  "+hostString[1]);
			
		   /* List<Rule> rList = new ArrayList<Rule>();
			rList.sort(new Comparator<Rule>(){
				public int compare(Rule o1, Rule o2) {
					return o1.compareTo(o2);
				}
				
			});*/
			r.setPriority(Integer.valueOf(rule.substring(priorityIndex+1,rule.length()-1)));
			return r;
		}
		
		
		private Host parseHostStringToHost(String host){
			Host h ;
			if(null != host && !host.trim().equals("")){
				h = new Host();
				if(host.indexOf("=")!=-1){
					if(host.indexOf("!") != -1){
						h.setIps(Arrays.asList(host.substring(host.indexOf("!=")).split(",")));
						h.setOps(Operator.EXCLUDE);
					}else{
						h.setIps(Arrays.asList(host.substring(host.indexOf("=")+1).split(",")));
						h.setOps(Operator.INCLUDE);
					}
				}
				return h;
			}
			return null;
		}
		
		public static void main(String args[]){
			StringToObjectParser  stringParser = new StringToObjectParser();
			String s = "rule=['host=192.168.1.100,127.0.0.1 =>',priority=1]";
			Rule rule = stringParser.parseRuleStringToRule(s);
			System.out.println("ddd----------  "+rule.toString());
		}
}
