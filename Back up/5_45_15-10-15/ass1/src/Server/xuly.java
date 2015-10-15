package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
public class xuly implements Runnable {
	ManaFile mf;
	Main m;
	Socket sk;
	DataInputStream in;
	DataOutputStream out;

	ManageClient mnc;
	String [] test;
	String user = null;
	boolean d= true;
	
	LinkedList<String> listClient1 = new LinkedList<String>();
	
	xuly(Socket a, Main m,ManageClient client, ManaFile mf,DataInputStream in, DataOutputStream out  ){
		this.mnc=client;
		this.sk =a;
		this.m=m;
		
		this.in =in;
		this.out =out;
		
		/*try {
			in = new DataInputStream(sk.getInputStream());
			out = new DataOutputStream(sk.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		this.mf=mf;
	}
	
	
	void updateC()
	{
		int i=0;
		for(i=0;i<listClient1.size();i++){
			listClient1.removeFirst();
		}
		listClient1 = new LinkedList<String>();
		for(i=0;i<mnc.listClient.size();i++){
			listClient1.add(mnc.listClient.get(i).username);
		}
		/*for(i=0;i<mnc.listClient.size()-1;i++){
			listClient1.removeFirst();
		}*/
	}
	
	public synchronized void upload(ManaFile mf,String path){
		
	}
	public synchronized String comand(String msg, ManaFile mf, Socket sk,Main m) {
		String [] p = msg.split("__AA__");
		
		if(p.length>1){
			
			if(p[1].equals("REQDOWN")){
				int index = Integer.parseInt(p[2]);
				m.txtArea.append("Client "+user +" request download file: "+mf.listFile.get(index).fileName+"\n");
				int port =Integer.parseInt(p[3]);
				String IP = p[4];
				String user= mf.listFile.get(index).userName;
				String path = mf.listFile.get(index).path;
				Socket skD = null;
				boolean find= false;
				int id = 0;
				for(int i=0; i<mnc.listClient.size();i++){
					if(mnc.listClient.get(i).username.equals(user)){
						//skD=mnc.listClient.get(index).skClient;
						//DataOutputStream out = mnc.listClient.get(index).out;
						System.out.print("lay dc socket "+i );
						id = i;
						find = true;
						break;
					}
					
				}
				if(!find){
					try {
						out.writeUTF("__AA__CLIENTOFF");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					try {
						
						System.out.println("IP la "+ mnc.listClient.get(id).username);
						mnc.listClient.get(id).out.writeUTF("__AA__REQDOWN__AA__"+IP+"__AA__"+port+"__AA__"+path);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
			}
			if(p[1].equals("UPLOAD")){
					FileNode fileN= new FileNode(p[3], p[2],Long.parseLong(p[4]), user,sk.getInetAddress().getHostAddress(),sk.getPort());;
					mf.addFile(fileN);
					m.txtArea.append("Client " + user+" upload file: " + fileN.fileName+"\n");
					
			}
			if(p[1].equals("UPDATE")){
				try {
					
					out.writeUTF("__AA__UPDATE");
					
					ObjectOutputStream oos= new ObjectOutputStream(out);
					oos.flush();
					if(mf.listFile.size()>0){
						oos.writeObject( mf.listFile);
					}
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(p[1].equals("UPDATE1")){
				
				try {
					out.writeUTF("__AA__UPDATE1");
					updateC();
					
					ObjectOutputStream oos1= new ObjectOutputStream(out);
					oos1.flush();
					if(mnc.listClient.size()>0){
						oos1.writeObject(listClient1);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			if(p[1].equals("DELETE")){
				for(int i=0;i<mf.listFile.size();i++){
					if(mf.listFile.get(i).fileName.equals(p[2])){
						mf.remove(i);
						break;
					}
				}
			}
			if(p[1].equals("DISCONNECT")){
				try {
					out.writeUTF("__AA__DISCONNECT");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mnc.removeClient(user);
				try {
					in.close();
					out.close();
					d=false;
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return "a";
	}
	public void run(){
		String s;
		
		try {
			
			user=in.readUTF();
			m.txtArea.append("Client "+ user+" is connected\n");
			ClientNode newNode = new ClientNode(sk, user, this.in, this.out);
			mnc.addClient(newNode);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(d){
			try {
				s= in.readUTF();
				s=comand(s,mf,sk,m);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
