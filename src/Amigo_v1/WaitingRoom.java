package Amigo_v1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;

class WRthread extends Thread {

	public void run() {
		WaitingRoom client;
		try {

			
			
			client = new WaitingRoom(Amigo_ID.getNickname());

			client.initNet();

			client.setBounds(0, 0, 600, 1000);
			client.setLocationRelativeTo(null);
			client.setVisible(true);

			client.readMsg();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

public class WaitingRoom extends JFrame implements ActionListener, MouseListener {
	final static int portno = 13474;


	AmigoDAO dao = new AmigoDAO();
	static String moon="";
	static String answer="";
	static String hint="";
	JList list1, list2;
	DefaultListModel<String> lm1, lm2;
	Date d1= new Date();
	JTextField tf;

	JButton b; // �游���
	JButton b2;
	Socket s;

	BufferedWriter bw;

	BufferedReader br;

	String id;

	Room room;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	WaitingRoom(String id) throws Exception {

		setIconImage(Toolkit.getDefaultToolkit().getImage(Amigo_Loading.class.getResource("/Amigo_image/logo.png")));

		setTitle(id + "�� ȯ���մϴ�");
		setBounds(0, 0, 600, 1000);
		this.id = id;
		System.out.println("???");

		list1 = new JList(new DefaultListModel<>()); // �� ����
		list1.setFont(new Font("�������", Font.PLAIN, 15));
		lm1 = (DefaultListModel<String>) list1.getModel();
		System.out.println("???");
		list2 = new JList(new DefaultListModel<>()); // �մ� ����
		lm2 = (DefaultListModel<String>) list2.getModel();
		list1.setBackground(SystemColor.activeCaption);
		list2.setBackground(new Color(153, 255, 255));
		tf = new JTextField();
		b2 = new JButton("");
		b2.setIcon(new ImageIcon(WaitingRoom.class.getResource("/Amigo_image/close.png")));
		b = new JButton("�游���");
		b.setFont(new Font("�������", Font.PLAIN, 14));
		tf.addActionListener(this);
		b.addActionListener(this);
		b2.addActionListener(this);
		list1.addMouseListener(this);

		// ��� �� ����Ʈ �г�
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		JScrollPane jScrollPane = new JScrollPane(list1);
		p1.add(jScrollPane);
//		p1.add(list2);

		// ���� �г�--------------(�游��� ��ư)
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p2.add(tf);
		p2.setBackground(new Color(26, 136, 183));
		b.setBorderPainted(false);
		b.setContentAreaFilled(false);
		b.setOpaque(false);
		p2.add(b);

		// ���� �г� ------------ (������ ��ư)
		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
		p3.setBackground(new Color(26, 188, 156));

		JLabel la1 = new JLabel(
				"    \uBC29 \uB9AC\uC2A4\uD2B8                                                                 ");
		la1.setFont(new Font("�������", Font.BOLD, 22));
		p3.add(la1);

		b2.setBorderPainted(false);
		b2.setContentAreaFilled(false);
		b2.setOpaque(false);
		p3.add(b2);

		getContentPane().add(p3, "North");

		getContentPane().add(p1);

		getContentPane().add(p2, "South");
		
		setLocationRelativeTo(null);
	}

	void stop() throws IOException {

		s.close();

	}

	void initNet() throws Exception {

		s = new Socket("192.168.58.22", portno);

		InputStream is = s.getInputStream();

		OutputStream os = s.getOutputStream();

		InputStreamReader isr = new InputStreamReader(is);

		br = new BufferedReader(isr);

		OutputStreamWriter osw = new OutputStreamWriter(os);

		bw = new BufferedWriter(osw);

		sendMsg("enter/" + id);

	}

	public void actionPerformed(ActionEvent e) {

		try {

			if (e.getSource() == b || e.getSource() == tf) {
				if (tf.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "���̸��� �������ּ���", "warning", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					setVisible(false);

					room = new Room(id, tf.getText(), this);

					room.setBounds(0, 0, 600, 1000);

					room.setVisible(true);

					sendMsg("mkroom/" + tf.getText()); // mkroom/�ٹ�

				}
			} else if (e.getSource() == b2) {

				try {
					new Amigo_FriendsList().setVisible(true);
					sendMsg("removeG/");
					dispose();
					setVisible(false);
					stop();
					
					// ���� ���� ���� --> interrupt
					

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		} catch (Exception e1) {

			e1.printStackTrace();

		}

	}

	void sendMsg(String msg) throws Exception {

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
			AmigoDAO dao= new AmigoDAO();
			
			while (true) {
				
				String line = br.readLine();
				

				System.out.println(line);

				String[] array = line.split("/");
				
				
				switch (array[0]) {

				case "guestlist":

					lm2.removeAllElements();

					int len = array.length;

					for (int i = 1; i < len; i++)

						lm2.addElement(array[i]);
					break;

				case "roomlist":

					lm1.removeAllElements();

					len = array.length;

					for (int i = 1; i < len; i++) 

						lm1.addElement(array[i]);
					

					break;

				case "roomnum":

					len = array.length;

					for (int i = 1; i < len; i++)

						lm1.setElementAt(lm1.getElementAt(i - 1) + "//" + array[i] + "��", i - 1);

					break;

				case "guestlistRoom":

					room.lm.removeAllElements();

					int len2 = array.length;

					for (int i = 1; i < len2; i++) {

						room.lm.addElement(array[i]);

					}

					break;

				case "out":

					room.lm.removeElement(array[1]);

					break;

				case "msg":

					System.out.println("�޽��� ����");

					room.ta.append("[" + array[1] + "]" + array[2] + "\n");
				
					break;
				
				case "queprint1":

					System.out.println("�޽��� ����");
					moon=array[2].replaceFirst("����", "\t\t\t\tQ.");
					//array[2]=dao.getPquiz()[1].replaceFirst("����", "\t\t\t\tQ.");
					room.txt.append("[" + "AMIGO" + "]" + moon + "\n");
					/*Timer t1 = new Timer();
					TimerTask tt1 = new TimerTask() {
						
						@Override
						public void run() {
							room.txt.append("[" + "AMIGO" + "]" + dao.getPquiz()[1].replaceFirst("����", "\t\t\t\tQ.") + "\n");
						t1.cancel();
						}
					};
					
					t1.schedule(tt1, 5000, 1);*/
					break;
					
				case "queprint2":

					System.out.println("�޽��� ����");
					answer=array[2];
					System.out.println(answer);
					//array[2]=dao.getPquiz()[1].replaceFirst("����", "\t\t\t\tQ.");
					//room.txt.append("[" + "AMIGO" + "]" + dao.getPquiz()[1].replaceFirst("����", "\t\t\t\tQ.") + "\n");
					/*Timer t1 = new Timer();
					TimerTask tt1 = new TimerTask() {
						
						@Override
						public void run() {
							room.txt.append("[" + "AMIGO" + "]" + dao.getPquiz()[1].replaceFirst("����", "\t\t\t\tQ.") + "\n");
						t1.cancel();
						}
					};
					
					t1.schedule(tt1, 5000, 1);*/
					break;
					
				case "queprint3":

					System.out.println("�޽��� ����");
					
					hint=array[2];
					//array[2]=dao.getPquiz()[1].replaceFirst("����", "\t\t\t\tQ.");
					//room.txt.append("[" + "AMIGO" + "]" + dao.getPquiz()[1].replaceFirst("����", "\t\t\t\tQ.") + "\n");
					/*Timer t1 = new Timer();
					TimerTask tt1 = new TimerTask() {
						
						@Override
						public void run() {
							room.txt.append("[" + "AMIGO" + "]" + dao.getPquiz()[1].replaceFirst("����", "\t\t\t\tQ.") + "\n");
						t1.cancel();
						}
					};
					
					t1.schedule(tt1, 5000, 1);*/
					break;
				case "ansqueprint":
					
					Timer t3 = new Timer();
				
					TimerTask tt3 = new TimerTask() {
						

						@Override
						public void run() {
							
							
							System.out.println("�������");
							room.txt.append("[" + "AMIGO" + "]" + "30�ʰ� �������ϴ�." + "\n");
							room.txt.append("[" + "AMIGO" + "]" + "������" + "\n");
							room.txt.append("[" + "AMIGO" + "]" + answer + "\n");
							try {
								dao.setPquiz(null);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							dao.setCsQuiz(null);

							moon = "";
							answer = "";
							hint = "";
							try {
								sendMsg("clear/" + "[AMIGO_QUIZ]" + "/" + room.rn+"/");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}//�������
							t3.cancel();
						}
						
						

					};
					t3.schedule(tt3, 30000, 1);
					if(answer.isEmpty()) {
						t3.cancel();
					}
					
					break;
					
				case "csprint1"://�ʼ�

					System.out.println("�޽��� ����");
					
					moon=array[2];
					//array[2]=dao.getPquiz()[1].replaceFirst("����", "\t\t\t\tQ.");
					room.txt.append("[" + "AMIGO" + "]" + moon + "\n");
					break;
				case "csprint2"://��Ʈ

					System.out.println("�޽��� ����");
					hint=array[2];
					room.txt.append("[" + "AMIGO" + "]" + hint + "\n");
					break;
				case "csprint3"://����

					System.out.println("�޽��� ����");
					answer=array[2].replaceAll(" ", "");
					break;
				case "queans1":
			
					System.out.println("�޽��� ����");
					String chat1=array[2].replaceAll(" ", "");
					try {
						if (!answer.isEmpty() && !array[2].isEmpty()) {
							if (answer.equals(chat1)) {

								sendMsg("que2/" + room.getName() + "/" + room.rn + "/" + "�� ����!!!!+50��");
								dao.setPquiz(null);
								dao.setCsQuiz(null);
								moon="";
								answer="";
								hint="";
								sendMsg("clear/" + id + "/" + room.rn + "/");

								/*Timer t1 = new Timer();
								TimerTask tt1 = new TimerTask() {

									@Override
									public void run() {
										try {
											sendMsg("clear/" + id + "/" + room.rn + "/");

											t1.cancel();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
								};
								t1.schedule(tt1, 5000, 1);*/
							}
						}
					} catch (Exception e) {

					} finally {
						room.ta.append("[" + array[1] + "]" + array[2] + "\n");
					}

					break;
				case "que":

					System.out.println("�޽��� ����");
					
					room.txt.append("[" + "AMIGO" + "]" + array[2] + "\n");

					break;
					
				case "queans":

					System.out.println("�޽��� ����");
					
					room.txt.append(id + array[2] + "\n");
					

					break;
					
				case "clear":

					System.out.println("5���� Ŭ����");
					
					Timer t1 = new Timer();
					TimerTask tt1 = new TimerTask() {

						@Override
						public void run() {
							try {
								room.txt.setText("");
								room.btnNewButton.setEnabled(true);
								room.btnNewButton_1.setEnabled(true);

								t1.cancel();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					};
					t1.schedule(tt1, 5000, 1);
					
					

					break;
					
				case "time":
		
					Timer tmr= new Timer();
					TimerTask ttask = new TimerTask() {

						@Override
						public void run() {
							System.out.println("5���������ϴ�");
							tmr.cancel();
						}
					};
					tmr.scheduleAtFixedRate(ttask, 5000, 1);

					break;
					
				case "search":
					
					
					room.txt.append(id + array[2] + "\n");

					break;
					


				}

			}

		} catch (Exception e) {

			System.out.println("�дٰ�������~" + e.getMessage());

			e.printStackTrace();

		}

	}

	@Override

	public void mouseClicked(MouseEvent e) {

		if (e.getClickCount() == 2) {

			try {

				String roomname[] = ((String) list1.getSelectedValue()).split("/");

				setVisible(false);

				dispose();

				room = new Room(id, roomname[0], this);

				room.setVisible(true);

				sendMsg("roomjoin/" + id + "/" + roomname[0]);

			} catch (Exception e1) {

				e1.printStackTrace();

			}

		}

	}


	@Override

	public void mouseEntered(MouseEvent e) {

	}

	@Override

	public void mouseExited(MouseEvent e) {

	}

	@Override

	public void mousePressed(MouseEvent e) {

	}

	@Override

	public void mouseReleased(MouseEvent e) {

	}

	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {

//		Random rc = new Random();

		// ���̵� �´� ����
		// Ȯ���ϱ�------------------------------------------------------------------------------------------
		String[] st = { "���� ����", "���ο� ����" };
		int no = JOptionPane.showOptionDialog(null, "����Ͻ� ������ �����ּ���", "���� ����", JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, st, "���� ����");
		String nickname = null;
		AmigoDAO amigoDao = new AmigoDAO();

		switch (no) {
		case -1:
			// ������ else���� ���� â���� ���ư��� ��ɹ� ��ġ
			break;

		case 0:
			nickname = amigoDao.searchNickname();
			if (nickname == null) {
				JOptionPane.showMessageDialog(null, "������ �����ϴ�. ���ο� ������ �������ּ���.");
			} else
				break;

		case 1:
			nickname = JOptionPane.showInputDialog("����Ͻ� �г����� �����ּ���");
			if (nickname != null) {
				boolean intt = amigoDao.setNickname(nickname);
			}

		}
		if (nickname != null) {
//		WaitingRoom client = new WaitingRoom("asdf" + rc.nextInt(10) * 1);
			WaitingRoom client = new WaitingRoom("nk");

			client.initNet();

			client.setBounds(0, 0, 600, 1000);

			client.setVisible(true);
		
			client.readMsg();
		} else {
			// ���� ���� ���� â���� ���ư���

		} // ����ä�� �������� �ű��

	}

}
