package pt.iul.pcd.tribes.server.messages;

import java.util.LinkedList;

import pt.iul.pcd.tribes.client.characters.CharacterObject;

public class StartGame extends Message {

	private LinkedList<CharacterObject> listOfCharacters = new LinkedList<CharacterObject>();
	
	public StartGame(LinkedList<CharacterObject> listOfCharacters) {
		this.listOfCharacters = listOfCharacters;
	}
	
	@Override
	public MessageType getType() {
		return MessageType.STARTGAME;
	}

	public void setListOfCharacters(LinkedList<CharacterObject> listOfCharacters) {
		this.listOfCharacters = listOfCharacters;
	}

	public LinkedList<CharacterObject> getListOfCharacters() {
		return listOfCharacters;
	}

}
