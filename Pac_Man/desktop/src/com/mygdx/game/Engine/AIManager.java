package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Interfaces.Movement;

public class AIManager implements Movement {
    
    private Ghost ghost;
    private float playerX;
    private float playerY;
    
    public AIManager(Ghost ghost, float playerX, float playerY) {
        this.ghost = ghost;
        this.playerX = playerX;
        this.playerY = playerY;
    }

    @Override
    public void handleMovement() {
    	// Calculate the direction vector towards the player
        Vector2 playerPosition = new Vector2(playerX, playerY);
        Vector2 ghostPosition = new Vector2(ghost.getX(), ghost.getY());
        Vector2 direction = playerPosition.sub(ghostPosition).nor();
        
        // Move the ghost towards the player
        float speedMultiplier = 10.0f; // Adjust speed if needed
        Vector2 velocity = direction.scl(ghost.getSpeed() * speedMultiplier * Gdx.graphics.getDeltaTime());
        ghostPosition.add(velocity);
        
        // Update the ghost's position
        ghost.setX(ghostPosition.x);
        ghost.setY(ghostPosition.y);
    }
}
