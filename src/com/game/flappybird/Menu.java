package com.game.flappybird;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.game.flappybird.FlappyBirdGame.STATE;
import com.main.Connector;
import com.main.FontLoader;
import com.main.GraphicsLoader;
import com.main.Handler;
import com.main.ID;

public class Menu extends MouseAdapter{
	
	private FlappyBirdGame game;
	
	private Handler handler;
	
	private HUD hud;
	
	private Window window;
	
	private Connector connector;
	
	private boolean newHighestScore; 
	
	Menu(FlappyBirdGame game, Handler handler, HUD hud, Window window, Connector connector){
		this.game = game;
		
		this.handler = handler;
		
		this.hud = hud;
		
		this.window = window;
		
		this.connector = connector;
		
		newHighestScore = false;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(game.gameState == STATE.Menu) {
			if(mouseOver(mx, my, 50, FlappyBirdGame.HEIGHT-175, 156, 50)) {
				game.gameState = STATE.Help;
			}
			if(mouseOver(mx, my, 220, FlappyBirdGame.HEIGHT-175, 156, 50)) {
				game.running = false;
				window.goToMain();
			}
		}
		else if(game.gameState == STATE.Help) {
			if(mouseOver(mx, my, 50, FlappyBirdGame.HEIGHT-175, 332, 50)) {
				game.gameState = STATE.Menu;
			}
		}
		else if(game.gameState == STATE.End) {
			if(mouseOver(mx, my, 50, FlappyBirdGame.HEIGHT-175, 156, 50)) {
				
				newHighestScore = false;
				
				handler.object.clear();
				
				hud.setScore(0);
				
				handler.addObject(new Bird(FlappyBirdGame.WIDTH/2-25, (FlappyBirdGame.HEIGHT-220)/2 - 16, ID.Bird, game, handler, hud));
				
				handler.addObject(new Pipe(FlappyBirdGame.WIDTH + 200, 518, ID.Pipe));
				
				game.gameState = STATE.Game;
			}
			if(mouseOver(mx, my, 220, FlappyBirdGame.HEIGHT-175, 156, 50)) {
				newHighestScore = false;
				handler.object.clear();
				
				handler.addObject(new Bird(FlappyBirdGame.WIDTH/2-25, (FlappyBirdGame.HEIGHT-220)/2 - 16, ID.Bird, game, handler, hud));
				
				handler.addObject(new Pipe(FlappyBirdGame.WIDTH + 200, 518, ID.Pipe));
				
				game.gameState = STATE.Menu;
			}
		}
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		Font font1 = null;
		Font font2 = null;
		font1 = FontLoader.loadFont("fonts/MuseoSans_700.otf").deriveFont(40, 40f);
		font2 = FontLoader.loadFont("fonts/MuseoSans_700.otf").deriveFont(0, 30f);
		
		if(game.gameState == STATE.Menu) {
			g2d.setFont(font1);
			g2d.setPaint(Color.WHITE);
			g2d.drawString("flappybird", FlappyBirdGame.WIDTH/2-98, 200 );
			
			g2d.setFont(font2);
			g2d.drawString("Press space to start", FlappyBirdGame.WIDTH/2-143, 350 );
			
			g2d.setStroke(new BasicStroke(2f));
			g2d.setPaint(Color.decode("#573303"));
			g2d.drawRect(50, FlappyBirdGame.HEIGHT-175, 156, 50);
			
			g2d.setStroke(new BasicStroke(4f));
			g2d.setPaint(Color.WHITE);
			g2d.drawRect(53, FlappyBirdGame.HEIGHT-172, 150, 44);
			
			g2d.setPaint(Color.decode("#e86101"));
			g2d.fillRect(55, FlappyBirdGame.HEIGHT-170, 146, 40);
			
			g2d.setPaint(Color.WHITE);
			g2d.setFont(font2);
			g2d.drawString("HELP", 93, FlappyBirdGame.HEIGHT-140);
			
			g2d.setStroke(new BasicStroke(2f));
			g2d.setColor(Color.decode("#573303"));
			g2d.drawRect(220, FlappyBirdGame.HEIGHT-175, 156, 50);
			
			g2d.setStroke(new BasicStroke(4f));
			g2d.setPaint(Color.WHITE);
			g2d.drawRect(223, FlappyBirdGame.HEIGHT-172, 150, 44);
			
			g2d.setPaint(Color.decode("#e86101"));
			g2d.fillRect(225, FlappyBirdGame.HEIGHT-170, 146, 40);
			
			g2d.setPaint(Color.WHITE);
			g2d.setFont(font2);
			g2d.drawString("QUIT", 263, FlappyBirdGame.HEIGHT-140);
		}
		else if(game.gameState == STATE.Help) {
			g2d.setFont(font1);
			g2d.setPaint(Color.WHITE);
			int newline = g2d.getFont().getSize() + 5 ;
			
			String[] strings = {"MOVEMENT", "    SPACE"};
			
			int y = 200;
			
			for( int i=0; i<strings.length; i++ ) {
	            g2d.drawString(strings[i], FlappyBirdGame.WIDTH/2-115, y += newline );
	        }
			
			g2d.setStroke(new BasicStroke(2f));
			g2d.setColor(Color.decode("#573303"));
			g2d.drawRect(50, FlappyBirdGame.HEIGHT-175, 332, 50);
			
			g2d.setStroke(new BasicStroke(4f));
			g2d.setPaint(Color.WHITE);
			g2d.drawRect(53, FlappyBirdGame.HEIGHT-172, 326, 44);
			
			g2d.setPaint(Color.decode("#e86101"));
			g2d.fillRect(55, FlappyBirdGame.HEIGHT-170, 322, 40);
			
			g2d.setPaint(Color.WHITE);
			g2d.setFont(font2);
			g2d.drawString("BACK", 180, FlappyBirdGame.HEIGHT-140);
		}
		else if(game.gameState == STATE.End) {
			
			
			String str;
			int shiftStr;
			if(connector.isConnected() && !newHighestScore) {
				if(connector.isNewHighScore(3, hud.getScore())) {
					newHighestScore = true;
				}
			}
			if(newHighestScore) {
				g2d.setFont(font2);
				str = "Your new Highest Score is";
				shiftStr = 190;
			}
			else {
				g2d.setFont(font1);
				str = "Your Score is";
				shiftStr = 115;
			}
			
			
			
			g2d.setPaint(Color.WHITE);
			g2d.drawString(str, FlappyBirdGame.WIDTH/2-shiftStr, 200 );
			
			BufferedImage[] images = new BufferedImage[10];
			
			for(int i = 0; i < 10; i++) {
				images[i] = GraphicsLoader.loadGraphics("flappybird/" + i + ".png");
			}
			
			int score = hud.getScore();
			int x = FlappyBirdGame.WIDTH/2 - 10;
			int y = 250;
			int shift = 5;
			
			char[] scoreChars = Integer.toString(score).toCharArray();
			
			int nextX = x - (scoreChars.length-1) * 10;
			
			for(int i = 0; i < scoreChars.length; i++){
				
				int num = Integer.parseInt(String.valueOf(scoreChars[i]));
				
				g2d.drawImage(images[num], nextX, y, null);
				
				nextX += images[num].getWidth()+shift;
			}
			
			g2d.setStroke(new BasicStroke(2f));
			g2d.setPaint(Color.decode("#573303"));
			g2d.drawRect(50, FlappyBirdGame.HEIGHT-175, 156, 50);
			
			g2d.setStroke(new BasicStroke(4f));
			g2d.setPaint(Color.WHITE);
			g2d.drawRect(53, FlappyBirdGame.HEIGHT-172, 150, 44);
			
			g2d.setPaint(Color.decode("#e86101"));
			g2d.fillRect(55, FlappyBirdGame.HEIGHT-170, 146, 40);
			
			g2d.setPaint(Color.WHITE);
			g2d.setFont(font2);
			g2d.drawString("RESTART", 63, FlappyBirdGame.HEIGHT-140);
			
			g2d.setStroke(new BasicStroke(2f));
			g2d.setColor(Color.decode("#573303"));
			g2d.drawRect(220, FlappyBirdGame.HEIGHT-175, 156, 50);
			
			g2d.setStroke(new BasicStroke(4f));
			g2d.setPaint(Color.WHITE);
			g2d.drawRect(223, FlappyBirdGame.HEIGHT-172, 150, 44);
			
			g2d.setPaint(Color.decode("#e86101"));
			g2d.fillRect(225, FlappyBirdGame.HEIGHT-170, 146, 40);
			
			g2d.setPaint(Color.WHITE);
			g2d.setFont(font2);
			g2d.drawString("QUIT", 263, FlappyBirdGame.HEIGHT-140);
		}
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width && my > y && my < y + height) {
			return true;
		}
		
		return false;
	}
}
