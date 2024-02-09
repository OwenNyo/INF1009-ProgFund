package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.Random;

public class GameMaster extends ApplicationAdapter {
	
	// Object Declaration
	private Player player;
	private Ghost ghost;
	private Collectible collectibles[];
	private Score score;

	// Static Attributes
	private int MAXPELLET = 5;
	
	// Ghost Attributes
	private int GhostSpeed = 10;
	private int GhostDamage = 10;
	
	// Player Attributes
	private float Player1SpawnX = 400;
	private float Player1SpawnY = 300;
	private float PlayerSpeed = 300;
	private int PlayerHealth = 100;
	
	@Override
	public void create() {
		
		// Create player object
		player = new Player("player", "PacMan.png", Player1SpawnX, Player1SpawnY
				, PlayerSpeed, PlayerHealth, 40, 40);
		
		// Create Ghost Objects
		ghost = new Ghost("ghost", "ghost.png", 0, 0, GhostSpeed, GhostDamage, 40, 40);
		ghost.GenerateSpawnPoint(player.getX(), player.getY());
		
		collectibles = new Collectible[MAXPELLET];
	    Random random = new Random();
		
		for (int i = 0; i < collectibles.length; i++) {
            float randomX = random.nextInt(Gdx.graphics.getWidth());
            float randomY = random.nextInt(Gdx.graphics.getHeight());

            collectibles[i] = new Collectible("collectible", "pellet.png", randomX, randomY, 15, 15);
        }	
		
		score = new Score();
	}
	
	@Override
	public void render() {
		// This line is used to ensure that the screen is blank and set to a dark blue background
		ScreenUtils.clear(0 , 0, 0.2f, 1); 
		
		// Texture Drawing 
		player.draw();  
		ghost.draw();
		score.draw();
		
		for (int i = 0; i < MAXPELLET; i++) {  
			collectibles[i].draw();
		}
		
		// User Input Segment
		// Player Movement Segment
		if (Gdx.input.isKeyPressed(Keys.UP) && player.getY() < Gdx.graphics.getHeight() - player.getHeight()) {
	        player.setY(player.getY() + player.getSpeed() * Gdx.graphics.getDeltaTime());
	    }
	        
	    if (Gdx.input.isKeyPressed(Keys.DOWN) && player.getY() > 0) {
	        player.setY(player.getY() - player.getSpeed() * Gdx.graphics.getDeltaTime());
	    }
	        
	    if (Gdx.input.isKeyPressed(Keys.LEFT) && player.getX() > 0) {
	        player.setX(player.getX() - player.getSpeed() * Gdx.graphics.getDeltaTime());
	    }
	    if (Gdx.input.isKeyPressed(Keys.RIGHT) && player.getX() < Gdx.graphics.getWidth() - player.getWidth()) {
	        player.setX(player.getX() + player.getSpeed() * Gdx.graphics.getDeltaTime());
	    }

		
		
		// Collision Manager's Job
		player.checkGhostCollision(ghost);
		
		// If true , increment score
		// If false, nothing
		if(player.checkCollectibleCollision(collectibles)) {
			score.calculateScore();
		}
		
		
	}
	
	@Override
	public void dispose() {
		player.getTex().dispose();
		ghost.getTex().dispose();
		for (int i = 0; i < MAXPELLET; i++) {  
			collectibles[i].getTex().dispose();
		}
		
	}
	
	
}