package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import java.util.concurrent.atomic.AtomicBoolean;

public class TimerClass {
	
	private SpriteBatch batch;
	private int time = 30; // Initial time
	private String TimerText = "";
	BitmapFont TimeFont;
	private Task timerTask; // Reference to the scheduled task
	private boolean isPaused = true; // Is timer currently paused
	
	public TimerClass() {
		
	    // Initialize score and scoreSystem
	    TimerText = "Time Remaining : " + time;
	    
	 // Initialize timer text
        updateTimerText();
        
//        // Schedule a task to decrease time every second
//        Timer.schedule(new Task() {
//            @Override
//            public void run() {
//                if (time > 0) {
//                    time--; // Decrease time by 1 second
//                    updateTimerText(); // Update timer text
//                }
//            }
//        }, 1, 1); // Initial delay of 1 second, repeat every 1 second
        scheduleTimerTask();
	}
	
	private void updateTimerText() {
		TimerText = "Time Remaining: " + time;
    }
	
	public void draw() {
		int screenHeight = Gdx.graphics.getHeight();
	    batch = new SpriteBatch();
		// Initialize batch and ScoreFont
	    batch.begin();
	    TimeFont = new BitmapFont();
	    TimeFont.getData().setScale(2);
	    TimeFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	    TimeFont.draw(batch, TimerText, 750, screenHeight - 25);
	    batch.end();
	}
	
	public int getTime() {
        return time;
    }
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public class MutableBoolean {
	    public boolean value;
	}

	public void timerCountdown(int seconds, AtomicBoolean variable) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
            	variable.set(false);
            }
        }, seconds);
    }
	
    public void pauseTimer() {
    	if (!isPaused) {
	        if (timerTask != null) {
	        	isPaused = true;
	            timerTask.cancel(); // Cancel the current task to pause the timer
	        }
    	}
    }
    
    public void resumeTimer() {
    	if (isPaused) {
    		scheduleTimerTask(); // Schedule the timer task to resume counting down
    	}
    }
    
    private void scheduleTimerTask() {
    	isPaused = false;
        timerTask = new Task() {
            @Override
            public void run() {
                if (time > 0) {
                    time--; // Decrease time by 1 second
                    updateTimerText(); // Update timer text
                }
            }
        };
        Timer.schedule(timerTask, 1, 1); // Initial delay of 1 second, repeat every 1 second
    }
    
}
