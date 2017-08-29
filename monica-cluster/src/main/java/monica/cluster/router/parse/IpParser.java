package monica.cluster.router.parse;

import java.util.List;

import monica.cluster.AbstractParser;
import monica.cluster.Parser;
import monica.cluster.router.parse.object.Rule;
import monica.registry.context.RegistryContext;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class IpParser extends AbstractParser implements Parser {
	List<String> temp_providers = (List) RegistryContext.clientCache.get("temp_providers");

	public void doParse(Rule rule) {

	}

	public boolean isExpected() {
		return false;
	}

	public void includeConsumer(Rule rule) {
		routeToIps(rule, temp_providers);
	}

	public void excludeConsumer(Rule rule) {
		routeToIps(rule, temp_providers);
	}

}
