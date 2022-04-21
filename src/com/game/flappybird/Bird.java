package com.game.flappybird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.flappybird.FlappyBirdGame.STATE;
import com.main.GameObject;
import com.main.GraphicsLoader;
import com.main.Handler;
import com.main.ID;

public class Bird extends GameObject{
	
	private FlappyBirdGame game;
	
	private Handler handler;
	
	private HUD hud;
	
	private int speed = 5;
	
	private BufferedImage[] imageLsit = new BufferedImage[3];
	private int currentImageIndex = 1;
	private int indexChange = 1;
	
	private long timerStart = 0;
	private long timerFinish = 0;
	private long timerDelay = 100;
	
	public Bird(int x, int y, ID id, FlappyBirdGame game, Handler handler, HUD hud) {
		super(x, y, id);
		
		for(int i = 0; i < imageLsit.length; i++) {
			imageLsit[i] = GraphicsLoader.loadGraphics("flappybird/bird" + i +".png");
		}
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}

	public void tick() {
		if(FlappyBirdGame.isMoving) {
			velY += speed;
			y += velY;
			
			
			if(velY > 12) {
				velY = 12;
			}
		}
		if(!FlappyBirdGame.isMoving && game.gameState == STATE.End) {
			y += 10;
		}
		if(y >= FlappyBirdGame.HEIGHT-250) {
			FlappyBirdGame.isMoving = false;
			game.gameState = STATE.End;
			
		}
		
		y = FlappyBirdGame.clamp(y, 0, FlappyBirdGame.HEIGHT-250);
		
		collision();
	}

	public void render(Graphics g) {
		if(FlappyBirdGame.isMoving && game.gameState != STATE.End) animation();
		
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(imageLsit[currentImageIndex], x, y, 51, 36, null);
		
		g2d.setPaint(Color.RED);
	}

	public Rectangle GetBounds() {
		return new Rectangle(x+5, y+5, 40, 25);
	}
	
	public boolean timerReach() {
		
		if(timerStart == 0) {
			timerStart = System.currentTimeMillis();
			timerFinish = timerStart + timerDelay;
		}
		
		if(timerFinish <= System.currentTimeMillis()) {
			timerStart = 0;
			return true;
		}
		
		else return false;
	}
	
	public void animation() {
		if(timerReach()) {
			currentImageIndex += indexChange;
			timerReach();
		}
		else { 
			timerReach();
		}
		if (currentImageIndex == imageLsit.length-1 && indexChange == 1|| currentImageIndex == 0 && indexChange == -1) {
			indexChange *= -1;
		}
	}
	
	public void collision() {
		for(GameObject tempObject : handler.object) {
			if(tempObject.getId() == ID.Pipe) {
				Pipe pipe = (Pipe) tempObject;
				
				Rectangle[][] pipeBounds = pipe.GetPipesBounds();
				
				for(int i = 0; i < pipeBounds.length; i++) {
					if(GetBounds().intersects(pipeBounds[i][0]) || GetBounds().intersects(pipeBounds[i][2])) {
						FlappyBirdGame.isMoving = false;
						game.gameState = STATE.End;
					}
					else if(x == pipeBounds[i][1].getX() + pipeBounds[i][1].getWidth()/2) hud.increaseScore();
				}
				
			}
		}
	}
}
