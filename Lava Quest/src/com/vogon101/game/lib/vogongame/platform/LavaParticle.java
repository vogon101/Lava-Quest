package com.vogon101.game.lib.vogongame.platform;
import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.vogon101.game.LD.DiggingDown.DigGame;
import com.vogon101.game.lib.vogongame.VogonGameException;
import com.vogon101.game.lib.vogongame.util.VogonTextureLoader;


public class LavaParticle {

	protected double x, y, floor, xSpeed, ySpeed;
	protected int timer, limit;
	protected boolean up, there = true;
	protected Texture texture;
	protected Level level;
	
	public LavaParticle (double x, double y, boolean up, double xSpeed, double ySpeed,int timeLimit, Level level) throws VogonGameException {
		this.x = x;
		this.y = y;
		this.up = up;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.texture = VogonTextureLoader.loadTexture("res/textures/lava.png");
		this.level = level;
		limit = timeLimit;
	}
	
	public void logic() {
		x+=xSpeed;
		y+=ySpeed;
		if (!up)
			gravity();
		else if (timer >= limit) {
			up = false;
			timer = -1;
		}
		else  if (timer < limit/2) {
			y+=2;
		}
		else { 
			y++;
		}
		timer++;
	}
	
	public void gravity() {
		if (y > ((DigGame)level.getGame()).lavaLevel - 32) {
			if (timer < 100) 
				y--;
			else
				y-=3;
		}
		else {
			there = false;
		}
	}

	
	public void draw() {
		if (there) {
			glPushMatrix();
			glTranslated(x, y, 0);
			
			glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
			glEnable(GL_TEXTURE_2D);
			glEnable(GL_BLEND); glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			Color.white.bind();
			
			glBegin(GL_QUADS);
			glTexCoord2d(0, 0);
			glVertex2d(0, 0);
			glTexCoord2d(1, 0);
			glVertex2d(16, 0);
			glTexCoord2d(1, 1);
			glVertex2d(16, 16);
			glTexCoord2d(0, 1);
			glVertex2d(0, 16);
			glEnd();
			glPopMatrix();
		}
	}
	
}
