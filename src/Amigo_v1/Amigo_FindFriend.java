package Amigo_v1;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Amigo_FindFriend extends JFrame {

   private JPanel contentPane;
   private JTextField textField;
   private JTable table;
   String PlusID="";

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               Amigo_FindFriend frame = new Amigo_FindFriend();
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
   public Amigo_FindFriend() throws ClassNotFoundException, SQLException {

      AmigoDAO dao = new AmigoDAO();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Amigo_Loading.class.getResource("/Amigo_image/logo.png")));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(600, 45, 600, 1000);
      contentPane = new JPanel();
      contentPane.setBackground(new Color(193, 234, 242));
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);

      JLabel lblNewLabel = new JLabel("\uCE5C\uAD6C\uCC3E\uAE30");
      lblNewLabel.setBounds(12, 77, 205, 65);
      lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
      lblNewLabel.setFont(new Font("굴림", Font.BOLD, 27));
      contentPane.add(lblNewLabel);

      JToolBar toolBar = new JToolBar();
      toolBar.setFloatable(false);
      toolBar.setBounds(89, 223, 367, 49);
      contentPane.add(toolBar);

      textField = new JTextField();
      toolBar.add(textField);
      textField.setColumns(10);

      JButton btnNewButton_1 = new JButton("\uB4A4\uB85C\uAC00\uAE30");
      btnNewButton_1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         }
      });
      btnNewButton_1.addMouseListener(new MouseAdapter() {
         //친구 목록으로 가기
         @Override
         public void mouseClicked(MouseEvent e) {
            if(e.getSource() == btnNewButton_1) {
               try {
                  dispose();
                  //밑을 친구 목록창으로 변경하면 됨
                  new Amigo_FriendsList().setVisible(true);
               } catch (ClassNotFoundException | SQLException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               }
            }
         }
      });
      btnNewButton_1.setBounds(214, 841, 154, 58);
      contentPane.add(btnNewButton_1);

      //JTable 생성

      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setBounds(42, 312, 483, 154);
      contentPane.add(scrollPane);


      String [] field = {"아이디","이름"};
      Object row[]= new Object[2];
      DefaultTableModel demoTable = new DefaultTableModel(field,0) {
         public boolean isCellEditable(int rowindex,int mCollindex) {
            return false;
         }
      };
      table = new JTable(demoTable);
      scrollPane.setViewportView(table);

      JButton btnNewButton = new JButton("");
      btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         }
      });
      btnNewButton.setIcon(new ImageIcon(Amigo_FindFriend.class.getResource("/Amigo_image/검색 버튼.png")));
      btnNewButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            //검색에서 마우스 클릭 액션
            String id = "";
            if(e.getSource() == btnNewButton) {
               demoTable.setNumRows(0);
               try {
                  id = textField.getText();
                  ArrayList<FindAmigoVO> list = dao.searchID(id);
                  if(!list.isEmpty()) {
                     for (int i = 0; i < list.size(); i++) {
                        FindAmigoVO favo = list.get(i);
                        row[0] = favo.getId();
                        PlusID=favo.getId();
                        row[1] = favo.getName();
                        demoTable.addRow(row);
                     }
                  } else 
                     JOptionPane.showMessageDialog(null, "찾고싶은 ID를 입력해주세요");
               }catch(Exception e1){

               }


            }

         }
      });

      toolBar.add(btnNewButton);

      JButton btnNewButton_2 = new JButton("\uCE5C\uAD6C \uCD94\uAC00");
      //마우스 클릭 부분 - 친구 추가하기 버튼
      btnNewButton_2.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            if(e.getSource() == btnNewButton_2) {
               try {
               AmigoDAO agodao=new AmigoDAO();
               System.out.println(PlusID);
               agodao.PlusFriend(PlusID);
               JOptionPane.showMessageDialog(null, "추가 되었습니다~!");
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
      btnNewButton_2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         }
      });
      btnNewButton_2.setBounds(383, 491, 118, 37);
      contentPane.add(btnNewButton_2);
   }
}