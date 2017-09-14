package monica.storage.filesystem;

import monica.framework.Storage;
import monica.framework.storage.AbstractStorageFactory;

public class DiskStorageFactory extends AbstractStorageFactory{
	private static Storage diskStorage = new StorageOnDisk();

	@Override
	public  Storage newStorageInstance() {
		if(null == diskStorage){
			diskStorage = new StorageOnDisk();
		}
		return diskStorage;
	}

}
