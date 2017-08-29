package monica.cluster;

import monica.cluster.router.parse.object.Rule;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public interface Parser {
	public void doParse(Rule rule);

	public boolean isExpected();
}
