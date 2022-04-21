package com.game.breakout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


import com.main.GameObject;
import com.main.Handler;
import com.main.ID;

public class Brick extends GameObject{

	Handler handler;
	
	private int score;
	
	private Color color;

	
	public Brick(int x, int y, ID id, Handler handler, Color color, int score) {
		super(x, y, id);
		this.handler = handler;
		this.score = score;
		this.color = color;
	}

	public void tick() {

	}

	public void render(Graphics g) {
		
			g.setColor(color);
			
			g.fillRect(x , y , 25, 8);
	}
	
		
	public Rectangle GetBounds() {
		return new Rectangle(x , y , 25, 8);
	}

	public int getBrickScore() {
		return score;
	}
	
}
