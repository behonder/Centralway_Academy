package pt.iul.pcd.tribes.server.messages;

public class LoseMessage extends Message {

	private final String playerName;

	public LoseMessage(String playerName) {
		this.playerName = playerName;
		
	}

	@Override
	public MessageType getType() {
		return MessageType.LOSE;
	}

	public String getPlayerName() {
		return playerName;
	}

}
