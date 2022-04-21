package com.game.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.main.GameObject;
import com.main.ID;

public class Tail extends GameObject{

	private List<Integer[]> tail = new ArrayList<Integer[]>();
	
	private Snake snake;
	private Color color;
	
	public Tail(int x, int y, ID id, Color color, Snake snake) {
		super(x, y, id);
		this.snake = snake;
		this.color = color;

		tail.add( new Integer[] { snake.getX()-1, snake.getY() } );
		
	}
	
	public void tick() {
		move();
	}
	public void render(Graphics g) {
		g.setColor(color);
		
		for(int i = 0; i < tail.size(); i++) {
			g.fillRect(tail.get(i)[0] * 35 + SnakeGame.SHIFT, tail.get(i)[1]*35 + SnakeGame.SHIFT , 35, 35);
		}
	}
	
	public Rectangle[] col() {
		Rectangle[] tailCol = new Rectangle[tail.size()];
		for(int i = 0; i < tail.size(); i++) {
			tailCol[i] = new Rectangle(tail.get(i)[0]*35,tail.get(i)[1]*35, 35, 35);
		}
		return tailCol;
	}
	
	public void move() {
		if(tail.size() > 1) {
			for(int i = tail.size()-1; i > 0; i--) {
				tail.get(i)[0] = tail.get(i-1)[0];
				tail.get(i)[1] = tail.get(i-1)[1];
			}
		}
		tail.get(0)[0] = snake.getX();
		tail.get(0)[1] = snake.getY();
	}
	
	public void increaseTail() {
		tail.add( new Integer[] { tail.get(tail.size()-1)[0], tail.get(tail.size()-1)[1] } );
	}

	public Rectangle GetBounds() {
		return null;
	}
}
