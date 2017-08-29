package monica.cluster.loadbalance.impl;

import java.util.List;
import java.util.Random;

import monica.cluster.LoadBalance;


/**
 * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 */
public class RandomLoadBalance implements LoadBalance {
	
	private static Random random = new Random();
	static int  length =10;
	
	
	private static int getIndex(int length){		
		int offset = random.nextInt(length);
		if((offset <  length  || offset ==length ) && offset >= 0 ){
			return offset;
		}else{
			return getIndex(length);
		}
	}
	
	
	public static void main(String args[]){
		for(int i=0;i<22;i++){
			System.out.println(getIndex(11));
		}
		
	}


	public int doSelect(List<?> temp_providers) {
		System.out.println("temp_providers.size() "+temp_providers.size());
		int index = getIndex(temp_providers.size());
		return index;
	}

}
