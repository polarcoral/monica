package monica.configuration.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class YamlRouter {
	private List<YamlRule> rules;

	public List<YamlRule> getRules() {
		return rules;
	}

	public void setRules(List<YamlRule> rules) {
		this.rules = rules;
	}

	public List<String> toStringList() {
		List<String> rulesList = new ArrayList();
		for (YamlRule r : rules) {
			String s = "rule=['";
			String e = "]";
			rulesList.add(s + r.getRule() + "',priority=" + r.getPriority() + e);
		}
		return rulesList;
	}
}
