package com.game.snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.main.GameObject;
import com.main.Handler;
import com.main.ID;

public class KeyInput extends KeyAdapter{
	private Handler handler; 
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.SnakeHead) {
				Snake snake = (Snake) tempObject;
				if(key == KeyEvent.VK_W && snake.getDirY() == 0) {snake.setVelY(-1); snake.setVelX(0);}
				if(key == KeyEvent.VK_S && snake.getDirY() == 0) {snake.setVelY(1); snake.setVelX(0);}
				if(key == KeyEvent.VK_D && snake.getDirX() == 0) {snake.setVelX(1); snake.setVelY(0);}
				if(key == KeyEvent.VK_A && snake.getDirX() == 0) {snake.setVelX(-1); snake.setVelY(0);}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
}
