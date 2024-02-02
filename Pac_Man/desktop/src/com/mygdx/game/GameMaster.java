package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameMaster extends ApplicationAdapter {
	
	// Object Declaration
	private Player player;
	private Ghost ghost;

	
	// Static Attributes
	// private int WINNINGSCORE = 100;
	// private int MAXPLAYERS = 2;
	
	// Ghost Attributes
	private int GhostSpeed = 10;
	private int GhostDamage = 10;
	
	// Player Attributes
	private float Player1SpawnX = 400;
	private float Player1SpawnY = 300;
	private float PlayerSpeed = 50;
	private int PlayerHealth = 100;
	private int PlayerPoints = 0;
	
	@Override
	public void create() {
		
		// Create player object
		player = new Player("player", "PacMan.png", Player1SpawnX, Player1SpawnY, PlayerSpeed, PlayerHealth, PlayerPoints);
		
		// Create Ghost Objects
		ghost = new Ghost("ghost", "ghost.png", 0, 0, GhostSpeed, GhostDamage);
	}
	
	@Override
	public void render() {
		// This line is used to ensure that the screen is blank and set to a dark blue background
		ScreenUtils.clear(0 , 0, 0.2f, 1);  // Tentatively just use a dark blue BG
		
		player.draw();  // Draw Player Texture
		ghost.draw();
		
		// User Input Segment
		// Player Movement Segment
		if (Gdx.input.isKeyPressed(Keys.UP)) player.setY(player.getY() + player.getSpeed() * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.DOWN)) player.setY(player.getY() - player.getSpeed() * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.LEFT)) player.setX(player.getX() - player.getSpeed() * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) player.setX(player.getX() + player.getSpeed() * Gdx.graphics.getDeltaTime());
		
		player.checkGhostCollision(ghost);
		
	}
	
	@Override
	public void dispose() {
		player.getTex().dispose();
		ghost.getTex().dispose();
	}
	
	
}