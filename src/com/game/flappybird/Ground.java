package com.game.flappybird;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.main.GraphicsLoader;

public class Ground {
	private BufferedImage image;
	private int x1, x2;
	
	private float velX;
	
	public Ground(){
		x1 = 0;
		x2 = FlappyBirdGame.WIDTH;
		
		velX = 3;
		
		image = GraphicsLoader.loadGraphics("flappybird/base.png");
	}
	public void tick() {
		x1 -= velX;
		x2 -= velX;
		
		if(x1 + FlappyBirdGame.WIDTH <= 0) x1 = FlappyBirdGame.WIDTH;
		if(x2 + FlappyBirdGame.WIDTH <= 0) x2 = FlappyBirdGame.WIDTH;
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, x1, FlappyBirdGame.HEIGHT-220, FlappyBirdGame.WIDTH, 181, null);
		g2d.drawImage(image, x2, FlappyBirdGame.HEIGHT-220, FlappyBirdGame.WIDTH, 181, null);
	}
}
