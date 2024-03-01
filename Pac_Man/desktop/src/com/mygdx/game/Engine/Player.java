package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Player extends Entity{

		/// Class Attributes
		private float Speed;
		private int Health;
		private int Points;
		private boolean IsFirstPlayer;
		private SpriteBatch batch;
		private String remainingHealth = "";
		BitmapFont healthFont;
		
		//	Constructors
		public Player() {
			this.Speed = 0;
			this.Health = 0;
		}
		
		// Player to earn points via pellet eating -> win cond = survive longer than other player while stacking
		public Player(String type, String filename, float x, float y, float speed, int health, int points, float height, float width, boolean AIControlled, boolean fp) {
			super(type, filename, x, y, width, height, AIControlled);
			this.Speed = speed;
			this.Health = health;
			this.IsFirstPlayer = fp;
			this.Points = points;
		}
		
		
		
		//	Getters and Setters
		public float getSpeed() {
			return Speed;
		}
		public void setSpeed(float speed) {
			Speed = speed;
		}
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
