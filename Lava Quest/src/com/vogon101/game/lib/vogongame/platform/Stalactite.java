package com.vogon101.game.lib.vogongame.platform;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

import com.vogon101.game.lib.vogongame.VogonGameException;
import com.vogon101.game.lib.vogongame.util.VogonTextureLoader;

public class Stalactite {

	protected double x, y;
	protected Texture texture;
	protected Random r = new Random();
	protected Level level;
	
	public Stalactite (double x_, double y_, Level lvl) throws VogonGameException {
		x = x_;
		y = y_;
		texture = VogonTextureLoader.loadTexture("res/textures/mite.png");
		level = lvl;
	}
	
	public void logic () {
		int num = r.nextInt(300);
		if (num == 0) {
			if (r.nextInt(2) == 0)
				level.addLava(x+16, y, false, r.nextDouble(), 0, 100);
			else {
				level.addLava(x+16, y, false, -r.nextDouble(), 0, 100);
			}
		}
	}
	
	public void draw() {
		glPushMatrix();
		glTranslated(x, y-32, 0);
		glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND); glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Color.white.bind();
		
		glBegin(GL_QUADS);
		{
			glTexCoord2d(0, 1);
			glVertex2d(0, 0);
			glTexCoord2d(1, 1);
			glVertex2d(32, 0);
			glTexCoord2d(1, 0);
			glVertex2d(32, 64);
			glTexCoord2d(0, 0);
			glVertex2d(0, 64);
		}
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glPopMatrix();
	}
	
}
