package Amigo_v1;

import java.net.ServerSocket;

import java.net.Socket;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Set;

class Server {
	final static int portno=13474;
	ArrayList<Guest> list;

	HashMap<String, ArrayList<Guest>> map;

	ArrayList<Guest> arraylist;

	ArrayList<Guest> peoplelist;

	void initNet() throws Exception {

		map = new HashMap<String, ArrayList<Guest>>();

		arraylist = new ArrayList<Guest>();

		list = new ArrayList<Guest>();

		ServerSocket ss = new ServerSocket(portno);
		System.out.println("기다림..");
		while (true) {

			Socket s = ss.accept();

			Guest g = new Guest(this, s); // 내 아이피

			g.start();

			addGuest(g);

		}

	}

	///////////////////////////////////////

	void removeRoom(String rn) { // 방에 아무도 없어서 사라짐

		if (map.get(rn).size() == 0) {

			map.remove(rn);

		}

	}

	void removeGuestRoom(String rn, Guest guest) throws Exception { // 방에서 사람 빠져나가는거

		map.get(rn).remove(guest);

		map.get(rn);

		broadcastRoom(rn, "out/" + guest.id);

	}

	// /////////////////////////////////////////////

	void addGuestRoom(String rn, Guest guest) {

		ArrayList<Guest> list2 = map.get(rn);

		list2.add(guest);

		System.out.print("방제" + rn + " ,");

		System.out.println("사람수 :" + list2.size());

	}

	void addRoom(String roomname, Guest guest) {

		ArrayList<Guest> arraylist2 = new ArrayList<Guest>();

		arraylist2.add(guest);

		map.put(roomname, arraylist2);

		System.out.println("개설된방 :" + roomname);

		System.out.println("사용자수 :" + arraylist2.size());

	}

	void addGuest(Guest g) {

		list.add(g);

		System.out.println("접속자수:" + list.size());

	}

	void makeRoomlist() throws Exception {

		Set<String> roomlist = map.keySet();

		StringBuffer buffer = new StringBuffer("roomlist/");

		for (String t : roomlist) {

			buffer.append(t + "/"); ///// 사람수 표시해야할꺼같은데

		}

		broadcast(buffer.toString());

		Roomnumber(roomlist);

	}

	void Roomnumber(Set<String> roomlist) throws Exception {

		StringBuffer buffer2 = new StringBuffer("roomnum/"); // 방에 사람수

		for (String t : roomlist) {

			buffer2.append(map.get(t).size() + "/");

		}

		broadcast(buffer2.toString());

	}

	/////////////////////////////////////////////////////////////

	public void talkMsg(String talk, String talk2, String talk3) {

		// talk 보낸놈

		// 2 받을놈

		// 3 할말

		for (Guest g : list) {

			if (g.id.equals(talk2)) {

				try {

					g.sendMsg("귓속말/" + talk + "&" + talk2 + "&" + talk3);

				} catch (Exception e) {

					System.out.println("게스트에서 귓말보내다가 에러" + e.getMessage());

				}

			}

		}

	}

	void removeGuest(Guest g) {

		list.remove(g);

		System.out.println("접속자수:" + list.size()); // 1명

	}

	void broadcast(String msg) throws Exception {

		try {

			for (Guest g : list) { // msg/[이름]안녀아세요 사람입니다.

				g.sendMsg(msg);

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

	}
	

	void broadcastRoom(String rn, String msg) throws Exception { // 해당방 메시지 날라옴

		ArrayList<Guest> list2 = map.get(rn); // arrayList에 등록되어있는 게스트들

		for (Guest g : list2) {

			g.sendMsg(msg); // 이름-guestlistRoom/이름/이름2 이름2-guestlistRoom/이름/이름2

		}

	}
	
	void oneChatcast(String msg) throws Exception {

		try {

			for (Guest g : arraylist) { // msg/[이름]안녀아세요 사람입니다.

				g.sendMsg(msg);

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

	}
	/*void searchAndBroad(String rn, String msg) throws Exception { // 해당방 메시지 날라옴
		
		ArrayList<Guest> list2 = map.get(rn); // arrayList에 등록되어있는 게스트들

		for (Guest g : list2) {

			g.sendMsg(msg); // 이름-guestlistRoom/이름/이름2 이름2-guestlistRoom/이름/이름2

		}

	}*/

	void makeGuestlist() throws Exception { // guestlist/홍길동/김길동/이길동/

		StringBuffer buffer = new StringBuffer("guestlist/"); // guestlist/

		for (Guest g : list) {

			buffer.append(g.id + "/");

		}

		broadcast(buffer.toString());

	}

	void makeGuestlistRoom(String rn) throws Exception { // guestlist/홍길동/김길동/이길동/

		ArrayList<Guest> list2 = map.get(rn);

		StringBuffer buffer = new StringBuffer("guestlistRoom/"); // guestlistRoom/이름/이름2

		peoplelist = list2;

		for (Guest g : list2) {

			buffer.append(g.id + "/");

		}

		broadcastRoom(rn, buffer.toString()); // 다방 , guestlistRoom/이름/이름2

	}
	
	void makeOneChatRoom(String id) throws Exception {

		String msg = "[" + id + "] 님 입장하셨습니다.";

		StringBuffer buffer = new StringBuffer("entrance/");

		// for (Guest t : arraylist) {

		buffer.append(msg); ///// 사람수 표시해야할꺼같은데

		// }
		oneChatcast(buffer.toString());
	}

	void routine(String id, String message) throws Exception {
		String msg = "[" + id + "] " + message;
		StringBuffer buffer = new StringBuffer("one/");

		// for (Guest t : arraylist) {

		buffer.append(msg); ///// 사람수 표시해야할꺼같은데

		// }
		oneChatcast(buffer.toString());

	}

	void addOneChat(Guest g) {
		arraylist.add(g);
		list.remove(g);
	}

	public static void main(String args[]) throws Exception {

		Server server = new Server();

		server.initNet();

	}

}



