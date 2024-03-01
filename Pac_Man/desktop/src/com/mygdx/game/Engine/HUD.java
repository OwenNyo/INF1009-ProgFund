package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {
    private SpriteBatch batch;
    private BitmapFont scoreFont;
    private BitmapFont healthFont;
	int screenHeight = Gdx.graphics.getHeight();

    public HUD() {
        batch = new SpriteBatch();
        scoreFont = new BitmapFont();
        scoreFont.getData().setScale(2);
        healthFont = new BitmapFont();
        healthFont.getData().setScale(2);
    }

    public void drawScore(int score) {
        batch.begin();
        scoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        scoreFont.draw(batch, "Score: " + score, 25, screenHeight - 25);
        batch.end();
    }

    public void drawRemainingHealth(int health) {
        batch.begin();
        healthFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        healthFont.getData().setScale(2);
        healthFont.draw(batch, "Health: " + health, 1500, screenHeight - 25);
        batch.end();
    }
    
    public void drawBackground(Texture backgroundTexture) {
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        scoreFont.dispose();
        healthFont.dispose();
    }
}
