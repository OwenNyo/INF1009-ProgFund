package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class score {
	
	private SpriteBatch batch;
	private int score = 0;
	private String scoreSystem = "";
	BitmapFont ScoreFont;
	
	public score() {
	    // Initialize score and scoreSystem
	    score = 0;
	    scoreSystem = "Score : " + score + " Poor";
	}
	
	public void draw() {

	    batch = new SpriteBatch();
		// Initialize batch and ScoreFont
	    batch.begin();
	    ScoreFont = new BitmapFont();
	    ScoreFont.getData().setScale(2);
	    ScoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	    ScoreFont.draw(batch, scoreSystem, 50, 450);
	    batch.end();
	}
	
	public void calculateScore()
	{
	    score += 10;
	    
	    if(score >= 100 && score < 200) {
	    	scoreSystem = "Score : " + score + " Good";
	    }
	    else if (score >= 200) {
	    	scoreSystem = "Score : " + score + " Excellent";
	    }
	    else {
	    	scoreSystem = "Score : " + score + " Poor";
	    }
		
	 
	}
}
