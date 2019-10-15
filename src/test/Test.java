package test;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class Test extends JFrame {

	public Test() {
		getContentPane().setLayout(null);
		
		
		JLabel titleLbl = new JLabel("Title");
		titleLbl.setBounds(10, 20, 370, 400);
		Border border = BorderFactory.createLineBorder(Color.BLUE, 5);
		titleLbl.setBorder(border);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.add(titleLbl);
		panel.setPreferredSize(new Dimension(380, 500));
		System.out.println(panel.getPreferredSize());
		
		JScrollPane sp = new JScrollPane();
		sp.setViewportView(panel);

		sp.setBounds(0, 0, 200, 200);
		getContentPane().add(sp);
	}

	public static void main(String[] args) {
		new Test();
		Test main = new Test();
		main.setSize(400, 400);
		main.setVisible(true);
	}
}
