package pt.iul.pcd.tribes.client.combatEngine;

import java.util.LinkedList;

import pt.iul.pcd.tribes.client.characters.CharacterObject;
import pt.iul.pcd.tribes.client.moveEngine.MovimentEngine;
import pt.iul.pcd.tribes.server.CreatedGame;

public class CombatEngine {

	// --- Combat Engine Tools ---
	private RangeAnalyser rangeAnalyser;
	private LinkedList<CharacterObject> charactersDetectedList = new LinkedList<CharacterObject>();
	private MovimentEngine[][] matrixThreads;
	private CharacterObject character;

	// --- Analyser Variables ---
	private int characterRange;
	private int topXLeftCorner;
	private int topYLeftCorner;
	private int xStopValue;
	private int yStopValue;
	private final CreatedGame game;
	private final String kingName = "king";	
	
	/**
	 * Constructor CombatEngine
	 * @param game
	 * @param character
	 */
	public CombatEngine(CreatedGame game, CharacterObject character) {
		this.game = game;
		matrixThreads = game.getMatrixThreads();
		this.character = character;
		this.characterRange = character.getRange();
		this.rangeAnalyser = new RangeAnalyser(this, character);	
		getRangeAnalyser().start();
	}
	
	public RangeAnalyser getRangeAnalyser() {
		return rangeAnalyser;
	}

	/**
	 * Method that analyses if an enemy is on range, if true enters attackDetectedEnemies else waits.
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void analyseRange() throws InterruptedException{
		while(isSomeOneInRange(characterRange) == false){
			wait();
		}
		attackDetectedEnemies();
	}
	/**
	 * Iterates list of targets and do damage to them, after dealing damage broadcasts all units. 
	 * If a target has less than zero life, calls killUnitAndCheckIfIsKing().
	 * @throws InterruptedException
	 */
	private synchronized void attackDetectedEnemies() throws InterruptedException {
		for(CharacterObject characterAttacked: charactersDetectedList){
			characterAttacked.getCombatEngine().notifyRangeAnalyser();
			int damage = (characterAttacked.getDefense() - character.getAttackPower()/charactersDetectedList.size());
			if(damage <= 0 ){
				characterAttacked.setHealth(characterAttacked.getCurrentHealth() + damage);
				if(characterAttacked.getCurrentHealth() <= 0){
					killUnitAndCheckIfIsKing(characterAttacked);					
				}
				game.broadcastChanges();
			}
		}			
	}
	/**
	 * Method that verify if the unit that will die is the king or not, if so it will send a lose message to the owner of the king.
	 * Also will check if there is any oppositor left, if negative it will end the game and send a victory message to the killer.
	 * @param characterAttacked
	 */
	
	private void killUnitAndCheckIfIsKing(CharacterObject characterAttacked) {
		String characterAttackedName = characterAttacked.getName();
		if(characterAttackedName.equals(kingName)){
			LinkedList<CharacterObject> charactersList = game.getAllCharacters();
			String playerName = characterAttacked.getPlayerName();
			for(CharacterObject chars: charactersList){
				if(chars.getPlayerName().equals(playerName)){
					killUnit(chars);
				}
			}
			game.getGamesBroadcaster().sendLoseMessage(playerName);
			game.getGamesBroadcaster().actualizeActivePlayers(playerName);
			if(game.getGamesBroadcaster().getAmIPlayingAlone() == true){
				System.out.println(game.getGamesBroadcaster().getUserNames().toString());
				game.getGamesBroadcaster().sendWinningMessage(game.getGamesBroadcaster().getUserNames().element());
				game.finishTheGame();
			}
		}
		else {
			killUnit(characterAttacked);
		}	
	}
	/**
	 * Method that eliminates the target by interrupting it and removing it from the matrix.
	 * After that action, it will broadcast the changes.
	 * @param chars
	 */
	private synchronized void killUnit(CharacterObject chars) {
		int xLocation = chars.getMatrixXPosition();
		int yLocation = chars.getMatrixYPosition();
		matrixThreads[xLocation][yLocation].interrupt();
		matrixThreads[xLocation][yLocation].getCharacterObject().getCombatEngine().getRangeAnalyser().interrupt();
		matrixThreads[xLocation][yLocation] = null;
	}

	/**
	 * Checks if a target is on range, if so it will add to the linkedList charactersDetectedList
	 * @param range
	 * @return
	 */
	public synchronized boolean isSomeOneInRange(int range){
		charactersDetectedList.clear();
		int xPos = character.getMatrixXPosition();
		int yPos = character.getMatrixYPosition();	
		// --- Range Box Limits ---
		topXLeftCorner = xPos-(range-1)/2;
		topYLeftCorner = yPos-(range-1)/2;
		xStopValue = xPos + (range-1)/2;
		yStopValue = yPos + (range-1)/2;
		// ------------------------
		verifyAnalyserBounds();	
		for (int i = topXLeftCorner; i <= xStopValue; i++) {
			for (int j = topYLeftCorner; j <= yStopValue; j++) {
				if(i > 0 && i < 19 && j > 0 && j < 19){
					if(matrixThreads[i][j] != null){
						CharacterObject characterDetected = matrixThreads[i][j].getCharacterObject();
						if (!characterDetected.getPlayerName().equals(character.getPlayerName())){
							charactersDetectedList.add(characterDetected);
						}
					}
				}
			}
		}
		if(charactersDetectedList.isEmpty())
			return false;
		else
			return true;
	}
	
	/**
	 * Notify's RangeAnalyser thread that might be on wait.
	 */
	public synchronized void notifyRangeAnalyser(){
		notify();
	}
	
	/**
	 * Method that delimiters the area to search.
	 */
	private synchronized void verifyAnalyserBounds(){
		int length = matrixThreads.length;
		if(xStopValue < 0)
			xStopValue = 0;
		if(xStopValue > length)
			xStopValue = length-1;
		if(yStopValue < 0)
			xStopValue = 0;
		if(yStopValue > length)
			xStopValue = length-1;
	}
}