package com.vogon101.game.lib.vogongame.platform;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.vogon101.game.lib.vogongame.VogonGameException;
import com.vogon101.game.lib.vogongame.util.VogonTextureLoader;

/**
 * Block class, platform that gives coins/items
 * @author Freddie Poser
 *
 */
public class Block extends Platform {

	protected boolean payed;
	public Texture payedTex, normalTex;

	public Block(double x, double y, double width, double height, Level level) throws VogonGameException {
		super(x, y, width, height, level);
		payedTex = VogonTextureLoader.loadTexture("res/textures/blockOne.png");
		normalTex = VogonTextureLoader.loadTexture("res/textures/blockTwo.png");
		setColor(1.0, 0.5, 0.4);
	}
	
	public void logic () {
		if (payed)
		  setColor(1.0, 0.6, 0.3);
	}

	public void land() {
		payed = true;
	}
	
	@Override
	public void draw() {
		glPushMatrix();
		glTranslated(x, y, 0);
		if (payed) 
			glBindTexture(GL_TEXTURE_2D, payedTex.getTextureID());
		else
			glBindTexture(GL_TEXTURE_2D, normalTex.getTextureID());
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND); glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Color.white.bind();
		
		if (payedTex != null && normalTex != null) {
			
			glBegin(GL_QUADS);
			{
				glTexCoord2d(0,0);
				glVertex2d(0, 0);
				glTexCoord2d(1,0);
				glVertex2d(width, 0);
				glTexCoord2d(1,1);
				glVertex2d(width, height);
				glTexCoord2d(0,1);
				glVertex2d(0, height);
			}
			glEnd();
			
			glDisable(GL_BLEND);
			glDisable(GL_TEXTURE_2D);
		}
		else {
			
			
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
			
			
		}
		glPopMatrix();
	}

}
