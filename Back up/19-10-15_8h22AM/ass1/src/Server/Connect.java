package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Hashtable;

public class Connect implements Runnable {
	ManaFile mFile;
	Main m;
	Hashtable<String, Socket> listClient = new Hashtable<String,Socket>();
	ServerSocket server;
	
	ManageClient mnClient;

	public Connect (ServerSocket sk, Main a,ManageClient mClient, ManaFile mf){
		this.mnClient = mClient;
		m =a;
		
		server = sk;
		this.mFile=mf;
	}
	
	public void run(){
		String s;
		System.out.println("Waiting connection");
		while(true){
			Socket socket;
			try {
				socket = server.accept();
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				xuly xl = new xuly(socket, m,mnClient,mFile,in,out);
				new Thread(xl).start();
				//add;
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
