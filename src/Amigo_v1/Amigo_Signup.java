package Amigo_v1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class Amigo_Signup extends JFrame {
   
   private JPanel contentPane;
   private JTextField textField;
   private JTextField textField_1;
   private JTextField textField_2;
   private JTextField textField_3;
   private JTextField textField_4;
   public static int swc=0;
   private JPasswordField passwordField;
   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               Amigo_Signup frame = new Amigo_Signup();
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
   public Amigo_Signup() throws ClassNotFoundException, SQLException {
      AmigoDAO dao = new AmigoDAO();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Amigo_Loading.class.getResource("/Amigo_image/logo.png")));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(45,45,600,1000);
      
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      contentPane.setBackground(new Color(193, 234, 242));
      
      JLabel lblNewLabel = new JLabel("\uD68C\uC6D0\uAC00\uC785");
      lblNewLabel.setFont(new Font("나눔손글씨 펜", Font.PLAIN, 60));
      lblNewLabel.setBounds(101, 37, 281, 57);
      contentPane.add(lblNewLabel);
      
      JLabel lblNewLabel_1 = new JLabel("*ID");
      lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
      lblNewLabel_1.setFont(new Font("나눔바른펜", Font.PLAIN, 25));
      lblNewLabel_1.setBounds(49, 170, 109, 48);
      contentPane.add(lblNewLabel_1);
      
      JLabel lblNewLabel_2 = new JLabel("*PW");
      lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
      lblNewLabel_2.setBounds(49, 261, 109, 57);
      lblNewLabel_2.setFont(new Font("나눔바른펜", Font.PLAIN, 25));
      contentPane.add(lblNewLabel_2);
      
      JLabel lblNewLabel_3 = new JLabel("*\uC774\uB984");
      lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
      
      lblNewLabel_3.setBounds(49, 367, 109, 43);
      lblNewLabel_3.setFont(new Font("나눔바른펜", Font.PLAIN, 25));
      contentPane.add(lblNewLabel_3);
      
      JLabel lblNewLabel_4 = new JLabel("\uBCC4\uBA85");
      lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
      lblNewLabel_4.setBounds(49, 465, 109, 48);
      lblNewLabel_4.setFont(new Font("나눔바른펜", Font.PLAIN, 25));
      contentPane.add(lblNewLabel_4);
      
      JLabel lblNewLabel_5 = new JLabel("");
      lblNewLabel_5.setBounds(187, 224, 215, 23);
      lblNewLabel_5.setFont(new Font("나눔바른펜", Font.PLAIN, 15));
      contentPane.add(lblNewLabel_5);
      
      textField = new JTextField();
      textField.setBounds(198, 183, 215, 31);
      contentPane.add(textField);
      textField.setColumns(10);
      
      textField_2 = new JTextField();
      textField_2.setColumns(10);
      textField_2.setBounds(198, 377, 215, 31);
      contentPane.add(textField_2);
      
      textField_3 = new JTextField();
      textField_3.setColumns(10);
      textField_3.setBounds(198, 478, 215, 31);
      contentPane.add(textField_3);
      
      textField_4 = new JTextField();
      textField_4.setColumns(10);
      textField_4.setBounds(198, 573, 215, 31);
      contentPane.add(textField_4);
      
      RoundedButton btnNewButton = new RoundedButton("\uB4A4\uB85C");
      btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               dispose();
               setVisible(false);
               new Amigo_Login().setVisible(true);
            } catch (ClassNotFoundException | SQLException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
         }
      });
      btnNewButton.setFont(new Font("나눔손글씨 펜", Font.PLAIN, 22));
      btnNewButton.setBounds(101, 699, 109, 31);
      contentPane.add(btnNewButton);
      
      
      RoundedButton btnNewButton_2 = new RoundedButton("\uC911\uBCF5\uD655\uC778");
      btnNewButton_2.setFont(new Font("나눔손글씨 펜", Font.PLAIN, 20));
      btnNewButton_2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            
         }
      });
      btnNewButton_2.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {

            String sid = textField.getText();

            boolean ck = dao.checkId(sid);
            if (sid.length() == 0) {
               lblNewLabel_5.setText("아이디를 입력해주세요.");
               lblNewLabel_5.setForeground(Color.red);
               swc = 0;
            }else {
               if (ck) {
                  lblNewLabel_5.setText("이 아이디는 사용가능합니다.");
                  lblNewLabel_5.setForeground(new Color(0, 102, 0));
                  swc = 1;

               } else {
                  lblNewLabel_5.setText("이 아이디는 사용할 수 없습니다.");
                  lblNewLabel_5.setForeground(Color.red);
                  swc = 0;
               }
            }

         }
      });
      btnNewButton_2.setBounds(442, 183, 97, 31);
      contentPane.add(btnNewButton_2);
      
      RoundedButton2 btnNewButton_1 = new RoundedButton2("\uAC00\uC785\uD558\uAE30");
      btnNewButton_1.setFont(new Font("나눔손글씨 펜", Font.PLAIN, 25));
      btnNewButton_1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            
            if (e.getSource() == btnNewButton_1) {
               String sid = textField.getText();
               String spw = textField_1.getText();
               String sname = textField_2.getText();
               String stel = textField_3.getText();
               String snick = textField_4.getText();
               boolean signck1 = false;
               if (snick.length() == 0) {//닉네임빈칸시
                  signck1 = dao.signup(sid, spw, sname, stel);
                  
                  System.out.println("닉네임x");
               } else if (snick.length() != 0) {// 닉네임있으면
                  signck1 = dao.signup(sid, spw, sname, stel, snick);

                  System.out.println("닉네임o");
               }
               System.out.println(signck1);
               if (swc == 0) {// 중복체크 안되어있으면
                  JOptionPane.showMessageDialog(null, "id중복체크를 완료 해주세요");
               } else if (swc == 1) {

                  if (signck1) {// 회원가입이 정상처리 되면
                     System.out.println(signck1);

                     JOptionPane.showMessageDialog(null, "회원가입을 완료하였습니다. \n id는 " + sid + " 입니다.");
                     swc = 0;
                     dispose();
                     setVisible(false);
                     try {
                        new Amigo_Login().setVisible(true);
                     } catch (ClassNotFoundException | SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                     }
                  } else {// 회원가입 오류시
                     JOptionPane.showMessageDialog(null, "pw는 최소 8글자, 최대 20글자로\n 영어,숫자를 조합하여야 합니다.");
                     System.out.println(4);
                  }
               } // swc if --end
            }

         }
      });

      btnNewButton_1.setBounds(243, 693, 176, 43);

      contentPane.add(btnNewButton_1);

      JLabel label = new JLabel("*\uC804\uD654\uBC88\uD638");
      label.setHorizontalAlignment(SwingConstants.CENTER);
      label.setFont(new Font("나눔바른펜", Font.PLAIN, 25));
      label.setBounds(37, 560, 149, 48);
      contentPane.add(label);
      
      JLabel label_1 = new JLabel("* \uB294 \uD544\uC218\uC785\uB825 \uD56D\uBAA9\uC785\uB2C8\uB2E4");
      label_1.setHorizontalAlignment(SwingConstants.LEFT);
      label_1.setFont(new Font("나눔스퀘어", Font.ITALIC, 15));
      label_1.setBounds(72, 129, 176, 31);
      contentPane.add(label_1);
      
      textField_1 = new JPasswordField();
      textField_1.setBounds(198, 277, 215, 31);
      contentPane.add(textField_1);
      
      setLocationRelativeTo(null);
      
   }

}