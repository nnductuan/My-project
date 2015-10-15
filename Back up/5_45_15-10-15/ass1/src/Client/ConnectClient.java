package Client;
import java.awt.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import Server.FileNode;
import Server.ManaFile;
public class ConnectClient implements Runnable {
	ClientGUI cGUI;
	Socket sk;
	ManaFile mf;
	DataOutputStream out;
	DataInputStream in;
	
	LinkedList<String> listClient1 = new LinkedList<String>();
	
	ConnectClient(ManaFile mf,Socket a,ClientGUI cGUI) throws UnknownHostException, IOException{
		sk=a;
		this.cGUI=cGUI;
		this.mf=mf;
	}
	
	public void run(){
		try {
			out = new DataOutputStream(sk.getOutputStream());
			in = new DataInputStream(sk.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			out.writeUTF(cGUI.txtUser.getText());
			out.flush();
			out.writeUTF("__AA__UPDATE");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s;
		while(true){
			try {
				
				s= in.readUTF();
				
				String [] p = s.split("__AA__");
				if(p[1].equals("UPDATE"));
				else
				System.out.println(s);
				if(p.length>1){
					if(p[1].equals("UPDATE")){
					
						ObjectInputStream ois= new ObjectInputStream(in);
						//ManaFile f= (ManaFile) ois.readObject();
						 mf.listFile=(java.util.List<FileNode>) ois.readObject();
						
					}
					
					if(p[1].equals("UPDATE1")){
						
						ObjectInputStream ois= new ObjectInputStream(in);
						//ManaFile f= (ManaFile) ois.readObject();
						 listClient1=(LinkedList<String>) ois.readObject();
						 System.out.println("danh sach client ") ;
							for(int i =0; i<listClient1.size();i++){
								
								System.out.println( listClient1.get(i));
							}
							
							DefaultListModel<String> model=new DefaultListModel<String>();
							for(int i =0; i<listClient1.size();i++){
								model.addElement(listClient1.get(i));
								
							}
							cGUI.listFriend.setModel(model);
					}
					
					if(p[1].equals("REQDOWN")){
						System.out.println("da vao toi REQ");
						Upload u = new Upload(p[4], p[2],Integer.parseInt(p[3]));
						new Thread(u).start();
						
					}
					if(p[1].equals("DISCONNECT")){
						System.exit(0);
					}
					if(p[1].equals("CLIENTOFF")){
						JOptionPane.showMessageDialog(null, "Client contain file is offine!");
					}
				}
				
				
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} /*catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			
		}
		
	}

}
