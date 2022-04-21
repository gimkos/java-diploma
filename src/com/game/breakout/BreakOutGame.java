package com.game.breakout;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.main.Connector;
import com.main.Handler;
import com.main.Main;

public class BreakOutGame extends Canvas implements Runnable{

	private static final long serialVersionUID = 8636345721013109301L;
	
	public final static int WIDTH = 471, HEIGHT = 610;
	
	public final static int SHIFT = 5;
	
	private Thread thread;
	
	public boolean running = false;
	
	private Handler handler;
	
	private HUD hud;
	private Menu menu;
	
	public enum STATE{
		Menu(),
		Game(),
		End(),
		Help()
	};
	public STATE gameState = STATE.Menu;
	
	public BreakOutGame(Main main, Connector connector){

		handler = new Handler();
		
		hud = new HUD();
		
		menu = new Menu(this, handler, hud, new Window(WIDTH, HEIGHT, "BreackOut", this, main), connector);

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
		handler.tick();
		menu.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 5, HEIGHT);
//		g.fillRect(WIDTH/2-7, 0, 1, HEIGHT);
		g.fillRect(WIDTH-21, 0, 5, HEIGHT);
		
		if(gameState == STATE.Game ||  gameState == STATE.Game) {
			g.fillRect(0, 40, WIDTH, 5);
			
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
		new BreakOutGame(null, null);
	}
}
