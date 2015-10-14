package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JLabel;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class downLoadBar extends JFrame {
	public JProgressBar pgr;
	public JPanel contentPane;
	public JLabel textD ;
	public JLabel mount;
	public JLabel speed;
	public JLabel filename;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					downLoadBar frame = new downLoadBar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public downLoadBar() {
		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			//Button btnInstance.setUI (new BEButtonUI (). setNormalColor (BEButtonUI.NormalColor.blue));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 383, 119);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		pgr= new JProgressBar(0,100);
		pgr.setBounds(31, 28, 310, 25);
		contentPane.add(pgr);
		pgr.setStringPainted(true);;
		pgr.setValue(0);

		
		textD = new JLabel("Downloading...");
		textD.setBounds(31, 11, 105, 14);
		contentPane.add(textD);
		
		mount = new JLabel("");
		mount.setBounds(167, 55, 174, 14);
		contentPane.add(mount);
		
		speed = new JLabel("New label");
		speed.setBounds(31, 55, 126, 14);
		contentPane.add(speed);
		
		filename = new JLabel("New label");
		filename.setBounds(167, 11, 174, 14);
		contentPane.add(filename);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent e) {
				int i= JOptionPane.showConfirmDialog(contentPane, "Closing");
				 if(i == JOptionPane.YES_OPTION){
					 contentPane.setVisible(false);
					 }
				 }
			 
		});
	}
}
