package monica.cluster.router.parse.object;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class Rule implements Comparable<Rule>{
	private Host source;
	private Host destination;
	private int priority;
	
	public Host getSource() {
		return source;
	}
	public void setSource(Host source) {
		this.source = source;
	}
	public Host getDestination() {
		return destination;
	}
	public void setDestination(Host destination) {
		this.destination = destination;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		
		this.priority = priority;
	}
	
	public String toString(){	
		if(null != source && null != destination){
			return source+"=>"+destination;
		}else if(null !=source && null == destination){
			return source+"=>" ;
		}else if(null == source && null != destination){
			return "=>"+destination;
		}else{
			return "";
		}
		
	}
	
	
	public int compareTo(Rule o) {
		if(this.priority < o.priority)
			return -1;
		return 1;
	}
	
}
