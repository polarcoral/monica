package monica.cluster.router.parse.object;

import java.util.List;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class Host {
	private List<String> ips;
	private Operator ops;

	public List<String> getIps() {
		return ips;
	}

	public void setIps(List<String> ips) {
		this.ips = ips;
	}

	public Operator getOps() {
		return ops;
	}

	public void setOps(Operator ops) {
		this.ops = ops;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("host");
		if (ops.equals(Operator.INCLUDE)) {
			stringBuilder.append("=");
		} else {
			stringBuilder.append("!=");
		}
		for (String s : ips) {
			System.out.println("host.object --------" + ips.indexOf(s) + "          " + ips.size());
			if (ips.indexOf(s) != (ips.size() - 1)) {
				stringBuilder.append(s + ",");
			} else {
				stringBuilder.append(s);
			}
		}
		return stringBuilder.toString();
	}
}
