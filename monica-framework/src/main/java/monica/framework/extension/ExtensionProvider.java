package monica.framework.extension;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Iterator;
import java.util.stream.Stream;

import monica.framework.SPI;



/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class ExtensionProvider {
	private static final String basePath="META-INF/monica/internal";
	
	public static  Class<?>  getDefaultExtension(Class<?> c) throws ClassNotFoundException{		
		SPI cSpi = c.getAnnotation(SPI.class);
		String spiValue = cSpi.value();
		String fileName = c.getName();
		String className = loadFile(fileName,spiValue);	
		return getClassLoader().loadClass(className);
	}
	
	private static String loadFile(String fileName,String key){
		URL url = getClassLoader().getResource(basePath+File.separator+fileName);
		if(null != url){
			  try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
				String line = "";
				
				while((line = reader.readLine()) !=null){					
					int index = line.trim().indexOf("=");
					if(index > 0 && line.substring(0,index).equals(key)){
						return line.substring(index+1);
					}
				}
				
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	    return null;
	}
	
	private static ClassLoader getClassLoader(){
		return ExtensionProvider.class.getClassLoader();
	}
	
	public static void main(String args[]){
		ExtensionProvider provider = new ExtensionProvider();
		//provider.getDefaultExtension(null);
	}
}
