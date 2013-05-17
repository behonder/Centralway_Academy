package pt.iul.pcd.tribes.server;

import java.io.ObjectOutputStream;
import java.util.LinkedList;

import pt.iul.pcd.tribes.client.characters.Archer;
import pt.iul.pcd.tribes.client.characters.CharacterObject;
import pt.iul.pcd.tribes.client.characters.King;
import pt.iul.pcd.tribes.client.characters.Mage;
import pt.iul.pcd.tribes.client.characters.Swordsman;
import pt.iul.pcd.tribes.client.moveEngine.MovimentEngine;
import pt.iul.pcd.tribes.client.world2d.MapWorld;
import pt.iul.pcd.tribes.client.world2d.World;
import pt.iul.pcd.tribes.server.messages.StartGame;
import pt.iul.pcd.tribes.server.messages.WorldMessage;
import pt.iul.pcd.tribes.server.tools.Broadcaster;

public class CreatedGame {
	
	// --- CREATED GAME VARIABLES ---
	private String gameCreator;
	private String gameName;
	private int numberOfPlayers;
	
	// ---------- WORLD VARIABLES ---
	private MapWorld mapWorld;
	private WorldMessage worldMessage =  new WorldMessage(); 
	private World world = new World();
	
	// --- CREATED GAME TOOLS ---
	private Broadcaster gamesBroadcaster;
	private ObjectOutputStream out;
	private int idDoll;
	private int numberOfPlayersThatHasJoined;
	private LinkedList<MovimentEngine> dolls = new LinkedList<MovimentEngine>();
	private MovimentEngine[][] matrixThreads = new MovimentEngine[world.getWORLD_WIDTH()][world.getWORLD_HEIGTH()];

	// --- CREATED GAME FUNTIONS ----
	public CreatedGame(String gameCreator, String gameName, int numberOfPlayers, ObjectOutputStream out) {
		this.gameCreator = gameCreator;
		this.gameName = gameName;
		this.numberOfPlayers = numberOfPlayers;
		this.out = out;
		this.gamesBroadcaster = new Broadcaster();
		
		worldMessage.setWorldMatrix(world.getNumericMatrix());
		setMapWorld(new MapWorld(world.getNumericMatrix()));
		joinBroadcast(out, gameCreator);
	}
	
	public synchronized Broadcaster getGamesBroadcaster() {
		return gamesBroadcaster;
	}
	
	public void joinBroadcast(ObjectOutputStream out, String gameCreator){
		setNumberOfPlayersThatHasJoined(getNumberOfPlayersThatHasJoined()+1);
		gamesBroadcaster.add(out, gameCreator);
		gamesBroadcaster.brodcastTargetMessage(worldMessage, out);
	}
	
	public synchronized void startGame() {
		
		switch (getNumberOfPlayers()) {
			case 2: add2PlayersDolls();
					startThreadsAndSendMessage();
					break;
			case 3: add3PlayersDolls();
					startThreadsAndSendMessage();
					break;
			case 4: add4PlayersDolls();
					startThreadsAndSendMessage();
					break;
		}
	}
	
	private void startThreadsAndSendMessage() {
		LinkedList<CharacterObject> temp = new LinkedList<CharacterObject>();
		for(MovimentEngine dollsList: dolls){
			getMatrixThreads()[dollsList.getCharacterObject().getMatrixXPosition()][dollsList.getCharacterObject().getMatrixYPosition()] = dollsList;
			temp.add(dollsList.getCharacterObject());
			dollsList.start();
		}
		gamesBroadcaster.broadcastMessage(new StartGame(temp));
	}

	private void add4PlayersDolls() {
		addPlayer1Dolls(gamesBroadcaster.getUserNames().get(0));
		addPlayer2Dolls(gamesBroadcaster.getUserNames().get(1));
		addPlayer3Dolls(gamesBroadcaster.getUserNames().get(2));
		addPlayer4Dolls(gamesBroadcaster.getUserNames().get(3));
	}

	private void addPlayer4Dolls(String string) {
		dolls.add(new MovimentEngine(new King(calculateNextId(), "player4",string, 0, 19), this));
		dolls.add(new MovimentEngine(new Swordsman(calculateNextId(), "player4",string, 0, 18), this));
		dolls.add(new MovimentEngine(new Archer(calculateNextId() , "player4",string, 1, 19), this));
		dolls.add(new MovimentEngine(new Mage(calculateNextId(), "player4",string, 1, 18), this));
	}

	private void addPlayer3Dolls(String string) {
		dolls.add(new MovimentEngine(new Mage(calculateNextId(), "player3",string, 19, 1), this));
		dolls.add(new MovimentEngine(new King(calculateNextId(), "player3",string, 18, 1), this));
		dolls.add(new MovimentEngine(new Archer(calculateNextId(), "player3", string, 19, 2), this));
		dolls.add(new MovimentEngine(new Swordsman(calculateNextId(), "player3",string, 18, 2), this));
	}

	private void addPlayer2Dolls(String string) {
		dolls.add(new MovimentEngine(new Mage(calculateNextId(), "player2",string, 17, 17), this));
		dolls.add(new MovimentEngine(new Swordsman(calculateNextId(), "player2",string, 17, 18), this));
		dolls.add(new MovimentEngine(new King(calculateNextId(), "player2", string, 18, 18), this));
		dolls.add(new MovimentEngine(new Archer(calculateNextId(),"player2", string, 18, 17), this));
	}


	private void add3PlayersDolls() {
		addPlayer1Dolls(gamesBroadcaster.getUserNames().get(0));
		addPlayer2Dolls(gamesBroadcaster.getUserNames().get(1));
		addPlayer3Dolls(gamesBroadcaster.getUserNames().get(2));
	}
	
	private void addPlayer1Dolls(String string) {
		dolls.add(new MovimentEngine(new King(calculateNextId(), "player1", string, 1, 1), this));
		dolls.add(new MovimentEngine(new Mage(calculateNextId(), "player1", string,  1, 2), this));
		dolls.add(new MovimentEngine(new Swordsman(calculateNextId() ,"player1",string, 2, 1), this));
		dolls.add(new MovimentEngine(new Archer(calculateNextId(), "player1", string, 2, 2), this));
	}

	private void add2PlayersDolls() {
		addPlayer1Dolls(gamesBroadcaster.getUserNames().get(0));
		addPlayer2Dolls(gamesBroadcaster.getUserNames().get(1));	
	}

	public synchronized void broadcastChanges() {
		LinkedList<CharacterObject> temp = getAllCharacters();
		gamesBroadcaster.broadcastMessage(new StartGame(temp));
	}
	
	private int calculateNextId(){
		setIdDoll(getIdDoll()+1);
		return getIdDoll();
	}
	
	public LinkedList<CharacterObject> getAllCharacters(){
		LinkedList<CharacterObject> allCharactersList = new LinkedList<CharacterObject>();
		for(int i=0; i < matrixThreads.length; i++){
			for(int j = 0; j < matrixThreads[i].length ; j++){
				if(matrixThreads[i][j] != null)
					allCharactersList.add(matrixThreads[i][j].getCharacterObject());
			}
		}
		return allCharactersList;
	}

	public void finishTheGame() {
		for(int i=0; i < matrixThreads.length; i++){
			for(int j = 0; j < matrixThreads[i].length ; j++){
				if(matrixThreads[i][j] != null){
					matrixThreads[i][j].interrupt();
					matrixThreads[i][j].getCharacterObject().getCombatEngine().getRangeAnalyser().interrupt();
				}
			}
		}	
	}
	
	// --- GETTERS AND SETTERS ---
	
	public String getGameCreator() {
		return gameCreator;
	}

	public String getGameName() {
		return gameName;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	public synchronized int getNumberOfPlayersThatHasJoined() {
		return numberOfPlayersThatHasJoined;
	}
	
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public void setGameCreator(String gameCreator) {
		this.gameCreator = gameCreator;
	}
	
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	
	public synchronized void setNumberOfPlayersThatHasJoined(int numberOfPlayersThatHasJoined) {
		this.numberOfPlayersThatHasJoined = numberOfPlayersThatHasJoined;
	}
	
	public synchronized MovimentEngine[][] getMatrixThreads() {
		return matrixThreads;
	}

	public void setMapWorld(MapWorld mapWorld) {
		this.mapWorld = mapWorld;
	}

	public MapWorld getMapWorld() {
		return mapWorld;
	}

	public void setIdDoll(int idDoll) {
		this.idDoll = idDoll;
	}

	public int getIdDoll() {
		return idDoll;
	}
	
	@Override
	public String toString(){
		return gameCreator + " " + " " + gameName + " " + numberOfPlayers;
	}
	
}