package Client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import Server.ManaFile;


public class ClientGUI {

	private JFrame frame;
	private JTextField txtIP;
	private JTextField txtPort;
	Socket Client;
	ConnectClient a;
	public JTextField txtUser;
	private JTabbedPane tabbedPane;
	private JPanel panel_1;
	private JButton btnBrowser;
	ManaFile mf= new ManaFile();
	String IP;
	public myFile listF = new myFile();
	private JPanel panel_2;
	private JList listMyFile;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	public boolean Diconnect=false;
	JButton btnExit = new JButton("Exit");
	
	public JButton btnCall = new JButton("Call");
	public JList listFriend = new JList();
	private JTextField txtConf;
	
	JLabel Nconf = new JLabel("Name:");
	JButton Jconf;
	String userC;
	
	public static int findFreePort() {
	    int port;
	    try {
	      ServerSocket socket= new ServerSocket(0);
	      port = socket.getLocalPort();
	      socket.close(); 
	    } catch (Exception e) { port = -1; }
	    return port;    
	  } 
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			//Button btnInstance.setUI (new BEButtonUI (). setNormalColor (BEButtonUI.NormalColor.blue));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
	}
	
	void startConnect(String IP, int port) throws UnknownHostException, IOException{
	     this.Client= new Socket(IP,port);
	     this.a = new ConnectClient(mf,this.Client, this);
	     Thread b= new Thread(a);
	     b.start();
	 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		
		frame = new JFrame("___CLIENT___");
		frame.setBounds(100, 100, 539, 354);
		frame.setBackground(Color.RED);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtIP = new JTextField();
		txtIP.setText("192.168.100.17");
		txtIP.setBounds(53, 11, 89, 20);
		frame.getContentPane().add(txtIP);
		txtIP.setColumns(10);
		
		try {
			InetAddress getIP = InetAddress.getLocalHost();
			IP= getIP.getHostAddress();
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		txtPort = new JTextField();
		txtPort.setBounds(209, 11, 86, 20);
		frame.getContentPane().add(txtPort);
		txtPort.setColumns(10);
		txtPort.setText("5000");
		final JButton startButton = new JButton("Log in");
		startButton.setUI(new BEButtonUI (). setNormalColor (BEButtonUI.NormalColor.blue));
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(txtUser.getText().equals("")){
					JOptionPane.showMessageDialog(frame, "Enter User Name, please!");
				}
				else{
					if(startButton.getText().equals("Log in")){
						btnExit.setVisible(false);
						txtIP.disable();
						txtPort.disable();
						userC=txtUser.getText();
						txtUser.disable();
						/*File file = new File("file.txt");
						FileReader fr;
						try {
							fr = new FileReader(file);
							BufferedReader br = new BufferedReader(fr);
							String line;
							
							while((line = br.readLine())!=null){
								String []s = line.split("__FF__");
								//System.out.println(s[4]);
								if (s[4].equals(txtUser.getText())){
									System.out.println("dang doc file");
									MyFileNode node = new MyFileNode(s[4],s[1],s[2],Long.parseLong(s[3]));
									listF.addFile(node);
									
								}
								
							}
							br.close();
							DefaultListModel<String> modelMyF=new DefaultListModel<String>();
							for(int i=0;i<listF.listFile.size();i++){
								modelMyF.addElement(listF.listFile.get(i).fileName);
							}
							listMyFile.setModel(modelMyF);
							
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
						
						
						try {
							startConnect(txtIP.getText(),Integer.parseInt(txtPort.getText()));
							
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							startButton.setText("Log out");
							startButton.setUI(new BEButtonUI (). setNormalColor (BEButtonUI.NormalColor.red));
							System.out.println(mf.listFile.size());
								
								for(int i=0;i<mf.listFile.size();i++){
									System.out.println(mf.listFile.get(i).userName);
									if(mf.listFile.get(i).userName.equals(txtUser.getText()))
									{
										MyFileNode node= new MyFileNode(mf.listFile.get(i).userName,mf.listFile.get(i).fileName, mf.listFile.get(i).path, mf.listFile.get(i).size);
										listF.listFile.add(node);
										System.out.println("da vao "+ node.path);
									}
								}
								DefaultListModel<String> modelMyF=new DefaultListModel<String>();
								for(int i=0;i<listF.listFile.size();i++){
									modelMyF.addElement(listF.listFile.get(i).fileName);
								}
								listMyFile.setModel(modelMyF);
							 
							//a.out.writeUTF("UUU___"+txtUser.getText()+"___UUU");
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else{
						
						
						try {
							a.out.writeUTF("__AA__DISCONNECT");
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						startButton.setText("Log in");
						startButton.setUI(new BEButtonUI (). setNormalColor (BEButtonUI.NormalColor.blue));
						System.exit(0);
						//a.out.writeUTF("adf");
					}
				}
				
				
			}
		});
		startButton.setBounds(409, 42, 89, 23);
		frame.getContentPane().add(startButton);
		
		txtUser = new JTextField();
		txtUser.setBounds(412, 11, 86, 20);
		frame.getContentPane().add(txtUser);
		txtUser.setColumns(10);
		txtUser.setText("");
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(26, 68, 221, 200);
		frame.getContentPane().add(tabbedPane);
		
		panel_1 = new JPanel();
		tabbedPane.addTab("File to Download", null, panel_1, null);
		panel_1.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 12, 195, 110);
		panel_1.add(scrollPane_1);
		
		final JList listFile = new JList();
		scrollPane_1.setViewportView(listFile);
		listFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2){
					int index = listFile.getSelectedIndex();
					JFileChooser fd = new JFileChooser();
					fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fd.showOpenDialog(null);
					File fileD = fd.getSelectedFile();
					long size= mf.listFile.get(index).size;
					try {
						ServerSocket svd= new ServerSocket(0);
						a.out.writeUTF("__AA__REQDOWN__AA__"+index+"__AA__"+svd.getLocalPort()+"__AA__"+IP);
						
						//System.out.println("__AA__REQDOWN__AA__"+index+"__AA__"+svd.getLocalPort()+"__AA__"+svd.getLocalSocketAddress());
						DownLoad d =new DownLoad(svd,fileD,size);
						new Thread(d).start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		JButton bottonDown = new JButton("DownLoad");
		bottonDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				int index = listFile.getSelectedIndex();
				JFileChooser fd = new JFileChooser();
				fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fd.showOpenDialog(null);
				File fileD = fd.getSelectedFile();
				long size= mf.listFile.get(index).size;
				try {
					ServerSocket svd= new ServerSocket(0);
					a.out.writeUTF("__AA__REQDOWN__AA__"+index+"__AA__"+svd.getLocalPort()+"__AA__"+IP);
					
					//System.out.println("__AA__REQDOWN__AA__"+index+"__AA__"+svd.getLocalPort()+"__AA__"+svd.getLocalSocketAddress());
					DownLoad d =new DownLoad(svd,fileD,size);
					new Thread(d).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		bottonDown.setBounds(10, 133, 89, 23);
		panel_1.add(bottonDown);
		
		panel_2 = new JPanel();
		tabbedPane.addTab("My flie", null, panel_2, null);
		panel_2.setLayout(null);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = listMyFile.getSelectedIndex();
				String name = listF.removeFile(index);
				System.out.println("index is " + index);
				System.out.println(listF.listFile.size());
				try {
					a.out.writeUTF("__AA__DELETE__AA__"+name);
					DefaultListModel<String> modelMyFde=new DefaultListModel<String>();
					for(int i=0;i<listF.listFile.size();i++){
						modelMyFde.addElement(listF.listFile.get(i).fileName);
						
					}
					listMyFile.setModel(modelMyFde);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
				
			}
		});
		btnDelete.setBounds(127, 134, 63, 23);
		panel_2.add(btnDelete);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 196, 113);
		panel_2.add(scrollPane);
		
		listMyFile = new JList();
		scrollPane.setViewportView(listMyFile);
		
		
		/*for(int i=0;i<listF.listFile.size();i++){
			modelMyF.addElement(listF.listFile.get(i).getName());
		}
		listMyFile.setModel(modelMyF);*/
		btnBrowser = new JButton("UpLoad");
		btnBrowser.setBounds(32, 134, 69, 23);
		panel_2.add(btnBrowser);
		btnBrowser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(null);
				File file = fc.getSelectedFile();
				try {
					a.out.writeUTF("__AA__UPLOAD__AA__"+file.getAbsolutePath()+"__AA__"+file.getName()+"__AA__"+file.length());
					MyFileNode FN = new MyFileNode(txtUser.getText(), file.getName(), file.getAbsolutePath(), file.length());
					
					listF.addFile(FN);
					DefaultListModel<String> modelMyF=new DefaultListModel<String>();
					for(int i=0;i<listF.listFile.size();i++){
						modelMyF.addElement(listF.listFile.get(i).fileName);
					}
					
					listMyFile.setModel(modelMyF);

				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
			
		});
		
		final JLabel mauMe = new JLabel("___3D GROUP___");
		mauMe.setBounds(26, 279, 348, 29);
		mauMe.setForeground(Color.black);
		mauMe.setBackground(Color.lightGray);
		frame.getContentPane().add(mauMe);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(26, 11, 17, 20);
		frame.getContentPane().add(lblIp);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(173, 14, 26, 14);
		frame.getContentPane().add(lblPort);
		
		JLabel lblNewLabel = new JLabel("UserName:");
		lblNewLabel.setBounds(344, 13, 55, 17);
		frame.getContentPane().add(lblNewLabel);
		
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				System.exit(0);
			}
		});
		btnExit.setBounds(409, 282, 89, 23);
		frame.getContentPane().add(btnExit);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(284, 68, 208, 211);
		frame.getContentPane().add(tabbedPane_1);
		
		JPanel friend = new JPanel();
		friend.setLayout(null);
		tabbedPane_1.addTab("Friend", null, friend, null);
		
		JScrollPane aaa = new JScrollPane();
		aaa.setBounds(10, 11, 183, 108);
		friend.add(aaa);
		
		
		aaa.setViewportView(listFriend);
		
		
		btnCall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnCall.getText().equals("Call")){
					int index = listFriend.getSelectedIndex();
					int port = findFreePort();
					btnCall.setText("Waiting....");
					try {
						a.out.writeUTF("__AA__RECALL__AA__"+index+"__AA__"+IP+"__AA__"+port+"__AA__"+userC);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				if(btnCall.getText().equals("End")){
					btnCall.setText("Call");
					btnCall.setUI(new BEButtonUI (). setNormalColor (BEButtonUI.NormalColor.green));
				}
			}
		});
		btnCall.setBounds(10, 131, 89, 23);
		friend.add(btnCall);
		btnCall.setUI(new BEButtonUI (). setNormalColor (BEButtonUI.NormalColor.green));
		JPanel conf = new JPanel();
		conf.setLayout(null);
		tabbedPane_1.addTab("Conference", null, conf, null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 10, 181, 89);
		conf.add(scrollPane_3);
		
		JList listConf = new JList();
		scrollPane_3.setViewportView(listConf);
		
		final JButton Cconf = new JButton("Create");
		Cconf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(Cconf.getText().equals("Create")){
					txtConf.setVisible(true);
					Nconf.setVisible(true);
					/*int result = JOptionPane.showConfirmDialog(null,
							"Are you sure you wish to exit application?", null,
							JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						System.out.println("yes");

					} else
						System.out.println("no");*/
					Cconf.setText("OK");
				
					Jconf.setText("Cancel");
					System.out.println("choose done");
				}
				else if(Cconf.getText().equals("OK")){
					txtConf.setVisible(false);
					Nconf.setVisible(false);
					Cconf.setText("Create");
					txtConf.setText("");
					Jconf.setText("Join");
				}
				

			}
		});
		Cconf.setBounds(10, 138, 89, 23);
		conf.add(Cconf);
		
		Jconf = new JButton("Join");
		Jconf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(Jconf.getText().equals("Cancel")){
					Nconf.setVisible(false);
					txtConf.setVisible(false);
					txtConf.setText("");
					Jconf.setText("Join");
					Cconf.setText("Create");
				}
			}
		});
		Jconf.setBounds(112, 138, 79, 23);
		conf.add(Jconf);
		
		txtConf = new JTextField();
		txtConf.setBounds(60, 110, 131, 20);
		conf.add(txtConf);
		txtConf.setColumns(10);
		
		
		Nconf.setBounds(10, 110, 40, 20);
		conf.add(Nconf);
		
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
					mauMe.setText("___PEER TO PEER CLIENT___");
				}
				else if(mauMe.getText().equals("___PEER TO PEER CLIENT___")){
					mauMe.setForeground(Color.BLUE);
					mauMe.setText("___3D GROUP___");
				}
				
			}
				
		};
		timerUpdateMau.schedule(ttMau, 0,2000);
		Timer timerUpdateList=new Timer();
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				if(startButton.getText().equals("Log out")){
					try {
						a.out.writeUTF("__AA__UPDATE");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(int i=1;i<100;i++);
					DefaultListModel<String> model=new DefaultListModel<String>();
					for(int i=0;i<mf.listFile.size();i++){
						model.addElement(mf.listFile.get(i).fileName);
						
					}
					listFile.setModel(model);
					
					try {
						a.out.writeUTF("__AA__UPDATE1");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				}
				
		};
		timerUpdateList.schedule(tt, 0,5000);
		UIManager . put ( "RootPane.setupButtonVisible" ,  false );
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Nconf.setVisible(false);
	    txtConf.setVisible(false);
	}
}
