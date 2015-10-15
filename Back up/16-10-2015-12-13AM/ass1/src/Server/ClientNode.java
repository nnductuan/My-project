package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.LinkedList;

public class ClientNode implements Serializable{
	Socket skClient;
	DataInputStream	in;
	DataOutputStream out;
	String IP="";
	String username = null;
	int status=-1;
	int port=-1;
	List<FileNode> ListFile = new LinkedList<FileNode>();
	ClientNode(Socket sk,String name,DataInputStream in, DataOutputStream out) throws IOException{
		skClient = sk;
		username =name;
		//in = new ObjectInputStream(skClient.getInputStream());
		//out = new ObjectOutputStream(skClient.getOutputStream());
		//IP = sk.getLocalSocketAddress().toString();  
		this.in =in;
		this.out=out;
		IP = sk.getInetAddress().getHostAddress();
		//port = sk.getPort();
	}

	
	
	void disconnect(){
		try {
			//in.close();
			//out.close();
			skClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
