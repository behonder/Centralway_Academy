package pt.iul.pcd.tribes.server.messages;


public class TextMessage extends Message {

	private String text;
	private String playerName;
	
	public String getText() {
		return text;
	}

	public TextMessage(String playerName, String text) {
		super();
		this.text = text;
		this.setPlayerName(playerName);
	}

	@Override
	public MessageType getType() {
		return MessageType.TEXT;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

}
