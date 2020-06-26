package Amigo_v1;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Amigo_Logout extends JFrame {

   private JPanel contentPane;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               Amigo_Logout frame = new Amigo_Logout();
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
   public Amigo_Logout() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Amigo_Loading.class.getResource("/Amigo_image/logo.png")));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(600, 100, 600, 1000);
      contentPane = new JPanel();
      contentPane.setBackground(new Color(193, 234, 242));
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);

      JLabel lblNewLabel = new JLabel("\uB85C\uADF8\uC544\uC6C3");
      lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
      lblNewLabel.setFont(new Font("굴림", Font.BOLD, 32));
      lblNewLabel.setBounds(31, 48, 218, 71);
      contentPane.add(lblNewLabel);

      JLabel lblNewLabel_1 = new JLabel("\uC774\uC6A9\uD574 \uC8FC\uC154\uC11C");
      lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
      lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 37));
      lblNewLabel_1.setBounds(65, 287, 441, 91);
      contentPane.add(lblNewLabel_1);

      JLabel lblNewLabel_2 = new JLabel("  \uAC10\uC0AC\uD569\uB2C8\uB2E4^^");
      lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 37));
      lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
      lblNewLabel_2.setBounds(65, 374, 441, 91);
      contentPane.add(lblNewLabel_2);

      JButton btnNewButton = new JButton("\uC885\uB8CC");
      //종료 버튼 - 마우스 클릭 기능
      btnNewButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent arg0) {
            if(arg0.getSource() == btnNewButton) {
               dispose();
               setVisible(false);
               System.exit(0);
            }
         }
      });
      btnNewButton.setFont(new Font("굴림", Font.BOLD, 24));
      btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         }
      });
      btnNewButton.setBounds(65, 700, 179, 71);
      contentPane.add(btnNewButton);

      JButton btnNewButton_1 = new JButton("\uB85C\uADF8\uC778");
      //로그인 창 - 마우스 클릭 기능
      btnNewButton_1.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            Amigo_MainProcess main = new Amigo_MainProcess();
            if(e.getSource() == btnNewButton_1) {
               try {
                  dispose();
                  setVisible(false);
                  new Amigo_Login().setVisible(true);
               } catch (ClassNotFoundException | SQLException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               }
            }

         }
      });
      btnNewButton_1.setFont(new Font("굴림", Font.BOLD, 24));
      btnNewButton_1.setBounds(327, 700, 179, 71);
      contentPane.add(btnNewButton_1);
   }
}