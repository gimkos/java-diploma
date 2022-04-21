package com.game.breakout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.game.breakout.BreakOutGame.STATE;
import com.main.Connector;
import com.main.Handler;
import com.main.ID;

public class Menu extends MouseAdapter{

	private BreakOutGame game;
	
	private Handler handler;
	
	private HUD hud;
	
	private Window window;
	
	private Connector connector;
	
	private boolean newHighestScore; 
	
	Menu(BreakOutGame game, Handler handler, HUD hud, Window window, Connector connector){
		this.game = game;
		
		this.handler = handler;
		
		this.hud = hud;
		
		this.window = window;
		
		this.connector = connector;
		
		newHighestScore = false;
	}
	
	public void tick(){
		if(hud.getLife() <= 0) {
			game.gameState = STATE.End;
			handler.object.clear();
		}
	}
	
	public void render(Graphics g) {
		Font font1 = new Font("Arial", 0, 40);
		Font font2 = new Font("Arial", 0, 35);
				
		if(game.gameState == STATE.Menu) {
			g.setFont(font1);
			g.setColor(Color.WHITE);
			g.drawString("BreakOut", BreakOutGame.WIDTH/2-91, 150);
			
			g.setFont(font2);
			g.drawRect(BreakOutGame.WIDTH/2-105, 195, 200, 64);
			g.drawString("PLAY", BreakOutGame.WIDTH/2-43, 238);
			
			g.setFont(font2);
			g.drawRect(BreakOutGame.WIDTH/2-105, 284, 200, 64);
			g.drawString("HELP", BreakOutGame.WIDTH/2-48, 327);
			
			g.setFont(font2);
			g.drawRect(BreakOutGame.WIDTH/2-105, 373, 200, 64);
			g.drawString("EXIT", BreakOutGame.WIDTH/2-45, 416);
		}
		else if(game.gameState == STATE.End) {
			String str;
			int shift;
			if(connector.isConnected() && !newHighestScore) {
				if(connector.isNewHighScore(2, hud.getScore())) {
					newHighestScore = true;
				}
			}
			if(newHighestScore) {
				g.setFont(new Font("Arial", 0, 28));
				g.setColor(Color.RED);
				str = "Your new Highest Score is: ";
				shift = 195;
			}
			else {
				g.setFont(font1);
				g.setColor(Color.WHITE);
				str = "Your score is: ";
				shift = 140;
			}
			
			
			g.drawString(str + hud.getScore(), BreakOutGame.WIDTH/2-shift, 150);
			g.setColor(Color.WHITE);
			g.setFont(font2);
			g.drawRect(BreakOutGame.WIDTH/2-100, 284, 200, 64);
			g.drawString("RESTART", BreakOutGame.WIDTH/2-78, 328);
			
			g.setFont(font2);
			g.drawRect(BreakOutGame.WIDTH/2-105, 373, 200, 64);
			g.drawString("EXIT", BreakOutGame.WIDTH/2-45, 416);
		}
		else if(game.gameState == STATE.Help) {
			g.setFont(font1);
			g.setColor(Color.WHITE);
			g.drawString("HELP", BreakOutGame.WIDTH/2-63, 150);
			int newline = g.getFont().getSize() + 5 ;
			
			String[] strings = {"MOVMENT", "       A D"};
			
			int y = 200;
			
			for( int i=0; i<strings.length; i++ ) {
	            g.drawString(strings[i], BreakOutGame.WIDTH/2-115, y += newline );
	        }
			
			g.setFont(font2);
			g.drawRect(BreakOutGame.WIDTH/2-105, 373, 200, 64);
			g.drawString("BACK", BreakOutGame.WIDTH/2-45, 416);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY();
		
		if(game.gameState == STATE.Menu) {
			if(mouseOver(mx, my, BreakOutGame.WIDTH/2-105, 195, 200, 64)) {
				
				game.gameState = STATE.Game;
				
				spawnObjects();
				
			}
			else if(mouseOver(mx, my, BreakOutGame.WIDTH/2-105, 284, 200, 64)) {
				
				game.gameState = STATE.Help;
			}
			else if(mouseOver(mx, my, BreakOutGame.WIDTH/2-105, 373, 200, 64)) {
				
				game.running = false;
				window.goToMain();
			
			}
		}
		else if(game.gameState == STATE.End) {
			hud.setLife(3);
			
			if(mouseOver(mx, my, BreakOutGame.WIDTH/2-100, 284, 200, 64)) {
				
				newHighestScore = false;
				
				game.gameState = STATE.Game;
				
				spawnObjects();
				
				hud.setScore(0);
			}
			else if(mouseOver(mx, my, BreakOutGame.WIDTH/2-105, 373, 200, 64)) {
				newHighestScore = false;
				game.gameState = STATE.Menu;
			
			}
		}
		else if(game.gameState == STATE.Help) {
			if(mouseOver(mx, my, BreakOutGame.WIDTH/2-105, 373, 200, 64)) {
				game.gameState = STATE.Menu;
			}
		}
	
	}
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width && my > y && my < y + height) {
			return true;
		}
		
		return false;
	}
	
	private void spawnObjects() {
		int ballX = (int) ((Math.random() * (BreakOutGame.WIDTH - 30 - 5)) + 5);
		
		Ball ball = new Ball(ballX, 290, ID.Ball, handler, hud);
		
		handler.addObject(new Panel(BreakOutGame.WIDTH/2-33, 533, ID.Panel, ball));
		
		Color color;
		int score;
		for(int i = 0; i < 8 ;i++) {
			if (i >5) {
				color = Color.YELLOW;
				score = 1;
			}
			else if (i >3) {
				color = Color.GREEN;
				score = 3;
			}
			else if (i >1) {
				color = Color.ORANGE;
				score = 5;
			}
			else {
				color = Color.RED;
				score = 7;
			}
			for(int j = 0; j < 15; j++) {
				handler.addObject(new Brick(j * 25 + (j * 5) + BreakOutGame.SHIFT, i * 15 + 85,
											ID.Brick, handler, color, score));
			}
		}
		
		handler.addObject(ball);
	}
}

