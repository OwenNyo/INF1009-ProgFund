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
	public void initEntities(int Stage, int SavedPoints, int SavedHealth) {
		
		// Create Array List
		entityList = new ArrayList<>();
		batch = new SpriteBatch();
    	shape = new ShapeRenderer();
    	
		// Create Player Object
		player = new Player("player", "spaceDog.png", Player1SpawnX, Player1SpawnY
				, PlayerSpeed, PlayerHealth, PlayerPoints, 100, 100, false, true);

		// Create Ghost Object
		enemy = new Enemy("enemy", "blackhole.png", 0, 0, EnemySpeed, EnemyDamage, 80, 80, true);
		enemy.GenerateSpawnPoint(player.getX(), player.getY());
		
		
		String[] planetsName = {"Earth", "Uranus", "Moon", "Mercury"
				, "Venus", "Mars", "Jupiter", "Saturn", "Neptune"};
		
		String[] planets = {"earth.png", "uranus.png", "moon.png"
				, "mercury.png", "venus.png", "mars.png", "jupiter.png", "saturn.png", "neptune.png"};

		int[] planetSize = {240, 280, 220, 190, 230, 210, 320, 300, 260};
    	
    	if(Stage == 1)
    	{
    		// Add entities to the list
    		entityList.add(player);
    		entityList.add(enemy);
    		
    		// Create Planet Objects
    		collectibles = new Collectible[4];
    	    Random random = new Random();
    		
    		for (int i = 0; i < collectibles.length; i++) {
                
                collectibles[i] = new Collectible(planetsName[i], planets[i], 0, 0
                		, planetSize[i], planetSize[i], false);
                collectibles[i].GenerateSpawnPoint(player.getX(), player.getY());
                entityList.add(collectibles[i]);
            }	
    		
    		// Create Asteroid Objects
    		asteroids = new Collectible[3];
    		
    		for (int i = 0; i < asteroids.length; i++) {
                asteroids[i] = new Collectible("asteroid", "asteroid.png", 0, 0, 1
    		    		, 80, 80, false);
                System.out.println("Player X :" + player.getX());
                System.out.println("Player Y : " + player.getY());
                asteroids[i].GenerateSpawnPoint(player.getX(), player.getY());
    		    entityList.add(asteroids[i]);
    		 }	
    	}		
    	else if(Stage == 2){
    		// Add entities to the list
    		entityList.add(player);
    		entityList.add(enemy);
    		player.setPoints(SavedPoints);
    		player.setHealth(SavedHealth);
    		
    		// Create Planet Objects
    		collectibles = new Collectible[4];
    	    Random random = new Random();
    		
    		for (int i = 0; i < collectibles.length; i++) {
                float randomX = random.nextInt(Gdx.graphics.getWidth());
                float randomY = random.nextInt(Gdx.graphics.getHeight());
                
                collectibles[i] = new Collectible(planetsName[i+collectibles.length]
                		, planets[i+collectibles.length], randomX, randomY
                		, planetSize[i+collectibles.length], planetSize[i+collectibles.length], false);
               entityList.add(collectibles[i]);
            }	
    		
    		// Create SpaceStation Objects
    		asteroids = new Collectible[3];
    				
    		for (int i = 0; i < asteroids.length; i++) {
                float randomX = random.nextInt(Gdx.graphics.getWidth());
                asteroids[i] = new Collectible("spaceStation", "space_station.png", randomX, 1200, 1
    		    		, 80, 80, false);
    		    entityList.add(asteroids[i]);
    		 }	
    	}
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

	public Collectible[] getCollectiblesArray(String type) {
		// Retrieve original list of collectibles
	    List<Collectible> collectibleList = getCollectibles();
	    
	    // Make new lists to return only collectibles we want
	    List<Collectible> filteredList = new ArrayList<>();
	    
	 // Filter collectibles based on type
	    for (Collectible collectible : collectibleList) {
	    	if(type.equals("asteroid") && collectible.getType().equals("asteroid")) {
	    		filteredList.add(collectible);
	    	}
	    	else if (type.equals("spaceStation") && collectible.getType().equals("spaceStation")) {
	    		filteredList.add(collectible);
	    	}
	    	else if (!type.equals("spaceStation") && !type.equals("asteroid")) {
	    		if(!collectible.getType().equalsIgnoreCase("asteroid") && !collectible.getType().equalsIgnoreCase("spaceStation")) {
	    			filteredList.add(collectible);
	    		}
	    	}
	    }
	       
	    // Convert the filtered list to an array
	    return filteredList.toArray(new Collectible[0]);
	}
    
    public void removePlanetFromCollectibles(String planetName) {    	
        // Count how many collectibles that match the planetName to remove
        int removeCount = 0;
        for (int i = 0; i < collectibles.length; i++) {
            if (collectibles[i].getType().equalsIgnoreCase(planetName)) {
                removeCount++;
            }
        }

        // If nothing to remove, return and end method
        if (removeCount == 0) {
            return;
        }
        
        // Create a new array for collectibles excluding the removed planet
        Collectible[] newCollectibles = new Collectible[collectibles.length - removeCount];
        
        int index = 0;
        for (int i = 0; i < collectibles.length; i++) {
            if (!collectibles[i].getType().equalsIgnoreCase(planetName)) {
                newCollectibles[index++] = collectibles[i];
            } else {
                // Remove the collectible from entityList
                entityList.remove(collectibles[i]);
            }
        }
        
        // Update the collectibles array
        collectibles = newCollectibles; 
    }
    
    public int countRemainingPlanets() {
        int planetCount = 0;
        for (Collectible collectible : collectibles) {
            if (collectible.getType().equals("Earth") || collectible.getType().equals("Uranus") || 
                collectible.getType().equals("Moon") || collectible.getType().equals("Mercury") ||
                collectible.getType().equals("Venus") || collectible.getType().equals("Mars") || 
                collectible.getType().equals("Jupiter") || collectible.getType().equals("Saturn") ||
                collectible.getType().equals("Neptune")) {
                planetCount++;
            }
        }
        return planetCount;
    }
    
	// Dispose of all Entities
	public void disposeEntities() {
        batch.dispose();
        shape.dispose();
        for (Entity entity : entityList) {
        	if (entity.getTex() != null && entity instanceof Player == false){
        		entity.getTex().dispose();
        	}
        }
    }
	
	public void swapEntities() {
        batch.dispose();
        shape.dispose();
        for (Entity entity : entityList) {
        	if(entity instanceof Player) {
        		
        	} else {
	    		if (entity.getTex() != null ){
	    			entity.getTex().dispose();
	    		}
        	}     	
        }
    }
}
