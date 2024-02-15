package com.mygdx.game.Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EntityManager {
	
	// Class Attributes
	private List<Entity> entityList;
	private SpriteBatch batch;
	private ShapeRenderer shape;
	
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
	
	// Manager Attributes
	private PlayerControlManager playerControlManager;
	private AIManager aiManager;
	
	
	// Initialize all Entities
	public void initEntities() {
		
		// Create Array List
		entityList = new ArrayList<>();
		batch = new SpriteBatch();
    	shape = new ShapeRenderer();
		
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

            collectibles[i] = new Collectible("collectible", randomX, randomY, Color.RED, 15, false);
            collectibles[i].setShape(shape);
           entityList.add(collectibles[i]);
        }	
		
		// Add entities to the list
		entityList.add(player);
		entityList.add(ghost);
		
		// Initialize PlayerControlManager
        playerControlManager = new PlayerControlManager(player);
        
        // Initialize AIManager
        aiManager = new AIManager();
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
				 aiManager.handleAIMovement((Ghost) entity, player.getX(), player.getY());
			}
			if(entity instanceof Player) {
				// UserMove will differentiate Player 1 or 2 in the subclass itself
				playerControlManager.handlePlayerMovement();
			}
		}
	}
	
	// Dispose of all Entities
	public void disposeEntities() {
        batch.dispose();
        for (Entity entity : entityList) {
        	if (entity.getTex() != null) {
        		entity.getTex().dispose();
            }   
        }
    }

}
