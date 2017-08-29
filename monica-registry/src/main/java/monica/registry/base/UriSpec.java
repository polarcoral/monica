package monica.registry.base;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class UriSpec {
	private String scheme;
	private String ip;
	private String port;

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
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

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (null == scheme || scheme.equals("")) {
			sb.append("monica://");
		} else {
			sb.append(scheme);
		}
		if (null != ip && !ip.equals("")) {
			sb.append(ip);
		}
		sb.append(":");
		if (null != port && !port.equals("")) {
			sb.append(port);
		}
		return sb.toString();
	}

}
