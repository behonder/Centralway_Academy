package pt.iul.pcd.tribes.server.messages;

import java.io.ObjectOutputStream;

public class CreateGameMessage extends Message {

	private String gameCreator;
	private String gameName;
	private int numberOfPlayers;
	private ObjectOutputStream out;
	
	public CreateGameMessage(String gameCreator, String gameName, Integer numberOfPlayers) {
		this.gameCreator = gameCreator;
		this.gameName = gameName;
		this.numberOfPlayers = numberOfPlayers;
	}

	@Override
	public MessageType getType() {
		return MessageType.CREATE;
	}

	public String getGameCreator() {
		return gameCreator;
	}

	public void setGameCreator(String gameCreator) {
		this.gameCreator = gameCreator;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	@Override
	public String toString() {
		return "CreateGameMessage [gameCreator=" + gameCreator
				+ ", numberOfPlayers=" + numberOfPlayers + ", gameName="
				+ gameName + "]";
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameName() {
		return gameName;
	}

}
