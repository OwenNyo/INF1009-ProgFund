package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class IOManager {
	

	// Attributes for managing audio
    protected Music BGMusic;
    protected Sound SEMusic;
    protected Sound SEMusicCollect; 
    protected float BGVolume = 0.5f;
    protected float SEVolume = 0.5f;
    protected boolean muteState;
    protected Preferences preferences; 

    public IOManager() {
    	
    	// Initialize audio resources
        BGMusic = Gdx.audio.newMusic(Gdx.files.internal("BGMusic/bg1.wav"));
        SEMusic = Gdx.audio.newSound(Gdx.files.internal("SEMusic/se1.wav"));
        SEMusicCollect = Gdx.audio.newSound(Gdx.files.internal("SEMusic/se2.wav"));
        muteState = false;

        // Initialize preferences with a unique name
        preferences = Gdx.app.getPreferences("SoundSettings");

        // Load settings from preferences
        loadSettings();
    }

    // Method to load settings from preferences
    private void loadSettings() {
    	
        // Load background music volume
        BGVolume = preferences.getFloat("BGVolume", BGVolume);
        
        // Load sound effects volume
        SEVolume = preferences.getFloat("SEVolume", SEVolume);
        
        // Load mute state
        muteState = preferences.getBoolean("muteState", muteState);
    }

    // Method to save settings to preferences
    private void saveSettings() {
    	
        // Save background music volume
        preferences.putFloat("BGVolume", BGVolume);
        
        // Save sound effects volume
        preferences.putFloat("SEVolume", SEVolume);
        
        // Save mute state
        preferences.putBoolean("muteState", muteState);
        
        // Flush changes to file
        preferences.flush();
    }

    
    // Method to play background music
    public void playBG() {
        BGMusic.setLooping(true);
        BGMusic.setVolume(BGVolume);
        BGMusic.play();
    }

    // Method to play sound effects for ghost collision
    public void playSE() {
        SEMusic.play(SEVolume);
    }

    // Method to play sound effects for planet collision
    public void playSECollect() {
        SEMusicCollect.play(SEVolume);
    }

    // Method to stop background music
    public void stopBG() {
        if (BGMusic.isPlaying()) {
            BGMusic.stop();
        }
    }

    // Getters and setters for background volume, sound effects volume, and mute state
    
    // Background volume
    public float getBGVolume() {
        return BGVolume;
    }

    public void setBGVolume(float volume) {
        this.BGVolume = volume;
        saveSettings();
    }

    // Sound Effects volume
    public float getSEVolume() {
        return SEVolume;
    }

    public void setSEVolume(float volume) {
        this.SEVolume = volume;
        saveSettings();
    }

    // Mute State
    public boolean getMuteState() {
        return muteState;
    }

    public void setMuteState(boolean mutestate) {
        this.muteState = mutestate;
        saveSettings();
    }

    // Dispose method to clear resources
    public void dispose() {
        BGMusic.dispose();
        SEMusic.dispose();
        SEMusicCollect.dispose();
    }
}
