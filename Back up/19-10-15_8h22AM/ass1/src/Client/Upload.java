package Client;

import java.io.*;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Upload implements Runnable{
	
	String path;
	String ip;
	int port;
	public Upload(String pa, String ip, int port){
		this.path=pa;
		this.ip=ip;
		this.port=port;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Socket sock = new Socket(ip,port);
			int ARRAY_SIZE = 524288;
			byte[] array = new byte[ARRAY_SIZE];
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
		    BufferedInputStream bis = new BufferedInputStream(fis);
		    OutputStream os = sock.getOutputStream();
		    int seg = 0;
		    
		    while ((seg = bis.read(array))!=-1){
	        	 
	        	  os.write(array, 0, seg);
	        	  os.flush();
	        }
		    JOptionPane.showMessageDialog(null,"Upload Done");
		    System.out.println("Upload Done!");
		    if (bis != null) bis.close();
	          if (os != null) os.close();
	          if (sock!=null) sock.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
