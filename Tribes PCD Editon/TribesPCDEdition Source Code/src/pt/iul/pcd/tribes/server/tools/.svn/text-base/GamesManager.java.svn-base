package pt.iul.pcd.tribes.server.tools;

import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import pt.iul.pcd.tribes.server.CreatedGame;
import pt.iul.pcd.tribes.server.messages.JoinGameMessage;

public class GamesManager{
	
	// --- GAMES MANAGER VARIABLES ---
	private LinkedList<CreatedGame> createdGameList = new LinkedList<CreatedGame>();
	private DefaultListModel createdGamesListModel = new DefaultListModel();

	// --- GAMES MANAGER FUNCTIONS ---
	public synchronized void addGame(CreatedGame game){
		createdGameList.add(game);
	}
	
	public synchronized DefaultListModel getGamesList(){
		return createdGamesListModel;
	}
	
	public synchronized void removeGame(CreatedGame game){
		Iterator<CreatedGame> iterator = createdGameList.iterator();
		
		while(iterator.hasNext()){
			CreatedGame temp = iterator.next();
			if(temp.getGameName().equals(game.getGameName()))
				iterator.remove();
		}
	}
	
	public synchronized JList getExistingGames(){
		createdGamesListModel.clear();
		for(CreatedGame c : createdGameList){
			createdGamesListModel.addElement(c.getGameName());
		}
		return new JList(createdGamesListModel);
	}

	public CreatedGame findTheGameAndJoin(JoinGameMessage temporaryGame, ObjectOutputStream out) {
		CreatedGame selectedGame = null;
		for(CreatedGame game: createdGameList){
			if(game.getGameName().equals(temporaryGame.getGameName())){
				game.joinBroadcast(out, temporaryGame.getPlayerName());
				if(game.getNumberOfPlayers() == game.getNumberOfPlayersThatHasJoined()) {
					removeGame(game);
					game.startGame();
				}	
				selectedGame  = game;
				break;
			}
		}
		return selectedGame;
	}
}
