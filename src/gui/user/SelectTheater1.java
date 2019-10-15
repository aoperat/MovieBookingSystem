package gui.user;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import dao.ComboDao;
import dao.DBConnection;
import models.Combo;
import models.Places;

@SuppressWarnings("serial")
public class SelectTheater1 extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JList<Combo> liPlace;
	private JList<String> liTheater;

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;

	private String userId;

	public SelectTheater1(String userId) {
		this.userId = userId;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

		// 여기서부터 실제 이벤트가 있는 것에 대한 코드 작성
		liPlace.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				Combo place = liPlace.getSelectedValue();
				int placeId = place.getKey();
				new SelectDate(userId, placeId, "Theater");
				frame.dispose();
			}
		});

		frame.setSize(426, 779);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private void init() {
		conn = DBConnection.getConnection();
		ArrayList<Places> places = new ArrayList<>();

		try {
			pstmt = conn.prepareStatement("SELECT * FROM PLACE");
			rs = pstmt.executeQuery();

			// get DB item
			while (rs.next()) {
				Places place = new Places();
				place.setId(rs.getInt("ID"));
				place.setName(rs.getString("NAME"));
				place.setAddr(rs.getString("ADDR"));
				places.add(place);
			} // end of while

			// origin
			backgroundPanel = new JPanel();
			frame.setContentPane(backgroundPanel);

			CustomUI custom = new CustomUI(backgroundPanel);
			custom.setPanel();
			
			// 여기서부터 실제 화면에 표현할 컴포넌트에 대한 코드 작성
			String place[] = new String[places.size()];
			for (int i = 0; i < place.length; i++) {
				place[i] = places.get(i).getName();
			}

			ComboDao cDao = ComboDao.getInstance();
			DefaultListModel<Combo> listModel = new DefaultListModel<>();
			Vector<Combo> comboPlaces = cDao.setCombo("place");
			
			for (Combo combo : comboPlaces) {
				listModel.addElement(combo);
			}
			
			liPlace = custom.setList("liPlace", listModel, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 여기까지 실제 화면에 표현할 컴포넌트에 대한 코드 작성
	}
}