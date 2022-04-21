package com.game.flappybird;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


import com.main.GameObject;
import com.main.GraphicsLoader;
import com.main.ID;

public class Pipe extends GameObject{

	BufferedImage image;
	
	private int gap = 150;
	
	private int x2, y2;
	
	public Pipe(int x, int y, ID id) {
		super(x, (int) (Math.random() * (FlappyBirdGame.HEIGHT - 250 - 250) + 250), id);
		
		image = GraphicsLoader.loadGraphics("flappybird/pipe.png");
		
		x2 = x+351;
		velX = 3;
		
		y2 = (int) (Math.random() * (FlappyBirdGame.HEIGHT - 250 - 250) + 250);
	}

	public void tick() {
		if(FlappyBirdGame.isMoving) {
			x -= velX;
			x2 -= velX;
		}
		if(x + 78 <= 0) {
			x = x2+351;
			y = (int) (Math.random() * (FlappyBirdGame.HEIGHT - 250 - 250) + 250);
		}
		if(x2 + 78 <= 0) {
			x2 = x+351;
			y2 = (int) (Math.random() * (FlappyBirdGame.HEIGHT - 250 - 250) + 250);
		}
	}

	public Rectangle[][] GetPipesBounds() {
		Rectangle[][] bounds = new 	Rectangle[2][3];
		
		bounds[0][0] = new Rectangle(x, 0, 78, y-gap);
		bounds[0][1] = new Rectangle(x, y-gap, 78, gap);
		bounds[0][2] = new Rectangle(x, y, 78, 480);
		
		bounds[1][0] = new Rectangle(x2, 0, 78, y2-gap);
		bounds[1][1] = new Rectangle(x2, y2-gap, 78, gap);
		bounds[1][2] = new Rectangle(x2, y2, 78, 480);
		
		return bounds;
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(image, x, y, 78, 480, null);

		g2d.drawImage(image, x, y-gap, 78, -480, null);
		
		g2d.drawImage(image, x2, y2, 78, 480, null);

		g2d.drawImage(image, x2, y2-gap, 78, -480, null);
		
	}

	public Rectangle GetBounds() {

		return null;
	}

}
