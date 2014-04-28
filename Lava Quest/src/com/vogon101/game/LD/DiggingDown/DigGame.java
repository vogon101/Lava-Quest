package com.vogon101.game.LD.DiggingDown;


import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

import com.vogon101.game.lib.vogongame.VogonGameException;
import com.vogon101.game.lib.vogongame.platform.Game;
import com.vogon101.game.lib.vogongame.platform.Level;
import com.vogon101.game.lib.vogongame.platform.Pickup;
import com.vogon101.game.lib.vogongame.platform.Player;
import com.vogon101.game.lib.vogongame.util.VogonTextureLoader;

public class DigGame extends Game {

	public static DigGame digGame;
	public int lavaLevel = -100, times1;
	
	protected Texture win;
	
	protected double r1 = 0.057777777777777777777777777777 , g1 = 0.02745098039215686274509803921569, b1 = 0;
	protected double r2 = 0.18039215686274509803921568627451, g2 = 0.09411764705882352941176470588235, b2 = 0;
	protected double r3 = 0.25098039215686274509803921568627, g3 = 0.12941176470588235294117647058824, b3 = 0;
	protected double r4 = 0.34901960784313725490196078431373, g4 = 0.18039215686274509803921568627451, b4 = 0;
	protected double r5 = 0.45098039215686274509803921568627, g5 = 0.23137254901960784313725490196078, b5 = 0;
	
	public DigGame () throws VogonGameException, IOException {
		setDimen(1280, 720);
		level = new Level(this, 1280, 720);
		level.setGame(this);
		player = new Player(this, level, 100, 100);
		player.coinSound =  AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/coin.wav"));
		player.winSound =  AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/win.wav"));
		player.lifeSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/powerup.wav"));
		setPlayer(player);
		level.setGame(this);
		title = "Lava Quest - A Game for Ludum Dare 29 | Vogon101";
	}
	
	public static void main (String a[]) throws LWJGLException, VogonGameException, IOException {
		//System.setProperty("org.lwjgl.librarypath", new File("windows").getAbsolutePath());
		digGame = new DigGame();
		digGame.start();
	}
	
	@Override
	public void start() throws VogonGameException {
		if (level == null) {
			throw new VogonGameException("The starting level is null");
		}
		else if (player == null) {
			throw new VogonGameException("The player is null");
		}
		try {
			initGl();
		} catch (LWJGLException e) {
			throw new VogonGameException("The window could not be created; ERROR: " + e.getMessage());
		}
		
		player.setTexture("res/textures/char.png");
		player.init();
		
		levelnum = 1;
		
		win = VogonTextureLoader.loadTexture("res/textures/win.png");
		
		level.gen(levelnum);
		for (Pickup pick : level.getPickups()) {
			pick.level = level;
		}
		
		mainloop();
	}
	
	@Override
	public void drawBG () {
		if (levelnum < 6) {
			glPushMatrix();
			glTranslated(0, 0, 0);
			glBegin(GL_QUADS);
			{
				if (levelnum == 1) 
					glColor3d(r1, g1, b1);
				else if (levelnum == 2)
					glColor3d(r2, g2, b2);
				else if (levelnum == 3)
					glColor3d(r3, g3, b3);
				else if (levelnum == 4)
					glColor3d(r4, g4, b4);
				else if (levelnum == 5)
					glColor3d(r5, g5, b5);
				glVertex2d(0, 0);
				glVertex2d(WIDTH, 0);
				if (levelnum == 1) 
					glColor3d(r2, g2, b2);
				else if (levelnum == 2)
					glColor3d(r3, g3, b3);
				else if (levelnum == 3)
					glColor3d(r4, g4, b4);
				else if (levelnum == 4)
					glColor3d(r5, g5, b5);
				else if (levelnum == 5)
					glColor3d(r5, g5, b5);
				glVertex2d(WIDTH, HEIGHT);
				glVertex2d(0, HEIGHT);
			}
			glEnd();
			glTranslated(0, lavaLevel-720, 0);
			glBegin(GL_QUADS);
			{
				glColor3d(0.8, 0.4, 0);
				glVertex2d(0, 0);
				glVertex2d(WIDTH, 0);
				glVertex2d(WIDTH, HEIGHT);
				glVertex2d(0, HEIGHT);
			}
			glEnd();
			glPopMatrix();
		
		}
		
		else {
			debug = true;
			
			glBegin(GL_QUADS);
			
			{
				glColor3d(0, 0.1, 1);
				glVertex2d(0, HEIGHT);
				glVertex2d(WIDTH, HEIGHT);
				glColor3d(0, 0.4, 1);;
				glVertex2d(WIDTH, 0);
				glVertex2d(0, 0);
			}
			glEnd();
			
			glBindTexture(GL_TEXTURE_2D, win.getTextureID());
			glEnable(GL_TEXTURE_2D);
			glEnable(GL_BLEND); glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			Color.white.bind();
			
			glBegin(GL_QUADS);
			
			{
				glTexCoord2d(0,0);
				glVertex2d(0, HEIGHT);
				glTexCoord2d(1,0);
				glVertex2d(WIDTH, HEIGHT);
				glTexCoord2d(1,1);
				glVertex2d(WIDTH, 256);
				glTexCoord2d(0,1);
				glVertex2d(0, 256);
			}
			
			glEnd();
			glDisable(GL_TEXTURE_2D);
			glDisable(GL_BLEND);
		}
	}
	
	@Override
	public void addLogic() {
		times1 ++;
		if (times1 > 10) {
			if (!debug)
				lavaLevel += 1;
			times1 = 0;
		}
		if (!debug) {
			Random r = new Random();
			int num = r.nextInt(300);
			if (num < 1) {
				if (r.nextInt(2) == 0)
					level.addLava(100+r.nextInt(1080), lavaLevel, true, r.nextDouble(), 0, 200);
				else
					level.addLava(100+r.nextInt(1080), lavaLevel, true, -r.nextDouble(), 0, 200);
			}
		}
	}
	
	@Override
	public void levelWin() {
		super.levelWin();
		if (levelnum == 2)
		lavaLevel = -20;
		else {
			lavaLevel = -100;
		}
	}
	
}
