package monica.framework;



/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public interface Server {
    public void start() throws Exception;
    public int getPort() ;
    public String getHostIp() throws Exception;
    public boolean isStarted();
}
