package com.vogon101.game.lib.vogongame.platform;

import java.util.ArrayList;

import com.vogon101.game.lib.vogongame.VogonGameException;

/**
 * The level class has the list of mobs and platforms
 * that are rendered to the screen. Also use the 
 * double WIDTH and HEIGHT found inside this class
 * not the ones in the main {@link Game} class
 * @author Freddie Poser
 *
 */
public class Level {

	protected ArrayList<Mob> mobs  = new ArrayList<Mob>();
	protected ArrayList<Platform> platforms = new ArrayList<Platform>();
	protected ArrayList<Coin> coins = new ArrayList<Coin>();
	protected ArrayList<Wall> walls = new ArrayList<Wall>();
	protected ArrayList<Pickup> pickups = new ArrayList<Pickup>();
	protected ArrayList<LavaParticle> lava = new ArrayList<LavaParticle>();
	protected ArrayList<Stalactite> stalactites  = new ArrayList<Stalactite>();
	protected Game game;
	protected double WIDTH, HEIGHT;
	
	/**
	 * <bConstrutor<b>
	 * @param game_ Reference game object
	 */
	public Level (Game game_) {
		game = game_;
	}
	
	/**
	 * <bConstrutor<b>
	 * @param game_ Reference game object
	 * @param WIDTH_ Width of the screen
	 * @param HEIGHT_ Height of the screen
	 */
	public Level (Game game_, double WIDTH_, double HEIGHT_) {
		game = game_;
		WIDTH =  WIDTH_;
		HEIGHT = HEIGHT_;
	}
	
	/**
	 * Add a new mob off screen, must have x and y set before being drawn
	 */
	public void addMob() {
		mobs.add(new Mob(this));
	}
	
	/**
	 * Add a new mob at coordinates
	 * @param x
	 * @param y
	 */
	public void addMob(double x, double y) {
		mobs.add(new Mob(this, x, y));
	}
	
	/**
	 * Add a new mob at coordinates with speed and direction
	 * @param x
	 * @param y
	 * @param xSpeed
	 * @param ySpeed
	 */
	public void addMob(double x, double y, int xSpeed, int ySpeed) {
		mobs.add(new Mob(this, x, y, xSpeed, ySpeed));
	}
	
	/**
	 * Add a new mob at coordinates with speed and direction
	 * and also default speed for reference
	 * @param x
	 * @param y
	 * @param xSpeed
	 * @param ySpeed
	 * @param xSave
	 * @param ySave
	 * @throws VogonGameException
	 */
	public void addMob(double x, double y, int xSpeed, int ySpeed, int xSave, int ySave){
		mobs.add(new Mob(this, x, y, xSpeed, ySpeed, xSave, ySave));
	}
	
	/**
	 * Return the list of {@link Mob}s in the current {@link Level}
	 * @return
	 */
	public ArrayList<Mob> getMobs() {
		return mobs;
	}
	
	
	/**
	 * Return the list of {@link Platform}s in the current {@link Level}
	 * @return
	 */
	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}
	
	/**
	 * Set the dimensions of the level (the screen dimensions)
	 * @param WIDTH_
	 * @param HEIGHT_
	 */
	public void setDimens (double WIDTH_, double HEIGHT_) {
		
		WIDTH =  WIDTH_;
		HEIGHT = HEIGHT_;
		
	}
	
	/**
	 * Override to add level generation
	 */
	public void genLevel() {
		
	}
	
	/**
	 * Add a new platform to the level
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void addPlatform (double x, double y, double width, double height) {
		platforms.add(new Platform(x, y, width, height, this));
	}
	
	public void addDissapearPlatform (double x, double y, double width, double height) {
		platforms.add(new DissapearingPlatform(x, y, width, height, this));
	}
	
	public void addBouncePlatform (double x, double y, double width, double height) {
		platforms.add(new BouncePlatform(x, y, width, height, this));
	}
	
	/**
	 * Returns the {@link ArrayList} of coins
	 * @return
	 */
	public ArrayList<Coin> getCoins () {
		return coins;
	}
	
	/**
	 * Add a new block to the level
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void addBlock (double x, double y, double width, double height) {
		try {
			platforms.add(new Block(x, y, width, height, this));
		} catch (VogonGameException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return the reference object
	 * @return
	 */
	public Game getGame () {
		return game;
	}
	
	public void addGoalPlatform(double x, double y, double width, double height) {
		platforms.add(new GoalPlatform(x, y, width, height, this));
	}

	public void gen(int levelnum){
		platforms.clear();
		mobs.clear();
		coins.clear();
		walls.clear();
		pickups.clear();
		lava.clear();
		stalactites.clear();
		if (levelnum == 1) {
			game.player.setSpawn(20, 0);
			game.player.setPos(20, 0);
			
			addPlatform(140, 50, 128, 16);
			addPlatform(270, 125, 128, 16);
			addBlock(140, 200, 32, 32);
			addBlock(174, 200, 32, 32);
			addBlock(208, 200, 32, 32);
			
			addPlatform(270, 275, 128, 16);
			addPlatform(380, 350, 128, 16);
			
			addBlock(380, 475, 32, 32);
			addBlock(414, 475, 32, 32);
			addBlock(448, 475, 32, 32);
			
			
			addLifePickup(460, 500, 16, 16);
			
			addPlatform(550, 450, 128, 16);
			addPlatform(780, 540, 128, 16);
			addGoalPlatform(970, 580, 128, 16);
		}
		
		if (levelnum == 2) {
			game.player.setSpawn(20, 0);
			game.player.setPos(20, 0);
			game.player.reset();
			
			addPlatform(140, 50, 128, 16);
			addPlatform(270, 125, 128, 16);
			addBlock(140, 200, 32, 32);
			addBlock(174, 200, 32, 32);
			addBlock(208, 200, 32, 32);
			
			addStalactite(400, 688);
			addLifePickup(460, 500, 16, 16);
			
			addPlatform(270, 275, 128, 16);
			addPlatform(380, 350, 128, 16);
			
			addBlock(380, 475, 32, 32);
			addBlock(414, 475, 32, 32);
			addBlock(448, 475, 32, 32);
			
			addPlatform(235, 500, 128, 16);
			
			addBlock(250, 600, 32, 32);
			addBlock(284, 600, 32, 32);
			addBlock(318, 600, 32, 32);
			
			addPlatform(550, 450, 128, 16);
			addBouncePlatform(780, 540, 128, 16);
			addGoalPlatform(970, 580, 128, 16);
			addWall(930, 0, 32, 650);
		}
		
		if (levelnum == 3) {
			game.player.setSpawn(20, 0);
			game.player.setPos(20, 0);
			game.player.reset();
			
			addPlatform(140, 50, 128, 16);
			addPlatform(270, 125, 128, 16);
			addBlock(140, 200, 32, 32);
			addBlock(174, 200, 32, 32);
			addBlock(208, 200, 32, 32);
			
			addPlatform(270, 275, 128, 16);
			
			addPlatform(400, 350, 32, 16);
			addPlatform(600, 350, 32, 16);
			addPlatform(800, 350, 32, 16);
			addPlatform(1000, 350, 32, 16);
			addLifePickup(1000, 370, 16, 16);
			
			addDissapearPlatform(1100, 425, 100, 16);
			
			addBlock(1000, 475, 32, 32);
			addBlock(800, 475, 32, 32);
			addBlock(600, 475, 32, 32);
			addBlock(400, 475, 32, 32);
			
			addStalactite(400, 688);
			addStalactite(1000, 688);
			
			addGoalPlatform(200, 550, 128, 16);
		}
		if (levelnum == 4) {
			game.player.setSpawn(20, 0);
			game.player.setPos(20, 0);
			game.player.reset();
			
			addLifePickup(140, 75, 16, 16);
			
			//3 platforms in column (x = 140)
			addPlatform(140, 50, 128, 16);
			addPlatform(140, 200, 128, 16);
			addPlatform(140, 350, 128, 16);
			
			//3 platforms in column (x = 300)
			addDissapearPlatform(300, 125, 64, 16);
			addBouncePlatform(300, 275, 64, 16);
			addDissapearPlatform(300, 425, 64, 16);
			
			//Row of three blocks (starting x = 140)
			addBlock(140, 475, 32, 32);
			addBlock(174, 475, 32, 32);
			addBlock(208, 475, 32, 32);
			
			//Stalactites
			addStalactite(140, 688);
			addStalactite(300, 688);
			
			//Passage to goal
			addPlatform(425, 500, 128, 16);
			addPlatform(650, 425, 128, 16);
			addPlatform(825, 525, 128, 16);
			
			//Goal
			addGoalPlatform(1050,625, 128, 16);
			
		}
		if (levelnum == 5) {
			game.player.setSpawn(20, 0);
			game.player.setPos(20, 0);
			game.player.reset();
			
			addLifePickup(140, 75, 16, 16);
			
			//6 platforms in column (x = 140)
			addPlatform(140, 50, 128, 16);
			addPlatform(140, 100, 128, 16);
			addPlatform(140, 150, 128, 16);
			addPlatform(140, 200, 128, 16);
			addPlatform(140, 250, 128, 16);
			addPlatform(140, 300, 128, 16);
			
			addBlock(140, 425, 32, 32);
			addBlock(174, 425, 32, 32);
			addBlock(208, 425, 32, 32);
			
			
			addDissapearPlatform(360, 375, 64, 16);
			addDissapearPlatform(560, 375, 64, 16);
			addDissapearPlatform(760, 375, 64, 16);
			
			addBouncePlatform(920, 450, 64, 16);
			
			addDissapearPlatform(360, 550, 64, 16);
			addDissapearPlatform(560, 550, 64, 16);
			addDissapearPlatform(760, 550, 64, 16);
			
			addBouncePlatform(250, 590, 60, 16);
			addBouncePlatform(100, 590, 60, 16);
			addBouncePlatform(250, 500, 60, 16);
			addBouncePlatform(100, 500, 60, 16);
			
			addWall(225, 590, 23, 64);
			
			addBlock(187, 525, 20, 20);
			addBlock(210, 525, 20, 20);
			
			addGoalPlatform(165, 470, 80, 16);
			
		}
		
	}
	
	public void addWall(int x, int y, int width , int height) {
		walls.add(new Wall(this, x, y, width, height));
	}
	
	public ArrayList<Wall> getWalls () {
		return walls;
	}
	
	public void setGame (Game game_) {
		game = game_;
	}
	
	public ArrayList<Pickup> getPickups() {
		return pickups;
	}
	
	public void addPickup(double x, double y, double width, double height) {
		pickups.add(new Pickup(x, y, width, height, this));
	}
	
	public void addLifePickup (double x, double y, double width, double height) {
		pickups.add(new LifePickup(x, y, width, height, this));
	}
	
	public ArrayList<LavaParticle> getLava() {
		return lava;
	}
	
	public void addLava(double x, double y, boolean up, double xSpeed, double ySpeed, int timeLimit) {
		try {
			lava.add(new LavaParticle(x, y, up, xSpeed, ySpeed, timeLimit, this));
		} catch (VogonGameException e) {
			e.printStackTrace();
		}
	
	}
	
	public ArrayList<Stalactite> getStalactites () {
		return stalactites;
	}
	
	public void addStalactite(double x, double y) {
		try {
			stalactites.add(new Stalactite(x, y, this));
		} catch (VogonGameException e) {
			e.printStackTrace();
		}
	}
}