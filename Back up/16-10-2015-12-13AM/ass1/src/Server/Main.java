package Server;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.net.*
;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextArea;

import org.jb2011.lnf.beautyeye.*;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
public class Main implements Runnable {
	
	private JFrame frame;
	private JTextField txtIP;
	private JTextField txtPort;
	ServerSocket serverSocket;
	ManageClient mnClient= new ManageClient();
	ManaFile mf=new ManaFile();
	JTextArea txtArea = new JTextArea();
	private JList listF;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					  
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws UnknownHostException 
	 */
	public Main() throws UnknownHostException {
		initialize();
	}
	/*void print (String s){
		txtArea.append(s);
	}*/
	void startServer(int port) throws IOException{
		
		if (port>1) this.serverSocket = new ServerSocket(port);
		else this. serverSocket = new ServerSocket(9999);
		
		//mnClient= new ManageClient();
		//new Thread(mnClient).start();
		Connect cn = new Connect(this.serverSocket, this,mnClient, mf);
		Thread tcn = new Thread(cn);
		tcn.start();
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws UnknownHostException 
	 */
	private void initialize() throws UnknownHostException {
		frame = new JFrame("__SERVER__");
		frame.setBounds(100, 100, 519, 524);
		frame.setForeground(Color.RED);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtIP = new JTextField();
		txtIP.setBounds(29, 11, 86, 20);
		frame.getContentPane().add(txtIP);
		txtIP.setColumns(10);
		InetAddress myHost = InetAddress.getLocalHost();
		txtIP.setText(myHost.getHostAddress());
		
		txtPort = new JTextField();
		txtPort.setBounds(175, 11, 86, 20);
		frame.getContentPane().add(txtPort);
		txtPort.setColumns(10);
		txtPort.setText("5000");
		final JButton StartButtom = new JButton("Start");
		StartButtom.setBounds(307, 10, 89, 23);
		StartButtom.setUI(new BEButtonUI (). setNormalColor (BEButtonUI.NormalColor.blue));
		StartButtom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(StartButtom.getText().equals("Start")){
					txtIP.disable();
					txtPort.disable();
					
					File file = new File("file.txt");
					FileReader fr;
					try {
						fr = new FileReader(file);
						BufferedReader br = new BufferedReader(fr);
						String line;
						
						while((line = br.readLine())!=null){
							String []s = line.split("__FF__");
							FileNode node = new FileNode(s[1], s[2], Long.parseLong(s[3]), s[4], s[5], Integer.parseInt(s[6]));
							mf.addFile(node);
						}
						br.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					StartButtom.setText("Exit");
					StartButtom.setUI(new BEButtonUI (). setNormalColor (BEButtonUI.NormalColor.red));
					int port = Integer.parseInt(txtPort.getText());
					try {
						startServer(port);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					File file = new File("file.txt");
					BufferedWriter out;
					try {
						
						out = new BufferedWriter(new FileWriter(file, true));
						for(int i = 0;i<mf.listFile.size();i++){
							out.append("__FF__"+mf.listFile.get(i).fileName+"__FF__"+mf.listFile.get(i).path+"__FF__"+mf.listFile.get(i).size
									+"__FF__"+mf.listFile.get(i).userName+"__FF__"+mf.listFile.get(i).IP+"__FF__"+mf.listFile.get(i).port);
							out.newLine();
						}
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					StartButtom.setText("Start");
					
					System.exit(0);
				}
				}
				
		});
		frame.getContentPane().add(StartButtom);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(287, 125, 109, 164);
		frame.getContentPane().add(scrollPane);
		
		listF = new JList();
		scrollPane.setViewportView(listF);
		
		JLabel lblServerFile = new JLabel("File To DownLoad");
		lblServerFile.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblServerFile.setBounds(71, 73, 151, 20);
		frame.getContentPane().add(lblServerFile);
		
		JLabel lblClientOnline = new JLabel("Client Online");
		lblClientOnline.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblClientOnline.setBounds(340, 73, 140, 20);
		frame.getContentPane().add(lblClientOnline);
		
		JLabel lblNotification = new JLabel("Notification:");
		lblNotification.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNotification.setBounds(71, 322, 68, 18);
		frame.getContentPane().add(lblNotification);
		
		final JLabel mauMe = new JLabel("___GVHD: NGUYEN HONG NAM___");
		mauMe.setBounds(29, 449, 307, 25);
		frame.getContentPane().add(mauMe);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(71, 341, 341, 97);
		frame.getContentPane().add(scrollPane_2);
		scrollPane_2.setViewportView(txtArea);
		
		JLabel lblNewLabel = new JLabel("IP:");
		lblNewLabel.setBounds(10, 14, 20, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(144, 14, 25, 14);
		frame.getContentPane().add(lblPort);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 104, 221, 197);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Client", null, panel, null);
		panel.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 180, 147);
		panel.add(scrollPane_1);
		
		final JList listClient = new JList();
		scrollPane_1.setViewportView(listClient);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Conference", null, panel_1, null);
		panel_1.setLayout(null);
		Timer timerUpdateMau=new Timer();
		TimerTask ttMau = new TimerTask() {
			@Override
			public void run() {
				if(mauMe.getText().equals("___3D GROUP___")){
					mauMe.setForeground(Color.RED);
					mauMe.setText("___GVHD: NGUYEN HONG NAM___");
				}
				else if(mauMe.getText().equals("___GVHD: NGUYEN HONG NAM___")){
					mauMe.setForeground(Color.MAGENTA);
					mauMe.setText("___PEER TO PEER SERVER___");
				}
				else if(mauMe.getText().equals("___PEER TO PEER SERVER___")){
					mauMe.setForeground(Color.BLUE);
					mauMe.setText("___3D GROUP___");
				}
				
			}
				
		};
		timerUpdateMau.schedule(ttMau, 0,2000);
		Timer timerUpdateListClient = new Timer();
		TimerTask tmClient = new TimerTask() {
			@Override
			public void run() {
				DefaultListModel<String> modelClient=new DefaultListModel<String>();
				for(int i=0;i<mnClient.listClient.size();i++){
					modelClient.addElement(mnClient.listClient.get(i).username);
				}
				listClient.setModel(modelClient);
			}
		};
		timerUpdateListClient.schedule(tmClient, 0,2000);
		
		
		
		Timer timerUpdateList=new Timer();
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				DefaultListModel<String> model=new DefaultListModel<String>();
				for(int i=0;i<mf.listFile.size();i++){
					model.addElement(mf.listFile.get(i).fileName);
				}
				listF.setModel(model);
			}
		};
		timerUpdateList.schedule(tt, 0,2000);
		
		/*Timer timerUpdateFILE=new Timer();
		TimerTask tFILE = new TimerTask() {
			@Override
			public void run() {
				
				File file = new File("file.txt");
				file.delete();
				BufferedWriter out;
				try {
					System.out.print("dang ghi file");
					out = new BufferedWriter(new FileWriter(file, true));
					for(int i = 0;i<mf.listFile.size();i++){
						out.append("__FF__"+mf.listFile.get(i).fileName+"__FF__"+mf.listFile.get(i).path+"__FF__"+mf.listFile.get(i).size
								+"__FF__"+mf.listFile.get(i).userName+"__FF__"+mf.listFile.get(i).IP+"__FF__"+mf.listFile.get(i).port);
						out.newLine();
					}
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		timerUpdateFILE.schedule(tFILE, 0,2000);*/
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent e) {
				JOptionPane.showMessageDialog(frame,"Plase click exit buttom to close window");
				 
			 }	 
		});
	}
	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
	}
}
