package Amigo_v1;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class te {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Amigo_ID aid = new Amigo_ID();
		AmigoDAO dao = new AmigoDAO();
		AmigoQuiz q = new AmigoQuiz();
		dao.createQuiz();
		Date d1= new Date();
		
		Timer tmr = new Timer();
		Timer tmr1= new Timer();
		Timer tmr2= new Timer();
		TimerTask ttask = new TimerTask() {
			int a = 0;
			@Override

			public void run() {
			
				System.out.println("�������1 " + a);
				
				a++;

			}
		};
		TimerTask ttask1 = new TimerTask() {
			int a = 0;
			@Override

			public void run() {
				
				System.out.println("�������2 " + a);
				a++;
				

			}
		};
		TimerTask ttask2 = new TimerTask() {
			int a = 0;
			@Override

			public void run() {
			
				
				System.out.println("�������3 " + a);
				a++;
				if(a==10) {
					tmr.cancel();
				}
				

			}
		};
		tmr.schedule(ttask, 1000,1000);//1�ʵ� 1�ʰ���
		tmr1.schedule(ttask1, 2000,1000);
		tmr2.schedule(ttask2, 3000,1000);

	

		// int sec=60-d1.getSeconds();//60-����ð���(0~60)=60/59.....55 //5�ʳ���� ������ //
	
	
	
	/*String[] bae1 = new String[3];
				
		String[] bae =dao.Pquiz; //�������� ���
		System.out.println(bae[0]);//������ȣ
		System.out.println(bae[1]);//����
		System.out.println(bae[2]);//��
		System.out.println(bae[3]);//��Ʈ
		*/
		/*for (int i = 0; i < 3; i++) {// ���� 3�� �̾ƺ���?
			String[] bae = dao.bae01();// ���� �Ѱ� �̰�
			if (bae[1] != bae1[1]) {// ������ ����������
				System.out.println(bae[1]);// �������
				bae = bae1;
			} else if (bae[1] == bae1[1]) {// ������ �ٽû̱�
				i--;
			}
		}*/
		
		/*//���� �˻� ����//���� ������ �ܾ� �����ϸ�  true��ȯ
		Scanner sc1 =new Scanner(System.in);
		
		String chat = sc1.next().replaceAll(" ", "").replaceAll(",", "");
		System.out.println(bae[2]);
		if(bae[2].contains(chat)) {
			System.out.println("����");
		}else {
			System.out.println("��!!");
		}*/
		

	}
}
