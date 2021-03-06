package com.main;


import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

	protected int x, y;
	protected ID id;
	protected int velX, velY;
	
	public GameObject(int x, int y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle GetBounds();
	
	
	//set functions
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setId(ID id) {
		this.id = id;
	}
	public void setVelX(int velX) {
		this.velX = velX;
	}
	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	//get functions
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public ID getId() {
		return id;
	}
	public int getvelX() {
		return velX;
	}
	public int getvelY() {
		return velY;
	}
}

