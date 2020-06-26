package Amigo_v1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

class CnThread extends Thread {
	public void run() {
		Amigo_OneChat agoOC;

		try {
			agoOC = new Amigo_OneChat(Amigo_ID.getOcname());

			agoOC.initNet();

			agoOC.setBounds(600, 45, 600, 1000);

			agoOC.setVisible(true);

			agoOC.readMsg();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

class FileTypeFilter extends FileFilter {
	private final String extension;
	private final String description;
	
	public FileTypeFilter(String extension, String description) {
		this.extension=extension;
		this.description=description;
	}
	
	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		if(f.isDirectory()) {
			return true;
		}
		return f.getName().endsWith(extension);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description + String.format("(*%s)", extension);
	}
	
}

public class Amigo_OneChat extends JFrame implements ActionListener {
	final static int portno=13426;
	JPanel contentPane, panel, panel2, panel3, panel4;
	JButton saveBtn, exitBtn, sendBtn;
	JLabel nameLab;
	JTextArea area;
	JTextField msgTxt;
	JScrollPane scroll;
	JScrollBar sb;
	BufferedReader br;
	BufferedWriter bw;
	String id=Amigo_ID.getOcname();
	String myname=Amigo_ID.getMyname();
	Socket s;
	
	public Amigo_OneChat(String name)  {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 45, 600, 1000);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(193, 234, 242));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(26, 188, 156));
		panel.setBounds(0, 0, 170, 67);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		saveBtn=new JButton("");
		saveBtn.addActionListener(this);
		saveBtn.setIcon(new ImageIcon(Amigo_OneChat.class.getResource("/Amigo_image/\uC800\uC7A5.png")));
		saveBtn.setBorderPainted(false);
		saveBtn.setContentAreaFilled(false);
		saveBtn.setOpaque(false);
		panel.add(saveBtn);
		Component horizontalStrut = Box.createHorizontalStrut(75);
		panel.add(horizontalStrut);
		
		
		panel2=new JPanel();
		panel2.setBackground(new Color(26, 188, 156));
		panel2.setBounds(414, 0, 170, 67);
		contentPane.add(panel2);
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Component horizontalStrut_1 = Box.createHorizontalStrut(75);
		panel2.add(horizontalStrut_1);
		
		exitBtn=new JButton("");
		exitBtn.addActionListener(this);
		exitBtn.setIcon(new ImageIcon(Amigo_OneChat.class.getResource("/Amigo_image/close.png")));
		exitBtn.setContentAreaFilled(false);
		exitBtn.setBorderPainted(false);
		panel2.add(exitBtn);
		
		panel3=new JPanel();
		panel3.setBackground(new Color(26, 188, 156));
		panel3.setBounds(170, 0, 245, 67);
		contentPane.add(panel3);
		panel3.setLayout(new BorderLayout(0, 0));
		
		
		nameLab=new JLabel(name);
		id=name;
		nameLab.setText(name);
		panel3.add(nameLab, BorderLayout.CENTER);
		nameLab.setHorizontalAlignment(SwingConstants.CENTER);
		
		msgTxt=new JTextField();
		msgTxt.setBounds(0, 923, 489, 38);
		contentPane.add(msgTxt);
		msgTxt.addActionListener(this);
		msgTxt.setColumns(10);
		
		sendBtn=new JButton("\uBCF4\uB0B4\uAE30");
		sendBtn.setBounds(488, 921, 96, 38);
		contentPane.add(sendBtn);
		sendBtn.addActionListener(this);
		sendBtn.setBackground(new Color(0, 102, 204));
		sendBtn.setFont(new Font("나눔고딕", Font.BOLD, 16));
		sendBtn.setForeground(Color.WHITE);
		sendBtn.setBorderPainted(false);
		sendBtn.setContentAreaFilled(false);
		sendBtn.setOpaque(false);
		
		panel4=new JPanel();
		panel4.setBackground(new Color(26, 136, 183));
		panel4.setBounds(0, 923, 584, 38);
		contentPane.add(panel4);
		panel4.setLayout(null);
		
		area=new JTextArea("");
		scroll=new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(0, 77, 584, 836);
		sb=scroll.getVerticalScrollBar();
		area.setFont(new Font("Dialog", Font.PLAIN, 20));
		area.setEditable(false);
		area.setBackground(new Color(193, 234, 242));
		area.setForeground(Color.black);
		contentPane.add(scroll);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		if(obj==saveBtn) {
			JFileChooser fs=new JFileChooser(new File("c:\\"));
			fs.setDialogTitle("저장");
			fs.setFileFilter(new FileTypeFilter(".txt","Text File"));	
			int result=fs.showSaveDialog(null);
			if(result==JFileChooser.APPROVE_OPTION) {
				String content=area.getText();
				File fi=fs.getSelectedFile();
				
				try {
					FileWriter fw=new FileWriter(fi.getPath()+".txt");
					fw.write(content);
					fw.flush();
					fw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		}
		else if(obj==exitBtn) {
			dispose();
			stop();
			try {
				new Amigo_FriendsList().setVisible(true);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(obj==sendBtn || obj==msgTxt) {
			String msg=msgTxt.getText();
			sendMsg("ONE/"+myname+"/"+msg);
		}
		sb.setValue(sb.getMaximum());
		msgTxt.setText("");
		msgTxt.requestFocus();
	}
	
	void stop() {
		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void initNet() throws UnknownHostException, IOException {
			s = new Socket("127.0.0.1", portno);
			
			InputStream is = s.getInputStream();

			OutputStream os = s.getOutputStream();

			InputStreamReader isr = new InputStreamReader(is);

			br = new BufferedReader(isr);

			OutputStreamWriter osw = new OutputStreamWriter(os);

			bw = new BufferedWriter(osw);

			sendMsg("OCNAME/" + myname);

	}
	
	void sendMsg(String msg) {
		System.out.println(msg);
		try {

			bw.write(msg + "\n");

			bw.flush();

		}

		catch (Exception ee) {

			ee.printStackTrace();

		}

	}
	
	public void readMsg() {

		try {

			while (true) {

				String line = br.readLine();

				System.out.println(line);

				String[] array = line.split("/");
				
				switch (array[0]) {

				case "entrance":
					System.out.println(array[1]);
					area.append(array[1]+"\n");
					
					break;

				case "one":
					System.out.println(array[1]);
					area.append(array[1]+"\n");
					

					break;

				}	
			}

		} catch (Exception e) {

			System.out.println("읽다가에러남~" + e.getMessage());

			e.printStackTrace();

		}

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Amigo_OneChat("hh");
	}
}