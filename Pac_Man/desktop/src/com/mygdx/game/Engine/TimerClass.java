package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import java.util.concurrent.atomic.AtomicBoolean;

public class TimerClass {
	
	// Class Attributes
	private SpriteBatch batch;
	private BitmapFont TimeFont;
	private Task timerTask; 
	private boolean isPaused = true; 
	private int time = 30; 
	private String TimerText = "";
	
	public TimerClass() {
		
	    // Initialize score and scoreSystem
	    TimerText = "Time Remaining : " + time;
	    
	    // Initialize timer text
        updateTimerText();
      
        // Schedule the timer task
        scheduleTimerTask();
	}
	
	// Update timer text
	private void updateTimerText() {
		TimerText = "Time Remaining: " + time;
    }
	
	// Draw timer text on screen
	public void draw() {
		int screenHeight = Gdx.graphics.getHeight();
	    batch = new SpriteBatch();
	    batch.begin();
	    TimeFont = new BitmapFont();
	    TimeFont.getData().setScale(2);
	    TimeFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	    TimeFont.draw(batch, TimerText, 750, screenHeight - 25);
	    batch.end();
	}
	
	// Getter and Setter
	
	// Get current time
	public int getTime() {
        return time;
    }
	
	// Set current time
	public void setTime(int time) {
		this.time = time;
	}
	
    
    // Class Methods //
    
    // For Popup Variables
	public void timerCountdown(int seconds, AtomicBoolean variable) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
            	variable.set(false);
            }
        }, seconds);
    }
    
	// Method to schedule the timer task
    private void scheduleTimerTask() {
    	isPaused = false;
        timerTask = new Task() {
            @Override
            public void run() {
                if (time > 0) {
                    time--; 
                    updateTimerText(); 
                }
            }
        };
        // Initial delay of 1 second, repeat every 1 second
        Timer.schedule(timerTask, 1, 1); 
    }
    
    // Method to pause timer
    public void pauseTimer() {
    	if (!isPaused) {
	        if (timerTask != null) {
	        	isPaused = true;
	            timerTask.cancel();
	        }
    	}
    }
    
    // Method to resume timer
    public void resumeTimer() {
    	if (isPaused) {
    		scheduleTimerTask();
    	}
    }
    
}
