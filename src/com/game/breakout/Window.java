package com.game.breakout;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.main.Main;
import com.main.Main.STATE;

public class Window extends Canvas{

	private static final long serialVersionUID = -341511858266813397L;
	
	JFrame frame;
	
	private Main main;
	
	public Window(int width, int height, String title, BreakOutGame game, Main main) {
		
		this.main = main;
		
		frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension((int) width, (int) height));
		frame.setMaximumSize(new Dimension((int) width, (int) height));
		frame.setMinimumSize(new Dimension((int) width, (int) height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.add(game);
		
		frame.setVisible(true);
		
		game.start();
	}
	public void goToMain() {
		frame.setVisible(false);
		frame.dispose();
		main.mainSTATE = STATE.Main;
		main.showMainWindow();
	}
}
