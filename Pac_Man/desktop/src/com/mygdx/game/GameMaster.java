package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameMaster extends ApplicationAdapter {
	
	// Object Declaration
	private Player player;
	private Ghost ghost;
	private Collectible collectibles[];
	private Score score;
	private List<Entity> entityList;
	
	private EntityManager entityManager;
	
	@Override
	public void create() {
		
		// Initialize EntityManager and entities
		entityManager = new EntityManager();
		entityManager.initEntities();
		
		// Fetch Entity List
		entityList = entityManager.getEntityList();
		
		// Initialize Scoring System
		score = new Score();
	}
	
	@Override
	public void render() {
		// This line is used to ensure that the screen is blank and set to a dark blue background
		ScreenUtils.clear(0 , 0, 0.2f, 1); 
		
		// Texture Drawing 
		score.draw();
		entityManager.drawEntities();
        entityManager.moveEntities();
	
        // Create a new list to hold the collectible
        List<Collectible> collectibleList = new ArrayList<>();

        // Loop through the entity list to filter collectible
        for (Entity entity : entityList) {
            if (entity instanceof Collectible) {
                collectibleList.add((Collectible) entity);
            }
            if (entity instanceof Player) {
                player = (Player) entity;
            } else if (entity instanceof Ghost) {
                ghost = (Ghost) entity;
            }
        }
        
        // Convert the list of collectible back to an array
        collectibles = collectibleList.toArray(new Collectible[collectibleList.size()]);

        if (CollisionManager.checkCollectibleCollision(player, collectibles)) {
            score.calculateScore();
        }

        
        // Check ghost collision
        if (player != null && ghost != null) {
            CollisionManager.checkGhostCollision(player, ghost);
        }
	    
	}
	
	@Override
	public void dispose() {
		// Properly dispose textures
		entityManager.disposeEntities();
	}
	
	
}