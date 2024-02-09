package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class CollisionManager {

    public static boolean collidedWithGhost(Player player, Ghost ghost) {
        Rectangle playerBounds = player.getBoundingRectangle();
        Rectangle ghostBounds = ghost.getBoundingRectangle();

        return playerBounds.overlaps(ghostBounds);
    }

    public static void checkGhostCollision(Player player, Ghost ghost) {
        if (collidedWithGhost(player, ghost)) {
            System.out.println("Ghost Collision detected");
            player.PlayerDamageTaken(10);
            ghost.GenerateSpawnPoint(player.getX(), player.getY());
        }
    }

    public static boolean collidedWithCollectible(Player player, Collectible c) {
        Rectangle playerBounds = player.getBoundingRectangle();
        Rectangle collectibleBounds = c.getBoundingRectangle();

        return playerBounds.overlaps(collectibleBounds);
    }

    public static boolean checkCollectibleCollision(Player player, Collectible collectibles[]) {
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

