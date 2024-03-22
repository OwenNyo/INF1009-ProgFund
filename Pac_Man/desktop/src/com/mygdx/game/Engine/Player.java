package com.mygdx.game.Engine;

import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;

public class Player extends Entity{

		/// Class Attributes
		private float ReducedSpeed;
		private int Health;
		private int Points;
		private boolean IsFirstPlayer;
		private SpriteBatch batch;
		private String remainingHealth = "";
		BitmapFont healthFont;
		
		//	Constructors
		public Player() {
			this.Health = 0;
		}
		
		// Player to earn points via pellet eating -> win cond = survive longer than other player while stacking
		public Player(String type, String filename, float x, float y, float speed, int health, int points, float height, float width, boolean AIControlled, boolean fp) {
			super(type, filename, x, y, width, height, speed, AIControlled);
			this.Health = health;
			this.IsFirstPlayer = fp;
			this.Points = points;
		}
		
		
		
		//	Getters and Setters
		public int getHealth() {
			return Health;
		}
		public void setHealth(int health) {
			Health = health;
		}
		public int getPoints() {
			return Points;
		}
		public void setPoints(int points) {
			Points = points;
		}
		
		public boolean isFirstPlayer() {
	        return IsFirstPlayer;
	    }
		
		
		// Class Methods
		public void PlayerScorePoints(int points) {
			Points = getPoints();
			Points += points;
			
//			System.out.println("Player has earned 10 points");
		}
		
		public void PlayerDamageTaken(int damage) {
			Health = getHealth();
			Health -= damage;
			
			if (Health > 0) {
				System.out.println("Damage Taken by Player, Health left:" + getHealth());
			}
			else {
				System.out.println("Player has lost all his health!");
			}
		}
		
		public void PlayerHealthHealed(int heal) {
			Health = getHealth();
			Health += heal;
			
			System.out.println("Heal Taken by Player, Health left:" + getHealth());
		}
		
		public void PlayerSpeedReduced() {
			ReducedSpeed = 300 / 2;
			
			setSpeed(ReducedSpeed);

		    // Schedule a task to restore original speed after the specified duration
		    Timer.schedule(new Timer.Task() {
		        @Override
		        public void run() {
		            // Restore original speed after the duration
		            setSpeed(300);
		        }
		    }, 5);
	
		}
		
		
		public void draw() {
			// Initialize batch 
			batch = new SpriteBatch();
			batch.begin();
			
			// Ensure it doesn't spawn outside the boundary
			float clampedX = MathUtils.clamp(getX(), 0, Gdx.graphics.getWidth() - super.getWidth());
			float clampedY = MathUtils.clamp(getY(), 0, Gdx.graphics.getHeight() - super.getHeight());

			// Draw with width and height of 50
			batch.draw(super.getTex(), clampedX, clampedY, super.getWidth(), super.getHeight());
			batch.end();
		 }
		
}
