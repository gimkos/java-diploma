package com.main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;


public class MainWindow extends Canvas{

	private static final long serialVersionUID = 5716874425127500134L;
	
	public JFrame frame;
	
	public MainWindow(int width, int height, String title, Main main){
		
		frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension((int) width, (int) height));
		frame.setMaximumSize(new Dimension((int) width, (int) height));
		frame.setMinimumSize(new Dimension((int) width, (int) height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.add(main);
		
		frame.setVisible(true);

		main.start();
	}
	
	public void closeMainWinow() {
		frame.setVisible(false);
	}
	
	public void openMainWinow() {
		frame.setVisible(true);
	}
}
