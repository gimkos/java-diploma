package com.game.breakout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.main.GameObject;
import com.main.ID;

public class Panel extends GameObject{
	
	private Ball ball;
	
	private int size = 50;

	private boolean ballIn = false;
	
	public Panel(int x, int y, ID id, Ball ball) {
		super(x, y, id);
		this.ball = ball;
		
	}

	public void tick() {
		x += velX;
		
		x = BreakOutGame.clamp(x, 5, BreakOutGame.WIDTH-size-20);
	
		if(ballIn) {
			if(ball.getY() < 270) {
				ballIn = false;
			}
		}
		
		collision();
	
	}

	public void render(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(x, y, size, 8);
	}
	public void collision() {
		
		if(GetBounds().intersects(ball.GetBounds()) && !ballIn) {
			ball.changeVelY();
			ball.setVelX(ball.getvelX() + velX/2);
			if(ball.getSpeed() < 7) ball.changeSpeed(1);
			ballIn = true;
			
		}
	}

	public Rectangle GetBounds() {
		return new Rectangle(x, y, size, 8);
	}
	
	public void changeSize() {
		switch(size) {
		case 50:
			size = 25;
			break;
		case 25:
			size = 15;
			break;
		}
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
}
