package com.game.flappybird;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.main.GraphicsLoader;

public class HUD {
	private BufferedImage[] images;
	
	private int x, y;
	
	private int score = 0;
	
	private char[] scoreChars;
	
	private int shift = 5;
	
	public HUD(int x, int y) {
		this.x = x;
		this.y = y;
		
		images = new BufferedImage[10];
		for(int i = 0; i < 10; i++) {
			images[i] = GraphicsLoader.loadGraphics("flappybird/" + i + ".png");
		}
	}
	
	public void tick() {
		scoreChars = Integer.toString(score).toCharArray();
	}
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		int nextX = x - (scoreChars.length-1) * 10;
		
		for(int i = 0; i < scoreChars.length; i++){
			
			int num = Integer.parseInt(String.valueOf(scoreChars[i]));
			
			g2d.drawImage(images[num], nextX, y, null);
			
			nextX += images[num].getWidth()+shift;
			
		}
	}
	public void increaseScore() {
		score++;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getScore() {
		return score;
	}
}
