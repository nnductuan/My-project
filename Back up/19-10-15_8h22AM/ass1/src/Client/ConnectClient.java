package Client;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSourceException;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.protocol.DataSource;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import Server.FileNode;
import Server.ManaFile;
public class ConnectClient implements Runnable {
	ClientGUI cGUI;
	Socket sk;
	ManaFile mf;
	DataOutputStream out;
	DataInputStream in;
	
	MediaLocator ml;
	DataSource ds;

	Player p11;
	 boolean a = true;
	 boolean t_a = false;
	LinkedList<String> listClient1 = new LinkedList<String>();
	
	ConnectClient(ManaFile mf,Socket a,ClientGUI cGUI) throws UnknownHostException, IOException{
		sk=a;
		this.cGUI=cGUI;
		this.mf=mf;
	}
	
	@SuppressWarnings("unchecked")
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
				
				final String [] p = s.split("__AA__");
				if(p[1].equals("UPDATE"));
				else
				System.out.println(s);
				if(p.length>1){
					if(p[1].equals("UPDATE")){
					
						ObjectInputStream ois= new ObjectInputStream(in);
						//ManaFile f= (ManaFile) ois.readObject();
						if(mf.listFile.size()>0){
							mf.listFile=(java.util.List<FileNode>) ois.readObject();
						}
						 
						
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
					
					if(p[1].equals("RECALL")){
						ml = new MediaLocator("file://C:/temp/gao.wav"); //file://C:/temp/gao.wav
						ds= null;
						cGUI.lCall.setText(p[4]+ " is calling...");
						try {
							ds = Manager.createDataSource(ml);
							try {
								p11 = Manager.createPlayer(ds);
								p11.start();
							} catch (NoPlayerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						} catch (NoDataSourceException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						/*int result = JOptionPane.showConfirmDialog(null,
								p[4]+" is calling. Answer?", null,
								JOptionPane.YES_NO_OPTION);
						
						if (result == JOptionPane.YES_OPTION) {
							p11.stop();
							cGUI.a.out.writeUTF("__AA__ACCEPT__AA__"+p[4]);
							cGUI.btnCall.setText("End");
							cGUI.btnCall.setUI(new BEButtonUI (). setNormalColor (BEButtonUI.NormalColor.red));

						} else
						{
							cGUI.a.out.writeUTF("__AA__DENY__AA__"+p[4]);
							p11.stop();
						}*/
						cGUI.btnCall.setText("Answer");
						 new Timer(45000, new ActionListener() {
			                    @Override
			                    public void actionPerformed(ActionEvent e) {
			                        a = false;
			                        //p11.stop();
			                        if(t_a==false){
			                        	try {
											cGUI.a.out.writeUTF("__AA__DENY__AA__" + p[4]);
											cGUI.btnCall.setText("Call");
											p11.stop();
											t_a=true;
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}	
			                        }
			                    }
			                }).start();
			           
						while (a) {
							System.out.println("da vao toi");
							if (cGUI.accept == true) {
								
								p11.stop();
								cGUI.a.out
										.writeUTF("__AA__ACCEPT__AA__" + p[4]);
								cGUI.btnCall.setText("End");
								cGUI.btnCall
										.setUI(new BEButtonUI()
												.setNormalColor(BEButtonUI.NormalColor.red));
								t_a = true;

							}
							if (cGUI.cancel == true) {
								cGUI.a.out.writeUTF("__AA__DENY__AA__" + p[4]);
								t_a = true;
							}

						}
						a= true;
						t_a=false;
					}
					if(p[1].equals("DENY")){
						JOptionPane.showMessageDialog(null,
							    "Calling is cancel.",
							    "Notification.",
							    JOptionPane.ERROR_MESSAGE);
						System.out.println("DENY");
						cGUI.btnCall.setText("Call");
					}
					
					if(p[1].equals("ACCEPT")){
						
						
						cGUI.btnCall.setText("End");
						cGUI.btnCall.setUI(new BEButtonUI (). setNormalColor (BEButtonUI.NormalColor.red));
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
