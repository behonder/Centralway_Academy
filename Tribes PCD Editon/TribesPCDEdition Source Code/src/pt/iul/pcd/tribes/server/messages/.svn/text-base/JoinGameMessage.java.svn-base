package pt.iul.pcd.tribes.server.messages;


public class JoinGameMessage extends Message{

	private final String playerName;
	private final String gameSelected;

	public JoinGameMessage(String playerName, String gameSelected) {
		this.playerName = playerName;
		this.gameSelected = gameSelected;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getGameSelected() {
		return gameSelected;
	}

	@Override
	public MessageType getType() {
		return MessageType.JOIN;
	}

	public String getGameName() {
		return gameSelected;
	}

}
