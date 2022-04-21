package com.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.game.breakout.BreakOutGame;
import com.game.flappybird.FlappyBirdGame;
import com.game.snake.SnakeGame;
import com.main.Main.STATE;

public class Menu extends MouseAdapter{
	private Main main;
	private Connector connector;
	private MainWindow window;
	public Menu(Main main, Connector connector, MainWindow window) {
		this.main = main;
		this.connector = connector;
		this.window = window;
	}
	
	public void tick() {
		
	}
	public void render(Graphics g) {
		Font font1 = null;
		Font font2 = null;
		font1 = FontLoader.loadFont("fonts/MuseoSans_700.otf").deriveFont(0, 40f);
		font2 = FontLoader.loadFont("fonts/MuseoSans_700.otf").deriveFont(0, 30f);
		if(main.mainSTATE == STATE.Main) {
			g.setFont(font2);
			g.setColor(Color.WHITE);
			
			// play
			g.drawRect(200, 150, 250, 50);
			g.drawString("PLAY", 290, 185);
			
			// score
			g.drawRect(200, 210, 250, 50);
			g.drawString("SCORE", 280, 245);
			
			// exit
			g.drawRect(200, 270, 250, 50);
			g.drawString("EXIT", 290, 305);
			
		}
		else if(main.mainSTATE == STATE.Games) {
			g.setColor(Color.WHITE);
			
			g.setFont(font1);
			
			g.drawString("Choose game", 195, 110);
			
			g.setFont(font2);
			
			// snake
			g.drawRect(200, 150, 250, 50);
			g.drawString("Snake", 290, 185);
			// breakout
			g.drawRect(200, 210, 250, 50);
			g.drawString("BreakOut", 265, 245);
			// flappybird
			g.drawRect(200, 270, 250, 50);
			g.drawString("FlappyBird", 255, 305);
			// back
			g.drawRect(200, 330, 250, 50);
			g.drawString("BACK", 290, 365);
		}
		else if(main.mainSTATE == STATE.Score) {
			if(connector.isConnected()) {
				g.setColor(Color.WHITE);
				
				g.setFont(font1);
				
				String str = connector.getUserNickname() + "'s ranks";
				
				g.drawString(str, 180, 110);
				g.setFont(font2);
				// global rank
				
	
				str = "All games: " + connector.getRank() + " | Score: " + connector.getAllScore();
				
				g.drawString(str, 185, 175);
				
				// rank for snake
				g.setColor(Color.decode("#426cdb"));
				
				str = "Snake: " + connector.getRankInGame(1) + " | Score: " + connector.getHighestScore(1);
				
				g.drawString(str, 185, 215);
				// rank for breakout
				g.setColor(Color.CYAN);
				
				str = "BreakOut: " + connector.getRankInGame(2) + " | Score: " + connector.getHighestScore(2);
				
				g.drawString(str, 185, 255);
				
				// rank for flappybird
				g.setColor(Color.decode("#d4bf27"));
				
				str = "FlappyBird: " + connector.getRankInGame(3) + " | Score: " + connector.getHighestScore(3);
				
				g.drawString(str, 185, 290);
				
				// back
				
			}
			else {
				g.setFont(font1);
				g.setColor(Color.RED);
				g.drawString("Connectrion Error", 165, 175);
				
				
			}
			
			g.setColor(Color.WHITE);
			
			
			g.setFont(font2);
			g.drawRect(200, 330, 250, 50);
			g.drawString("BACK", 290, 365);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(main.mainSTATE == STATE.Main) {
			if(mouseOver(mx, my, 200, 150, 250, 50)) {
				main.mainSTATE = STATE.Games;
			}
			else if(mouseOver(mx, my, 200, 210, 250, 50)) {
				main.mainSTATE = STATE.Score;
			}
			else if(mouseOver(mx, my, 200, 270, 250, 50)) {
				System.exit(1);
			}
		}
		else if(main.mainSTATE == STATE.Games) {
			if(mouseOver(mx, my, 200, 150, 250, 50)) {
				main.mainSTATE = STATE.InGame;
				window.closeMainWinow();
				new SnakeGame(main, connector);
			}
			else if(mouseOver(mx, my, 200, 210, 250, 50)) {
				main.mainSTATE = STATE.InGame;
				window.closeMainWinow();
				new BreakOutGame(main, connector);
			}
			else if(mouseOver(mx, my, 200, 270, 250, 50)) {
				main.mainSTATE = STATE.InGame;
				window.closeMainWinow();
				new FlappyBirdGame(main, connector);
			}
			else if(mouseOver(mx, my, 200, 330, 250, 50)) {
				main.mainSTATE = STATE.Main;
			}
		}
		else if(main.mainSTATE == STATE.Score) {
			if(mouseOver(mx, my, 200, 330, 250, 50)) {
				main.mainSTATE = STATE.Main;
			}
		}
	}
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width && my > y && my < y + height) {
			return true;
		}
		
		return false;
	}
}
