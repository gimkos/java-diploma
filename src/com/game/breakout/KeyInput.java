package com.game.breakout;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.main.GameObject;
import com.main.Handler;
import com.main.ID;

public class KeyInput extends KeyAdapter{
	private Handler handler;
	
	private boolean left = false, right = false;
	
	KeyInput(Handler handler){
		this.handler = handler;
	}
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();

		for(GameObject tempObject : handler.object) {
			if(tempObject.getId() == ID.Panel) {
				if(key == KeyEvent.VK_D) {tempObject.setVelX(15); right = true;}
				if(key == KeyEvent.VK_A) {tempObject.setVelX(-15); left = true;}
			}
		}
	}
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();

		for(GameObject tempObject : handler.object) {
			if(tempObject.getId() == ID.Panel) {
				if(key == KeyEvent.VK_D) right = false;
				if(key == KeyEvent.VK_A) left = false;
				
				if(!left && !right) tempObject.setVelX(0);
			}
		}
	}
}
