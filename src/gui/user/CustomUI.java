package gui.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import models.Combo;

@SuppressWarnings("serial")
class CustomUI extends JFrame {
	JPanel backgroundPanel;
	
	public CustomUI() {}
	
	public CustomUI(JPanel backgroundPanel) {
		this.backgroundPanel = backgroundPanel;
	}

	protected void setPanel() {
		backgroundPanel.setLayout(null);
		backgroundPanel.setBackground(Color.WHITE);
		
		JPanel topBluePanel = new JPanel();
		topBluePanel.setBounds(0, 0, 420, 70);
		topBluePanel.setBackground(new Color(53, 121, 247));
		backgroundPanel.add(topBluePanel);
		
		JPanel topGrayPanel = new JPanel();
		topGrayPanel.setBounds(0, 70, 420, 50);
		topGrayPanel.setBackground(new Color(230, 236, 240));
		backgroundPanel.add(topGrayPanel);
	}
	
	protected JTextField setTextField(String name, String placeholder, int x, int y, int width, int height) {
		JTextField txt = new JTextField();
		
		if (placeholder == null) {
			txt.setText("Please input here");
		} else {
			txt.setText(placeholder);
		}
		
		Font tfFont = new Font("Arial", Font.PLAIN, 20);
		txt.setFont(tfFont);
		txt.setBackground(Color.white);
		txt.setForeground(Color.gray.brighter());
		
		txt.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField tf = (JTextField)e.getComponent();
				if(tf.getText().equals("")) {
					if (placeholder == null) {
						tf.setForeground(Color.gray.brighter());
						tf.setText("Please input here");
					} else {
						tf.setForeground(Color.gray.brighter());
						tf.setText(placeholder);
					}
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				JTextField tf = (JTextField)e.getComponent();
				if (tf.getText().equals(placeholder) || tf.getText().equals("Please input here") || tf.getText().equals("")) {
					tf.setText("");
					tf.setForeground(Color.BLACK);
				}
			}
		});
		
		txt.setBounds(x, y, width, height);
		backgroundPanel.add(txt);
		txt.setName(name);
		
		return txt;
	}
	
	protected JPasswordField setPasswordField(String name, String placeholder, int x, int y, int width, int height) {
		JPasswordField txt = new JPasswordField();
		
		if (placeholder == null) {
			txt.setText("Please input here");
		} else {
			txt.setText(placeholder);
		}
		
		Font tfFont = new Font("Arial", Font.PLAIN, 20);
		txt.setFont(tfFont);
		txt.setBackground(Color.white);
		txt.setForeground(Color.gray.brighter());
		
		txt.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField tf = (JTextField)e.getComponent();
				if(tf.getText().equals("")) {
					if (placeholder == null) {
						tf.setForeground(Color.gray.brighter());
						tf.setText("Please input here");
					} else {
						tf.setForeground(Color.gray.brighter());
						tf.setText(placeholder);
					}
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				JTextField tf = (JTextField)e.getComponent();
				if (tf.getText().equals(placeholder) || tf.getText().equals("Please input here") || tf.getText().equals("")) {
					tf.setText("");
					tf.setForeground(Color.BLACK);
				}
			}
		});
		
		txt.setBounds(x, y, width, height);
		backgroundPanel.add(txt);
		txt.setName(name);
		
		return txt;
	}
	
	protected JButton setBtnBlue(String name, String text, int y) {
		JButton btn = new JButton();
		
		Font btnFont = new Font("맑은 고딕", Font.PLAIN, 20);
		btn.setFont(btnFont);
		btn.setBackground(new Color(53, 121, 247));
		btn.setForeground(Color.WHITE);
		btn.setBorderPainted(false);
		btn.setBounds(35, y, 350, 45);
		btn.setText(text);
		backgroundPanel.add(btn);
		btn.setName(name);
		
		return btn;
	}
	
	protected JButton setBtnWhite(String name, String text, int y) {
		JButton btn = new JButton();
		
		Font btnFont = new Font("맑은 고딕", Font.PLAIN, 20);
		btn.setFont(btnFont);
		btn.setBackground(Color.WHITE);
		btn.setForeground(new Color(53, 121, 247));
		btn.setBounds(35, y, 350, 45);
		btn.setText(text);
		backgroundPanel.add(btn);
		btn.setName(name);
		
		return btn;
	}
	
	protected JButton setBtnMovie(String name, String time, String seatCnt, int x, int y) {
		JButton btn = new JButton("<html>" + time + "<br/>" + seatCnt + "</html>");
//		JLabel lbStartTime = new JLabel(time);
//		JLabel lbSeatCnt = new JLabel("\n\n" + seatCnt);
		
//		Font lbTimeFont = new Font("맑은 고딕", Font.PLAIN, 15);
//		lbStartTime.setFont(lbTimeFont);
//		lbStartTime.setForeground(Color.BLACK);
//		Font lbSeatFont = new Font("맑은 고딕", Font.PLAIN, 10);
//		lbSeatCnt.setFont(lbSeatFont);
//		lbSeatCnt.setForeground(new Color(114, 114, 114));
		
		btn.setBackground(new Color(230, 236, 240));
		btn.setForeground(new Color(114, 114, 114));
		btn.setBorderPainted(false);
		btn.setBounds(x, y, 90, 70);

//		btn.add(lbStartTime);
//		btn.add(lbSeatCnt);
		backgroundPanel.add(btn);
		btn.setName(name);
		
		return btn;
	}
	
	protected JButton setBtnSeat(String name, String seat, int x, int y) {
		JButton btn = new JButton(seat);
		
		Font btnFont = new Font("맑은 고딕", Font.BOLD, 14);
		btn.setFont(btnFont);
		btn.setBackground(new Color(230, 236, 240));
		btn.setForeground(new Color(114, 114, 114));
		btn.setBorderPainted(false);
		btn.setBounds(x, y, 53, 48);
		backgroundPanel.add(btn);
		btn.setName(name);
		
		return btn;
	}
	
	protected JLabel SetLb(String name, String text, int x, int y, int width, int height, String alignment, int fontSize) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.PLAIN, fontSize);
		lb.setFont(lbFont);
		lb.setForeground(new Color(114, 114, 114));
		lb.setHorizontalAlignment(setAlign(alignment));	
		lb.setBounds(x, y, width, height);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}
/* 작업완료 후 코드 재정리 필요 : 중복되는 label 너무 많음 : 매개변수화하기	
	protected JLabel SetLbEm(String name, String text, int x, int y, int width, int height, String alignment, int fontSize) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.BOLD, fontSize);
		lb.setFont(lbFont);
		lb.setForeground(new Color(114, 114, 114));
		lb.setHorizontalAlignment(setAlign(alignment));	
		lb.setBounds(x, y, width, height);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}
*/
	protected JLabel setLbTitle(String name, String text, int x, int y, int width, int height, String alignment) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.BOLD, 20);
		lb.setFont(lbFont);
		lb.setForeground(new Color(114, 114, 114));
		lb.setHorizontalAlignment(setAlign(alignment));	
		lb.setBounds(x, y, width, height);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}
	
	protected JLabel setLbContents(String name, String text, int x, int y, int width, int height, String alignment) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.PLAIN, 15);
		lb.setFont(lbFont);
		lb.setForeground(new Color(114, 114, 114));
		lb.setHorizontalAlignment(setAlign(alignment));	
		lb.setBounds(x, y, width, height);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}
	
	protected JLabel setLbEmContents(String name, String text, int x, int y, int width, int height, String alignment) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.ITALIC | Font.BOLD, 15);
		lb.setFont(lbFont);
		lb.setForeground(new Color(53, 121, 247));
		lb.setHorizontalAlignment(setAlign(alignment));	
		lb.setBounds(x, y, width, height);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}

	protected JLabel setLbEmContents(String name, String text, int x, int y, int width, int height) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.ITALIC | Font.BOLD, 18);
		lb.setFont(lbFont);
		lb.setForeground(new Color(53, 121, 247));
		lb.setBounds(x, y, width, height);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}
	
	protected JLabel setLbBox(String name, String text, int x, int y) {
		JLabel lb = new JLabel(text);
		int age = Integer.parseInt(text);
		
		if(age == 99) {
			lb.setFont(new Font("맑은 고딕", Font.BOLD, 0));
			lb.setBackground(new Color(53, 121, 247));
		} else if(age >= 19) {
			lb.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			lb.setBackground(Color.RED);
		} else if (age <= 0) {
			lb.setFont(new Font("맑은 고딕", Font.BOLD, 10));
			lb.setBackground(Color.BLUE);
			lb.setText("전체");
		} else {
			lb.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			lb.setBackground(Color.GREEN);
		}
		lb.setOpaque(true);
		lb.setForeground(Color.WHITE);
		lb.setHorizontalAlignment(SwingConstants.CENTER);
		lb.setBounds(x, y, 27, 27);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}
	
	protected JLabel setLbImg(String name, int iconNum, int x, int y) {
		JLabel lb = new JLabel();

		ImageIcon imgIc = new ImageIcon("img/icon"+iconNum+".png");
		Image img = imgIc.getImage();
		if(iconNum == 1 || iconNum == 2) {
			img = img.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
		} else if(iconNum == 3) {
			img = img.getScaledInstance(100, 70, Image.SCALE_SMOOTH);
		} else if(iconNum == 4) {
			img = img.getScaledInstance(80, 56, Image.SCALE_SMOOTH);
		} else if(iconNum == 5) {
			img = img.getScaledInstance(60, 55, Image.SCALE_SMOOTH);
		}
		ImageIcon resizeImgIc = new ImageIcon(img);
		lb.setIcon(resizeImgIc);
		lb.setHorizontalAlignment(SwingConstants.CENTER);		
		lb.setBounds(x, y, 100, 100);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}
	
	protected JCheckBox setCheckBox(String name, String text, int x, int y) {
		JCheckBox cb = new JCheckBox();
		cb.setBackground(Color.WHITE);
		cb.setIcon(new ImageIcon("img/checkbox.png"));
		cb.setSelectedIcon(new ImageIcon("img/selectedcheckbox.png"));
		cb.setBounds(x, y, 31, 31);
		backgroundPanel.add(cb);
		cb.setName(name);
		
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.PLAIN, 16);
		lb.setFont(lbFont);
		lb.setForeground(new Color(114, 114, 114));
		lb.setLocation(x+35, y-3);
		lb.setSize(300, 35);
		backgroundPanel.add(lb);
		
		return cb;
	}
	
	protected JButton setbtnBar(String name, String text, int y) {
		JButton btn = new JButton();
		
		Font btnFont = new Font("맑은 고딕", Font.BOLD, 14);
		btn.setFont(btnFont);
		btn.setBackground(new Color(230, 236, 240));
		btn.setForeground(new Color(114, 114, 114));
		btn.setBorderPainted(false);
		btn.setBounds(45, y, 334, 40);
		btn.setText(text);
		backgroundPanel.add(btn);
		btn.setName(name);
		
		return btn;
	}
	
	protected JComboBox<Combo> setCombo(String name, Vector<Combo> combos, int x, int y, int width, int height){
		JComboBox<Combo> combo = new JComboBox<>();
		
		if(combos == null) {
			Combo comboNull = new Combo(0, "없음");
			combo.addItem(comboNull);
		} else {
			for (Combo c : combos) {
				combo.addItem(c);
			}
		}
		
		combo.setBackground(Color.WHITE);
		combo.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		combo.setBounds(x, y, width, height);
		backgroundPanel.add(combo);
		combo.setName(name);
		
		return combo;
	}
	
	protected JComboBox<String> setCombo(String name, String[] text, int x, int y, int width, int height){
		JComboBox<String> combo = new JComboBox<>(text);
		
		combo.setBackground(Color.WHITE);
		combo.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		combo.setBounds(x, y, width, height);
		backgroundPanel.add(combo);
		combo.setName(name);
		
		return combo;
	}
	
	private int setAlign(String alignment) {
		if(alignment.toUpperCase().equals("CENTER")) {
			return 0;
		} else if(alignment.toUpperCase().equals("LEFT")) {
			return 2;
		}  else if(alignment.toUpperCase().equals("RIGHT")) {
			return 4;
		} else {
			return 0;
		}
	}
	
	protected JLabel setLbText2(String name, String text, int x, int y, int width, int height) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.BOLD, 17);
		lb.setFont(lbFont);
		lb.setForeground(new Color(114, 114, 114));
		lb.setBounds(x, y, width, height);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}
	
	protected JLabel setLbText3(String name, String text, int x, int y, int width, int height) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.PLAIN, 17);
		lb.setHorizontalAlignment(SwingConstants.RIGHT);
		lb.setFont(lbFont);
		lb.setForeground(new Color(0,0,0));
		lb.setBounds(x, y, width, height);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}

	protected JLabel setLbFont(String name, String text, int x, int y, int width, int height) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.PLAIN, 14);
		lb.setFont(lbFont);
		lb.setForeground(new Color(0, 0, 0));
		lb.setBounds(x, y, width, height);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}

		protected JLabel setLbRoomFont(String name, String text, int x, int y, int width, int height) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.BOLD, 14);
		lb.setFont(lbFont);
		lb.setForeground(new Color(53, 121, 247));
		lb.setBounds(x, y, width, height);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}

		protected JLabel setLbTimeFont(String name, String text, int x, int y, int width, int height) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.PLAIN, 13);
		lb.setHorizontalAlignment(SwingConstants.RIGHT);
		lb.setFont(lbFont);
		lb.setForeground(new Color(114, 114, 114));
		lb.setBounds(x, y, width, height);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}
		
		protected JList<String> setList(String name, String[] text, int x) {
		JList<String> list = new JList<>(text);
		
		list.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		list.setForeground(new Color(114, 114, 114));
		
		JLabel lb = (JLabel)list.getCellRenderer();
		lb.setPreferredSize(new Dimension(200, 50));
		
		JScrollPane sp = new JScrollPane(list);
		sp.setBounds(x, 120, 210, 630);
	    backgroundPanel.add(sp);
		
		return list;
	}
		
		protected JList<Combo> setList(String name, DefaultListModel listModel, int x) {
	      JList<Combo> list = new JList<>(listModel);
	      list.setFont(new Font("맑은 고딕", Font.BOLD, 15));
	      list.setForeground(new Color(114, 114, 114));
	      
	      JLabel lb = (JLabel)list.getCellRenderer();
	      lb.setPreferredSize(new Dimension(200, 50));
	      
	      JScrollPane sp = new JScrollPane(list);
	      sp.setBounds(x, 120, 420, 630);
	       backgroundPanel.add(sp);
	       list.setName(name);
	      
	      return list;
	   }
		
		protected JButton setBtnImg(String name, String text, int x, int y) {
		ImageIcon icon = new ImageIcon("img/icon5.png");
		JButton btn = new JButton(text, icon);
		
		Font btnFont = new Font("맑은 고딕", Font.PLAIN, 18);
		btn.setFont(btnFont);
		btn.setBackground(new Color(53, 121, 247));
		btn.setForeground(Color.WHITE);
		btn.setVerticalTextPosition(SwingConstants.BOTTOM);
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
		
		btn.setBorderPainted(false);
		btn.setBounds(x, y, 170, 150);
		btn.setText(text);
		backgroundPanel.add(btn);
		btn.setName(name);
		
		return btn;
	}
}