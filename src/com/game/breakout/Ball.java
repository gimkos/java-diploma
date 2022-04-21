package com.game.breakout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.main.GameObject;
import com.main.Handler;
import com.main.ID;

public class Ball extends GameObject{

	private float speed;
	
	private Handler handler;
	
	private HUD hud;
	
	private boolean inBrick = false;
	
	private Panel panel;
	
	public Ball(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);
		this.handler = handler;
		this.hud = hud;
		velX = 1;
		velY = 1;
		speed = 5;
		
	}


	public void tick() {
		if(panel == null) {
			for(int j = 0; j <  handler.object.size(); j++) {
				GameObject tempObjectP = handler.object.get(j);
				if(tempObjectP.getId() == ID.Panel) {
					panel = (Panel) tempObjectP;
				}
			}
		}
		velX = BreakOutGame.clamp(velX, -1, 1);
		
		speed = BreakOutGame.clamp((int) speed, 5, 10);
		
		x += velX * speed;
		y += velY * speed;

		if(x > BreakOutGame.WIDTH - 30 || x < 5) changeVelX();
		if(y < 50) {
			changeVelY();
			inBrick = false;
		}
		if(y > 270 && velY == 1) {
			inBrick = false;
		}
		if(y >  BreakOutGame.HEIGHT - 30) {
			
			hud.decreaseLife();
			panel.setSize(50);
			x = (int) ((Math.random() * (BreakOutGame.WIDTH - 30 - 5)) + 5); 
			y = 290;
			velX *= -1;
			speed = 5;
		}
		collision();
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.WHITE);
		
		g2d.fillOval(x, y, 10, 10);
	}

	
	public void collision() {
		int bricksCount = 0;
		for(int i = 0; i <  handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Brick) {
				bricksCount++;
				if(GetBounds().intersects(tempObject.GetBounds()) && !inBrick) {
					handler.object.remove(tempObject);
					changeVelY();
					inBrick = true;
					
					bricksCount--;
					
					Brick delatedBrick = (Brick) tempObject;
					
					int brickScore = delatedBrick.getBrickScore();
					
					hud.increaseScore(brickScore);
					
					if(brickScore == 7) {
						changeSpeed(5f);
						panel.changeSize();
					}
				}
			}
		}
		if(bricksCount == 0 && y > 270) {
			panel.setSize(50);
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
		}
	}
	
	public Rectangle GetBounds() {
		return new Rectangle(x, y, 10, 10);
	}

	
	public void changeSpeed(float speed) {
		this.speed += speed;
	}
	public float getSpeed() {
		return speed;
	}
	public void changeVelX() {
		velX *= -1;
	}
	public void changeVelY() {
		velY *= -1;
	}

}
