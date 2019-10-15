package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.DBConnection;
import dao.ReserveDao;
import dao.SeatDao;
import models.Movies;
import models.Reserves;
import models.Seats;

@SuppressWarnings("serial")
public class SelectMovie2 extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JButton btnMovie[][];
	private JLabel lbBox[], lbPlace[], lbTheater[];

	private int firstY = 160;
	private int moveY = 55;
	private int firstX = 15;
	private int btnMovieY = firstY + moveY;
	private int btnMoveX = 100;
	private int jj = 0;
	private int jjj = 3;
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;

	private ActionListener btnListener;
	private ArrayList<Movies> movies = new ArrayList<>();

	private String userId, reserveDate;
	private int movieId;
	
	private String sql;

	public SelectMovie2(String userId, int movieId, String reserveDate) {
		this.userId = userId;
		this.movieId = movieId;
		this.reserveDate = reserveDate;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JButton) {
					JButton btn = (JButton) e.getSource();
					String btnName = btn.getName();
					
					conn = DBConnection.getConnection();
					try {
						sql = "SELECT M.ID, M.TITLE, M.PRICE, M.AGE, M.RUNNING_TIME, P.ID AS PLACE_ID, P.NAME AS PLACE_NAME, T.ID AS THEATER_ID, T.NAME AS THEATER_NAME, S.START_TIME";
						sql += " FROM MOVIE M";
						sql += " INNER JOIN SCREEN S ON M.ID = S.MOVIE_ID";
						sql += " INNER JOIN PLACE P ON S.PLACE_ID = P.ID";
						sql += " INNER JOIN THEATER T ON S.THEATER_ID = T.ID AND T.PLACE_ID = P.ID";
						sql += " WHERE M.ID = ? AND ? BETWEEN S.START_DATE AND S.END_DATE";
						
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, movieId);
						pstmt.setString(2, reserveDate);
						rs = pstmt.executeQuery();

						while (rs.next()) {
							Movies movie = new Movies();
							movie.setId(rs.getInt("ID"));
							movie.setTitle(rs.getString("TITLE"));
							movie.setPrice(rs.getInt("PRICE"));
							movie.setAge(rs.getInt("AGE"));
							movie.setRunningTime(rs.getInt("RUNNING_TIME"));
							movie.setPlaceId(rs.getInt("PLACE_ID"));
							movie.setPlaceName(rs.getString("PLACE_NAME"));
							movie.setTheaterId(rs.getInt("THEATER_ID"));
							movie.setTheaterName(rs.getString("THEATER_NAME"));
							movie.setStartTime(rs.getString("START_TIME"));
							movies.add(movie);
						}

						int num = Integer.parseInt(btnName.substring(8, 9));
						int num2 = Integer.parseInt(btnName.substring(9, 10));

						int placeId = movies.get(num).getPlaceId();
						int theaterId = movies.get(num).getTheaterId();
						String splitTime[] = movies.get(num).getStartTime().split("\\|");
						String reserveTime = splitTime[num2];

						new Seat(userId, movieId, placeId, theaterId, reserveDate, reserveTime, "Movie");
						frame.dispose();
					} catch (Exception ee) {
					}
				}
			}
		};

		init();

		// 여기서부터 실제 이벤트가 있는 것에 대한 코드 작성
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
			sql = "SELECT M.ID, M.TITLE, M.PRICE, M.AGE, M.RUNNING_TIME, P.ID AS PLACE_ID, P.NAME AS PLACE_NAME, T.ID AS THEATER_ID, T.NAME AS THEATER_NAME, S.START_TIME";
			sql += " FROM MOVIE M";
			sql += " INNER JOIN SCREEN S ON M.ID = S.MOVIE_ID";
			sql += " INNER JOIN PLACE P ON S.PLACE_ID = P.ID";
			sql += " INNER JOIN THEATER T ON S.THEATER_ID = T.ID AND T.PLACE_ID = P.ID";
			sql += " WHERE M.ID = ? AND ? BETWEEN S.START_DATE AND S.END_DATE";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movieId);
			pstmt.setString(2, reserveDate);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Movies movie = new Movies();
				movie.setId(rs.getInt("ID"));
				movie.setTitle(rs.getString("TITLE"));
				movie.setPrice(rs.getInt("PRICE"));
				movie.setAge(rs.getInt("AGE"));
				movie.setRunningTime(rs.getInt("RUNNING_TIME"));
				movie.setPlaceId(rs.getInt("PLACE_ID"));
				movie.setPlaceName(rs.getString("PLACE_NAME"));
				movie.setTheaterId(rs.getInt("THEATER_ID"));
				movie.setTheaterName(rs.getString("THEATER_NAME"));
				movie.setStartTime(rs.getString("START_TIME"));
				
				String splitTime[] = movie.getStartTime().split("\\|");
				for (int i = 0; i < splitTime.length; i++) {
					ReserveDao rDao = ReserveDao.getInstance();
					Reserves reserve = rDao.selectedSeats(movie.getId(), movie.getPlaceId(), movie.getTheaterId(), reserveDate, splitTime[i]);
					if(i == splitTime.length-1) {
						movie.setSeat(movie.getSeat() + reserve.getSeat());
					} else if (i == 0){
						movie.setSeat(reserve.getSeat() + "|");
						
					} else {
						if (movie.getSeat() != null) {
							movie.setSeat(movie.getSeat() + reserve.getSeat() + "|");
						} else {
							movie.setSeat(movie.getSeat() + "|");
						}
						
						
					}
				}
				
				movies.add(movie);
			}

			lbBox = new JLabel[movies.size()];
			lbPlace = new JLabel[movies.size()];
			lbTheater = new JLabel[movies.size()];
			for (int i = 0; i < movies.size(); i++) {
				if (i == 0) {
					lbBox[i] = custom.setLbBox("lbBox"+i, "99", 17, firstY);
					lbPlace[i] = custom.setLbFont("lbFont" + i, movies.get(i).getPlaceName(), 55, firstY + 2, 300, 20);
					lbTheater[i] = custom.setLbRoomFont("lbRoomFont"+i, movies.get(i).getTheaterName(), 380, firstY + 2, 300, 20);
				} else {
					lbBox[i] = custom.setLbBox("lbBox"+i, "99", 17, firstY + moveY);
					lbPlace[i] = custom.setLbFont("lbFont"+ i, movies.get(i).getPlaceName(), 55, firstY + 2 + moveY, 300, 20);
					lbTheater[i] = custom.setLbRoomFont("lbRoomFont"+i, movies.get(i).getTheaterName(), 380, firstY + 2 + moveY, 300, 20);
				}

				String splitStartTimes[] = movies.get(i).getStartTime().split("\\|");
				String splitSeat[] = movies.get(i).getSeat().split("\\|");
				for (int j = 0; j < splitStartTimes.length; j++) {
					if (j > jjj) {
						jj = jj + 4;
						btnMovieY = btnMovieY + 80;
						jjj = jjj + 4;
					}
					
					SeatDao dao = SeatDao.getInstance();
					Seats seat = dao.selectSeat(movies.get(i).getPlaceId(), movies.get(i).getTheaterId());
					int seatCnt = seat.getRow() * seat.getCol();
					
					int selectedSeatCnt = 0;
					if(!(splitSeat[j].equals("null"))) {
						selectedSeatCnt = splitSeat[j].split("\\,").length;
					}
							
					int remainingSeats = seatCnt - selectedSeatCnt;
					
					btnMovie = new JButton[movies.size()][splitStartTimes.length];
					btnMovie[i][j] = custom.setBtnMovie("btnMovie" + i + j, splitStartTimes[j], remainingSeats + "석", firstX + (btnMoveX * (j - jj)), btnMovieY);
					btnMovie[i][j].addActionListener(btnListener);
				}
				
				firstY = btnMovieY + moveY;
				btnMovieY = firstY + moveY * 2;
				jj = 0;
				jjj = 3;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 여기까지 실제 화면에 표현할 컴포넌트에 대한 코드 작성
	}

}