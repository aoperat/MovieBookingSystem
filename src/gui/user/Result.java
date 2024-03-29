package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.ReserveDao;
import models.Reserves;

@SuppressWarnings("serial")
public class Result extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JLabel lbIcon, lbTitle;
	private JLabel lbTitleMovie, lbMovie, lbTitleDate, lbDate, lbTitleCnt, lbCnt, lbTitleSeat, lbSeat, lbTitlePrice, lbPrice, lbTitleDt, lbDt;
	private JButton btnMain;
	
	private String userId;

	public Result(String userId) {
		this.userId = userId;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

		// 여기서부터 실제 이벤트가 있는 것에 대한 코드 작성
		ReserveDao rDao = ReserveDao.getInstance();
		Reserves reserve = rDao.selectRecent(userId);
		lbMovie.setText(reserve.getMovieTitle());
		lbDate.setText(reserve.getReserveDate());
		lbCnt.setText(reserve.getReserveCnt()+"인");
		lbSeat.setText(reserve.getSeat().replace(",", ", "));
		lbPrice.setText(NumberFormat.getInstance().format(reserve.getPrice()) + "원");
		lbDt.setText(reserve.getInsDt());
		
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

		lbIcon = custom.setLbImg("lbIcon", 4, 160, 130);
		lbTitle = custom.setLbTitle("lbTitle", "예매가 완료되었습니다", 100, 150, 220, 185, "center");

		lbTitleMovie = custom.setLbText2("lbTitleMovie", "영화제목", 35, 310, 100, 20);
		lbMovie = custom.setLbText3("lbMovie", "계절과 계절 사이", 195, 310, 180, 20);

		lbTitleDate = custom.setLbText2("lbTitleDate", "상영일자", 35, 360, 100, 20);
		lbDate = custom.setLbText3("lbDate", "19.10.08", 195, 360, 180, 20);

		lbTitleCnt = custom.setLbText2("lbTitleCnt", "예매인원", 35, 410, 100, 20);
		lbCnt = custom.setLbText3("lbCnt", "2인", 195, 410, 180, 20);

		lbTitleSeat = custom.setLbText2("lbTitleSeat", "좌석번호", 35, 460, 100, 20);
		lbSeat = custom.setLbText3("lbSeat", "A4, E8", 195, 460, 180, 20);

		lbTitlePrice = custom.setLbText2("lbTitlePrice", "결제금액", 35, 510, 100, 20);
		lbPrice = custom.setLbText3("lbPrice", "7,000원", 195, 510, 180, 20);

		lbTitleDt = custom.setLbText2("lbTitleDt", "예매일자", 35, 560, 130, 20);
		lbDt = custom.setLbText3("lbDt", "19.10.05 12:27", 195, 560, 180, 20);

		btnMain = custom.setBtnBlue("btnMain", "메인으로", 655);

		// 여기까지 실제 화면에 표현할 컴포넌트에 대한 코드 작성
	}
}