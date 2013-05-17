package pt.iul.pcd.tribes.client.moveEngine;

import java.util.concurrent.Semaphore;

import pt.iul.pcd.tribes.client.characters.CharacterObject;
import pt.iul.pcd.tribes.client.world2d.MapWorld;
import pt.iul.pcd.tribes.server.CreatedGame;

/**
 * Class that extends {@link Thread} and implements {@link Runnable}
 * @author Miguel Oliveira && @author João Barreto
 *
 */
public class MovimentEngine extends Thread {
	private CharacterObject characterObject;
	private Graphic graphics;
	private Semaphore semaphore = new Semaphore(0);
	private int nextXAfterSelection;
	private int nextYAfterSelection;
	private int caseOnSwitchForPainting;
	private boolean follow;
	private Thread threadToFollow;
	private MapWorld mapWorld;
	private CreatedGame createdGame;

	public MovimentEngine(CharacterObject characterObject, CreatedGame createdGame) {
		this.characterObject = characterObject;
		characterObject.createCombatEngine(createdGame);
		this.createdGame = createdGame;
		this.setMapWorld(createdGame.getMapWorld());
	}

	/**
	 * {@link Runnable}, uses a {@link Semaphore}.
	 */
	@Override
	public void run() {
		try {
			while (true) {
				getSemaphore().acquire();
				if(!isFollow())
					getCharacterObject().mover();
				else 					
					getCharacterObject().follow();
			}
		} catch (Exception e) {}
	}

	public CharacterObject getCharacterObject() {
		return characterObject;
	}

	public void setGraphics(Graphic graphics) {
		this.graphics = graphics;
	}
	
	public Graphic getGraphics(){
		return graphics;
	}

	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public void setNextXAfterSelection(int nextXAfterSelection) {
		this.nextXAfterSelection = nextXAfterSelection;
	}

	public int getNextXAfterSelection() {
		return nextXAfterSelection;
	}

	public void setNextYAfterSelection(int nextYAfterSelection) {
		this.nextYAfterSelection = nextYAfterSelection;
	}

	public int getNextYAfterSelection() {
		return nextYAfterSelection;
	}


	public void setCaseOnSwitchForPainting(int caseOnSwitchForPainting) {
		this.caseOnSwitchForPainting = caseOnSwitchForPainting;
	}

	public int getCaseOnSwitchForPainting() {
		return caseOnSwitchForPainting;
	}

	public void setFollow(boolean moveOrFollow) {
		this.follow = moveOrFollow;
	}

	public boolean isFollow() {
		return follow;
	}

	public void setThreadToFollow(Thread threadToFollow) {
		this.threadToFollow = threadToFollow;
	}

	public Thread getThreadToFollow() {
		return threadToFollow;
	}

	public void setMapWorld(MapWorld mapWorld) {
		this.mapWorld = mapWorld;
	}

	public MapWorld getMapWorld() {
		return mapWorld;
	}

	public CreatedGame getCreatedGame() {
		return createdGame;
	}
}