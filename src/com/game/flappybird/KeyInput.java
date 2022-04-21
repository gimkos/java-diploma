package com.game.flappybird;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.main.GameObject;
import com.main.Handler;
import com.main.ID;

public class KeyInput extends KeyAdapter{
	private Handler handler;
	
	KeyInput(Handler handler){
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();

		for(GameObject tempObject : handler.object) {
			if(tempObject.getId() == ID.Bird) {
				if(key == KeyEvent.VK_SPACE) {
					tempObject.setVelY(-25);
					if(!FlappyBirdGame.isMoving) FlappyBirdGame.isMoving = true;
				}
			}
		}
	}
	public void keyReleased(KeyEvent e) {
	}

}
