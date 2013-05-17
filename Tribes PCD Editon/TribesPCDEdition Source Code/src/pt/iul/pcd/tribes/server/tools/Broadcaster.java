package pt.iul.pcd.tribes.server.tools;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import pt.iul.pcd.tribes.server.messages.LoseMessage;
import pt.iul.pcd.tribes.server.messages.Message;
import pt.iul.pcd.tribes.server.messages.WinningMessage;

public class Broadcaster{

	// --- BROADCASTER VARIABLES ---
	private LinkedList<String> userNames = new LinkedList<String>();
	private LinkedList<ObjectOutputStream> outs = new LinkedList<ObjectOutputStream>();

	
	// --- BROACASTER FUNCTIONS ---
	public synchronized void add(ObjectOutputStream out, String name) {
		getOuts().add(out);
		getUserNames().add(name);
	}

	public synchronized void broadcastMessage(Message message) {
		for (ObjectOutputStream out : getOuts()) {
			try {
				out.reset();
				out.writeObject(message);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void brodcastTargetMessage(Message message, ObjectOutputStream out){
		try {
			out.reset();
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setUserNames(LinkedList<String> userNames) {
		this.userNames = userNames;
	}

	public LinkedList<String> getUserNames() {
		return userNames;
	}

	public void actualizeActivePlayers(String playerName) {
		Iterator<String> iterator = userNames.iterator();
		int iteratorNumber = 0;
		while(iterator.hasNext()){
			String nameOfThePlayer = iterator.next();
			if(nameOfThePlayer.equals(playerName)){
				removeRespectiveOut(iteratorNumber);
				iterator.remove();
			}
			iteratorNumber++;
		}	
	}

	private void removeRespectiveOut(int iteratorNumber) {
		int numberIterated = 0;
		Iterator<ObjectOutputStream> iterator = getOuts().iterator();
		while(iterator.hasNext()){
			iterator.next();
			if(numberIterated == iteratorNumber){
				iterator.remove();
				break;
			}
			numberIterated++;
		}
	}
	
	public synchronized void sendLoseMessage(String playerName){
		LoseMessage loseMessage = new LoseMessage(playerName);
		int position = 0;
		int numberCount = 0;
		for(String names: getUserNames()){
			if(names.equals(playerName)) {
				position = numberCount;
				break;
			}
			numberCount++;
				
		}
		brodcastTargetMessage(loseMessage, getOuts().get(position));
	}


	public LinkedList<ObjectOutputStream> getOuts() {
		return outs;
	}

	public boolean getAmIPlayingAlone() {
		if(userNames.size() == 1)
			return true;
		return false;		
	}

	public void sendWinningMessage(String playerName) {
		WinningMessage message = new WinningMessage(playerName);
		brodcastTargetMessage(message, getOuts().element());
	}
}