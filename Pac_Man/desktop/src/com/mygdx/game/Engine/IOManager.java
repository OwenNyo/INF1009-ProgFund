package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class IOManager {

    protected Music BGMusic;
    protected Sound SEMusic;
    protected Sound SEMusicCollect; 
    protected float BGVolume;
    protected float SEVolume;
    protected boolean muteState;
    protected Preferences preferences; // Preferences object to save/load settings

    public IOManager() {
        BGMusic = Gdx.audio.newMusic(Gdx.files.internal("BGMusic/bg1.wav"));
        SEMusic = Gdx.audio.newSound(Gdx.files.internal("SEMusic/se1.wav"));
        SEMusicCollect = Gdx.audio.newSound(Gdx.files.internal("SEMusic/se2.wav"));
        BGVolume = 0.5f; // Default volume set as float
        SEVolume = 0.5f; // Default volume set as float
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

    // Method to play sound effects - ghost collide
    public void playSE() {
        SEMusic.play(SEVolume);
    }

    // Method to play sound effects - collect pellets 
    public void playSECollect() {
        SEMusicCollect.play(SEVolume);
    }

    // Method to stop background music
    public void stopBG() {
        if (BGMusic.isPlaying()) {
            BGMusic.stop();
        }
    }

    // Getter and Setter for background volume
    public float getBGVolume() {
        return BGVolume;
    }

    public void setBGVolume(float volume) {
        this.BGVolume = volume;
        // Save settings when volume is changed
        saveSettings();
    }

    // Getter and Setter for sound effects volume
    public float getSEVolume() {
        return SEVolume;
    }

    public void setSEVolume(float volume) {
        this.SEVolume = volume;
        // Save settings when volume is changed
        saveSettings();
    }

    // Getter and Setter for mute state
    public boolean getMuteState() {
        return muteState;
    }

    public void setMuteState(boolean mutestate) {
        this.muteState = mutestate;
        // Save settings when mute state is changed
        saveSettings();
    }

    // Dispose method to clear resources
    public void dispose() {
        BGMusic.dispose();
        SEMusic.dispose();
        SEMusicCollect.dispose();
    }
}
