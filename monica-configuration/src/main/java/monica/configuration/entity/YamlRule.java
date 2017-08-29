package monica.configuration.entity;



/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class YamlRule {
	private String rule;
	private int priority;
	
	public YamlRule(String rule, int priority){
		this.rule =  rule;
	    this.priority = priority;
	}
	
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}
