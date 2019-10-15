package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.DBConnection;
import models.Movies;

public class BookingList extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JButton btnBack;
	private JLabel lbTitle, lbBox[], lbMovieTitle[], lbTime[];

	private final String SQL = "SELECT M.TITLE, M.AGE, M.RUNNING_TIME, R.ID AS RID FROM MOVIE M JOIN RESERVE R ON M.ID = R.MOVIE_ID WHERE R.USER_ID = ?";
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<String> movieTitle = new ArrayList<>();
	private ArrayList<Movies> mv = new ArrayList<Movies>();
	
	private String userId;
	private int reserveId;

	public BookingList(String userId) {
		this.userId = userId;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

		// 여기서부터 실제 이벤트가 있는 것에 대한 코드 작성
		btnBack.addActionListener(new ActionListener() {
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
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Movies m = new Movies();
				m.setTitle(rs.getString("TITLE"));
				m.setAge(rs.getInt("AGE"));
				m.setRunningTime(rs.getInt("RUNNING_TIME"));
				m.setId(rs.getInt("RID"));
				mv.add(m);
			}
			
			int i = 0;
			lbBox = new JLabel[mv.size()];
			lbMovieTitle = new JLabel[mv.size()];
			lbTime = new JLabel[mv.size()];
			for (Movies m : mv) {
				int moveY = 55 * i;
				i++;
				lbBox[i-1] = custom.setLbBox("lbBox"+i, m.getAge()+"", 35, 265 + moveY);
				lbMovieTitle[i-1] = custom.setLbFont("lbMovieTitle"+i, m.getTitle(), 75, 265 + moveY, 300, 20);
				lbTime[i-1]= custom.setLbTimeFont("lbTime"+i, m.getRunningTime()+"분", 80, 265 + moveY, 300, 20);
				
				lbMovieTitle[i - 1].addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {}
					public void mousePressed(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseClicked(MouseEvent e) {
						JLabel lb = (JLabel) e.getSource();
						int num = Integer.parseInt(lb.getName().substring(12));
						reserveId = mv.get(num - 1).getId();

						new BookingDetail(userId, reserveId);
						frame.dispose();
					}
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		lbTitle = custom.setLbTitle("lbTitle", "예매 내역", 100, 85, 220, 185, "center");
		btnBack = custom.setBtnWhite("btnBack", "이전으로", 650);
		// 여기까지 실제 화면에 표현할 컴포넌트에 대한 코드 작성
	}
}