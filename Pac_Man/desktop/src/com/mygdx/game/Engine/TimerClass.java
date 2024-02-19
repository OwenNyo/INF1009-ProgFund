package com.mygdx.game.Engine;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class TimerClass {
	
	private SpriteBatch batch;
	private int time = 60; // Initial time
	private String TimerText = "";
	BitmapFont TimeFont;
	
	public TimerClass() {
		
	    // Initialize score and scoreSystem
	    TimerText = "Time Remaining : " + time;
	    
	 // Initialize timer text
        updateTimerText();
        
        // Schedule a task to decrease time every second
        Timer.schedule(new Task() {
            @Override
            public void run() {
                if (time > 0) {
                    time--; // Decrease time by 1 second
                    updateTimerText(); // Update timer text
                }
            }
        }, 1, 1); // Initial delay of 1 second, repeat every 1 second
	}
	
	private void updateTimerText() {
		TimerText = "Time Remaining: " + time;
    }
	
	public void draw() {

	    batch = new SpriteBatch();
		// Initialize batch and ScoreFont
	    batch.begin();
	    TimeFont = new BitmapFont();
	    TimeFont.getData().setScale(2);
	    TimeFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	    TimeFont.draw(batch, TimerText, 390, 775);
	    batch.end();
	}
	
	public int getTime() {
        return time;
    }
	
	public void setTime(int time) {
		this.time = time;
	}
}
