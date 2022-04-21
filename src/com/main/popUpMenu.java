package com.main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class popUpMenu {
	public popUpMenu(Connector connector, String userID, Main main, MainWindow window) {
		
		window.closeMainWinow();
		
		JFrame frame = new JFrame("user");
		
		frame.setPreferredSize(new Dimension(300, 100));
		frame.setMaximumSize(new Dimension(300, 100));
		frame.setMinimumSize(new Dimension(300, 100));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.setLayout(new FlowLayout());
		
		JLabel jlb = new JLabel("Nickname:");
		JTextField jtf = new JTextField(15);  
		JButton jbt = new JButton("GO!!!");
		
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nickname = jtf.getText();
				
				connector.createUser(nickname);
				
				frame.pack();
				frame.setVisible(false);
				
				window.openMainWinow();
			}
		});
		
		frame.add(jlb);
		frame.add(jtf);
		frame.add(jbt);
		frame.setVisible(true);
		
	}
}
