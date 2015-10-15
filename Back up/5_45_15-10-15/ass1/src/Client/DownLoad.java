package Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class DownLoad implements Runnable{
	public ServerSocket server;
	static int speed=0;

	File fd;
	long size;
	DownLoad(ServerSocket sv, File f, long si){
		server = sv;
		fd =f;
		size = si;
	}
	boolean complete=false;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Socket sock = server.accept();
			final downLoadBar a = new downLoadBar();
			int ARRAY_SIZE = 524288;
			byte[] array = new byte[ARRAY_SIZE];
			InputStream is = sock.getInputStream();
			FileOutputStream fos = new FileOutputStream(fd.getAbsolutePath());
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			final Timer timerDownLoad=new Timer();
			a.textD.setText("Downloading...");
			TimerTask tt = new TimerTask() {
				@Override
				public void run() {
					//System.out.println("da vao timer voi "+ a.textD.getText());
					if (speed*2/1024>1024){
						a.speed.setText("Speed: "+String.valueOf( ((double)(speed*2*10/1024/1024)/10)) +" MB/s");
					}
					else a.speed.setText("Speed: "+speed*2/1024 +" kB/s");
					speed = 0;
					if(complete==true){
						timerDownLoad.cancel();
					}
					if(a.textD.getText().equals("Downloading.")){
						a.textD.setText("Downloading..");
					}
					else if(a.textD.getText().equals("Downloading..")){
						a.textD.setText("Downloading...");	
					}
					else if(a.textD.getText().equals("Downloading...")){
						a.textD.setText("Downloading....");
					}
					else if(a.textD.getText().equals("Downloading....")){
						
						a.textD.setText("Downloading.....");
					}
					else if(a.textD.getText().equals("Downloading.....")){
						a.textD.setText("Downloading.");
					}
					
				}	
			};
			timerDownLoad.schedule(tt, 0,500);
			a.filename.setText("File: "+fd.getName());
			long pro = 0;
			int seg = 0;
		      while((seg=is.read(array))!=-1){
		    	  speed = speed + seg;
		    	  pro = pro +seg;
		    	  bos.write(array, 0, seg);
		    	  bos.flush();
		    	  a.pgr.setValue((int) (pro*100/size));
		    	  a.mount.setText(pro/1024+"/"+size/1024+" (KB) ");
		      }
		      complete = true;
		      JOptionPane.showMessageDialog(null, "Download Done");
		      System.out.println("Download Done!");
		      a.textD.setText("Download completed!!!");
		      a.speed.setText("");
		      //a.contentPane.setVisible(false);
		 	    
		      //JOptionPane.showMessageDialog();
		      if (fos != null) fos.close();
		      if (bos != null) bos.close();
		      if (sock != null) sock.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
