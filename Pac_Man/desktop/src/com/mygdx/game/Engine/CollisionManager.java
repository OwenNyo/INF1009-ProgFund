package com.mygdx.game.Engine;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Engine.IOManager;

public class CollisionManager {
	
	private IOManager ioManager = new IOManager();

	// Logic to check for collision with Ghost
    public static boolean collidedWithGhost(Player player, Ghost ghost) {
        Rectangle playerBounds = player.getBoundingRectangle();
        Rectangle ghostBounds = ghost.getBoundingRectangle();

        return playerBounds.overlaps(ghostBounds);
    }
    
    // Logic to handle what happens if there is a collision with Ghost
    public void checkGhostCollision(Player player, Ghost ghost) {
        if (collidedWithGhost(player, ghost)) {
            System.out.println("Ghost Collision detected");
            player.PlayerDamageTaken(10);
            ghost.GenerateSpawnPoint(player.getX(), player.getY());
            
            ioManager.playSE();
        }
    }

    // Logic to check for collision with Pellet
    public static boolean collidedWithCollectible(Player player, Collectible c) {
        Rectangle playerBounds = player.getBoundingRectangle();
        Rectangle collectibleBounds = c.getBoundingRectangle();

        return playerBounds.overlaps(collectibleBounds);
    }

    // Logic to handle what happens if there is a collision with Pellet
    public boolean checkCollectibleCollision(Player player, Collectible collectibles[]) {
        for (Collectible c : collectibles) {
            if (collidedWithCollectible(player, c)) {
                System.out.println("Pellet Collision detected");
                c.resetPosition(player.getX(), player.getY());
                return true;
            }
        }
        return false;
    }
}

