package Amigo_v1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.Font;

public class Quiz extends JFrame implements Runnable{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Quiz frame = new Quiz();
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
	public Quiz() throws ClassNotFoundException, SQLException {
		Amigo_ID aid = new Amigo_ID();
		AmigoDAO dao = new AmigoDAO();
		AmigoQuiz q = new AmigoQuiz();
		dao.createQuiz();
		String [] bae01 =dao.getPquiz();
		
		setUndecorated(true);
		setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255,255,204));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("³ª´®½ºÄù¾î_ac", Font.PLAIN, 15));
		textArea.setBounds(12, 25, 276, 101);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		contentPane.add(textArea);
		textArea.setText(bae01[1]);// ¹®Á¦
		

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setFont(new Font("³ª´®½ºÄù¾î_ac Bold", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 173, 300, 27);
		lblNewLabel_1.setText(bae01[2]);// Á¤´ä
		lblNewLabel_1.setVisible(false);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(12, 136, 276, 27);
		lblNewLabel_2.setText(bae01[3]);//ÈùÆ®
		lblNewLabel_2.setVisible(false);
		contentPane.add(lblNewLabel_2);
		setAlwaysOnTop(true);
		setLocation(700, 150);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
