package Amigo_v1;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Amigo_FriendsList extends JFrame {

	JPanel contentPane;
	JTextField textField;
	JTable table;
	AmigoDAO dao;
	Amigo_MainProcess main = new Amigo_MainProcess();
	WaitingRoom client;
	Amigo_OneChat agoOC;
	String ocname=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Amigo_FriendsList frame = new Amigo_FriendsList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Amigo_FriendsList() throws ClassNotFoundException, SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Amigo_Loading.class.getResource("/Amigo_image/logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 45, 600, 1000);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(193, 234, 242));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("\uCE5C \uAD6C");
		label.setFont(new Font("HY엽서L", Font.BOLD, 55));
		label.setBounds(12, 10, 147, 100);
		contentPane.add(label);

		JLabel lblNewLabel = new JLabel("\uCE5C\uAD6C \uCC3E\uAE30");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == lblNewLabel) {
					dispose();
					setVisible(false);
					try {
						new Amigo_FindFriend().setVisible(true);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("휴먼매직체", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(171, 91, 114, 29);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\uAC8C\uC784 \uCC44\uD305");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try { //오픈 채팅
					// 아이디에 맞는 별명 확인하기------------------------------------------------------------------------------------------
					String[] st = { "기존 별명", "새로운 별명" };
					int no = JOptionPane.showOptionDialog(null, "사용하실 별명을 정해주세요", "별명 설정", JOptionPane.DEFAULT_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, st, "기존 별명");
					String nickname = null;
					AmigoDAO amigoDao = new AmigoDAO();

					switch (no) {
					case -1:
						// 마지막 else문에 원래 창으로 돌아가는 명령문 위치
						break;

					case 0:
						nickname = amigoDao.searchNickname();
						if (nickname == null) {
							JOptionPane.showMessageDialog(null, "별명이 없습니다. 새로운 별명을 생성해주세요.");
						} else
							break;

					case 1:
						nickname = JOptionPane.showInputDialog("사용하실 닉네임을 적어주세요");
						if (nickname != null) {
							boolean intt = amigoDao.setNickname(nickname);
						}

					}
					if (nickname != null) {
						Amigo_ID.setNickname(nickname);
//						WaitingRoom client = new WaitingRoom("asdf" + rc.nextInt(10) * 1);
						dispose();
						setVisible(false);
						WRthread th = new WRthread();
						th.start();
						
					} else {
						// 껐을 때는 원래 창으로 돌아가기

					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setFont(new Font("휴먼매직체", Font.BOLD, 25));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(310, 91, 114, 29);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("\uB85C\uADF8 \uC544\uC6C3");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//로그아웃
				dispose();
				new Amigo_Logout().setVisible(true);
				Amigo_ID.setId(null);
				
			}
		});
		lblNewLabel_3.setForeground(Color.BLUE);
		lblNewLabel_3.setFont(new Font("휴먼매직체", Font.BOLD, 25));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(458, 91, 114, 29);
		contentPane.add(lblNewLabel_3);

		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(Color.WHITE);
		toolBar.setFloatable(false);
		toolBar.setForeground(Color.BLACK);
		toolBar.setBounds(324, 209, 248, 52);
		contentPane.add(toolBar);

		JButton btnNewButton = new JButton("");
		btnNewButton.addMouseListener(new MouseAdapter() {
			Amigo_MainProcess main = new Amigo_MainProcess();

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setIcon(new ImageIcon(Amigo_FriendsList.class.getResource("/Amigo_image/friend.png")));
		toolBar.add(btnNewButton);

		textField = new JTextField();
		textField.setFont(new Font("휴먼매직체", Font.BOLD, 20));
		textField.setBackground(Color.WHITE);
		toolBar.add(textField);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getSource() == textField)
					textField.selectAll();
			}
		});
		textField.setText("\uCE5C\uAD6C \uC774\uB984 \uAC80\uC0C9");
		textField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 293, 584, 668);
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0, 0)));
		scrollPane.setBackground(new Color(0, 0, 0, 0));
		contentPane.add(scrollPane);

		ArrayList<FriendsListVO> FVO = Find();

		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setBackground(new Color(0, 0, 0, 0));
		toolBar_1.setOrientation(SwingConstants.VERTICAL);
		toolBar_1.setFloatable(false);
		scrollPane.setViewportView(toolBar_1);

		if (!FVO.isEmpty() && FVO.size() >= 1) {
			JPanel panel1 = new JPanel();
			panel1.setBackground(new Color(0, 0, 0, 0));
			toolBar_1.add(panel1);
			panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

			JLabel lblNewLabel_4 = new JLabel("");
			lblNewLabel_4.setIcon(new ImageIcon(Amigo_FriendsList.class.getResource("/Amigo_image/friend.png")));
			panel1.add(lblNewLabel_4);
			Component horizontalGlue = Box.createHorizontalStrut(20);
			panel1.add(horizontalGlue);

			JLabel label_1 = new JLabel("");
			label_1.setText(FVO.get(0).getName());
			panel1.add(label_1);
			label_1.setFont(new Font("휴먼매직체", Font.BOLD, 35));
			Component horizontalGlue_1 = Box.createHorizontalStrut(250);
			panel1.add(horizontalGlue_1);

			JButton btnNewButton_1 = new JButton("\uCC44 \uD305");
			btnNewButton_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ocname=label_1.getText();
					Amigo_ID.setOcname(ocname);
					dispose();
					setVisible(false);
					ocname="";
					CnThread cn=new CnThread();
					cn.start();
				}
			});
			panel1.add(btnNewButton_1);
			Component verticalStrut = Box.createVerticalStrut(30);
			verticalStrut.setEnabled(false);
			toolBar_1.add(verticalStrut);
		}

		if (!FVO.isEmpty() && FVO.size() >= 2) {
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(0, 0, 0, 0));
			toolBar_1.add(panel_1);
			panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

			JLabel lblNewLabel_4_1 = new JLabel("");
			lblNewLabel_4_1.setIcon(new ImageIcon(Amigo_FriendsList.class.getResource("/Amigo_image/friend.png")));
			panel_1.add(lblNewLabel_4_1);
			Component horizontalGlue_2 = Box.createHorizontalStrut(20);
			panel_1.add(horizontalGlue_2);

			JLabel label_1_1 = new JLabel("");
			label_1_1.setText(FVO.get(1).getName());
			label_1_1.setFont(new Font("휴먼매직체", Font.BOLD, 35));
			panel_1.add(label_1_1);
			Component horizontalGlue_1_1 = Box.createHorizontalStrut(250);
			panel_1.add(horizontalGlue_1_1);

			JButton btnNewButton_1_1 = new JButton("\uCC44 \uD305");
			btnNewButton_1_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ocname=label_1_1.getText();
					Amigo_ID.setOcname(ocname);
					dispose();
					setVisible(false);
					ocname="";
					CnThread cn=new CnThread();
					cn.start();
				}
			});
			panel_1.add(btnNewButton_1_1);
			Component verticalStrut_1 = Box.createVerticalStrut(30);
			verticalStrut_1.setEnabled(false);
			toolBar_1.add(verticalStrut_1);
		}

		if (!FVO.isEmpty() && FVO.size() >= 3) {
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(0, 0, 0, 0));
			toolBar_1.add(panel_2);
			panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

			JLabel lblNewLabel_4_1_1 = new JLabel("");
			lblNewLabel_4_1_1.setIcon(new ImageIcon(Amigo_FriendsList.class.getResource("/Amigo_image/friend.png")));
			panel_2.add(lblNewLabel_4_1_1);
			Component horizontalGlue_2_1 = Box.createHorizontalStrut(20);
			panel_2.add(horizontalGlue_2_1);

			JLabel label_1_1_1 = new JLabel("");
			label_1_1_1.setText(FVO.get(2).getName());
			label_1_1_1.setFont(new Font("휴먼매직체", Font.BOLD, 35));
			panel_2.add(label_1_1_1);
			Component horizontalGlue_1_2 = Box.createHorizontalStrut(250);
			panel_2.add(horizontalGlue_1_2);

			JButton btnNewButton_1_1_1 = new JButton("\uCC44 \uD305");
			btnNewButton_1_1_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ocname=label_1_1_1.getText();
					Amigo_ID.setOcname(ocname);
					dispose();
					setVisible(false);
					ocname="";
					CnThread cn=new CnThread();
					cn.start();
				}
			});
			panel_2.add(btnNewButton_1_1_1);
			Component verticalStrut_2 = Box.createVerticalStrut(30);
			verticalStrut_2.setEnabled(false);
			toolBar_1.add(verticalStrut_2);
		}

		if (!FVO.isEmpty() && FVO.size() >= 4) {
			JPanel panel_3 = new JPanel();
			panel_3.setBackground(new Color(0, 0, 0, 0));
			toolBar_1.add(panel_3);
			panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

			JLabel lblNewLabel_4_1_2 = new JLabel("");
			lblNewLabel_4_1_2.setIcon(new ImageIcon(Amigo_FriendsList.class.getResource("/Amigo_image/friend.png")));
			panel_3.add(lblNewLabel_4_1_2);
			Component horizontalGlue_2_2 = Box.createHorizontalStrut(20);
			panel_3.add(horizontalGlue_2_2);

			JLabel label_1_1_2 = new JLabel("");
			label_1_1_2.setText(FVO.get(3).getName());
			label_1_1_2.setFont(new Font("휴먼매직체", Font.BOLD, 35));
			panel_3.add(label_1_1_2);
			Component horizontalGlue_1_3 = Box.createHorizontalStrut(250);
			panel_3.add(horizontalGlue_1_3);

			JButton btnNewButton_1_1_2 = new JButton("\uCC44 \uD305");
			btnNewButton_1_1_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ocname=label_1_1_2.getText();
					Amigo_ID.setOcname(ocname);
					dispose();
					setVisible(false);
					ocname="";
					CnThread cn=new CnThread();
					cn.start();
				}
			});
			panel_3.add(btnNewButton_1_1_2);
			Component verticalStrut_3 = Box.createVerticalStrut(30);
			verticalStrut_3.setEnabled(false);
			toolBar_1.add(verticalStrut_3);
		}

		if (!FVO.isEmpty() && FVO.size() >= 5) {
			JPanel panel_4 = new JPanel();
			panel_4.setBackground(new Color(0, 0, 0, 0));
			toolBar_1.add(panel_4);
			panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));

			JLabel lblNewLabel_4_1_3 = new JLabel("");
			lblNewLabel_4_1_3.setIcon(new ImageIcon(Amigo_FriendsList.class.getResource("/Amigo_image/friend.png")));
			panel_4.add(lblNewLabel_4_1_3);
			Component horizontalGlue_2_3 = Box.createHorizontalStrut(20);
			panel_4.add(horizontalGlue_2_3);

			JLabel label_1_1_3 = new JLabel("");
			label_1_1_3.setText(FVO.get(4).getName());
			label_1_1_3.setFont(new Font("휴먼매직체", Font.BOLD, 35));
			panel_4.add(label_1_1_3);
			Component horizontalGlue_1_4 = Box.createHorizontalStrut(250);
			panel_4.add(horizontalGlue_1_4);

			JButton btnNewButton_1_1_3 = new JButton("\uCC44 \uD305");
			btnNewButton_1_1_3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ocname=lblNewLabel_4_1_3.getText();
					Amigo_ID.setOcname(ocname);
					dispose();
					setVisible(false);
					ocname="";
					CnThread cn=new CnThread();
					cn.start();
				}
			});
			panel_4.add(btnNewButton_1_1_3);
			Component verticalStrut_4 = Box.createVerticalStrut(30);
			verticalStrut_4.setEnabled(false);
			toolBar_1.add(verticalStrut_4);
		}

		if (!FVO.isEmpty() && FVO.size() >= 6) {
			JPanel panel_5 = new JPanel();
			panel_5.setBackground(new Color(0, 0, 0, 0));
			toolBar_1.add(panel_5);
			panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));

			JLabel lblNewLabel_4_1_4 = new JLabel("");
			lblNewLabel_4_1_4.setIcon(new ImageIcon(Amigo_FriendsList.class.getResource("/Amigo_image/friend.png")));
			panel_5.add(lblNewLabel_4_1_4);
			Component horizontalGlue_2_4 = Box.createHorizontalStrut(20);
			panel_5.add(horizontalGlue_2_4);

			JLabel label_1_1_4 = new JLabel("");
			label_1_1_4.setText(FVO.get(5).getName());
			label_1_1_4.setFont(new Font("휴먼매직체", Font.BOLD, 35));
			panel_5.add(label_1_1_4);
			Component horizontalGlue_1_5 = Box.createHorizontalStrut(250);
			panel_5.add(horizontalGlue_1_5);

			JButton btnNewButton_1_1_4 = new JButton("\uCC44 \uD305");
			btnNewButton_1_1_4.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ocname=label_1_1_4.getText();
					Amigo_ID.setOcname(ocname);
					dispose();
					setVisible(false);
					ocname="";
					CnThread cn=new CnThread();
					cn.start();
				}
			});
			panel_5.add(btnNewButton_1_1_4);
			Component verticalStrut_5 = Box.createVerticalStrut(30);
			verticalStrut_5.setEnabled(false);
			toolBar_1.add(verticalStrut_5);
		}

		if (!FVO.isEmpty() && FVO.size() >= 7) {
			JPanel panel_6 = new JPanel();
			panel_6.setBackground(new Color(0, 0, 0, 0));
			toolBar_1.add(panel_6);
			panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));

			JLabel lblNewLabel_4_1_3_1 = new JLabel("");
			lblNewLabel_4_1_3_1.setIcon(new ImageIcon(Amigo_FriendsList.class.getResource("/Amigo_image/friend.png")));
			panel_6.add(lblNewLabel_4_1_3_1);
			Component horizontalGlue_2_5 = Box.createHorizontalStrut(20);
			panel_6.add(horizontalGlue_2_5);

			JLabel label_1_1_3_1 = new JLabel("");
			label_1_1_3_1.setText(FVO.get(6).getName());
			label_1_1_3_1.setFont(new Font("휴먼매직체", Font.BOLD, 35));
			panel_6.add(label_1_1_3_1);
			Component horizontalGlue_1_6 = Box.createHorizontalStrut(250);
			panel_6.add(horizontalGlue_1_6);

			JButton btnNewButton_1_1_3_1 = new JButton("\uCC44 \uD305");
			btnNewButton_1_1_3_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ocname=label_1_1_3_1.getText();
					Amigo_ID.setOcname(ocname);
					dispose();
					setVisible(false);
					ocname="";
					CnThread cn=new CnThread();
					cn.start();
				}
			});
			panel_6.add(btnNewButton_1_1_3_1);
			Component verticalStrut_6 = Box.createVerticalStrut(30);
			verticalStrut_6.setEnabled(false);
			toolBar_1.add(verticalStrut_6);
		}
	}

	private ArrayList<FriendsListVO> Find() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		AmigoDAO agodao = new AmigoDAO();
		Amigo_ID id = new Amigo_ID();
		id.getId();
		ArrayList<FriendsListVO> Find = agodao.findFriends();

		return Find;
	}

	public void setMain(Amigo_MainProcess main) {
		// TODO Auto-generated method stub
		this.main = main;
	}
}