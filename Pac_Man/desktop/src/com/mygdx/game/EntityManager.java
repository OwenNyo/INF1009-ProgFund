package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityManager {
	
	// Class Attributes
	private List<Entity> entityList;
	private SpriteBatch batch;
	
	// Object Declaration
	private Player player;
	private Ghost ghost;
	private Collectible collectibles[];

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
	
	
	// Initialize all Entities
	public void initEntities() {
		
		// Create Array List
		entityList = new ArrayList<>();
		batch = new SpriteBatch();
		
		// Create Player Object
		player = new Player("player", "PacMan.png", Player1SpawnX, Player1SpawnY
				, PlayerSpeed, PlayerHealth, 40, 40, false, true);
		
		// Create Ghost Object
		ghost = new Ghost("ghost", "ghost.png", 0, 0, GhostSpeed, GhostDamage, 40, 40, true);
		ghost.GenerateSpawnPoint(player.getX(), player.getY());
		
		// Create Collectible Objects
		collectibles = new Collectible[MAXPELLET];
	    Random random = new Random();
		
		for (int i = 0; i < collectibles.length; i++) {
            float randomX = random.nextInt(Gdx.graphics.getWidth());
            float randomY = random.nextInt(Gdx.graphics.getHeight());

            collectibles[i] = new Collectible("collectible", "pellet.png", randomX, randomY, 15, 15, false);
           entityList.add(collectibles[i]);
        }	
		
		// Add entities to the list
		entityList.add(player);
		entityList.add(ghost);
	}
	
	public List<Entity> getEntityList() {
	    return entityList;
	}
	

	// Draw all Entities
	public void drawEntities() {
		for (Entity entity : entityList) {
			entity.draw();
		}
	}
	
	// Movement logic for all Entities
	public void moveEntities() {
		for(Entity entity : entityList) {
			if(entity.getAIControlled()) {
				entity.AIMove(player.getX(), player.getY());
			}
			if(entity instanceof Player) {
				// UserMove will differentiate Player 1 or 2 in the subclass itself
				entity.UserMove();
			}
		}
	}
	
	// Dispose of all Entities
	public void disposeEntities() {
        batch.dispose();
        for (Entity entity : entityList) {
            entity.getTex().dispose();
        }
    }

}
