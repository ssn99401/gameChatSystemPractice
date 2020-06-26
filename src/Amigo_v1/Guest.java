package Amigo_v1;

import java.io.*;

import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

class Guest extends Thread {
 Timer t1=new Timer();
	
	
	String id;// args0 번방 이름

	Server server;

	Socket socket;

	BufferedReader br;

	BufferedWriter bw;

	String name;

	String you;

	Amigo_OneChat agoOC;

	Guest(Server server, Socket socket) throws Exception {

		this.server = server;

		this.socket = socket;

		InputStream is = socket.getInputStream();

		InputStreamReader isr = new InputStreamReader(is);

		br = new BufferedReader(isr);

		OutputStream os = socket.getOutputStream();

		OutputStreamWriter osw = new OutputStreamWriter(os);

		bw = new BufferedWriter(osw);

	}

	public void run() {

		try {
			AmigoDAO dao = new AmigoDAO();
			
			while (true) {

				String line = br.readLine();
				
				System.out.println(line + "읽음");
				
				String array[] = line.split("/");
			/*	String chat = array[3].replaceAll(" ", "");*/
				
				switch (array[0]) {

				case "enter":

					id = array[1];

					server.makeGuestlist();

					server.broadcast(line);

					server.makeRoomlist();

					break;

				case "info":

					server.broadcastRoom(array[2], "msg/" + "[INFO]" + "/" + "게임 모드가 아미고 퀴즈 모드로 설정되었습니다.");
				
					break;
					
				case "info1":

					server.broadcastRoom(array[2], "msg/" + "[INFO]" + "/" + "게임 모드가 초성 게임 모드로 설정되었습니다.");
					
					break;
					
				case "info2":

					server.broadcastRoom(array[2], "msg/" + "[INFO]" + "/" + "게임 모드가 초기화 되었습니다.");
					
					break;
					
				case "ques1":
					
					
					server.broadcastRoom(array[2], "que/" + "[INFO]" + "/" + "5초뒤에 문제가 출력됩니다. ");
					server.broadcastRoom(array[2], "time/" + id + "/");
					/*Timer tmr= new Timer();
					TimerTask ttask = new TimerTask() {

						@Override
						public void run() {
							int a = 0;
							System.out.println("5초기다리는중");
							System.out.println(a);

							if (a == 5) {
								System.out.println("5초가 지났습니다.");
								tmr.cancel();
								
							}else {
							a++;
							}
						}
					};
					tmr.scheduleAtFixedRate(ttask, d1, 1000);*/
				
					
					break;
					
				case "que":
					
					server.broadcastRoom(array[2], "que/" + "AMIGO"+ "/" + array[3]);
					
					break;
				
				case "que2":
					
					server.broadcastRoom(array[2], "que/" + id+ "/" + array[3]);
					
					break;
					
				case "queprint":
					
					
					dao.createQuiz();
					
					server.broadcastRoom(array[2], "queprint1/" + "AMIGO"+ "/"+dao.getPquiz()[1]);
					server.broadcastRoom(array[2], "queprint2/" + "AMIGO"+ "/"+dao.getPquiz()[2].replaceAll(" ", ""));
					server.broadcastRoom(array[2], "queprint3/" + "AMIGO"+ "/"+dao.getPquiz()[3]);
					/*dao.createQuiz();
					
					server.broadcastRoom(array[2],
							"que/" + "AMIGO" + "/" + dao.getPquiz()[1].replaceFirst("문제", "\t\t\t           Q."));
					*/
					break;
				case "csprint":
					
					
					dao.csQuiz();
					
					server.broadcastRoom(array[2], "csprint1/" + "AMIGO"+ "/"+dao.getCsQuiz()[0]);
					server.broadcastRoom(array[2], "csprint2/" + "AMIGO"+ "/"+dao.getCsQuiz()[1]);
					server.broadcastRoom(array[2], "csprint3/" + "AMIGO"+ "/"+dao.getCsQuiz()[2]);
					/*dao.createQuiz();
					
					server.broadcastRoom(array[2],
							"que/" + "AMIGO" + "/" + dao.getPquiz()[1].replaceFirst("문제", "\t\t\t           Q."));
					*/
					break;
				
					

				case "msgsearch":// 정답검색

					System.out.println("서치시작");
					try {
						server.broadcastRoom(array[2], "queans1/" + id + "/" + array[3]);
						/*if (!array[3].isEmpty() && !dao.getPquiz()[2].isEmpty()) {
							if (array[3].equals(dao.getPquiz()[2])) {
								// server.broadcastRoom(array[2], "msg/" + id + "/" + array[3]);
								server.broadcastRoom(array[2], "queans/" + id + "/" + " 님 정답!! +50점");
								server.broadcastRoom(array[2], "que/" + "AMIGO" + "/" + "3초 뒤에 다음문제가 나갑니다.");
								TimerTask tt2 = new TimerTask() {

									@Override
									public void run() {
										try {
											server.broadcastRoom(array[2], "clear/" + id + "/");
											t1.cancel();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								};
								t1.scheduleAtFixedRate(tt2, 3000, 1);
								
								
								//server.broadcastRoom(array[2], "buttun/"+ id + "/");
							
								// server.broadcastRoom(array[2], "ques1" + id + "/" + array[3]);
								// waitingRoom.sendMsg("ques1/"+"[AMIGO_QUIZ]" + "/" + rn );
								break;
							} else {
								// server.broadcastRoom(array[2], "msg/" + id + "/" + array[3]);

								System.out.println("틀렸어");
							}
						} else {

						}*/
					} catch (Exception e3) {
					} 
					break;
					
				case "msgsearch1":// 정답검색

					System.out.println("서치시작");
					server.broadcastRoom(array[2], "queans1/" + id + "/" + array[3]);
					
						
					break;
					
				case "ansqueprint":// 정답검색

					System.out.println("서치시작");
					server.broadcastRoom(array[2], "ansqueprint/" + id + "/");
					
						
					break;
					
				case "clear":// 정답검색

				
					server.broadcastRoom(array[2], "clear/" + id + "/");
					
						
					break;
				case "csSearch": // 초성게임 정답검색
					System.out.println("서치 시작");
					try {
						if (!array[3].isEmpty() && !dao.getCsQuiz()[2].isEmpty()) {
							if (array[3].equals(dao.getCsQuiz()[2])) {
//								server.broadcastRoom(array[2], "msg/" + id + "/" + array[3]);
								server.broadcastRoom(array[2], "queans/" + id + "/" + " 님 정답!!");
								server.broadcastRoom(array[2], "que/" + "AMIGO" + "/" + "3초 뒤에 다음문제가 나갑니다.");
								TimerTask tt3 = new TimerTask() {

									@Override
									public void run() {
										try {
											server.broadcastRoom(array[2], "clear/" + id + "/");
											t1.cancel();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								};
								t1.scheduleAtFixedRate(tt3, 3000, 1);
								break;
							} else {
								// server.broadcastRoom(array[2], "msg/" + id + "/" + array[3]);

								System.out.println("틀렸어");
							}
						} else {

						}
					} catch (Exception e3) {
					}
					;
				case "plusS": // 정답 맞춘 사람 점수 추가
					System.out.println("점수 추가");
					server.broadcastRoom(array[2], "queans/" + id + "/" + " 님 Score 50점 추가!!");
					break;
					
				case "infoans":

					server.broadcastRoom(array[2], "msg/" + id + "/"+ array[3]);
					
					break;
						
				
				case "msg":

					server.broadcastRoom(array[2], "msg/" + id + "/" + array[3]);
					
					break;

				case "exit":

//for(int i=0; i<server.list.size(); i++){

//if(server.list.get(i).id.equals(array[1]))

// server.removeGuest(server.list.get(i));

//}

					server.removeGuest(this);

					String str2 = "exit/" + array[1];

					server.broadcast(str2);

					break;

				case "귓속말":

					String[] talk = array[1].split("&");

					server.talkMsg(talk[0], talk[1], talk[2]);

					break;

//talk[0] 보낸놈

//talk[1] 받는놈

//talk[2] 할말

				case "mkroom":

//구민정(방장);

					server.addRoom(array[1], this);

					server.removeGuest(this);

					server.makeGuestlist();

					server.makeGuestlistRoom(array[1]);

					server.makeRoomlist();

					break;

				case "roomjoin":
					
					server.removeGuest(this); // 대기실에 자기 제외

					server.makeGuestlist(); // 다시 대기실 목록 뿌림

					server.addGuestRoom(array[2], this); // 그 방에 자기를 넣음

					server.makeGuestlistRoom(array[2]); // 방에 참여한 접속자리스트 생성

					server.makeRoomlist();
					server.broadcastRoom(array[2], "msg/" + id + "/" + "님이 입장하셨습니다");

					break;

				case "roomout":
					
					server.removeGuestRoom(array[2], this);

					server.removeRoom(array[2]);

					server.makeGuestlist();

					server.makeRoomlist();
					server.broadcastRoom(array[2], "msg/" + id + "/" + "님이 퇴장하셨습니다.");
					break;
					
				case "removeG":
					server.removeGuest(this);
					interrupt();
					break;
					
				case "OCNAME":
					//server.check(this);
					System.out.println(array[1]+" 옴");
					server.addOneChat(this);
					id = array[1];
					server.makeOneChatRoom(id);
					
					break;
										
				case "ONE":
					System.out.println(array[1]+", "+array[2]+" 옴");
					server.routine(array[1], array[2]);
					break;
				}

			}

		} catch (Exception e) {

// e.printStackTrace();

//server.removeGuest(this);

			try {

			} catch (Exception e1) {

// TODO Auto-generated catch block

				e1.printStackTrace();

			}

		}

	}
//g.sendmsg(msg)
	public void sendMsg(String msg) throws Exception {

		bw.write(msg + "\n");

		bw.flush();

	}
	

 }

