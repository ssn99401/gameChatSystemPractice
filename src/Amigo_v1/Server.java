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
		System.out.println("��ٸ�..");
		while (true) {

			Socket s = ss.accept();

			Guest g = new Guest(this, s); // �� ������

			g.start();

			addGuest(g);

		}

	}

	///////////////////////////////////////

	void removeRoom(String rn) { // �濡 �ƹ��� ��� �����

		if (map.get(rn).size() == 0) {

			map.remove(rn);

		}

	}

	void removeGuestRoom(String rn, Guest guest) throws Exception { // �濡�� ��� ���������°�

		map.get(rn).remove(guest);

		map.get(rn);

		broadcastRoom(rn, "out/" + guest.id);

	}

	// /////////////////////////////////////////////

	void addGuestRoom(String rn, Guest guest) {

		ArrayList<Guest> list2 = map.get(rn);

		list2.add(guest);

		System.out.print("����" + rn + " ,");

		System.out.println("����� :" + list2.size());

	}

	void addRoom(String roomname, Guest guest) {

		ArrayList<Guest> arraylist2 = new ArrayList<Guest>();

		arraylist2.add(guest);

		map.put(roomname, arraylist2);

		System.out.println("�����ȹ� :" + roomname);

		System.out.println("����ڼ� :" + arraylist2.size());

	}

	void addGuest(Guest g) {

		list.add(g);

		System.out.println("�����ڼ�:" + list.size());

	}

	void makeRoomlist() throws Exception {

		Set<String> roomlist = map.keySet();

		StringBuffer buffer = new StringBuffer("roomlist/");

		for (String t : roomlist) {

			buffer.append(t + "/"); ///// ����� ǥ���ؾ��Ҳ�������

		}

		broadcast(buffer.toString());

		Roomnumber(roomlist);

	}

	void Roomnumber(Set<String> roomlist) throws Exception {

		StringBuffer buffer2 = new StringBuffer("roomnum/"); // �濡 �����

		for (String t : roomlist) {

			buffer2.append(map.get(t).size() + "/");

		}

		broadcast(buffer2.toString());

	}

	/////////////////////////////////////////////////////////////

	public void talkMsg(String talk, String talk2, String talk3) {

		// talk ������

		// 2 ������

		// 3 �Ҹ�

		for (Guest g : list) {

			if (g.id.equals(talk2)) {

				try {

					g.sendMsg("�ӼӸ�/" + talk + "&" + talk2 + "&" + talk3);

				} catch (Exception e) {

					System.out.println("�Խ�Ʈ���� �Ӹ������ٰ� ����" + e.getMessage());

				}

			}

		}

	}

	void removeGuest(Guest g) {

		list.remove(g);

		System.out.println("�����ڼ�:" + list.size()); // 1��

	}

	void broadcast(String msg) throws Exception {

		try {

			for (Guest g : list) { // msg/[�̸�]�ȳ�Ƽ��� ����Դϴ�.

				g.sendMsg(msg);

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

	}
	

	void broadcastRoom(String rn, String msg) throws Exception { // �ش�� �޽��� �����

		ArrayList<Guest> list2 = map.get(rn); // arrayList�� ��ϵǾ��ִ� �Խ�Ʈ��

		for (Guest g : list2) {

			g.sendMsg(msg); // �̸�-guestlistRoom/�̸�/�̸�2 �̸�2-guestlistRoom/�̸�/�̸�2

		}

	}
	
	void oneChatcast(String msg) throws Exception {

		try {

			for (Guest g : arraylist) { // msg/[�̸�]�ȳ�Ƽ��� ����Դϴ�.

				g.sendMsg(msg);

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

	}
	/*void searchAndBroad(String rn, String msg) throws Exception { // �ش�� �޽��� �����
		
		ArrayList<Guest> list2 = map.get(rn); // arrayList�� ��ϵǾ��ִ� �Խ�Ʈ��

		for (Guest g : list2) {

			g.sendMsg(msg); // �̸�-guestlistRoom/�̸�/�̸�2 �̸�2-guestlistRoom/�̸�/�̸�2

		}

	}*/

	void makeGuestlist() throws Exception { // guestlist/ȫ�浿/��浿/�̱浿/

		StringBuffer buffer = new StringBuffer("guestlist/"); // guestlist/

		for (Guest g : list) {

			buffer.append(g.id + "/");

		}

		broadcast(buffer.toString());

	}

	void makeGuestlistRoom(String rn) throws Exception { // guestlist/ȫ�浿/��浿/�̱浿/

		ArrayList<Guest> list2 = map.get(rn);

		StringBuffer buffer = new StringBuffer("guestlistRoom/"); // guestlistRoom/�̸�/�̸�2

		peoplelist = list2;

		for (Guest g : list2) {

			buffer.append(g.id + "/");

		}

		broadcastRoom(rn, buffer.toString()); // �ٹ� , guestlistRoom/�̸�/�̸�2

	}
	
	void makeOneChatRoom(String id) throws Exception {

		String msg = "[" + id + "] �� �����ϼ̽��ϴ�.";

		StringBuffer buffer = new StringBuffer("entrance/");

		// for (Guest t : arraylist) {

		buffer.append(msg); ///// ����� ǥ���ؾ��Ҳ�������

		// }
		oneChatcast(buffer.toString());
	}

	void routine(String id, String message) throws Exception {
		String msg = "[" + id + "] " + message;
		StringBuffer buffer = new StringBuffer("one/");

		// for (Guest t : arraylist) {

		buffer.append(msg); ///// ����� ǥ���ؾ��Ҳ�������

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



