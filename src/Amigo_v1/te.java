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
			
				System.out.println("문제출력1 " + a);
				
				a++;

			}
		};
		TimerTask ttask1 = new TimerTask() {
			int a = 0;
			@Override

			public void run() {
				
				System.out.println("문제출력2 " + a);
				a++;
				

			}
		};
		TimerTask ttask2 = new TimerTask() {
			int a = 0;
			@Override

			public void run() {
			
				
				System.out.println("문제출력3 " + a);
				a++;
				if(a==10) {
					tmr.cancel();
				}
				

			}
		};
		tmr.schedule(ttask, 1000,1000);//1초뒤 1초간격
		tmr1.schedule(ttask1, 2000,1000);
		tmr2.schedule(ttask2, 3000,1000);

	

		// int sec=60-d1.getSeconds();//60-현재시간초(0~60)=60/59.....55 //5초남기고 싶으면 //
	
	
	
	/*String[] bae1 = new String[3];
				
		String[] bae =dao.Pquiz; //뽑은문제 출력
		System.out.println(bae[0]);//문제번호
		System.out.println(bae[1]);//문제
		System.out.println(bae[2]);//답
		System.out.println(bae[3]);//힌트
		*/
		/*for (int i = 0; i < 3; i++) {// 문제 3개 뽑아볼까?
			String[] bae = dao.bae01();// 문제 한개 뽑고
			if (bae[1] != bae1[1]) {// 문제가 같지않으면
				System.out.println(bae[1]);// 문제출력
				bae = bae1;
			} else if (bae[1] == bae1[1]) {// 같으면 다시뽑기
				i--;
			}
		}*/
		
		/*//정답 검색 로직//말한 문장중 단어 포함하면  true반환
		Scanner sc1 =new Scanner(System.in);
		
		String chat = sc1.next().replaceAll(" ", "").replaceAll(",", "");
		System.out.println(bae[2]);
		if(bae[2].contains(chat)) {
			System.out.println("정답");
		}else {
			System.out.println("땡!!");
		}*/
		

	}
}
