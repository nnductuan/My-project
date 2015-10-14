package Server;

import java.io.Serializable;

public class FileNode implements Serializable {
	public String fileName;
	public String path;
	public long size;
	public String userName;
	public String IP;
	int port;
	FileNode(String fn,String pa,long si, String un, String Ip, int port){
		fileName = fn;
		path = pa;
		size = si;
		userName = un;
		IP = Ip;
		this.port = port;
		
	}

}