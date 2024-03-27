package com.mygdx.game.Logic;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Engine.Entity;

public class Enemy extends Entity {
    
    // Static Variables
    private static final int NO_SPAWN_RADIUS = 30;

    // Class Attributes
    private int damage;    
    private SpriteBatch batch;

    // Constructors
    public Enemy() {
        super();
        this.damage = 0;
    }
    
    public Enemy(String type, String filename, float x, float y, float speed, int damage, float height, float width, boolean AIControlled) {
        super(type, filename, x, y, height, width, speed, AIControlled);
        this.damage = damage;
    }

    // Getters and Setters
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    // Class Methods
    
    // Generate spawn point for enemy
    public void generateSpawnPoint(float playerX, float playerY) {
        Random rand = new Random();
        System.out.println("Generating the enemy spawn point to avoid spawning on top of the player");

        // Generate a min and max pos, preventing enemies from spawning in this range
        float minX = playerX - NO_SPAWN_RADIUS;
        float maxX = playerX + NO_SPAWN_RADIUS;
        float minY = playerY - NO_SPAWN_RADIUS;
        float maxY = playerY + NO_SPAWN_RADIUS;
        
        // Initialize x and y coordinates for spawn location
        int x, y;

        // Adjust the range for x and y to be within the screen boundaries
        do {
            x = rand.nextInt(1500);
            y = rand.nextInt(900);
        } while (x > minX && x < maxX && y > minY && y < maxY);

        // Set the spawn location after confirming it's not within range of the player
        super.setX(x);
        super.setY(y);
        
        // Release the Random object
        rand = null; 
    }

    // Draw enemy
    public void draw() {
        // Initialize batch 
        batch = new SpriteBatch();
        batch.begin();
        
        // Ensure it doesn't spawn outside the boundary
        float clampedX = MathUtils.clamp(getX(), 0, Gdx.graphics.getWidth() - super.getWidth());
        float clampedY = MathUtils.clamp(getY(), 0, Gdx.graphics.getHeight() - super.getHeight());

        // Draw enemy with width and height of 50
        batch.draw(super.getTex(), clampedX, clampedY, super.getWidth(), super.getHeight());
        batch.end();
     }
}
