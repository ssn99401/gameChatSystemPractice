package Amigo_v1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;

public class Amigo_Loading extends JFrame {

	private JPanel contentPane;
	private Amigo_MainProcess main;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	}

	/**
	 * Create the frame.
	 */
	public Amigo_Loading() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Amigo_Loading.class.getResource("/Amigo_image/logo.png")));
		setTitle("\uC544\uBBF8\uACE0 \uD1A1!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 1000);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(193, 234, 242));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		lodingPanel(panel);
		getContentPane().add(panel);
		setVisible(true);
	}
	
	private void lodingPanel(JPanel panel) {
		// TODO Auto-generated method stub
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(Amigo_Loading.class.getResource("/Amigo_image/logo.png")));
		label.setBounds(0, 219, 584, 269);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\uCE5C\uD55C \uC0AC\uB78C\uACFC \uD558\uB294 \uCC44\uD305\uACFC \uAC8C\uC784 !");
		label_1.setForeground(new Color(0, 191, 255));
		label_1.setFont(new Font("ÈÞ¸Õ¸ÅÁ÷Ã¼", Font.BOLD | Font.ITALIC, 29));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(0, 638, 584, 104);
		contentPane.add(label_1);
		setLocationRelativeTo(null);
	}
	
	public void setMain(Amigo_MainProcess main) {
		// TODO Auto-generated method stub
		this.main=main;
	}
}
