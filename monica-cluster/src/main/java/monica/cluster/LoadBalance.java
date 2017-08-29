package monica.cluster;

import java.util.List;

/**
 * selective loadbalance algorithm for client to achieve the provider
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */

public interface LoadBalance {
	public int doSelect(List<?> providers);
}
