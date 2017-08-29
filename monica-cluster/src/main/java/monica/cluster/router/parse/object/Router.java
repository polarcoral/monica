package monica.cluster.router.parse.object;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class Router {
	private List<Rule> rules;

	public List<Rule> getRules() {
		return rules;
	}
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	public String[] toStringRules(){
		List<String> rString = new ArrayList<String>();
		String[] rs = new String[rules.size()];
		for(Rule s: rules){
			rString.add(s.toString());
		}
		return rString.toArray(rs);
	}
	
}


