package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.DBConnection;

@SuppressWarnings("serial")
public class Login extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JTextField txtUserId;
	private JPasswordField txtPassword;
	private JButton btnLogin, btnJoin;
	private JLabel lbLogo, lbSearch;

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	private static final String SQL = "SELECT * FROM USERS WHERE USER_ID = ? AND PASSWORD = ?";

	public Login() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		
		lbSearch.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "관리자에게 문의하세요.", "오류", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		txtPassword.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
				}
			}
			public void keyPressed(KeyEvent e) {}
		});
		
		// 여기서부터 실제 이벤트가 있는 것에 대한 코드 작성
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 1 텍스트 필드 값 가져오기
				String userId = txtUserId.getText();
				String password = String.valueOf(txtPassword.getPassword());
				
				// 2 디비 연결
				conn = DBConnection.getConnection();
				
				// 3 쿼리작성-> 쿼리전송(실행)
				try {
					pstmt = conn.prepareStatement(SQL);
					pstmt.setString(1, userId);
					pstmt.setString(2, password);
					rs = pstmt.executeQuery();

					// 4 결과 두개로 나눈다
					if (rs.next()) {
						if(rs.getString("ADMIN_FG").equals("Y")) {
							new gui.admin.Main();
							frame.dispose();
						} else {
							new Main(userId);
							frame.dispose();
						}
					} else {
						JOptionPane.showMessageDialog(frame, "일치하는 사용자가 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(frame, "인증되지 않았습니다.");
				}
			}
		});
		
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Join();
				frame.dispose();
			}
		});

		// 여기까지 실제 이벤트가 있는 것에 대한 코드 작성
		frame.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				frame.requestFocus();
			}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		});
		frame.requestFocus();
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
		lbLogo = custom.setLbImg("lbLogo", 4, 150, 150);
		txtUserId = custom.setTextField("txtUserId", "ID", 35, 290, 350, 45);
		txtPassword = custom.setPasswordField("txtPassword", "Password", 35, 345, 350, 45);
		btnLogin = custom.setBtnBlue("btnLogin", "로그인", 425);
		btnJoin = custom.setBtnWhite("btnJoin", "회원가입", 480);
		lbSearch = custom.setLbContents("lbSearch", "아이디 찾기 ｜ 비밀번호 찾기", 100, 535, 200, 40, "center");
		// 여기까지 실제 화면에 표현할 컴포넌트에 대한 코드 작성
	}

	public static void main(String[] args) {
		new Login();
	}
}