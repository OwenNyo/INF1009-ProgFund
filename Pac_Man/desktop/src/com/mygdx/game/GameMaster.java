package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Engine.Collectible;
import com.mygdx.game.Engine.CollisionManager;
import com.mygdx.game.Engine.EntityManager;
import com.mygdx.game.Engine.Ghost;
import com.mygdx.game.Engine.Player;
import com.mygdx.game.Engine.Score;

public class GameMaster extends ApplicationAdapter {
	
	// Object Declaration
	private Player player;
	private Ghost ghost;
	private Collectible collectibles[];
	private Score score;
	
	// Manager Declaration
	private EntityManager entityManager;
	
	@Override
	public void create() {
		
		// Initialize EntityManager and entities
		entityManager = new EntityManager();
		entityManager.initEntities();
		
		// Initialize Scoring System
		score = new Score();
		
		// Fetch Entity List
		collectibles = entityManager.getCollectibles();
        player = entityManager.getPlayer();
        ghost = entityManager.getGhost();
		
	}
	
	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0.2f, 1);
        score.draw();
        entityManager.drawEntities();
        entityManager.moveEntities();

        if (CollisionManager.checkCollectibleCollision(player, collectibles)) {
            score.calculateScore();
        }

        CollisionManager.checkGhostCollision(player, ghost);
	    
	}
	
	@Override
	public void dispose() {
		// Properly dispose textures
		entityManager.disposeEntities();
	}
	
	
}