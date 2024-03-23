package com.mygdx.game.Engine;

import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;

public class Player extends Entity {

    // Class Attributes
    private float reducedSpeed; // Naming convention: camelCase for variables
    private int health;
    private int points;
    private boolean isFirstPlayer;
    private SpriteBatch batch;
    private String remainingHealth = "";
    private BitmapFont healthFont;

    // Constructors
    public Player() {
        this.health = 0;
    }

    // Player constructor with parameters
    public Player(String type, String filename, float x, float y, float speed, int health, int points,
            float height, float width, boolean AIControlled, boolean isFirstPlayer) {
        super(type, filename, x, y, width, height, speed, AIControlled);
        this.health = health;
        this.isFirstPlayer = isFirstPlayer;
        this.points = points;
    }

    // Getters and Setters
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    // Class Methods

    // Method to increase player's points
    public void playerScorePoints(int points) {
    	
    	// Track points
        this.points += points; 
        
        // Output for debugging or logging
        System.out.println("Player has earned " + points + " points. Total points: " + this.points);
    }

    // Method to decrease player's health when damaged
    public void playerDamageTaken(int damage) {
    	
    	// Track health left
        this.health -= damage; 
        
     // Output for debugging or logging
        if (health > 0) {           
            System.out.println("Damage taken by player. Health left: " + health);
        } 
        else {
            System.out.println("Player has lost all their health!");
        }
    }

    // Method to increase player's health when healed
    public void playerHealthHealed(int heal) {
    	
    	// Track health gain
        this.health += heal;
        
        // Output for debugging or logging
        System.out.println("Health healed for player. Health now: " + health);
    }

    // Method to temporarily reduce player's speed
    public void playerSpeedReduced() {
    	
    	// Reduce and set speed by half
        reducedSpeed = 300 / 2; 
        setSpeed(reducedSpeed);

        // Schedule a task to restore original speed after a specified duration
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                // Restore original speed after the duration
                setSpeed(300);
            }
        }, 5); 
    }

    // Method to draw the player
    public void draw() {
        // Initialize batch
        batch = new SpriteBatch();
        batch.begin();

        // Ensure the player doesn't spawn outside the screen boundaries
        float clampedX = MathUtils.clamp(getX(), 0, Gdx.graphics.getWidth() - super.getWidth());
        float clampedY = MathUtils.clamp(getY(), 0, Gdx.graphics.getHeight() - super.getHeight());

        // Draw the player with its current position and dimensions
        batch.draw(super.getTex(), clampedX, clampedY, super.getWidth(), super.getHeight());
        batch.end();
    }
}
