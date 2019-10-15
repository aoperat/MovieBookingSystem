package gui.admin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.ComboDao;
import dao.ScreenDao;
import models.Combo;
import models.Movies;
import util.Utils;

@SuppressWarnings("serial")
public class Screen extends JDialog {

	private JPanel contentPane;
	private JLabel lbTitle, lbMovieTitle, lbPlace, lbTheater, lbDate, lbBtwDate, lbTime;
	private JComboBox<Combo> comboPlace, comboTheater, comboMovie;
	private JTextField tfStartDate, tfEndDate, tfTime;
	private JButton btnInsUpd, btnDelCan;
	
	public Screen(Main mainFrame, int status, int id) {
		Main main = mainFrame;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 448, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		init();

		Utils util = new Utils();

		ComboDao cDao = ComboDao.getInstance();
		Vector<Combo> comboMovies = cDao.setCombo("movie");
		if (comboMovies == null) {
			Combo comboNull = new Combo(0, "없음");
			comboMovie.addItem(comboNull);
			JOptionPane.showMessageDialog(this, "ER1:영화가 존재하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
		} else {
			for (Combo combo : comboMovies) {
				comboMovie.addItem(combo);
			}
		}

		Vector<Combo> comboPlaces = cDao.setCombo("place");
		if (comboPlaces == null) {
			Combo comboNull = new Combo(0, "없음");
			comboPlace.addItem(comboNull);
			JOptionPane.showMessageDialog(this, "ER1:지점이 존재하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
		} else {
			for (Combo combo : comboPlaces) {
				comboPlace.addItem(combo);
			}
		}

		comboPlace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Combo selectedPlace = (Combo) comboPlace.getSelectedItem();
				int placeId = selectedPlace.getKey();
				comboTheater.removeAllItems();
				
				Vector<Combo> comboTheaters = cDao.setCombo("theater", placeId);
				if (comboTheaters == null) {
					Combo comboNull = new Combo(0, "없음");
					comboTheater.addItem(comboNull);
					JOptionPane.showMessageDialog(null, "ER1:상영관이 존재하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
				} else {
					for (Combo combo : comboTheaters) {
						comboTheater.addItem(combo);
					}
				}
			}
		});

//		comboTheater.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				Combo selectedTheater = (Combo) comboTheater.getSelectedItem();
//				if (selectedTheater != null) {
//					int theaterId = selectedTheater.getKey();
//				}
//			}
//		});

		ScreenDao sdao = ScreenDao.getInstance();
		Movies movie;

		int executeCd = 1;

		if (status == 1) {
			btnInsUpd.setText("입력");
			btnDelCan.setText("취소");
		} else if (status == 2) {
			movie = sdao.selectOne(id);
			if (movie == null) {
				JOptionPane.showMessageDialog(this, "ER1:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				tfStartDate.setText(movie.getStartDate());
				tfTime.setText(movie.getStartTime());
				tfEndDate.setText(movie.getEndDate());
				comboMovie.setSelectedIndex(util.getComboIndex(comboMovie, movie.getId()));
				comboPlace.setSelectedIndex(util.getComboIndex(comboPlace, movie.getPlaceId()));
				comboTheater.setSelectedIndex(util.getComboIndex(comboTheater, movie.getTheaterId()));

				btnInsUpd.setText("수정");
				btnDelCan.setText("취소");
			}
		} else if (status == 3) {
			movie = sdao.selectOne(id);
			if (movie == null) {
				JOptionPane.showMessageDialog(this, "ER2:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				tfStartDate.setText(movie.getStartDate());
				tfTime.setText(movie.getStartTime());
				tfEndDate.setText(movie.getEndDate());
				comboMovie.setSelectedIndex(util.getComboIndex(comboMovie, movie.getId()));
				comboPlace.setSelectedIndex(util.getComboIndex(comboPlace, movie.getPlaceId()));
				comboTheater.setSelectedIndex(util.getComboIndex(comboTheater, movie.getTheaterId()));

				tfStartDate.setEditable(false);
				tfTime.setEditable(false);
				tfEndDate.setEditable(false);
				comboMovie.setEnabled(false);
				comboPlace.setEnabled(false);
				comboTheater.setEnabled(false);

				btnInsUpd.setText("수정");
				btnDelCan.setText("삭제");
			}
		}

		btnInsUpd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String statusText = btnInsUpd.getText();
				int result = 0;

				Combo selectedMovie = (Combo) comboMovie.getSelectedItem();
				int movieId = selectedMovie.getKey();
				
				Combo selectedPlace = (Combo) comboPlace.getSelectedItem();
				int placeId = selectedPlace.getKey();

				Combo selectedTheater = (Combo) comboTheater.getSelectedItem();
				int theaterId = selectedTheater.getKey();
				
				String startDate = tfStartDate.getText();
				String endDate = tfEndDate.getText();
				String startTime = tfTime.getText();

				if (statusText.equals("입력")) {
					result = sdao.insert(movieId, placeId, theaterId, startDate, endDate, startTime);
					if (result == -1) {
						JOptionPane.showMessageDialog(contentPane, "ER3:상영정보 데이터를 입력할 수 없습니다.\n", "오류", JOptionPane.ERROR_MESSAGE);
					} else if (result == 0) {
						JOptionPane.showMessageDialog(contentPane, "ER4:상영정보 데이터를 입력할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(contentPane, "데이터 입력이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
						main.screenTable.setModel(main.setScreenTable(null));
						util.hiddenTableColumn(main.screenTable, 0);
						dispose();
					}
				} else if (statusText.equals("수정")) {
					if (status == 2) {
						result = sdao.update(id, movieId, placeId, theaterId, startDate, endDate, startTime);
						if (result == -1) {
							JOptionPane.showMessageDialog(contentPane, "ER5:상영정보 데이터를 수정할 수 없습니다.\n", "오류", JOptionPane.ERROR_MESSAGE);
						} else if (result == 0) {
							JOptionPane.showMessageDialog(contentPane, "ER6:상영정보 데이터를 수정할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(contentPane, "데이터 수정이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							main.screenTable.setModel(main.setScreenTable(null));
							util.hiddenTableColumn(main.screenTable, 0);
							dispose();
						}
					} else if (status == 3) {
						new Screen(main, 2, id);
						dispose();
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "ER7:정상적인 호출이 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});

		btnDelCan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String statusText = btnDelCan.getText();
				if (statusText.equals("삭제")) {
					int result = 0;
					int check = JOptionPane.showConfirmDialog(contentPane, "삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (check == JOptionPane.YES_OPTION) {
						result = sdao.delete(id);
						if (result == -1) {
							JOptionPane.showMessageDialog(contentPane, "ER8:상영정보 데이터를 삭제할 수 없습니다.\n", "오류", JOptionPane.ERROR_MESSAGE);
						} else if (result== 0) {
							JOptionPane.showMessageDialog(contentPane, "ER9:상영정보 데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							main.screenTable.setModel(main.setScreenTable(null));
							util.hiddenTableColumn(main.screenTable, 0);
							dispose();
						}
					}
				} else if (statusText.equals("취소")) {
					dispose();
				} else {
					JOptionPane.showMessageDialog(contentPane, "ER10:정상적인 호출이 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});

		setVisible(true);
		
		if(executeCd == -1) {
			dispose();
		}
	}

	private void init() {
		lbTitle = new JLabel("상영정보");
		lbTitle.setFont(new Font("Dialog", Font.BOLD, 20));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(132, 0, 137, 40);
		contentPane.add(lbTitle);

		lbMovieTitle = new JLabel("영화제목");
		lbMovieTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lbMovieTitle.setBounds(37, 50, 80, 20);
		contentPane.add(lbMovieTitle);
		
		comboMovie = new JComboBox<Combo>();
		comboMovie.setBounds(129, 50, 168, 20);
		contentPane.add(comboMovie);

		lbDate = new JLabel("상영기간");
		lbDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lbDate.setBounds(37, 80, 80, 20);
		contentPane.add(lbDate);

		tfStartDate = new JTextField();
		tfStartDate.setColumns(10);
		tfStartDate.setBounds(129, 80, 86, 20);
		contentPane.add(tfStartDate);

		lbBtwDate = new JLabel("~");
		lbBtwDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbBtwDate.setBounds(218, 84, 20, 15);
		contentPane.add(lbBtwDate);

		tfEndDate = new JTextField();
		tfEndDate.setBounds(240, 80, 86, 20);
		contentPane.add(tfEndDate);

		lbTime = new JLabel("상영시간");
		lbTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lbTime.setBounds(228, 110, 80, 20);
		contentPane.add(lbTime);

		tfTime = new JTextField();
		tfTime.setBounds(320, 110, 76, 20);
		contentPane.add(tfTime);

		lbPlace = new JLabel("상영지점");
		lbPlace.setHorizontalAlignment(SwingConstants.RIGHT);
		lbPlace.setBounds(37, 110, 80, 20);
		contentPane.add(lbPlace);

		comboPlace = new JComboBox<Combo>();
		comboPlace.setBounds(129, 110, 102, 20);
		contentPane.add(comboPlace);

		lbTheater = new JLabel("상영관");
		lbTheater.setHorizontalAlignment(SwingConstants.RIGHT);
		lbTheater.setBounds(61, 138, 56, 20);
		contentPane.add(lbTheater);

		comboTheater = new JComboBox<Combo>();
		comboTheater.setBounds(129, 138, 102, 20);
		contentPane.add(comboTheater);

		btnInsUpd = new JButton();
		btnInsUpd.setBounds(99, 280, 76, 28);
		contentPane.add(btnInsUpd);

		btnDelCan = new JButton();
		btnDelCan.setBounds(199, 280, 76, 28);
		contentPane.add(btnDelCan);
	}
	
}