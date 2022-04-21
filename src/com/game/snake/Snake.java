package com.game.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.snake.SnakeGame.STATE;
import com.main.GameObject;
import com.main.Handler;
import com.main.ID;

public class Snake extends GameObject{

	private SnakeGame game;
	
	private Handler handler;
	
	private HUD hud;
	
	private int dirX, dirY;
	
	private Tail snakeTail;
	
	private int score;
	
	private long timerStart = 0;
	private long timerFinish = 0;
	private long timerDelay = 2000;
	
	private boolean appleSpawned = true;
	
	public Snake(int x, int y, ID id, Handler handler, HUD hud, SnakeGame game) {	
		
		super(x, y, id);
	
		this.game = game;
		
		this.handler = handler;
	
		this.hud = hud;
		
		snakeTail = new Tail(x,  y, ID.SnakeTail, Color.decode("#4774ea"), this);
		handler.addObject(snakeTail);
		
	}


	public void tick() {
		
		dirX = velX; dirY = velY;
		boolean flag = false;
		if(!flag && dirX > 0 && dirY == 0) { x += dirX; flag = true; }
		if(!flag && dirX < 0 && dirY == 0) { x += dirX; flag = true; }
		if(!flag && dirY > 0 && dirX == 0) { y += dirY; flag = true; }
		if(!flag && dirY < 0 && dirX == 0) { y += dirY; flag = true; } 
		flag = false;
		
		x = SnakeGame.clamp(x, 0, 16);
		y = SnakeGame.clamp(y, 0, 16); 
		
		if(dirX != 0 || dirY != 0) collision();
		
		if(appleSpawned) {
			appleSpawned = false;
			if(timerReach()) {
				score = 5;
				timerReach();
			}
			else { 
				score = 40;
				timerReach();
			}
			
		}
	}
	
	public int getDirX() {
		return dirX;
	}
	public int getDirY() {
		return dirY;
	}
	public void render(Graphics g) {
		g.setColor(Color.decode("#426cdb"));
		g.fillRect(x * 35 + SnakeGame.SHIFT, y * 35 + SnakeGame.SHIFT, 35, 35);
	}
	public void collision() {
		Rectangle[] tailCol = snakeTail.col();
		for(int i = 0; i < tailCol.length; i++) {
			if(GetBounds().intersects(tailCol[i])){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.object.clear();
				game.gameState = STATE.End;
			}
		}
		for(int i = 0; i < handler.object.size(); i++ ) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Apple) {
				if(GetBounds().intersects(tempObject.GetBounds())) {
					
					int spawnMap[][][] = new int[17][17][2];
					
					for(int mapY = 0; mapY < spawnMap.length; mapY++) {
						for(int mapX = 0; mapX < spawnMap[mapY].length; mapX++) {
							spawnMap[mapY][mapX][0] = mapY;
							spawnMap[mapY][mapX][1] = mapX;
						}
					}
					
					spawnMap[y] = removeCoordinate(spawnMap[y], x);
					
					for(Rectangle tailPart : tailCol) {
						int tailPartX = (int) tailPart.getLocation().getX()/35;
						int tailPartY = (int) tailPart.getLocation().getY()/35;
						
						spawnMap[tailPartY] = removeCoordinate(spawnMap[tailPartY], tailPartX);
					}
					boolean noSpace = true;
					for(int[][] row : spawnMap) {
						if(row.length > 0 ) {
							noSpace = false;
							break;
						}
					}
					if(noSpace) {
						game.gameState = STATE.End;
					}
					
					int appleX = (int) Math.round(Math.random()*16);
					
					while(spawnMap[appleX].length == 0) {
						
						appleX = (int) Math.round(Math.random()*16);
						
						
					}
					int appleY = (int) Math.round(Math.random()*(spawnMap[appleX].length-1));
					
					tempObject.setX(spawnMap[appleX][appleY][1]);
					tempObject.setY(spawnMap[appleX][appleY][0]);
				
					
					hud.increaseScore(score);
					snakeTail.increaseTail();
					
					appleSpawned = true;
					break;
				}
			}
		}
	}
	public Rectangle GetBounds() {
		return new Rectangle(x*35,y*35, 35, 35);
	}
	
	public int[][] removeCoordinate(int[][] arr, int delCoordinate) {
		int[][] result = new int[arr.length-1][2];
		
		int index = 0;
	
		for(int i = 0; i < arr.length; i++) {
			if(arr[i][1] == delCoordinate) {
				continue;
			}
			result[index] = arr[i];
			index++;
		}
		return result;
	}
	
	public boolean timerReach() {
		if(timerStart == 0) {
			timerStart = System.currentTimeMillis();
			timerFinish = timerStart + timerDelay;
		}
		if(timerFinish <= System.currentTimeMillis()) {
			timerStart = 0;
			return true;
		}
		else return false;
	}
	
}
