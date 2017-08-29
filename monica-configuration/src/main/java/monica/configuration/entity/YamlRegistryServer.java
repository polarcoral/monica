package monica.configuration.entity;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class YamlRegistryServer {
	private String ip;
	private String port;
	
	public YamlRegistryServer(String ip,String port){
		this.ip = ip;
		this.port=port;
		init();
	}
	
	public static void init(){
		
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	public String toString(){
		return ip+":"+port;
	}

}
