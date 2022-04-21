package com.game.snake;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.main.Connector;
import com.main.Handler;
import com.main.Main;

public class SnakeGame extends Canvas implements Runnable{

	private static final long serialVersionUID = 344010277057424342L;

	public static final int WIDTH = 650, HEIGHT = 710; // WIDTH / 12 * 9
	
	public static final int SHIFT = 20;
	
	private Thread thread;
	
	public boolean running = false;
	
	private Handler handler;

	private Menu menu;
	
	private HUD hud;
	
	public enum STATE{
		Menu(),
		Game(),
		End(),
		Help()
	};
	
	public STATE gameState = STATE.Menu;
	
	public SnakeGame(Main main, Connector connector) {
		
		
		handler = new Handler();
		
		hud = new HUD();
		
		menu = new Menu(this, handler, hud, new Window(WIDTH, HEIGHT, "Snake", this, main), connector);
		
		this.addKeyListener(new KeyInput(handler));
		
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
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		
		while(running) {
			tick();
			render();
			try{
				Thread.sleep(90);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		stop();
	}
	
	private void tick() {
		if(gameState == STATE.Game) {
			handler.tick();
		}
	}
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		
		g.setColor(Color.decode("#578a34"));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(gameState == STATE.Game) {
			
			for(int i = 0; i<17; i++) {
				for(int j = 0; j < 17; j++) {
					if((i + j) % 2 == 0) {
						g.setColor(Color.decode("#aad751"));
					} 
					else {
						g.setColor(Color.decode("#a2d149"));
					}
					g.fillRect(i * 35 + SHIFT, j * 35 + SHIFT, 35, 35);
				}
			}	
			
			handler.render(g);
			
			hud.render(g);
			
			
		}
		
		else {
			menu.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	public static int clamp(int var, int min, int max) {
		if(var >= max) return var = max;
		else if(var <= min) return var = min;
		else return var;
	}
	public static void main(String[] args) {
		new SnakeGame(null, null);
	}

}
