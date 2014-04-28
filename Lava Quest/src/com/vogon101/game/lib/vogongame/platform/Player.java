package com.vogon101.game.lib.vogongame.platform;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.vogon101.game.LD.DiggingDown.DigGame;
import com.vogon101.game.lib.vogongame.VogonGameException;
import com.vogon101.game.lib.vogongame.util.BadTextRender;
import com.vogon101.game.lib.vogongame.util.VogonTextureLoader;


/**
 * 
 * <b>The main player class for the platform game.</b><br/>
 * Will default to square, one colour quad to represent the player on the
 * screen. This will be a 64x64 red quad. To overide this either extend the
 * class or use the {@link setTexture(String path)}, {@link setColour(double r,
 * double g, double b)} and {@link setSize(int w, int h)} <br/>
 * <b>Variables to use for control</b> (All protected) <br/>
 * <b>x</b> - The x postion of the player <br/>
 * <b>y</b> - The y postion of the player <br/>
 * <b>xSpeed</b> - The x speed of the player<br/>
 * <b>ySpeed</b> - The y speed of the player<br/>
 * <b>newFloor</b> - True if the player is not on the base floor<br/>
 * <b>jump</b. - True if the player is mid jump;
 * 
 * @author Freddie Poser
 * 
 */
public class Player {
	
	public Audio coinSound;
	public Audio winSound;
	public Audio lifeSound;
	
	protected Game game;
	protected Texture texture = null, heartFull, heartEmpty;
	protected Level level;
	protected int height = 64, width = 64, xSpeed = 0, ySpeed = 0,
			jumptimer = 0, deaths, mobkills, score, respawnTimer = 0,
			dieTimer = 0, lives = 3;
	protected double x, y, r = 1, g = 0, b = 0, floor = 0, baseFloor = 0, xSpawn = 0, ySpawn =0;
	protected boolean newFloor = false, jump = false, alive = true,
			dieing = false, respawning = false, win = false;

	public void init() throws VogonGameException {
		heartFull =  VogonTextureLoader.loadTexture("res/textures/heart-full.png");
		heartEmpty = VogonTextureLoader.loadTexture("res/textures/heart-empty.png");
	}
	
	/**
	 * <b>Constructor</b><br/>
	 * If you use this constructor you MUST set the x and y position BEFORE
	 * drawing {@link setPos(double x, double y)}<br/>
	 * 
	 * @param game_
	 *            This is the main game object (ie: something that extends
	 *            {@link Game})
	 * @param level_
	 *            The starting current level
	 */
	public Player(Game game_, Level level_) {
		game = game_;
	}

	/**
	 * <b>Constructor</b><br/>
	 * Starting point of player contained in x and y <br/>
	 * 
	 * @param game_
	 *            This is the main game object (ie: something that extends
	 *            {@link Game})
	 * @param level
	 *            The starting level
	 * @param x
	 *            The starting x position
	 * @param y
	 *            The starting y position
	 */
	public Player(Game game_, Level level_, double x, double y) {
		game = game_;
		level = level_;
		this.x = x;
		this.y = y;
		xSpawn = x;
		ySpawn = y;
	}

	/**
	 * Set the texture to a pre-loaded texture
	 * 
	 * @param texture
	 *            A pre loaded texture (Use {@link VogonTextureLoader})
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
		width = texture.getImageWidth();
		height = texture.getImageHeight();
	}

	/**
	 * Load a new texture to use for rendering from <b>PNG</b> image<br/>
	 * Uses {@link VogonTextureLoader} which extends {@link TextureLoader}
	 * 
	 * @param path
	 *            - The path of the <b>PNG</b> image
	 * @throws VogonGameException
	 *             - Thrown when the image can't be loaded
	 */
	public void setTexture(String path) throws VogonGameException {
		texture = VogonTextureLoader.loadTexture(path);
		width = texture.getImageWidth();
		height = texture.getImageHeight();
	}

	/**
	 * Load a new texture to use for rendering from <b>PNG</b> image<br/>
	 * Uses {@link VogonTextureLoader} which extends {@link TextureLoader}
	 * 
	 * @param path
	 *            - The path of the <b>PNG</b> image
	 * @param type
	 *            - The file extention/type of image ie <b>PNG</b> or <b>JPG</b>
	 * @throws VogonGameException
	 *             - Thrown when the image can't be loaded
	 */
	public void setTexture(String path, String type) throws VogonGameException {
		texture = VogonTextureLoader.loadTexture(path, type);
		width = texture.getImageWidth();
		height = texture.getImageHeight();
	}

	/**
	 * Set the size of the quad to be rendered
	 * 
	 * @param w
	 *            - Width
	 * @param h
	 *            - Height
	 */
	public void setSize(int w, int h) {
		width = w;
		height = h;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * <b>The player's render method</b><br/>
	 * Override to add custom rendering otherwise it will default to a square
	 * (size set by user ({@link setSize(int w, int h)}) or default 64x64 and
	 * colour red or set by user ({@link setColour (double r, double g, double
	 * b)}) unless texture is set {@link setTexture (Texture texture)}
	 */
	public void draw() {
		glPushMatrix();
		glTranslated(x, y, 0);
		glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND); glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Color.white.bind();

		/*
		 * For a quad the coords are: vertex 1 = 0, 0 vertex 2 = width, 0 vertex
		 * 3 = width, height vertex 4 = 0, height
		 */

		
		if (texture != null) {
			
			glBegin(GL_QUADS);
			
			{
				glTexCoord2d(0, 1);
				glVertex2d(0, 0);
				glTexCoord2d(1, 1);
				glVertex2d(width, 0);
				glTexCoord2d(1, 0);
				glVertex2d(width, height);
				glTexCoord2d(0, 0);
				glVertex2d(0, height);
			}
			glEnd();
			
		} else {
			glBegin(GL_QUADS);
			{
				glColor3d(1, 0, 0);
				glVertex2d(0, 0);
				glVertex2d(width, 0);
				glVertex2d(width, height);
				glVertex2d(0, height);
			}
			glEnd();
		}
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
		
		glBindTexture(GL_TEXTURE_2D, heartFull.getTextureID());
		glEnable(GL_TEXTURE_2D);
		Color.white.bind();
		for (int i = 0; i < lives; i++) {
			glPushMatrix();
			glTranslated( x + (0+18*i),  y + height + 5, 0);
			glBegin(GL_QUADS);
			
				{
					glTexCoord2d(0, 1);
					glVertex2d(0, 0);
					glTexCoord2d(1, 1);
					glVertex2d(16, 0);
					glTexCoord2d(1, 0);
					glVertex2d(16, 16);
					glTexCoord2d(0, 0);
					glVertex2d(0, 16);
				}
				glEnd();
			
				glPopMatrix();
			}
			glDisable(GL_TEXTURE_2D);
			glDisable(GL_BLEND);
			
		
		
		glPopMatrix();
		
		BadTextRender.drawString("Score " + String.valueOf(score), 100, 100);
	}

	public void checkLava() {
		if (y < ((DigGame) game).lavaLevel) {
			die();
			((DigGame) game).lavaLevel = -100;
		}
		for (LavaParticle lava : level.getLava()) {
			if (lava.there) {
				if (x+10 > lava.x && x-10 < lava.x + 16) {
					if (y+10 > lava.y && y-10 < lava.y + 16) {
						lava.there = false;
						die();
						((DigGame) game).lavaLevel = -100;
					}
				}
				
			}
		}
	}
	
	/**
	 * Main logic method
	 */
	public void logic() {
		
		if (alive) {
			x += xSpeed;
			y += ySpeed;
			control();
			checkPlatforms();
			checkOutside();
			checkMobs();
			checkLava();
			checkPickups();
			gravity();
			if (!newFloor) {
				floor = baseFloor;
			}
			checkWalls();
			checkCoins();
			
			if (win) {
				game.levelWin();
			}
						
		}
		else {
			DieAnime();
		}
	}
	
	public void checkPickups() {
		for (Pickup pick : level.getPickups()) {
			if (pick.x < x+50 && pick.x > x-50) {
				if (pick.x < x) {
					pick.x++;
				}
				else {
					pick.x--;
				}
				
				if (pick.y < y-5) {
					pick.y++;
				}
				else { 
					pick.y--;
				}
					
			}
			if (pick.x < x+25 && pick.x > x-25) {
				if (pick.y < y+height+25 && pick.y  > y-25) {
					if (pick.there && pick.getClass() == LifePickup.class) {
						pick.there = false;
						if (lives < 5){
							lives ++;
							lifeSound.playAsSoundEffect(1.0f, 1.0f, false);
						}
						pick.xSpeed = 0;
					}
				}
			}
		}
	}

	//TODO: Must make sure that the player only picks up coins in the correct place along the y axis
	
	/**
	 * Pick up nearby coins
	 */
	public void checkCoins () {
		for (Coin coin : level.getCoins()) {
			if (coin.x < x+50 && coin.x > x-50 && coin.y < y+height+50 && coin.y  > y-50) {
				if (coin.x < x) {
					coin.x++;
				}
				else {
					coin.x--;
				}
				
				if (coin.y < y-5) {
					coin.y+= 2;
				}
				else { 
					coin.y-= 2;
				}
					
			}
			if (coin.x < x+25 && coin.x > x-25) {
				if (coin.y < y+height+25 && coin.y  > y-25) {
					if (!coin.falling && coin.there) {
						coin.there = false;
						score++;
						coinSound.playAsSoundEffect(1.0f, 0.7f, false);
						coin.xSpeed = 0;
					}
				}
			}
		}
	}
	
	/**
	 * Make the player "Ascend to heaven"
	 */
	public void DieAnime () {
		if (!alive) {
			if (dieing) {
				dieTimer+=2;
				if (dieTimer > 0 && dieTimer < 150) {
					y++;
				}
				else if (dieTimer > 150 && dieTimer < 550) {
					y--;
				}
				
				if (dieTimer == 350) {
					dieing = false;
					respawning = true;
					dieTimer = 0;
					respawnTimer = 0;
					x = xSpawn + 50;
					y = ySpawn;
					
				}
			}
			else if (respawning) {
				respawnTimer ++;			
				if (respawnTimer > 0 && respawnTimer < 150) {
					y++;
				}
				else if (respawnTimer > 150 && respawnTimer < 200) {
					y--;
				}
				
				if (respawnTimer == 200) {
					dieing = false;
					respawning = false;
					respawnTimer = 0;
					alive = true;
					jump = false;
					jumptimer = 0;
					floor = baseFloor;
				}
			}
		}
	}
	
	/**
	 * Mob logic method, kills mobs/player
	 */
	public void checkMobs() {
		for (Mob mob : level.getMobs()) {
			if (mob.isAlive()) {
				if (x>mob.getLeftEdge()-width/2 && x < mob.getRightEdge()+width/2) {
					if (y<mob.getTopEdge()/2 && y> mob.getBottomEdge()-5) {
						//die
						die();
						if (score > 0)
							score--;
						deaths ++;
					}
					else if (y<mob.getTopEdge()+5&&y>mob.getTopEdge()-5) {
						//kill mob
						mob.kill();
						score++;
						mobkills++;
					}
				}
			}
		}
	}
	
	//TODO:USE LibGDX
	
	/**
	 * Check if the player is outside the bounds of the level
	 */
	public void checkOutside() {
		if (x < 0) {
			xSpeed = 0;
			x++;
		} else if (x > level.WIDTH - width) {
			xSpeed = 0;
			x--;
		}
	}

	/**
	 * Basic platform collision
	 */
	public void checkPlatforms() {
		boolean check = false;
		for (final Platform plat : level.getPlatforms()) {
			if (isOnPlatform(plat)) {
				//The level end
				if (plat.getClass() == GoalPlatform.class) {
					win = true;
					winSound.playAsSoundEffect(1.0f, 0.7f, false);
				}
				else if (plat.getClass() == DissapearingPlatform.class) {
					DissapearingPlatform a = (DissapearingPlatform) plat;
					if (a.isThere) {
						if (!a.isCounting) {
							a.startCountdown(true);
							floor = a.getTopEdge();
						}
						else {
							floor = a.getTopEdge();
						}
						newFloor = true;
						check = true;
					}
					else {
						floor = game.floor;
						jump = false;
						jumptimer = 0;
						
					}
				}
				else if (plat.getClass() == BouncePlatform.class) {
					//set the floor level for gravity
					floor = plat.getTopEdge();
					//tracking booleans
					jump = true;
					jumptimer = 0;
				}
				else {
					floor = plat.getTopEdge();
					newFloor = true;
					check = true;
				}
			}
			if (plat.getClass() == Block.class) {
				if (y+height > plat.getBottomEdge() && y+height < plat.getTopEdge()+4) {
					if (x + width / 1.5 > plat.getLeftEdge() && x + width / 3 < plat.getRightEdge()) {
						y-=3;
						jump = false;
						jumptimer = 0;
						if (!((Block) plat).payed) {
							((Block) plat).land();
							Random r = new Random();
							for (int i = 0; i < r.nextInt(40); i++) {
								int test  = r.nextInt(2);
								if (test == 1) {
									
									level.getCoins().add(new Coin(plat.x+(width/2), plat.y+(height/2), level.game, -r.nextDouble()+0.2, r.nextDouble()-0.2, 120-r.nextInt(40)));
								}
								level.getCoins().add(new Coin(plat.x+(width/2), plat.y+(height/2), level.game, r.nextDouble()-0.2, r.nextDouble()-0.2, 120-r.nextInt(40)));
							}
						}
					}	
				}
			}
			
		}

		if (!check) {
			newFloor = false;
		}
	}

	/**
	 * Basic gravity, override for fancier stuff
	 */
	public void gravity() {
		if (!jump) {
			if (y > floor) {
				ySpeed = -1;
			} else {
				ySpeed = 0;
			}
		} else {
			jumptimer++;
			if (jumptimer < 50) {
				ySpeed = 2;
			} else if (jumptimer < 70) {
				ySpeed = 1;
			} else {
				jump = false;
			}
		}

		if (!isOnFloor() && !jump) {
			y--;
		}
	}

	/**
	 * Returns true if the player is on his current floor
	 * 
	 * @return
	 */
	public boolean isOnFloor() {
		if (y > floor - 2 && y < floor + 1) {
			return true;
		}
		return false;
	}

	
	/**
	 * Main keyboard input method
	 */
	public void control() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W) && isOnFloor()) {
			jump = true;
			jumptimer = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S) && y >= floor
				|| Keyboard.isKeyDown(Keyboard.KEY_S) && y >= floor + 1) {
			jump = false;
			jumptimer = 0;
			ySpeed = -3;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			xSpeed = 2;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			xSpeed = -2;
		} else {
			xSpeed = 0;
		}
		
		//DEBUG TOOLS
		if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
			if (!game.debug)
				game.debug = true;
			else
				game.debug = false;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_N)) {
			((DigGame) game).lavaLevel -= 10;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
			die();
			((DigGame) game).lavaLevel = -100;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
			((DigGame) game).lavaLevel += 10;
		}
	}

	/**
	 * Set a colour for use when rendering the default quad (@link
	 * GL11.glColor3d}
	 * 
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setColour(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Set the x and y coordiantes of the player. Essential if you didn't use
	 * the constructor with this option
	 * 
	 * @param x_
	 * @param y_
	 */
	public void setPos(double x_, double y_) {
		x = x_;
		y = y_;
	}

	/**
	 * Check if the player is on a particular platform
	 * 
	 * @param plat
	 * @return true if player is on platform
	 */
	public boolean isOnPlatform(Platform plat) {
		if (x + width / 1.5 > plat.getLeftEdge()
				&& x + width / 3 < plat.getRightEdge()) {
			// if above top of platform
			if (y > plat.getTopEdge() - 4 && y < plat.getTopEdge() + 4) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Set the "ground level" for the player, the gravity will bring him down to
	 * here if he is not on a platform 
	 * 
	 * @param floor
	 */
	public void setBaseFloor(double floor) {
		baseFloor = floor;
	}
	
	/**
	 * Kill the player
	 */
	public void die() {
		dieing = true;
		alive = false;
		lives--;
		if (lives < 1) {
			gameEnd();
		}
	}
	
	public void reset() {
		x = xSpawn;
		win = false;
		y = ySpawn+4;
	}
	
	public void checkWalls() {
		for (Wall wall : level.getWalls())
			if (!(y+height/2 > wall.getTopEdge() || y+4 < wall.getBottomEdge())) {
				if (x + width > wall.getLeftEdge() - 5 && x + width < wall.getLeftEdge() + 5) {
					x-=3;
				}
				else if (x > wall.getRightEdge() - 5 && x <  + wall.getRightEdge() + 5) {
					x+=3;
				}
			}
	}
	
	public void setSpawn ( double xSpawn_, double ySpawn_) {
		xSpawn = xSpawn_;
		ySpawn = ySpawn_;
	}
	
	public void gameEnd() {
		game.gameEnd = true;
	}
}
