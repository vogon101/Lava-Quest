package com.vogon101.game.lib.vogongame.platform;

import static org.lwjgl.opengl.GL11.*;

public class Pickup {
	
	protected double x, y, width, height, xSpeed, ySpeed;
	protected double r = 0, g = 0,  b = 1, floor = 0;
	public Level level;
	protected boolean there = true;
	
	/**
	 * <b>Constructor</b>
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Pickup (double x, double y,double  width, double height, Level level){
		this.x=x;
		this.y=y;
		this.width = width;
		this.height = height;
	}
	
	public void draw () {
		if (there) {
			glPushMatrix();
			
			glTranslated(x, y, 0);
			
			{
				/*
				 * For a quad the coords are:
				 * vertex 1 = 0, 0
				 * vertex 2 = width, 0
				 * vertex 3 = width, height
				 * vertex 4 = 0, height
				 */
				glBegin(GL_QUADS);
				glColor3d(r, g, b);
				glVertex2d(0, 0);
				
				glVertex2d(width, 0);
				
				glVertex2d(width, height);
				
				glVertex2d(0, height);
				
				glEnd();
			}
			
			glPopMatrix();
		}
	}
	
	public void setColor(Double r, Double g, Double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public double getTopEdge() {
		return y+height;
	}
	
	public double getLeftEdge() {
		return x;
	}
	
	public double getRightEdge() {
		return x+width;
	}
	
	public double getBottomEdge(){
		return y;
	}
	
	public void gravity() {
		if (y > floor) {
			ySpeed = -1;
		} else {
			ySpeed = 0;
		}
	}
	
	public void logic() {

		gravity();
		
		Platform p = checkPlatforms();
		if (p != null) {
			 floor = p.getTopEdge();
			 
		}
		else {
			x += xSpeed;
			floor = level.game.floor;
		}
		
		x += xSpeed;
		y += ySpeed;
	}
	
	
	/**
	 * Return the platform the coin is on OR null
	 * if not on one
	 * @return
	 */
	public Platform checkPlatforms() {
		for (Platform plat : level.getPlatforms()) {
			if (isOnPlatform (plat)) {
				return plat;
				
			}
		}
		return null;
	}
	

	/**
	 * Check if the coin is on a particular platform
	 * 
	 * @param plat
	 * @return true if coin is on platform
	 */
	public boolean isOnPlatform(Platform plat) {
		if (x + 10 / 1.5 > plat.getLeftEdge()
				&& x + 10 / 3 < plat.getRightEdge()) {
			// if above top of platform
			if (y > plat.getTopEdge() - 4 && y < plat.getTopEdge() + 4) {
				return true;
			}
		}
		return false;
	}
}
