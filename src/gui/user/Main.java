package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JButton btnMovie, btnTheater, btnList, btnInfo, btnLogout;
	
	private String userId;

	public Main(String userId) {
		this.userId = userId;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

		// 여기서부터 실제 이벤트가 있는 것에 대한 코드 작성
		btnMovie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SelectDate(userId, 0, "Movie");
				frame.dispose();
			}
		});
		btnTheater.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SelectTheater1(userId);
				frame.dispose();
			}
		});
		btnList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new BookingList(userId);
				frame.dispose();
			}
		});
		btnInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UserInfo(userId);
				frame.dispose();
			}
		});
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Login();
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
		btnMovie = custom.setBtnImg("btnMovie", "영화별 예매", 33, 240);
		btnTheater = custom.setBtnImg("btnTheater", "상영관별 예매", 212, 240);
		btnList = custom.setBtnImg("btnList", "예매 확인", 33, 400);
		btnInfo = custom.setBtnImg("btnInfo", "내 정보 보기", 212, 400);
		btnLogout = custom.setBtnWhite("btnLogout", "로그아웃", 650);
		// 여기까지 실제 화면에 표현할 컴포넌트에 대한 코드 작성
	}

}