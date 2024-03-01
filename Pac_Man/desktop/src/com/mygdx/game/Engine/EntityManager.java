package com.mygdx.game.Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EntityManager {
	
	// Class Attributes
	private List<Entity> entityList;
	private SpriteBatch batch;
	private ShapeRenderer shape;
	
	// Object Declaration
	private Player player;
	private Enemy enemy;
	private Collectible collectibles[], asteroids[];

	// Static Attributes
	private int MAXPELLET = 3;
	
	// Ghost Attributes
	private int EnemySpeed = 10;
	private int EnemyDamage = 10;
	
	// Player Attributes
	private float Player1SpawnX = 400;
	private float Player1SpawnY = 300;
	private float PlayerSpeed = 300;
	private int PlayerHealth = 100;
	private int PlayerPoints = 0;
	
	// Manager Attributes
	
	// Initialize all Entities
	public void initEntities() {
		
		// Create Array List
		entityList = new ArrayList<>();
		batch = new SpriteBatch();
    	shape = new ShapeRenderer();
		
		// Create Player Object
		player = new Player("player", "CuteAstro.png", Player1SpawnX, Player1SpawnY
				, PlayerSpeed, PlayerHealth, PlayerPoints, 100, 100, false, true);
		
		// Create Ghost Object
		enemy = new Enemy("enemy", "blackhole.png", 0, 0, EnemySpeed, EnemyDamage, 80, 80, true);
		enemy.GenerateSpawnPoint(player.getX(), player.getY());
		
		String[] planetsName = {"Earth", "Uranus", "Moon", "Mercury"
				, "Venus", "Mars", "Jupiter", "Saturn", "Neptune"};
		
		String[] planets = {"earth.png", "uranus.png", "moon.png"
				, "mercury.png", "venus.png", "mars.png", "jupiter.png", "saturn.png", "neptune.png"};

		int[] planetSize = {120, 160, 80, 80, 100, 80, 200, 180, 140};
		
		// Create Collectible Objects
		collectibles = new Collectible[9];
	    Random random = new Random();
		
		for (int i = 0; i < collectibles.length; i++) {
            float randomX = random.nextInt(Gdx.graphics.getWidth());
            float randomY = random.nextInt(Gdx.graphics.getHeight());
            
            collectibles[i] = new Collectible(planetsName[i], planets[i], randomX, randomY
            		, planetSize[i], planetSize[i], false);
           entityList.add(collectibles[i]);
        }	
		
		// Create Collectible Objects
		asteroids = new Collectible[3];
				
		for (int i = 0; i < asteroids.length; i++) {
            float randomX = random.nextInt(Gdx.graphics.getWidth());
            asteroids[i] = new Collectible("asteroid", "asteroid.png", randomX, 1200, 1
		    		, 80, 80, false);
		    entityList.add(asteroids[i]);
		 }	
		
		
		// Add entities to the list
		entityList.add(player);
		entityList.add(enemy);
       
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
        for (Entity entity : entityList) {
            if (entity instanceof Player) {
                PlayerControlManager playerControlManager = new PlayerControlManager((Player) entity);
                playerControlManager.handleMovement();
            } else if (entity instanceof Enemy) {
                AIManager aiManager = new AIManager((Enemy) entity, player.getX(), player.getY()); // Pass player coordinates
                aiManager.handleMovement();
            } 
        }
        
        for (int i = 0; i < asteroids.length; i++) {
        	AIManager aiManager = new AIManager(asteroids[i], asteroids[i].getX(), asteroids[i].getY());
        	aiManager.handleMovement(asteroids[i].getSpeed());
        }
    }
	
	// Get Entity from Entity List
	public List<Collectible> getCollectibles() {
	    List<Collectible> collectibleList = new ArrayList<>();
	    for (Entity entity : entityList) {
	        if (entity instanceof Collectible) {
	            collectibleList.add((Collectible) entity);
	        }
	    }
	    return collectibleList;
	}

	public Player getPlayer() {
	    for (Entity entity : entityList) {
	        if (entity instanceof Player) {
	            return (Player) entity;
	        }
	    }
	    return null; // Return null if player is not found
	}

	public Enemy getEnemy() {
	    for (Entity entity : entityList) {
	        if (entity instanceof Enemy) {
	            return (Enemy) entity;
	        }
	    }
	    return null; // Return null if ghost is not found
	}

	public Collectible[] getCollectiblesArray() {
	    List<Collectible> collectibleList = getCollectibles();
	    return collectibleList.toArray(new Collectible[0]);
	}
	
	public Collectible[] getAsteroidArray() {
	    List<Collectible> collectibleList = getCollectibles();
	    List<Collectible> asteroidList = new ArrayList<>();

	    // Filter collectibles with type "asteroid"
	    for (Collectible collectible : collectibleList) {
	        if (collectible.getType() == "asteroid") {
	            asteroidList.add(collectible);
	        }
	    }

	    // Convert the filtered list to an array
	    return asteroidList.toArray(new Collectible[0]);
	}
    
    
	// Dispose of all Entities
	public void disposeEntities() {
        batch.dispose();
        shape.dispose();
        for (Entity entity : entityList) {
        	if (entity.getTex() != null) {
        		entity.getTex().dispose();
            }  
        }
    }
}
