package pt.iul.pcd.tribes.client;

import java.net.UnknownHostException;

import pt.iul.pcd.tribes.client.UI.WelcomeScreen;

public class ClientFinal {
	
	private static final String MASTER_SERVER_IP = null;
	private boolean soundIsOn = true;
	private WelcomeScreen welcomeScreen = new WelcomeScreen(MASTER_SERVER_IP, soundIsOn);
	
	public void initGame() {
		
		try {
			welcomeScreen.init();
		} catch (UnknownHostException e) {
			// Server offline
		}
	}

	public static void main(String[] args) {
		ClientFinal newGame = new ClientFinal();
		newGame.initGame();
	}
}