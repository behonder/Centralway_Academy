package pt.iul.pcd.tribes.server.tools;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import pt.iul.pcd.tribes.client.characters.CharacterObject;
import pt.iul.pcd.tribes.client.moveEngine.MovimentEngine;
import pt.iul.pcd.tribes.server.CreatedGame;
import pt.iul.pcd.tribes.server.messages.CreateGameMessage;
import pt.iul.pcd.tribes.server.messages.GamesList;
import pt.iul.pcd.tribes.server.messages.JoinGameMessage;
import pt.iul.pcd.tribes.server.messages.Message;
import pt.iul.pcd.tribes.server.messages.SelectMessage;

public class DealWithGameClient extends Thread {
	
	// --- DEAL WITH GAME CLIENT VARIABLES ---
	private Socket socket;
	private ServerGUI gui;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String nameOfPlayer;
	private CreatedGame gameJoined;
	private GamesManager gamesManager;
	
	// --- DEAL WITH GAME CLIENT FUNCTIONS ---
	public DealWithGameClient(Socket socket, ServerGUI gui, GamesManager gamesManager) {
		this.setGui(gui);
		this.gamesManager = gamesManager;
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			
			while(true){
				getGui().writeMessageOnConsole("Awaiting for orders from client: " + socket.toString());

				
				Message instruction = (Message) in.readObject();
				getGui().writeMessageOnConsole("Message received: " + socket.toString());

				switch (instruction.getType()) {
				case GETLIST:
						getList(socket, out);
						break;
				case CREATE:
						createGame(instruction);
						break;

				case JOIN:
						joinGame(instruction);
						break;
						
				case SELECTEDANDMOVE:
						selectAndMove(instruction);
						break;
				case TEXT:
						broadcastText(instruction);
						break;
				}

			}
		} catch (IOException e) {
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void broadcastText(Message instruction) {
		gameJoined.getGamesBroadcaster().broadcastMessage(instruction);
	}

	private void selectAndMove(Message instruction) {
		Point pointSelectedFrom = ((SelectMessage) instruction).getPointFrom();
		Point pointSelectedTo = (((SelectMessage) instruction).getPointTo());
		
		MovimentEngine engine = gameJoined.getMatrixThreads()[pointSelectedFrom.x][pointSelectedFrom.y];
		
		if(engine != null){
			CharacterObject dollSelected = gameJoined.getMatrixThreads()[pointSelectedFrom.x][pointSelectedFrom.y].getCharacterObject();
			if(dollSelected != null && dollSelected.getPlayerName().equals(nameOfPlayer)){	
				engine.setNextXAfterSelection(pointSelectedTo.x);
				engine.setNextYAfterSelection(pointSelectedTo.y);
				engine.getSemaphore().release();
			}
		}	
	}

	private void getList(Socket socket, ObjectOutputStream out){
		try {
			getGui().writeMessageOnConsole("ORDER RECEIVED: send game list > " + socket.getInetAddress());

			GamesList list = new GamesList();
			list.setGamesList(gamesManager.getExistingGames());
			out.writeObject(list);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createGame(Message instruction){
		nameOfPlayer = ((CreateGameMessage) instruction).getGameCreator();
		gameJoined = new CreatedGame(((CreateGameMessage) instruction).getGameCreator(),((CreateGameMessage) instruction).getGameName(),((CreateGameMessage) instruction).getNumberOfPlayers(), out);
		gamesManager.addGame(gameJoined);
		
		getGui().writeMessageOnConsole("ORDER RECEIVED: create game. DATA:> " + instruction.toString());
	}
	
	private void joinGame(Message instruction){
		JoinGameMessage temporaryGame = ((JoinGameMessage) instruction);
		nameOfPlayer = temporaryGame.getPlayerName();
		setGameJoined(gamesManager.findTheGameAndJoin(temporaryGame, out));
		
		getGui().writeMessageOnConsole("ORDER RECEIVED: connect client: " + socket.toString() + "TO GAME: " + temporaryGame.getGameName());
	}

	public void setGui(ServerGUI gui) {
		this.gui = gui;
	}

	public ServerGUI getGui() {
		return gui;
	}

	public void setGameJoined(CreatedGame gameJoined) {
		this.gameJoined = gameJoined;
	}

	public CreatedGame getGameJoined() {
		return gameJoined;
	}
}