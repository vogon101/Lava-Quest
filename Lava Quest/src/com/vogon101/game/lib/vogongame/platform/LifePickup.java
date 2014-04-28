package com.vogon101.game.lib.vogongame.platform;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.vogon101.game.lib.vogongame.VogonGameException;
import com.vogon101.game.lib.vogongame.util.VogonTextureLoader;

public class LifePickup extends Pickup {

	protected Texture heartTex;
	
	public LifePickup(double x, double y, double width, double height,
			Level level) {
		super(x, y, width, height, level);
		try {
			heartTex = VogonTextureLoader.loadTexture("res/textures/heart-full.png");
		} catch (VogonGameException e) {
			e.printStackTrace();
		}
	}
	
	public void init() throws VogonGameException {
		
	}
	
	@Override
	public void draw() {
		if (there) {
			glPushMatrix();
			glBindTexture(GL_TEXTURE_2D, heartTex.getTextureID());
			glEnable(GL_TEXTURE_2D);
			glEnable(GL_BLEND); glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			Color.white.bind();
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
				glTexCoord2d(0, 1);
				glVertex2d(0, 0);
				glTexCoord2d(1, 1);
				glVertex2d(width, 0);
				glTexCoord2d(1, 0);
				glVertex2d(width, height);
				glTexCoord2d(0, 0);
				glVertex2d(0, height);
				
				glEnd();
			}
			glDisable(GL_TEXTURE_2D);
			glDisable(GL_BLEND);
			glPopMatrix();
		}
	}

}
