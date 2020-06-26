package Amigo_v1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

class DBConn {
	private Connection conn;

	public DBConn() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.58.22:1521:xe", "hr", "hr");
	}

	public Connection getConnection() {
		// TODO Auto-generated method stub
		return conn;
	}

}

class AmigoDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	AmigoVO vo;
	private String[] Pquiz=new String[4];
	private String[] csQuiz = new String[3];

	public void csQuiz() throws SQLException {
		
			ArrayList<chosungVO> list = gameWord();
			String[] bae = {list.get(0).getCs(),list.get(0).getGenre(),list.get(0).getWord()};
			csQuiz = bae;
			System.out.println(csQuiz[0]+ csQuiz[1]+csQuiz[2]);
		
	}
	
	public String[] getCsQuiz() {
		return csQuiz;
	}
	public void setCsQuiz(String[] CsQuiz) {
		csQuiz = CsQuiz;
	}
	
	public void createQuiz() throws SQLException {//�Ѱ��� ��ȣ,����,���� ��Ʈ�� �迭�� ����
		String []bae=PrintallQuiz().get(0).toString().split("��");//ù���� ���������� �迭�� �ֱ�
		Pquiz=bae;//�������� ����
	}
	public String[] getPquiz() {
		return Pquiz;
	}


	public void setPquiz(String[] pquiz) throws SQLException {

		Pquiz = pquiz;
	}


	public AmigoDAO() throws ClassNotFoundException, SQLException {
		conn = new DBConn().getConnection();
	}// ������


	// ȸ������ â id/pw/name/tel
	public boolean signup(String id, String pw, String name, String tel) {

		// ��й�ȣ�� �˸°� �����ߴ��� Ȯ���ϴ� if�� (Ư������, ����, ���ڷ� �������ϸ� 8~20�� ���̿����Ѵ�.)
		if (pw.length() < 7 || pw.length() > 20) {
			return false;

		}
		for (int i = 0; i < pw.length(); i++) {
			if (pw.charAt(i) < 33 || pw.charAt(i) > 126)
				return false;
		}

		String sql = "Insert into Amigo(ID,PW,NAME,TEL) values (?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			ps.setString(3, name);
			ps.setString(4, tel);
			ps.executeUpdate();
			return true;

		} catch (SQLException e) {
			return false;
		}

	}

	// �г��� ȸ������ â id/pw/name/nickname/tel
	public boolean signup(String id, String pw, String name, String nickname, String tel) {

		// ��й�ȣ�� �˸°� �����ߴ��� Ȯ���ϴ� if�� (Ư������, ����, ���ڷ� �������ϸ� 8~20�� ���̿����Ѵ�.)
		if (pw.length() < 7 || pw.length() > 20) {
			return false;
		}
		for (int i = 0; i < pw.length(); i++) {
			if (pw.charAt(i) < 33 || pw.charAt(i) > 126)
				return false;
		}

		String sql = "Insert into Amigo(ID,PW,NAME,TEL,NICKNAME) values (?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			ps.setString(3, name);
			ps.setString(4, tel);
			ps.setString(5, nickname);
			ps.executeUpdate();
			return true;

		} catch (SQLException e) {
			return false;
		}

	}

	// ���̵� �ߺ�üũ Ȯ�� (���� ��ư�� ������ ���)
	public boolean checkId(String id) {
		String sql = "select ID from AMIGO";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				if (id.equals(rs.getString("ID")))
					return false;
			}
			return true;

		} catch (SQLException e) {
			return false;
		}
	}

	// �α���(id/pw)

	public String Login(String id, String pw) throws SQLException {
		String sql = "select ID, PW from AMIGO";

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();

		while (rs.next()) {
			if (id.equals(rs.getString("ID")))
				if (pw.equals(rs.getString("PW")))
					return rs.getString("ID");
		}
		return null;
	}
	
	// DB�� ���� �� �ڱ� �̸� ������
	public String getMyName(String id, String pw) throws SQLException {
		String myname = null;
		String sql = "select NAME from AMIGO where ID=? and PW=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, pw);
		rs = ps.executeQuery();
		while (rs.next()) {
			String name = rs.getString("name");
			myname = name;
		}
		return myname;
	}

	// ȸ�� Ż��(id)
	public boolean signout(String id) {
		String sql = "delete from AMIGO where ID=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.executeUpdate();
			return true;

		} catch (SQLException e) {
			return false;
		}
	}

	// ģ�����
	public ArrayList Listsearch(String id) throws SQLException {
		ArrayList farray = new ArrayList();
		String sql = "select ID from AMIGO where id=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, "%" + id + "%");
		ps.executeQuery();
		return farray;

	}
	// ģ�� ID�˻�

	public ArrayList<FindAmigoVO> searchID(String id) throws SQLException {
		System.out.println(1);
		ArrayList<FindAmigoVO> list = new ArrayList<FindAmigoVO>();
		String sql = "select id, name from AMIGO where ID=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		rs = ps.executeQuery();

		while (rs.next()) {
			System.out.println(2);
			String name = rs.getString("name");
			String id1 = rs.getString("id");

			FindAmigoVO favo = new FindAmigoVO(id1, name);
			list.add(favo);
			System.out.println("�߰��Ϸ�");
		}
		return list;
	}

	// ģ�� �߰��ϱ�

	public void PlusFriend(String PlusID) throws SQLException {
		Amigo_ID id = new Amigo_ID();
		String sql = "insert into FRIENDS values (?, ?)";
		ps = conn.prepareStatement(sql);
		ps.setString(1, id.getId().toString());
		ps.setString(2, PlusID);
		rs = ps.executeQuery();
	}

	// �α׾ƿ�
	public void logout() {
		Amigo_ID.setId(null);
	}

	// ���� �ҷ�����
	public String searchNickname() throws SQLException {
		String sql = "select nickname from AMIGO where ID=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, Amigo_ID.getId());
		rs = ps.executeQuery();
		if (rs.next())
			return rs.getString(1);

		return "";
	}

	// ���� �����ϱ�
	public boolean setNickname(String nick) {
		String sql = "update AMIGO set NICKNAME=? where ID=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, nick);
			ps.setString(2, Amigo_ID.getId());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public ArrayList<FriendsListVO> findFriends() throws SQLException {
		// TODO Auto-generated method stub
		Amigo_ID id = new Amigo_ID();
		ArrayList<FriendsListVO> Find = new ArrayList<>();
		String sql = "select a.name from AMIGO a, FRIENDS f where a.id=f.friend_id and f.id like ?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, id.getId().toString());
		rs = ps.executeQuery();
		while (rs.next()) {
			String name = rs.getString("name");

			FriendsListVO vo = new FriendsListVO(name);
			Find.add(vo);
		}
		return Find;
	}
	
/*	//GAME���̺� ���̵� �ֱ�
	public boolean gameID(String id) {
		
		//String sql = "insert into GAME values(?)"
	}*/
	
	public boolean collectAns(String id) {//����� �ش�  id ���� +50��

		String sql = "update GAME set score= score+50 where id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.executeUpdate();
			System.out.println("���� �� �Ϸ�");
			return true;
		} catch (SQLException e) {
			System.out.println("error");
			return false;
		}
	}

	public boolean scoreSet(String id) {// ���� ��������

		String sql = "SELECT g.score, g.id FROM game g, Amigo a "
				+ "where a.id = g.id ORDER BY score DESC";
		try {
			ps = conn.prepareStatement(sql);
			ps.execute();
			System.out.println("���� �� �Ϸ�");
			return true;
		} catch (SQLException e) {
			System.out.println("error");
			return false;
		}
	}

	public boolean scoreReset() {// ��������

		String sql = "update GAME set score=0";
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			System.out.println("���� �� �Ϸ�");
			return true;
		} catch (SQLException e) {
			System.out.println("error");
			return false;
		}
	}

	public boolean gameindexSet(int index) {//���� ��� ����
		String sql = "update game_setting set game_index=?";
		try {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, index);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("error");
			return false;
		}

	}
	
	public int getgameindex() {//���� ��� ���
		String sql = "Select * from game_setting";
		int getindex = 0;
		try {
			ps = conn.prepareStatement(sql);
			rs =ps.executeQuery();
			while (rs.next()) {
				getindex=rs.getInt("game_index");
			}
		} catch (SQLException e) {
			System.out.println("error");
		}
		return getindex;

	}

	public ArrayList<AmigoQuiz> PrintallQuiz() throws SQLException {// ��ü ��������
		ArrayList<AmigoQuiz> qarray = new ArrayList<AmigoQuiz>(); 

		String sql = "select * from(SELECT * \r\n" + "        FROM quiz\r\n"
				+ "       ORDER BY DBMS_RANDOM.RANDOM())";

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			int no = rs.getInt("no");
			String ques = rs.getString("ques");
			String ans = rs.getString("ans");
			String hint = rs.getString("hint");

			AmigoQuiz aqo = new AmigoQuiz(no, ques, ans, hint);

			qarray.add(aqo);

		}

		return qarray;
	}

	//�������� �����ؼ� ����Ʈ�� �ֱ�. //����Ʈ�� ���°� Ȯ�� �Ϸ�
	public ArrayList<chosungVO> gameWord() throws SQLException {
		ArrayList<chosungVO> list = new ArrayList<>();
		String sql = "select * from (select * from chosung order by dbms_random.value) where rownum =1";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while(rs.next()) {
			String word = rs.getString("word");
			String genre = rs.getString("genre");
			String cs = rs.getString("cs");
			
			chosungVO vo = new chosungVO(word, genre, cs);
			list.add(vo);
			System.out.println(list.get(0)+"1");
		}
			
		return list;
	}
	
}

class chosungVO{
	private String word;
	private String genre;
	private String cs;

	public String getCs() {
		return cs;
	}
	public void setCs(String cs) {
		this.cs = cs;
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public chosungVO() {

	}

	public chosungVO(String word, String genre, String cs) {
		this.word = word;
		this.genre = genre;
		this.cs = cs;
	}

}

class AmigoQuiz {
	@Override
	public String toString() {//Į����  ������ ��Ÿ����  tostring
		return no+"��"+ques+"��"+ans+"��"+hint;
	}
	private int no;
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}
	
	
	private String ques;
	private String ans;
	private String hint;

	public AmigoQuiz() {
		// TODO Auto-generated constructor stub
	}

	public AmigoQuiz(int no,String ques, String ans, String hint) {
		super();
		this.no = no;
		this.ques = ques;
		this.ans = ans;
		this.hint = hint;
	}
	

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public AmigoQuiz(String ques) {
		this.ques = ques;
	}

	public String getQues() {
		return ques;
	}

	public void setQues(String ques) {
		this.ques = ques;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}
}

class AmigoVO {
	private String id;
	private String pw;
	private String name;
	private String tel;
	private String nickname;
	private String friends;
	private String word;

	public String getWord() {
		return word;
	}

	public AmigoVO(String word) {
		super();
		this.word = word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public AmigoVO() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFriends() {
		return friends;
	}

	public void setFriends(String friends) {
		this.friends = friends;
	}

	public AmigoVO(String id, String pw, String name, String tel, String nickname, String friends) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.tel = tel;
		this.nickname = nickname;
		this.friends = friends;
	}

}

class FindAmigoVO {
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FindAmigoVO() {

	}

	public FindAmigoVO(String id, String name) {
		this.id = id;
		this.name = name;
	}

}

class FriendsListVO {
	private String name;

	public FriendsListVO() {

	}

	public FriendsListVO(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

public class Amigo_MainProcess {
	Amigo_Loading ago_loading;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		DBConn con = new DBConn();
		Amigo_MainProcess main = new Amigo_MainProcess();
		main.showLoading();	

	}

	public void showLoading() throws InterruptedException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		
		this.ago_loading = new Amigo_Loading();
		Thread.sleep(3000);
		this.ago_loading.dispose();
		new Amigo_Login().setVisible(true);
	}

}