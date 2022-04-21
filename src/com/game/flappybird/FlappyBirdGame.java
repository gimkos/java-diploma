package com.game.flappybird;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;

import com.main.Connector;
import com.main.GraphicsLoader;
import com.main.Handler;
import com.main.ID;
import com.main.Main;

public class FlappyBirdGame extends Canvas implements Runnable{

	private static final long serialVersionUID = 3297334903253152960L;

	public final static int WIDTH = 432, HEIGHT = 768;
	
	public static boolean isMoving = false;
	
	private Thread thread;
	
	public boolean running = false;
	
	private Handler handler;

	private HUD hud;
	
	private Menu menu;
	
	private Ground ground;
	
	public enum STATE {
		Menu(),
		Game(),
		End(),
		Help()
	};
	
	public STATE gameState = STATE.Menu;
	
	public FlappyBirdGame(Main main, Connector connector){

		ground = new Ground();
		
		handler = new Handler();
		
		hud = new HUD(WIDTH / 2 - 10, 50);
		
		menu = new Menu(this, handler, hud, new Window(WIDTH, HEIGHT, "Falppy Bird", this, main), connector);
		
		handler.addObject(new Bird(WIDTH/2-25, (HEIGHT-220)/2 - 16, ID.Bird, this, handler, hud));
		
		handler.addObject(new Pipe(WIDTH + 200, 518, ID.Pipe));
		
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
		if(gameState != STATE.End) {
			
			if(isMoving) gameState = STATE.Game;
			
			ground.tick();
			
			handler.tick();
	
			hud.tick();
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics(); 
		
		BufferedImage background = GraphicsLoader.loadGraphics("flappybird/background.png");
		g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
		
		ground.render(g);
		
		if(gameState != STATE.Help) handler.render(g);
		
		if(gameState == STATE.Game) hud.render(g);
		
		menu.render(g);

		g.dispose();
		bs.show();
	}
	
	public static int clamp(int var, int min, int max) {
		if(var >= max) return var = max;
		else if(var <= min) return var = min;
		else return var;
	}
	
	
	
	
	public static void main(String[] args) {
		new FlappyBirdGame(null, null);
	}
}
