package pt.iul.pcd.tribes.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import pt.iul.pcd.tribes.server.tools.DealWithGameClient;
import pt.iul.pcd.tribes.server.tools.GamesManager;
import pt.iul.pcd.tribes.server.tools.ServerGUI;

public class Server {

	// ------------ NETWORK VARIABLES ----------------
	public static final int GAME_PORT = 8081;
	private GamesManager gamesManager = new GamesManager();
	
	// ------------ Graphic Interface ----------------
	private ServerGUI gui = new ServerGUI();

	// ------------ Server Functions  ----------------
	public static void main(String[] args) {
		new Server().serve();
	}
	
	public void serve() {
		gui.setVisible(true);
		gameConnectionListener();
	}
	
	private void gameConnectionListener() {
		ServerSocket ss = null;
		try {
			
			ss = new ServerSocket(GAME_PORT);
			gui.writeMessageOnConsole("SERVER PORT CREATED: " + ss.toString());
			gui.writeMessageOnConsole("Game server started on port: " + GAME_PORT);
			
			while (true) {
				Socket socket = ss.accept();
				gui.writeMessageOnConsole("Client connected: " + socket.toString());
				
				DealWithGameClient newClient = new DealWithGameClient(socket, gui, gamesManager);
				newClient.start();
			}
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "ERROR: one instance of the server is already running. \nSystem is shutting down.");
			gui.dispose();
		} 		
	}
}