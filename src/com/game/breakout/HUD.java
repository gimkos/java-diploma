package com.game.breakout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD {
	public int score = 0;
	public int lifes = 3;
	HUD(){
		
	}
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 45));
		g.drawString(""+score, 30, 35);
		
		g.drawString(""+lifes, 400, 35);
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
	public void decreaseLife() {
		lifes--;
	}
	public void setLife(int n) {
		lifes = n;
	}
	public int getLife() {
		return lifes;
	}
	
}
