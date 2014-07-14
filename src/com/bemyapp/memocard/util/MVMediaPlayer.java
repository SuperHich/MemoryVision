package com.bemyapp.memocard.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.bemyapp.memocard.R;

public class MVMediaPlayer {
	
	private static MVMediaPlayer countryPlayer = null;
	
	private static MediaPlayer 	mp1 = null,
								mp2 = null,
								mp3 = null,
								mp4 = null,
								mp5 = null,
								mp6 = null,
								mp7 = null;
	
	private boolean isSoundOn = true,
					isMusicOn = true;
	
	private Context context;
	
	private MVMediaPlayer(Context context) {
		this.context = context;
	}
	
	public static MVMediaPlayer getInstance(Context context){
		if(countryPlayer == null)
			countryPlayer = new MVMediaPlayer(context);
		
		return countryPlayer;
	}
		
	public synchronized void initBackgroundSound(){
		
		stopBackgroundSound();
		
		mp1 = MediaPlayer.create(context, R.raw.concentration_song);
		mp1.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mp1.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer arg0) {
				
				resumeBackgroundSound();
				
			}
		});
	}
	
	public void resumeBackgroundSound(){
		stopEffectMusic();
		
		if(mp1 != null && isMusicOn){
			if(!mp1.isPlaying())
				mp1.start();
		}
	}
	
	public void pauseBackgroundSound(){
		if(mp1 != null){
			mp1.pause();
		}
	}
	
	public void stopBackgroundSound(){
		if(mp1 != null){
			mp1.stop();
			mp1.release();
			mp1 = null;
		}
	}
	
	public synchronized void playClick() {
		if(mp2 != null){
			mp2.stop();
			mp2.release();
			mp2 = null;
		}
		
		if(isSoundOn){
			mp2 = MediaPlayer.create(context, R.raw.click_sound_01);
			mp2.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp2.start();
		}
	}
	
	public synchronized void playFail(){
		if(mp3 != null){
			mp3.stop();
			mp3.release();
			mp3 = null;
		}
		
		if(isSoundOn){
			mp3 = MediaPlayer.create(context, R.raw.fail_sound_01);
			mp3.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp3.start();
		}
	}
	
	public synchronized void playSuccess(){
		if(mp4 != null){
			mp4.stop();
			mp4.release();
			mp4 = null;
		}
		
		if(isSoundOn){
			mp4 = MediaPlayer.create(context, R.raw.success);
			mp4.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp4.start();
		}
	}
	
	public synchronized void playMenuClick(){
		if(mp5 != null){
			mp5.stop();
			mp5.release();
			mp5 = null;
		}
		
		if(isSoundOn){
			mp5 = MediaPlayer.create(context, R.raw.menu_click);
			mp5.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp5.start();
		}
	}
	
	public synchronized void playSingleCardFlip(){
		if(mp6 != null){
			mp6.stop();
			mp6.release();
			mp6 = null;
		}
		
		if(isSoundOn){
			mp6 = MediaPlayer.create(context, R.raw.card_flip_sound);
			mp6.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp6.start();
		}
	}
	
	public synchronized void playCardsFlipSound(){
		if(mp7 != null){
			mp7.stop();
			mp7.release();
			mp7 = null;
		}
		
		if(isSoundOn){
			mp7 = MediaPlayer.create(context, R.raw.shuffling_cards_2);
			mp7.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp7.start();
		}
	}
	
	public synchronized void stopEffectMusic(){
		
		if(mp2 != null){
			mp2.stop();
			mp2.release();
			mp2 = null;
		}
		
		if(mp3 != null){
			mp3.stop();
			mp3.release();
			mp3 = null;
		}
		
		if(mp4 != null){
			mp4.stop();
			mp4.release();
			mp4 = null;
		}
		
		if(mp5 != null){
			mp5.stop();
			mp5.release();
			mp5 = null;
		}
		
		if(mp6 != null){
			mp6.stop();
			mp6.release();
			mp6 = null;
		}
		
		if(mp7 != null){
			mp7.stop();
			mp7.release();
			mp7 = null;
		}
		
	}

	public boolean isSoundOn() {
		return isSoundOn;
	}

	public void setSoundOn(boolean isSoundOn) {
		this.isSoundOn = isSoundOn;
	}

	public boolean isMusicOn() {
		return isMusicOn;
	}

	public void setMusicOn(boolean isMusicOn) {
		this.isMusicOn = isMusicOn;
	}

}
