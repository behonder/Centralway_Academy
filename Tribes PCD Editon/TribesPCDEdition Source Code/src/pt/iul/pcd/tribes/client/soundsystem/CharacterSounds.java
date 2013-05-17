package pt.iul.pcd.tribes.client.soundsystem;

import java.io.Serializable;

public class CharacterSounds implements Serializable{

	private Player soundPlayer = null;
      
	public void playSound(String soundPath) {
		
		soundPlayer = new Player(soundPath);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				soundPlayer.play(1);
			}
		}).start();
	}
} 
