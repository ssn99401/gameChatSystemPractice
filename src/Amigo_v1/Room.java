package Amigo_v1;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.omg.CORBA.DATA_CONVERSION;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.EOFException;

class Room extends JFrame implements ActionListener {
	Date d1= new Date();
	
	WaitingRoom wr;
	JPanel p1;
	JTextArea ta;
	JList list;
	DefaultListModel<String> lm;
	JTextField tf;
	JButton b;
	JButton bsend;
	JButton fr;
	JButton btnNewButton;
	JButton btnNewButton_1;
	JButton btnNewButton_2;
	JButton bMenu;
	String id;
	WaitingRoom waitingRoom, waitingRoom2;
	static String chat="";
	String rn;
	private JPanel panel;
	JTextArea txt;

	
	Room(String name, String rn, WaitingRoom waitingRoom) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Amigo_Loading.class.getResource("/Amigo_image/logo.png")));

		setTitle(name + "님의 채팅창" + "   방제목 :" + rn);
		setBounds(0, 0, 600, 1000);
		this.rn = rn;
		this.waitingRoom = waitingRoom;
		id = name;

		JPanel p5 = new JPanel();
		p5.setLayout(new BoxLayout(p5, BoxLayout.Y_AXIS));

		// 북쪽 패널 -------------------------------
		JPanel p3 = new JPanel();
		p3.setBackground(new Color(26, 188, 156));
		p3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		bMenu = new JButton("");
		bMenu.setIcon(new ImageIcon(Room.class.getResource("/Amigo_image/menu.png")));
		bMenu.setBorderPainted(false);
		bMenu.setContentAreaFilled(false);
		bMenu.setOpaque(false);
		p3.add(bMenu);

		JLabel l1 = new JLabel("                   게임채팅                        ");
		l1.setFont(new Font("나눔고딕", Font.BOLD, 25));
		p3.add(l1);

		b = new JButton("");
		b.setIcon(new ImageIcon(Room.class.getResource("/Amigo_image/close.png")));
		b.setBorderPainted(false);
		b.setContentAreaFilled(false);
		b.setOpaque(false);

		p3.add(b);
		p5.add(p3);
	

		// 친구 확인하는 리스트 뜨게 만드는 창 + 생명 보여주는 창--------
		JPanel p4 = new JPanel();
		p4.setBorder(null);
		p4.setBackground(new Color(193, 234, 242));

		p5.add(p4);

		btnNewButton = new JButton("\uC544\uBBF8\uACE0 \uD034\uC988");
		btnNewButton.addActionListener(this);

		p4.add(btnNewButton);

		btnNewButton_1 = new JButton("\uCD08\uC131 \uAC8C\uC784");
		btnNewButton_1.addActionListener(this);
		p4.add(btnNewButton_1);

		btnNewButton_2 = new JButton("\uC124\uC815 \uCD08\uAE30\uD654");
		btnNewButton_2.addActionListener(this);
		p4.add(btnNewButton_2);

		fr = new JButton("");
		getContentPane().add(p5, "North");
		
		panel = new JPanel();
		p5.add(panel);
		panel.setBorder(null);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		txt = new JTextArea();
		txt.setLineWrap(true);
		txt.setBackground(new Color(193, 234, 242));
		txt.setEditable(false);
		txt.setBackground(new Color(204, 204, 255));
		txt.setFont(new Font("나눔고딕", Font.BOLD, 18));
		panel.add(txt);
		

		// 방에 있는 사람 리스트 창------------------------
		list = new JList(new DefaultListModel<>());
		lm = (DefaultListModel<String>) list.getModel();
		

		////// 가운데 창 -------------------
		// 채팅 창----------------------------------
		ta = new JTextArea();
		ta.setFont(new Font("Dialog", Font.PLAIN, 20));
		ta.setEditable(false);
		ta.setBackground(new Color(193, 234, 242));
		ta.setLineWrap(true);
		// ta.enable(false);
		ta.setForeground(Color.black);
		
		JScrollPane jScrollPane = new JScrollPane(ta);

		

		p1 = new JPanel();// p1.add(list);
		p1.add(jScrollPane);
		p1.add(list);
		list.setVisible(false);
		getContentPane().add(p1, "Center");
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		

		// 남쪽 보내기 패널 -----------------------
		JPanel p2 = new JPanel();
		p2.setBackground(new Color(26, 136, 183));
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));

		tf = new JTextField();
		p2.add(tf);

		bsend = new JButton("\uBCF4\uB0B4\uAE30");
		bsend.setFont(new Font("나눔고딕", Font.BOLD, 19));
		bsend.setForeground(Color.WHITE);
		bsend.setBorderPainted(false);
		bsend.setContentAreaFilled(false);
		bsend.setOpaque(false);
		p2.add(bsend);
		getContentPane().add(p2, "South");
		bsend.addActionListener(this);
		bMenu.addActionListener(this); // 아직 안만듬
		b.addActionListener(this); // okay
		tf.addActionListener(this); // okay

		setBounds(0, 0, 600, 1000);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Amigo_ID aid = new Amigo_ID();
		AmigoDAO dao;
		
		try {
			dao = new AmigoDAO();
			dao.gameindexSet(0);//기본값 0
			if (e.getSource() == b) {

				try {

					waitingRoom.sendMsg("roomout/" + id + "/" + rn);

					waitingRoom.dispose();
					setVisible(false);
					dispose();
					waitingRoom.setVisible(true);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			else if (e.getSource() == tf || e.getSource() == bsend) {
				try {
					dao = new AmigoDAO();
					/*if (dao.getgameindex() == 1) {
						if (tf.getText().trim().length() > 0) {
							chat = tf.getText().replaceAll(" ", "");
							System.out.println("채팅" + chat);
							waitingRoom.sendMsg("msgsearch/" + id + "/" + rn + "/" + chat);
							// waitingRoom.sendMsg("msg/" + id + "/" + rn + "/" + tf.getText());
							tf.setText("");
							ta.setCaretPosition(ta.getDocument().getLength());

						}
					} else {*/
						if (tf.getText().trim().length() > 0) {
							chat = tf.getText().replaceAll(" ", "");
							System.out.println("채팅" + chat);
							 waitingRoom.sendMsg("msgsearch/"+ id + "/" + rn + "/" + tf.getText());
							//waitingRoom.sendMsg("msg/" + id + "/" + rn + "/" + tf.getText());
							tf.setText("");
							ta.setCaretPosition(ta.getDocument().getLength());

						}

					
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			} 
			if (e.getSource() == bMenu) {
				System.out.println(list.isVisible());
				if (list.isVisible())
					list.setVisible(false);
				else
					list.setVisible(true);

			}
			if (e.getSource() == btnNewButton) {// 아미고 퀴즈모드
				
				dao.gameindexSet(1);
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(false);

				if (dao.getgameindex() == 1) {
				
					waitingRoom.sendMsg("que/" + "[AMIGO_QUIZ]" + "/" + rn + "/" + "아미고 퀴즈 모드입니다.");
					
					waitingRoom.sendMsg("queprint/" + "[AMIGO_QUIZ]" + "/" + rn+"/");// 문제출력 명령
					
					waitingRoom.sendMsg("ansqueprint/" + "[AMIGO_QUIZ]" + "/" + rn+"/");//정답출력
					/*waitingRoom.sendMsg("time/" + "[AMIGO_QUIZ]" + "/" + rn + "/");
					
					Timer t1 = new Timer();
					
					TimerTask tt1 = new TimerTask() {

						@Override
						public void run() {

							try {

								waitingRoom.sendMsg("queprint/" + "[AMIGO_QUIZ]" + "/" + rn);// 문제출력 명령
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							t1.cancel();

						}
					};
					t1.scheduleAtFixedRate(tt1, 10000, 1);
				*/
					
				
					//waitingRoom.sendMsg("ques1/"+"[AMIGO_QUIZ]" + "/" + rn );
					//waitingRoom.sendMsg("que/" + "[AMIGO_QUIZ]" + "/" + rn + "/" + "10초가 지났습니다.다음문제로 넘어갑니다.");

					
					
					}
					

				

				
			}

			if (e.getSource() == btnNewButton_1) {// 초성게임 모드
				dao.gameindexSet(2);
				btnNewButton_1.setEnabled(false);
				btnNewButton.setEnabled(false);
				
				if (dao.getgameindex() == 2) {
					
					
					//waitingRoom.sendMsg("info1/" + id + "/" + rn);
					waitingRoom.sendMsg("que/" + "[AMIGO_QUIZ]" + "/" + rn + "/" + "초성 퀴즈 모드 입니다.");

					waitingRoom.sendMsg("csprint/" + "[AMIGO_QUIZ]" + "/" + rn + "/");//문제출력 명령
					//waitingRoom.sendMsg("queprint/" + "[AMIGO_QUIZ]" + "/" + rn + "/");
					
					//waitingRoom.sendMsg("ques1/"+"[AMIGO_QUIZ]" + "/" + rn );
					
					
					/*Timer t1 = new Timer();
					
					TimerTask tt1 = new TimerTask() {

						@Override
						public void run() {

							try {
								waitingRoom.sendMsg("csprint/" + "[AMIGO_QUIZ]" + "/" + rn);//문제출력 명령
								//waitingRoom.sendMsg("queprint/" + "[AMIGO_QUIZ]" + "/" + rn);// 문제출력 명령
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							t1.cancel();

						}
					};
					t1.scheduleAtFixedRate(tt1, 10000, 1);*/
					
					
				}
			}
			
			
			if (e.getSource() == btnNewButton_2) {// 초기화
				dao.gameindexSet(0);

				if (dao.getgameindex() == 0) {
					waitingRoom.sendMsg("info2/" + id + "/" + rn);
					btnNewButton.setEnabled(true);
					btnNewButton_1.setEnabled(true);
				}
			}

			

		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {

		}
	}

	
}
