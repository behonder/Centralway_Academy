package pt.iul.pcd.tribes.server.messages;
import java.util.LinkedList;

public class UsersMessage extends Message {

	private LinkedList<String> users;
	
	public LinkedList<String> getNewUser() {
		return users;
	}

	public UsersMessage(LinkedList<String> newUser) {
		super();
		this.users = newUser;
	}

	@Override
	public MessageType getType() {
		return MessageType.USERS;
	}

}
