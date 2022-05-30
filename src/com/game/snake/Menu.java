package com.game.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.game.snake.SnakeGame.STATE;
import com.main.Connector;
import com.main.FontLoader;
import com.main.Handler;
import com.main.ID;

public class Menu extends MouseAdapter{

	private SnakeGame game;
	
	private Handler handler;
	
	private HUD hud;
	
	private Window window;
	
	private Connector connector;
	
	private boolean newHighestScore; 
	
	Menu(SnakeGame game, Handler handler, HUD hud, Window window, Connector connector){
		this.game = game;
		
		this.handler = handler;
		
		this.hud = hud;
		
		this.window = window;
		
		this.connector = connector;
		
		newHighestScore = false;
	}
	
	public void tick() {
		
	}

	public void render(Graphics g) {
		Font font1 = null;
		Font font2 = null;
		font1 = FontLoader.loadFont("fonts/MuseoSans_700.otf").deriveFont(0, 40f);
		font2 = FontLoader.loadFont("fonts/MuseoSans_700.otf").deriveFont(0, 30f);
		
		
		
		if(game.gameState == STATE.Menu) {
			g.setFont(font1);
			g.setColor(Color.WHITE);
			g.drawString("SNAKE", SnakeGame.WIDTH/2-60, 150);
			
			g.setFont(font2);
			g.drawRect(SnakeGame.WIDTH/2-100, 195, 200, 64);
			g.drawString("PLAY", SnakeGame.WIDTH/2-35, 235);
			
			g.setFont(font2);
			g.drawRect(SnakeGame.WIDTH/2-100, 284, 200, 64);
			g.drawString("HELP", SnakeGame.WIDTH/2-35, 324);
			
			g.setFont(font2);
			g.drawRect(SnakeGame.WIDTH/2-100, 373, 200, 64);
			g.drawString("EXIT", SnakeGame.WIDTH/2-35, 413);
		}
		else if(game.gameState == STATE.End) {
			String str;
			int shift;
			if(connector.isConnected() && !newHighestScore) {
				if(connector.isNewHighScore(1, hud.getScore())) {
					newHighestScore = true;
				}
			}
			if(newHighestScore) {
				str = "Your new highest is: ";
				shift = 210;
			}
			else {
				str = "Your score is: ";
				shift = 140;
			}
			
			g.setFont(font1);
			g.setColor(Color.WHITE);
			g.drawString(str + hud.getScore(), SnakeGame.WIDTH/2-shift, 150);
			
			g.setFont(font2);
			g.drawRect(SnakeGame.WIDTH/2-100, 284, 200, 64);
			g.drawString("RESTART", SnakeGame.WIDTH/2-60, 324);
			
			g.setFont(font2);
			g.drawRect(SnakeGame.WIDTH/2-100, 373, 200, 64);
			g.drawString("EXIT", SnakeGame.WIDTH/2-35, 413);
		}
		else if(game.gameState == STATE.Help) {
			g.setFont(font1);
			g.setColor(Color.WHITE);
			g.drawString("HELP", SnakeGame.WIDTH/2-60, 150);
			int newline = g.getFont().getSize() + 5 ;
			
			String[] strings = {"MOVEMENT", "   W A S D"};
			
			int y = 200;
			
			for( int i=0; i<strings.length; i++ ) {
	            g.drawString(strings[i], SnakeGame.WIDTH/2-105, y += newline );
	        }
			
			g.setFont(font2);
			g.drawRect(SnakeGame.WIDTH/2-100, 373, 200, 64);
			g.drawString("BACK", SnakeGame.WIDTH/2-35, 413);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY();
		
		if(game.gameState == STATE.Menu) {
			if(mouseOver(mx, my, SnakeGame.WIDTH/2-100, 195, 200, 64)) {
				
				game.gameState = STATE.Game;
				
				handler.addObject(new Snake(4, 8, ID.SnakeHead, handler, hud, game));
				
				handler.addObject(new Apple(12, 8, ID.Apple));
			}
			else if(mouseOver(mx, my, SnakeGame.WIDTH/2-100, 284, 200, 64)) {
				
				game.gameState = STATE.Help;
			}
			else if(mouseOver(mx, my, SnakeGame.WIDTH/2-100, 373, 200, 64)) {
				
				game.running = false;
				window.goToMain();
			
			}
		}
		else if(game.gameState == STATE.End) {
			
			if(mouseOver(mx, my, SnakeGame.WIDTH/2-100, 284, 200, 64)) {
				
				game.gameState = STATE.Game;
				
				handler.addObject(new Snake(4, 8, ID.SnakeHead, handler, hud, game));
				
				handler.addObject(new Apple(12, 8, ID.Apple));
				
				hud.setScore(0);
				newHighestScore = false;
			}
			else if(mouseOver(mx, my, SnakeGame.WIDTH/2-100, 373, 200, 64)) {
				
				hud.setScore(0);
				game.gameState = STATE.Menu;
				newHighestScore = false;
			}
		}
		else if(game.gameState == STATE.Help) {
			if(mouseOver(mx, my, SnakeGame.WIDTH/2-100, 373, 200, 64)) {
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
	
}
