package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Player extends Entity{

		/// Class Attributes
		private float Speed;
		private int Health;
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
		public Player(String type, String filename, float x, float y, float speed, int health, float height, float width, boolean AIControlled, boolean fp) {
			super(type, filename, x, y, width, height, AIControlled);
			this.Speed = speed;
			this.Health = health;
			this.IsFirstPlayer = fp;
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
		
		public boolean isFirstPlayer() {
	        return IsFirstPlayer;
	    }
		
		
		// Class Methods
		public void PlayerPoints(int points) {
			System.out.println("Player <> earned points");
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
		
		public void drawRemainingHealth() {

			remainingHealth = "Health: " + this.Health;
			
		    batch = new SpriteBatch();
			// Initialize batch and ScoreFont
		    batch.begin();
		    healthFont = new BitmapFont();
		    healthFont.getData().setScale(2);
		    healthFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		    healthFont.draw(batch, remainingHealth, 825, 775);
		    batch.end();
		}
}
