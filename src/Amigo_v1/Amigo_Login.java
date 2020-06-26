package Amigo_v1;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;

public class Amigo_Login extends JFrame {

   private JPanel contentPane;
   private JTextField textField;
   private JTextField textField_1;
   private JPasswordField passwordField;

   /**
    * Launch the application.
    * 
    * @throws SQLException
    * @throws ClassNotFoundException
    */
   public static void main(String[] args) throws ClassNotFoundException, SQLException {


      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               Amigo_Login frame = new Amigo_Login();
               frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the frame.
    * @throws SQLException 
    * @throws ClassNotFoundException 
    */
   public Amigo_Login() throws ClassNotFoundException, SQLException {


		setIconImage(Toolkit.getDefaultToolkit().getImage(Amigo_Loading.class.getResource("/Amigo_image/logo.png")));
      AmigoDAO dao = new AmigoDAO();
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(45, 45, 600, 1000);
      contentPane = new JPanel();
      contentPane.setBackground(new Color(193, 234, 242));
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);

      textField = new JTextField();
      textField.setBounds(153, 270, 274, 30);
      contentPane.add(textField);
      textField.setColumns(10);

      RoundedButton2 btnNewButton = new RoundedButton2("LOGIN");
      btnNewButton.addActionListener(new ActionListener() {
         int trylg = 0;

         public void actionPerformed(ActionEvent e) {

            String ckid = null;
            String id = null;
            String pw = null;
            String myname=null;

            if (e.getSource() == btnNewButton)// 로그인 버튼을 누르면
            {

               try {

                  id = textField.getText();
                  pw = textField_1.getText();

                  ckid = dao.Login(id, pw);
               } catch (SQLException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               }

               if (id.length() == 0 || pw.length() == 0) {
                  JOptionPane.showMessageDialog(null, "id나 비밀번호를 입력해주세요.");
               } else {
                  if (ckid != null) {
                     try {
                    	Amigo_ID.setMyname(myname);
                        JOptionPane.showMessageDialog(null, "로그인을 성공하였습니다");
                        Amigo_ID.setId(id);
                        System.out.println(id);
                        dispose();
                        setVisible(false);

                        new Amigo_FriendsList().setVisible(true);
                     } catch (ClassNotFoundException | SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                     }
                  } else {

                     trylg++;
                     JOptionPane.showMessageDialog(null, "아이디나 비밀번호가 잘못되셨습니다.\n(" + trylg + "회 시도)", "WRONG",
                           JOptionPane.ERROR_MESSAGE);

                     if (trylg >= 5) {
                        JOptionPane.showMessageDialog(null,
                              "id,비밀번호를 5회이상 틀리셨습니다. \n기타 문의사항은 관리자 번호(010-xxxx-xxxx)로 연락주세요");
                        dispose();

                     }

                  } // if --end

               } // event if -- end
            } // event--end
         }
      });
      btnNewButton.setBounds(216, 458, 129, 42);

      btnNewButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 20));
      contentPane.add(btnNewButton);

      JLabel lblNewLabel = new JLabel("LOGIN");
      lblNewLabel.setForeground(Color.DARK_GRAY);
      lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
      lblNewLabel.setFont(new Font("나눔스퀘어 Bold", Font.BOLD, 60));
      lblNewLabel.setBounds(38, 129, 252, 85);
      contentPane.add(lblNewLabel);

      JLabel lblNewLabel_1 = new JLabel("ID");
      lblNewLabel_1.setForeground(Color.DARK_GRAY);
      lblNewLabel_1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 35));
      lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
      lblNewLabel_1.setBounds(38, 258, 103, 42);
      contentPane.add(lblNewLabel_1);

      JLabel lblNewLabel_2 = new JLabel("PW");
      lblNewLabel_2.setForeground(Color.DARK_GRAY);
      lblNewLabel_2.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 35));
      lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
      lblNewLabel_2.setBounds(12, 308, 156, 100);
      contentPane.add(lblNewLabel_2);

      JLabel lblNewLabel_3 = new JLabel("\uC544\uBBF8\uACE0\uAC00 \uCC98\uC74C\uC774\uC2E0\uAC00\uC694?");
      lblNewLabel_3.setForeground(Color.DARK_GRAY);
      lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
      lblNewLabel_3.setFont(new Font("나눔고딕", Font.BOLD, 24));
      lblNewLabel_3.setBounds(114, 546, 335, 37);
      contentPane.add(lblNewLabel_3);

      RoundedButton btnNewButton_1 = new RoundedButton("\uD68C\uC6D0\uAC00\uC785");

      btnNewButton_1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnNewButton_1) {
               dispose();
               setVisible(false);
               try {
                  new Amigo_Signup().setVisible(true);
               } catch (ClassNotFoundException | SQLException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               }
            }
         }
      });

      btnNewButton_1.setBounds(184, 627, 194, 42);
      btnNewButton_1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 20));
      contentPane.add(btnNewButton_1);
      
      textField_1 = new JPasswordField();
      //엔터시 로그인 되는 기능
      textField_1.addActionListener(new ActionListener() {
         int trylg = 0;
         public void actionPerformed(ActionEvent e) {
            String ckid = null;
            String id = null;
            String pw = null;
            String myname=null;

            if (e.getSource() == textField_1)// 로그인 버튼을 누르면
            {

               try {

                  id = textField.getText();
                  pw = textField_1.getText();

                  ckid = dao.Login(id, pw);
               } catch (SQLException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               }

               if (id.length() == 0 || pw.length() == 0) {
                  JOptionPane.showMessageDialog(null, "id나 비밀번호를 입력해주세요.");
               } else {
                  if (ckid != null) {
                     try {
                    	Amigo_ID.setMyname(myname);
                        JOptionPane.showMessageDialog(null, "로그인을 성공하였습니다");
                        Amigo_ID.setId(id);
                        System.out.println(id);
                        dispose();
                        setVisible(false);

                        new Amigo_FriendsList().setVisible(true);
                     } catch (ClassNotFoundException | SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                     }
                  } else {

                     trylg++;
                     JOptionPane.showMessageDialog(null, "아이디나 비밀번호가 잘못되셨습니다.\n(" + trylg + "회 시도)", "WRONG",
                           JOptionPane.ERROR_MESSAGE);

                     if (trylg >= 5) {
                        JOptionPane.showMessageDialog(null,
                              "id,비밀번호를 5회이상 틀리셨습니다. \n기타 문의사항은 관리자 번호(010-xxxx-xxxx)로 연락주세요");
                        dispose();

                     }

                  } // if --end

               } // event if -- end
            } // event--end
         }
      });
      textField_1.setBounds(153, 343, 274, 30);
      contentPane.add(textField_1);

      setLocationRelativeTo(null);
   }
}