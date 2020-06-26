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
	
	
	String id;// args0 ���� �̸�

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
				
				System.out.println(line + "����");
				
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

					server.broadcastRoom(array[2], "msg/" + "[INFO]" + "/" + "���� ��尡 �ƹ̰� ���� ���� �����Ǿ����ϴ�.");
				
					break;
					
				case "info1":

					server.broadcastRoom(array[2], "msg/" + "[INFO]" + "/" + "���� ��尡 �ʼ� ���� ���� �����Ǿ����ϴ�.");
					
					break;
					
				case "info2":

					server.broadcastRoom(array[2], "msg/" + "[INFO]" + "/" + "���� ��尡 �ʱ�ȭ �Ǿ����ϴ�.");
					
					break;
					
				case "ques1":
					
					
					server.broadcastRoom(array[2], "que/" + "[INFO]" + "/" + "5�ʵڿ� ������ ��µ˴ϴ�. ");
					server.broadcastRoom(array[2], "time/" + id + "/");
					/*Timer tmr= new Timer();
					TimerTask ttask = new TimerTask() {

						@Override
						public void run() {
							int a = 0;
							System.out.println("5�ʱ�ٸ�����");
							System.out.println(a);

							if (a == 5) {
								System.out.println("5�ʰ� �������ϴ�.");
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
							"que/" + "AMIGO" + "/" + dao.getPquiz()[1].replaceFirst("����", "\t\t\t           Q."));
					*/
					break;
				case "csprint":
					
					
					dao.csQuiz();
					
					server.broadcastRoom(array[2], "csprint1/" + "AMIGO"+ "/"+dao.getCsQuiz()[0]);
					server.broadcastRoom(array[2], "csprint2/" + "AMIGO"+ "/"+dao.getCsQuiz()[1]);
					server.broadcastRoom(array[2], "csprint3/" + "AMIGO"+ "/"+dao.getCsQuiz()[2]);
					/*dao.createQuiz();
					
					server.broadcastRoom(array[2],
							"que/" + "AMIGO" + "/" + dao.getPquiz()[1].replaceFirst("����", "\t\t\t           Q."));
					*/
					break;
				
					

				case "msgsearch":// ����˻�

					System.out.println("��ġ����");
					try {
						server.broadcastRoom(array[2], "queans1/" + id + "/" + array[3]);
						/*if (!array[3].isEmpty() && !dao.getPquiz()[2].isEmpty()) {
							if (array[3].equals(dao.getPquiz()[2])) {
								// server.broadcastRoom(array[2], "msg/" + id + "/" + array[3]);
								server.broadcastRoom(array[2], "queans/" + id + "/" + " �� ����!! +50��");
								server.broadcastRoom(array[2], "que/" + "AMIGO" + "/" + "3�� �ڿ� ���������� �����ϴ�.");
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

								System.out.println("Ʋ�Ⱦ�");
							}
						} else {

						}*/
					} catch (Exception e3) {
					} 
					break;
					
				case "msgsearch1":// ����˻�

					System.out.println("��ġ����");
					server.broadcastRoom(array[2], "queans1/" + id + "/" + array[3]);
					
						
					break;
					
				case "ansqueprint":// ����˻�

					System.out.println("��ġ����");
					server.broadcastRoom(array[2], "ansqueprint/" + id + "/");
					
						
					break;
					
				case "clear":// ����˻�

				
					server.broadcastRoom(array[2], "clear/" + id + "/");
					
						
					break;
				case "csSearch": // �ʼ����� ����˻�
					System.out.println("��ġ ����");
					try {
						if (!array[3].isEmpty() && !dao.getCsQuiz()[2].isEmpty()) {
							if (array[3].equals(dao.getCsQuiz()[2])) {
//								server.broadcastRoom(array[2], "msg/" + id + "/" + array[3]);
								server.broadcastRoom(array[2], "queans/" + id + "/" + " �� ����!!");
								server.broadcastRoom(array[2], "que/" + "AMIGO" + "/" + "3�� �ڿ� ���������� �����ϴ�.");
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

								System.out.println("Ʋ�Ⱦ�");
							}
						} else {

						}
					} catch (Exception e3) {
					}
					;
				case "plusS": // ���� ���� ��� ���� �߰�
					System.out.println("���� �߰�");
					server.broadcastRoom(array[2], "queans/" + id + "/" + " �� Score 50�� �߰�!!");
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

				case "�ӼӸ�":

					String[] talk = array[1].split("&");

					server.talkMsg(talk[0], talk[1], talk[2]);

					break;

//talk[0] ������

//talk[1] �޴³�

//talk[2] �Ҹ�

				case "mkroom":

//������(����);

					server.addRoom(array[1], this);

					server.removeGuest(this);

					server.makeGuestlist();

					server.makeGuestlistRoom(array[1]);

					server.makeRoomlist();

					break;

				case "roomjoin":
					
					server.removeGuest(this); // ���ǿ� �ڱ� ����

					server.makeGuestlist(); // �ٽ� ���� ��� �Ѹ�

					server.addGuestRoom(array[2], this); // �� �濡 �ڱ⸦ ����

					server.makeGuestlistRoom(array[2]); // �濡 ������ �����ڸ���Ʈ ����

					server.makeRoomlist();
					server.broadcastRoom(array[2], "msg/" + id + "/" + "���� �����ϼ̽��ϴ�");

					break;

				case "roomout":
					
					server.removeGuestRoom(array[2], this);

					server.removeRoom(array[2]);

					server.makeGuestlist();

					server.makeRoomlist();
					server.broadcastRoom(array[2], "msg/" + id + "/" + "���� �����ϼ̽��ϴ�.");
					break;
					
				case "removeG":
					server.removeGuest(this);
					interrupt();
					break;
					
				case "OCNAME":
					//server.check(this);
					System.out.println(array[1]+" ��");
					server.addOneChat(this);
					id = array[1];
					server.makeOneChatRoom(id);
					
					break;
										
				case "ONE":
					System.out.println(array[1]+", "+array[2]+" ��");
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

