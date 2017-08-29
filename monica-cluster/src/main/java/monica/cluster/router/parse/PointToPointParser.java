package monica.cluster.router.parse;

import monica.cluster.Parser;
import monica.cluster.router.parse.object.Rule;
import monica.configuration.context.ConfigurationContext;
import monica.configuration.entity.YamlClient;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class PointToPointParser implements Parser{
    private volatile boolean parseResult = false;
    
	public void doParse(Rule rule) {
		final String CLIENT_KEY ="client";
		Object value = ConfigurationContext.propMap.get(CLIENT_KEY);
		if(null != value && value instanceof YamlClient){
			YamlClient client = (YamlClient)value;
			if(null != client && (null != client.getProvider() || !client.getProvider().equals(""))){
				parseResult = true;
			}
		}
	}

	public boolean isExpected() {
		return parseResult;
	}

	

}
