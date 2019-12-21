package monica.examples.storage;


import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

import monica.framework.Storage;
import monica.framework.transport.TransportFile;

public class StorageOnDisk implements Storage {
	

	@Override
	public void storage(TransportFile receivedFile) {
		String path = "f:\\upload";
		String fileName = receivedFile.getFileName();
		byte[] content = receivedFile.getContent();
		File newFile = new File(path + File.separator + fileName);
		System.out.println("received msg :   " + newFile.getAbsolutePath() + "        " + receivedFile.getContent());
		try {
			if (newFile.exists()) {
				newFile.delete();
			}
			if (!newFile.exists()) {
				newFile.createNewFile();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FileChannel ch = FileChannel.open(newFile.toPath(), StandardOpenOption.APPEND);
			ByteBuffer buffer = ByteBuffer.allocate(content.length);
			buffer.put(content);
			buffer.flip();
			try {
				ch.write(buffer);
				buffer.clear();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					ch.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
