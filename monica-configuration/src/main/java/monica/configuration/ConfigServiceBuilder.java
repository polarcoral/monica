package monica.configuration;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import monica.configuration.api.Config;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class ConfigServiceBuilder {
	
	
	public static void build() throws NotFoundException,Exception{
	   File newfile = new File("");	   
	   if(null != newfile && newfile.exists()){
		   String clazzname = newfile.getName().substring(newfile.getName().indexOf("."))+"Config";
		   ClassPool pool = ClassPool.getDefault();   
		   CtClass cc = pool.get(clazzname);  		   
		   Object o =cc.toClass().newInstance();
		   Config config = (Config)o;
		   config.load(newfile);		 
	   }	   	  
	}
	
	

	public static void reBuild() throws NotFoundException,Exception{
	   File newfile = new File("");	   
	   if(null != newfile && newfile.exists()){
		   String clazzname = newfile.getName().substring(newfile.getName().indexOf("."))+"Config";
		   ClassPool pool = ClassPool.getDefault();   
		   CtClass cc = pool.get(clazzname);  		   
		   Object o =cc.toClass().newInstance();
		   Config config = (Config)o;
		   config.reLoad(newfile);		 
	   }	   	  
	}
}
