package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.DBConnection;
import models.Users;

@SuppressWarnings("serial")
public class UserInfo extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JButton btnMain;
	private JLabel lbTitle, lbTitleId, lbId, lbTitleBirth, lbBirth, lbTitleTel, lbTel;
	
	private String userId;

	public UserInfo(String userId) {
		this.userId = userId;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		
		// 여기부터 실제 이벤트가 있는 것에 대한 코드 작성
		setUserInfo(userId);
		
		btnMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Main(userId);
				frame.dispose();
			}
		});
		// 여기까지 실제 이벤트가 있는 것에 대한 코드 작성

		frame.setSize(426, 779);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private void init() {
		backgroundPanel = new JPanel();
		frame.setContentPane(backgroundPanel);

		CustomUI custom = new CustomUI(backgroundPanel);
		custom.setPanel();
		
		// 여기서부터 실제 화면에 표현할 컴포넌트에 대한 코드 작성
		lbTitle = custom.setLbImg("lbTitle", 2, 165, 150);

		lbTitleId = custom.setLbText2("lbTitleId", "아이디", 35, 300, 100, 20);
		lbId = custom.setLbText3("lbId", "", 195, 300, 180, 20);

		lbTitleBirth = custom.setLbText2("lbTitleBirth", "생년월일", 35, 360, 100, 20);
		lbBirth = custom.setLbText3("lbText3", "", 195, 360, 180, 20);

		lbTitleTel = custom.setLbText2("lbTitleTel", "전화번호", 35, 420, 100, 20);
		lbTel = custom.setLbText3("lbBirth", "", 195, 420, 180, 20);

		btnMain = custom.setBtnBlue("btnMain", "메인으로", 605);
		// 여기까지 실제 화면에 표현할 컴포넌트에 대한 코드 작성
	}

	public Users setUserInfo(String userId) {
		// DB에서 데이터가져와서
		final String SQL = "SELECT BIRTH_DATE, PHONE FROM USERS WHERE USER_ID = ?"; // users 테이블의 id가 특정한 값인 모든 데이터를 조회

		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs;
		conn = DBConnection.getConnection();
		
		Users user = new Users();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				user.setBirthDate(rs.getInt("BIRTH_DATE"));
				user.setPhone(rs.getString("PHONE"));
			}
			
			lbId.setText(userId);
			lbBirth.setText(formatDate(user.getBirthDate()));
			lbTel.setText(formatTel(user.getPhone()));
			
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String formatTel(String tel) {
		if(tel.length() == 10) {
			String tel1 = tel.substring(0, 3);
			String tel2 = tel.substring(3, 6);
			String tel3 = tel.substring(6);
			tel = tel1 + "-" + tel2 + "-" + tel3;
		} else if(tel.length() == 11) {
			String tel1 = tel.substring(0, 3);
			String tel2 = tel.substring(3, 7);
			String tel3 = tel.substring(7);
			tel = tel1 + "-" + tel2 + "-" + tel3;
		} else {
			return tel;
		}
		return tel;
	}
	
	private String formatDate(int date) {
		String formatDate = date + "";
		String date1 = formatDate.substring(0, 4);
		String date2 = formatDate.substring(4, 6);
		String date3 = formatDate.substring(6);
		formatDate = date1 + "-" + date2 + "-" + date3;
		return formatDate;
	}
}