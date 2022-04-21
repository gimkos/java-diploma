package com.game.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.main.GameObject;
import com.main.ID;

public class Apple extends GameObject{

	public Apple(int x, int y, ID id) {
		super(x, y, id);
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.decode("#e7471d"));
		g2d.fillOval(x * 35 + SnakeGame.SHIFT, y * 35 + SnakeGame.SHIFT, 35, 35);
	}
	public Rectangle GetBounds() {
		return new Rectangle(x*35, y*35, 35, 35);
	}

}
