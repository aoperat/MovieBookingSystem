package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dao.DBConnection;
import models.Reserves;

public class BookingDetail extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JLabel lbTitleReserveDt, lbTitleReserveCnt, lbTitleSeat, lbTitlePrice, lbTitleInsDt;
	private JLabel lbMovieTitle, lbReserveDt, lbReserveCnt, lbSeat, lbPrice, lbInsDt;
	private JButton btnCancel, btnBack;

	private final String SQL = "SELECT M.TITLE, R.RESERVE_DATE, R.RESERVE_TIME, R.RESERVE_CNT, R.SEAT, R.PRICE, TO_CHAR(R.INS_DT, 'YYYY-MM-DD HH24:MI') AS INS_DT FROM RESERVE R INNER JOIN MOVIE M ON M.ID = R.MOVIE_ID WHERE R.ID = ? AND R.USER_ID = ?";
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private String userId;
	private int reserveId;

	public BookingDetail(String userId, int reserveId) {
		this.userId = userId;
		this.reserveId = reserveId;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

		// 여기서부터 실제 이벤트가 있는 것에 대한 코드 작성
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, reserveId);
			pstmt.setString(2, userId);
			rs = pstmt.executeQuery();
			Reserves r = new Reserves();
			
			if (rs.next()) {
				r.setMovieTitle(rs.getString("TITLE")); // 영화제목
				r.setReserveDate(rs.getString("RESERVE_DATE"));
				r.setReserveTime(rs.getString("RESERVE_TIME"));
				r.setReserveCnt(rs.getInt("RESERVE_CNT")); // 예약인원
				r.setSeat(rs.getString("SEAT")); // 예약좌석
				r.setPrice(rs.getInt("PRICE")); // 결제금액
				r.setInsDt(rs.getString("INS_DT")); // 예약일자
			}

			lbMovieTitle.setText(r.getMovieTitle());
			lbReserveDt.setText(r.getReserveDate() + " " + r.getReserveTime());
			lbReserveCnt.setText(r.getReserveCnt()+"인");
			lbSeat.setText(r.getSeat());
			lbPrice.setText(NumberFormat.getInstance().format(r.getPrice())+"원");
			lbInsDt.setText(r.getInsDt());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "예매를 취소하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					int resultCnt = delete(userId, reserveId);
					if(resultCnt == 0) {
						JOptionPane.showMessageDialog(null, "해당되는 데이터가 없습니다.");
					} else {
						JOptionPane.showMessageDialog(null, "예매가 취소되었습니다.");
						new BookingList(userId);
						frame.dispose();
					}
				}
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BookingList(userId);
			}
		});
		// 여기까지 실제 이벤트가 있는 것에 대한 코드 작성

		frame.setSize(426, 779);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	//예매 취소
	public int delete(String userId, int reserveId) {
		String sql = "DELETE FROM RESERVE R WHERE R.USER_ID=? AND R.ID=?";
		conn = DBConnection.getConnection();
		try {
			int returnCnt = 0;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, reserveId);
			returnCnt = pstmt.executeUpdate();

			conn.close();
			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	private void init() {
		backgroundPanel = new JPanel();
		frame.setContentPane(backgroundPanel);

		CustomUI custom = new CustomUI(backgroundPanel);
		custom.setPanel();
		
		// 여기서부터 실제 화면에 표현할 컴포넌트에 대한 코드 작성
		lbMovieTitle = custom.setLbTitle("lbMovieTitle", "", 100, 85, 220, 185, "center");

		lbTitleReserveDt = custom.setLbText2("lbTitleReserveDt", "상영일시", 35, 265, 100, 20);
		lbReserveDt = custom.setLbText3("lbReserveDt", "", 195, 265, 180, 20);

		lbTitleReserveCnt = custom.setLbText2("lbTitleReserveCnt", "예매인원", 35, 325, 100, 20);
		lbReserveCnt = custom.setLbText3("lbReserveCnt", "", 195, 325, 180, 20);

		lbTitleSeat = custom.setLbText2("lbTitleSeat", "좌석번호", 35, 385, 100, 20);
		lbSeat = custom.setLbText3("lbSeat", "", 195, 385, 180, 20);

		lbTitlePrice = custom.setLbText2("lbTitlePrice", "결제금액", 35, 445, 100, 20);
		lbPrice = custom.setLbText3("lbPrice", "", 195, 445, 180, 20);

		lbTitleInsDt = custom.setLbText2("lbTitleInsDt", "예매일자", 35, 505, 130, 20);
		lbInsDt = custom.setLbText3("lbInsDt", "", 195, 505, 180, 20);

		btnCancel = custom.setBtnBlue("btnCancel", "예매취소", 600);
		btnBack = custom.setBtnWhite("btnBack", "이전으로", 655);
		// 여기까지 실제 화면에 표현할 컴포넌트에 대한 코드 작성
	}

}