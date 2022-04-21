package com.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable{

	private static final long serialVersionUID = 7086056870984540910L;

	public final static int WIDTH = 650, HEIGHT = 488;
	
	private Thread thread;
	
	private User user = new User();
	
	private Connector connector;
	
	private MainWindow window;
	
	private Menu menu;
	
	public boolean running = false;
	
	private String userID;
	
	public enum STATE{
		Main(),
		Games(),
		Score(),
		InGame()
	}
	
	public STATE mainSTATE = STATE.Main;
	
	public Main() {

		if(!user.isUserFileExist()) user.createUser();
		
		userID = user.getUser();	
		
		connector = new Connector(userID);
		
		window = new MainWindow(WIDTH, HEIGHT, "Games", this);
		
		menu = new Menu(this, connector, window);
		
		if(connector.isConnected()) {
			
			if(!connector.isUserExist()) new popUpMenu(connector, userID, this, window);
			
			else window.openMainWinow();
			
		} 
		
		else {System.out.println("not Connected");}
		
		this.addMouseListener(menu);
		
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		while(running) {
			tick();
			render();
			
			try{
				Thread.sleep(30);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		stop();
	}
	private void tick() {
	}
	
	private void render() {
		if(mainSTATE != STATE.InGame) {
			BufferStrategy bs = this.getBufferStrategy();
			
			if(bs == null) {
				this.createBufferStrategy(3);
				return;
			}
			
			Graphics g = bs.getDrawGraphics(); 
			
			g.setColor(Color.BLACK);
			
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			menu.render(g);
			
			
			g.dispose();
			bs.show();
		}
	}
	
	public void showMainWindow() {
		window.openMainWinow();
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
}
