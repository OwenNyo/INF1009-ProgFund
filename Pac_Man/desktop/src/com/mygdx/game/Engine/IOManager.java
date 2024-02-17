package com.mygdx.game.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class IOManager {

//	protected HashMap<String, Music> BGMusic;
//	protected HashMap<String, Sound> SEMusic;
	protected Music BGMusic;
	protected Sound SEMusic;
	protected int BGVolume;
	protected int SEVolume;
	protected boolean muteState;

	//private List<String> BGList, SEList;

	public IOManager(){
//		BGMusic = new HashMap<String, Music>();
//		SEMusic = new HashMap<String, Sound>();
//		BGList = new ArrayList<String>();
//		SEList = new ArrayList<String>();
		BGMusic = Gdx.audio.newMusic(Gdx.files.internal("BGMusic/bg1.wav"));
		SEMusic = Gdx.audio.newSound(Gdx.files.internal("SEMusic/se1.wav"));
		BGVolume = 1;
//		SEVolume = 1;
		muteState = false;
	}
	
	
	public void playBG() {
		BGMusic.setLooping(true);
		BGMusic.setVolume(BGVolume);
		BGMusic.play();
	}

	public void playSE() {
		SEMusic.play();
	}
	
	public void stopBG() {
	    if (BGMusic.isPlaying()) {
	        BGMusic.stop();
	    }
	}
	
	
//	public void adjustBG() {
//		if (BGVolume < 1 && BGVolume > 0) {
//			if (Gdx.input.isKeyPressed(Keys.MINUS)) {
//				BGVolume -= 0.1;
//				BGMusic.setVolume(BGVolume);
//			}
//			if (Gdx.input.isKeyPressed(Keys.PLUS)) {
//				BGVolume += 0.1;
//				BGMusic.setVolume(BGVolume);
//			}
//		
//		}
//	}
	
	//mute audio 
//	public void muteToggle() {
//		// mute or unmute ??
//		if (muteState = false) {
//			System.out.println("in mute is false");
//			this.muteState = true;
//			setBGVolume(0);
//			setSEVolume(0);
//		} else if (muteState = true){
//			System.out.println("in mute is true");
//			this.muteState = false;
//			setBGVolume(1);
//			setSEVolume(1);
//		}
//	
//		
//	}
	
	
	// Getter and Setter
	public int getBGVolume() {
		return BGVolume;
	}
	public void setBGVolume(int bgvolume) {
		this.BGVolume = bgvolume;
	}
	
	
	public int getSEVolume() {
		return SEVolume;
	}
	public void setSEVolume(int sevolume) {
		this.SEVolume = sevolume;
	}
	

	public boolean getmuteState() {
		return muteState;
	}
	public void setmuteState(boolean mutestate) {
		this.muteState = mutestate;
	}
	
	
	 // Dispose method to clear resources
	 public void dispose() {
	        BGMusic.dispose();
	        SEMusic.dispose();
	 }
	

	
}
