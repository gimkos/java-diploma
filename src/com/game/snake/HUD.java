package com.game.snake;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.main.FontLoader;

public class HUD {
	
	private int score = 0;
	
	HUD(){
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		

		Font font = null;
		font = FontLoader.loadFont("fonts/MuseoSans_700.otf").deriveFont(0, 25);
		
		g.setFont(font);
		
		g.drawString("Score: " + score, SnakeGame.SHIFT, 650);
	}
	
	public void increaseScore(int n) {
		score += n;
	}
	
	public void setScore(int n) {
		score = n;
	}
	
	public int getScore() {
		return score;
	}
}
