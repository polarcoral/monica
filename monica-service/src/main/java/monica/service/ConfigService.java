package monica.service;

import java.util.List;

import monica.framework.SPI;
import monica.service.impl.zookeeper.ZookeeperConfigService;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
@SPI(ZookeeperConfigService.NAME)
public interface ConfigService {
	public void initService() throws Exception;

	public void rulesStore(List<String> rules) throws Exception;

	public void rulesStore() throws Exception;
}
