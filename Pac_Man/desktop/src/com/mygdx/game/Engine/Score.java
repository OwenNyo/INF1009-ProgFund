package com.mygdx.game.Engine;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score {
	
	private SpriteBatch batch;
	private int score = 0;
	private String scoreSystem = "";
	private String EndScore = "";
	BitmapFont ScoreFont;
	
	public Score() {
	    // Initialize score and scoreSystem
	    score = 0;
	    scoreSystem = "Score : " + score;
	}
	
	public void draw() {

	    batch = new SpriteBatch();
		// Initialize batch and ScoreFont
	    batch.begin();
	    ScoreFont = new BitmapFont();
	    ScoreFont.getData().setScale(2);
	    ScoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	    ScoreFont.draw(batch, scoreSystem, 25, 775);
	    batch.end();
	}
	
	public void draw(int totalScore) {

	    EndScore = "Game Over!\nScore : " + totalScore ;
	    batch = new SpriteBatch();
		// Initialize batch and ScoreFont
	    batch.begin();
	    ScoreFont = new BitmapFont();
	    ScoreFont.getData().setScale(3);
	    ScoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	    ScoreFont.draw(batch, EndScore, 390, 660);
	    batch.end();
	}
	
	public void calculateScore()
	{
	    score += 10;
	    scoreSystem = "Score : " + score;
		
	}
	
	public int getScore() {
        return score;
    }
	
	public void setScore(int score) {
		this.score = score;
	}
}
