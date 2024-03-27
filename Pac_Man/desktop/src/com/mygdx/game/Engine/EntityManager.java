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
		private static final int MAX_COLLECTIBLES = 3;
		
		// Ghost Attributes
		private static final int ENEMY_SPEED = 10;
		private static final int ENEMY_DAMAGE = 10;
		
		// Player Attributes
		private static final float PLAYER_SPAWN_X = 400;
		private static final float PLAYER_SPAWN_Y = 300;
		private static final float PLAYER_SPEED = 300;
		private static final int PLAYER_HEALTH = 100;
		private static final int PLAYER_POINTS = 0;
	
		// Initialize all Entities
		public void initEntities(int stage, int savedPoints, int savedHealth) {
			entityList = new ArrayList<>();
			batch = new SpriteBatch();
	    	shape = new ShapeRenderer();
	    	
	    	player = EntityFactory.createPlayer(PLAYER_SPAWN_X, PLAYER_SPAWN_Y,
	    			PLAYER_SPEED, PLAYER_HEALTH, PLAYER_POINTS);
	    	
	    	enemy = EntityFactory.createEnemy(0, 0, ENEMY_SPEED, ENEMY_DAMAGE);
			enemy.generateSpawnPoint(player.getX(), player.getY());
			
	    	
	    	if(stage == 1) {
	    		entityList.add(player);
	    		entityList.add(enemy);
	    		
	    		collectibles = new Collectible[MAX_COLLECTIBLES];
	    		asteroids = new Collectible[MAX_COLLECTIBLES];
	    	    
	    		for (int i = 0; i < collectibles.length; i++) {
	    	        Collectible collectible = EntityFactory.createRandomCollectible(0, 0, i);
	    	        collectible.generateSpawnPoint(player.getX(), player.getY());
	    	        entityList.add(collectible);
	    	    }

	            for (int i = 0; i < asteroids.length; i++) {
	                Collectible asteroid = EntityFactory.createAsteroid(0, 0);
	                asteroid.generateSpawnPoint(player.getX(), player.getY());
	                entityList.add(asteroid);
	            }
	    		
	    	}		
	    	else if(stage == 2) {
	    		entityList.add(player);
	    		entityList.add(enemy);
	    		player.setPoints(savedPoints);
	    		player.setHealth(savedHealth);
	    		
	    		collectibles = new Collectible[MAX_COLLECTIBLES];
	    		asteroids = new Collectible[MAX_COLLECTIBLES];
	    		
	    		for (int i = 0; i < 3; i++) {
	    	        Collectible collectible = EntityFactory.createRandomCollectible(0, 0, i + MAX_COLLECTIBLES);
	    	        entityList.add(collectible);
	    	    }
	    		
	            for (int i = 0; i < 3; i++) {
	                Collectible spaceStation = EntityFactory.createSpaceStation(0, 1200);
	                spaceStation.generateSpawnPoint(player.getX(), player.getY());
	                entityList.add(spaceStation);
	            }
	    	}
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
	                PlayerControlManager playerControlManager = new PlayerControlManager((Player) entity, new FirstPlayerMovement());
	                playerControlManager.handleMovement();
	            } else if (entity instanceof Enemy) {
	                AIManager aiManager = new AIManager((Enemy) entity, getPlayer().getX(), getPlayer().getY());
	                aiManager.handleMovement();
	            }
	        }

	        for (Entity entity : entityList) {
	            if (entity instanceof Collectible) {
	                AIManager aiManager = new AIManager((Collectible) entity, entity.getX(), entity.getY(), entity.getSpeed());
	                aiManager.handleMovement(entity.getSpeed());
	            }
	        }
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
		
		
		// Getter and Setters //
		
		// Get Entity List
		public List<Entity> getEntityList() {
		    return entityList;
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
		
		// Get Player from Entity List
		public Player getPlayer() {
		    for (Entity entity : entityList) {
		        if (entity instanceof Player) {
		            return (Player) entity;
		        }
		    }
		    return null; 
		}

		// Get Enemy from Entity List
		public Enemy getEnemy() {
		    for (Entity entity : entityList) {
		        if (entity instanceof Enemy) {
		            return (Enemy) entity;
		        }
		    }
		    return null; 
		}
		
		// Get Collectibles Array from Entity List
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
		
	
		// Class Methods //
		
		// Remove Planet from Collectibles Array
		public void removePlanetFromCollectibles(String planetName) {
		    List<Collectible> collectibleList = getCollectibles();

		    // Count how many collectibles that match the planetName to remove
		    int removeCount = 0;
		    for (Collectible collectible : collectibleList) {
		        if (collectible.getType().equalsIgnoreCase(planetName)) {
		            removeCount++;
		            entityList.remove(collectible);
		        }
		    }
		}

		public int countRemainingPlanets() {
		    int planetCount = 0;
		    List<Collectible> collectibleList = getCollectibles();
		    for (Collectible collectible : collectibleList) {
		        if (isPlanet(collectible.getType())) {
		            planetCount++;
		        }
		    }
		    return planetCount;
		}

		private boolean isPlanet(String type) {
		    String[] planetNames = {"Earth", "Uranus", "Moon", "Mercury", "Venus", "Mars", "Jupiter", "Saturn", "Neptune"};
		    for (String planet : planetNames) {
		        if (type.equalsIgnoreCase(planet)) {
		            return true;
		        }
		    }
		    return false;
		}
}
