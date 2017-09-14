package monica.framework.transport;

import java.io.Serializable;

public class TransportFile implements Serializable{

	private static final long serialVersionUID = 1L;
	private String fileName;
	private byte[] content;
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	
}
