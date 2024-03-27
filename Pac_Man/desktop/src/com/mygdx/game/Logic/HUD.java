package com.mygdx.game.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {
	// Fields
    private SpriteBatch batch;
    private BitmapFont scoreFont;
    private BitmapFont healthFont;
	int screenHeight = Gdx.graphics.getHeight();

	// Constructor to initialize HUD elements
    public HUD() {
        batch = new SpriteBatch();
        scoreFont = new BitmapFont();
        scoreFont.getData().setScale(2);
        healthFont = new BitmapFont();
        healthFont.getData().setScale(2);
    }

    // Method to draw the score on the HUD
    public void drawScore(int score) {
        batch.begin();
        scoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        scoreFont.draw(batch, "Score: " + score, 25, screenHeight - 25);
        batch.end();
    }

 // Method to draw the remaining health on the HUD
    public void drawRemainingHealth(int health) {
        batch.begin();
        healthFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        healthFont.getData().setScale(2);
        healthFont.draw(batch, "Health: " + health, 1500, screenHeight - 25);
        batch.end();
    }
    
    // Method to draw the background texture
    public void drawBackground(Texture backgroundTexture) {
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    // Method to dispose of resources
    public void dispose() {
        batch.dispose();
        scoreFont.dispose();
        healthFont.dispose();
    }
}
