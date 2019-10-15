package gui.user;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dao.DBConnection;
import dao.UserDao;
import models.Movies;
import models.Users;
import util.Utils;

@SuppressWarnings("serial")
public class SelectMovie1 extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JLabel lbBox[], lbMovieName[], lbTime[];

	// add
	private static final String SQL = "SELECT DISTINCT M.ID, M.TITLE, M.AGE, M.RUNNING_TIME FROM MOVIE M INNER JOIN SCREEN S ON M.ID = S.MOVIE_ID WHERE ? BETWEEN S.START_DATE AND S.END_DATE";
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;

	private int movieId;
	private ArrayList<Movies> movies = new ArrayList<>();
	
	private String userId, reserveDate;

	// end of add
	public SelectMovie1(String userId, String reserveDate) {
		this.userId = userId;
		this.reserveDate = reserveDate;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		
		// 여기서부터 실제 이벤트가 있는 것에 대한 코드 작성

		// 여기까지 실제 이벤트가 있는 것에 대한 코드 작성
		
		frame.setSize(426, 779);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private void init() {
		// add
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, reserveDate);
			rs = pstmt.executeQuery();

			// get DB item
			while (rs.next()) {
				Movies movie = new Movies();
				movie.setId(rs.getInt("ID"));
				movie.setTitle(rs.getString("TITLE"));
				movie.setAge(rs.getInt("AGE"));
				movie.setRunningTime(rs.getInt("RUNNING_TIME"));
				movies.add(movie);
			} // end of while

			// origin
			backgroundPanel = new JPanel();
			frame.setContentPane(backgroundPanel);

			CustomUI custom = new CustomUI(backgroundPanel);
			custom.setPanel();

			// 여기서부터 실제 화면에 표현할 컴포넌트에 대한 코드 작성
			lbBox = new JLabel[movies.size()];
			lbMovieName = new JLabel[movies.size()];
			lbTime = new JLabel[movies.size()];
			
			for (int j = 0; j < movies.size(); j++) {
				int moveY = 55;
				lbBox[j] = custom.setLbBox("lbBox"+j, movies.get(j).getAge() + "", 35, 160 + (moveY * j));
//				lbMovieName[j] = custom.setLbFont("lbMovieName"+j, movies.get(j).getTitle(), 75, 162 + (moveY * j), 300, 20);
				lbMovieName[j] = custom.setLb("lbMovieName"+j, movies.get(j).getTitle(), 75, 162 + (moveY * j), 300, 20, "left", 14, "plain");
//				lbTime[j] = custom.setLbTimeFont("lbTime"+j, movies.get(j).getRunningTime() + "분", 80, 162 + (moveY * j), 300, 20);
				lbTime[j] = custom.setLb("lbTime"+j, movies.get(j).getRunningTime() + "분", 80, 162 + (moveY * j), 300, 20, "right", 13, "plain");

				lbMovieName[j].addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {}
					public void mousePressed(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseClicked(MouseEvent e) {
						String movieTitle = e.getSource().toString();
						int movieAge = 0;

						for (int i = 0; i < lbMovieName.length; i++) {
							if (movieTitle.contains(movies.get(i).getTitle())) {
								movieId = movies.get(i).getId();
								movieAge = movies.get(i).getAge();
							}
						}
						
						UserDao dao = UserDao.getInstance();
						Users user = dao.selectBirth(userId);
						
						String birth = user.getBirthDate()+"";
						int birthYear = Integer.parseInt(birth.substring(0, 4));
						int birthMonth = Integer.parseInt(birth.substring(4, 6));
						int birthDay = Integer.parseInt(birth.substring(6));
						
						Utils util = new Utils();
						int age = util.getAge(birthYear, birthMonth, birthDay);

						if(age >= movieAge) {
							new SelectMovie2(userId, movieId, reserveDate);
							frame.dispose();
						} else {
							JOptionPane.showMessageDialog(frame, "관람 가능한 나이가 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
						}
						
					}// end of mouseclicked
				});
			}
			// 여기까지 실제 화면에 표현할 컴포넌트에 대한 코드 작성
			// end of origin
		} catch (Exception e) {
			e.printStackTrace();
		} // end of trycatch
	}
}