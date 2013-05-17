package pt.iul.pcd.tribes.server.messages;

import javax.swing.JList;

public class GamesList extends Message{

	private JList gamesList;
	
	public GamesList(){
		super();
	}
	
	public void setGamesList(JList gamesList){
		this.gamesList = gamesList;
	}
	
	@Override
	public MessageType getType() {
		return MessageType.GETLIST;
	}

	public JList getGamesList(){
		return gamesList;
	}
	
}
