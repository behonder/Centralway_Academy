package pt.iul.pcd.tribes.client.characters;

import pt.iul.pcd.tribes.client.moveEngine.Movement;

public class Archer extends CharacterObject {
	
	private final int START_HEALTH = 80;
	
	/**
	 * Creates the character.
	 * @param ID
	 * @param playerNameDefault
	 * @param nameOfPlayer
	 * @param xLocation
	 * @param yLocation
	 */
	public Archer(int ID, String playerNameDefault, String nameOfPlayer, int xLocation, int yLocation) {
		super.setNameOfImageString("src/pt/iul/pcd/tribes/client/images/characters/"+ playerNameDefault + "/" + "archer_front.png");
		super.setPlayerNameDefault(playerNameDefault);
		super.setID(ID);
		super.setHealth(START_HEALTH);
		super.setAttackPower(15);
		super.setDefense(0);
		super.setRange(5);
		super.setMovimentSpeed(3);
		super.setAttackSpeed(3);
		super.setxLocation(xLocation);
		super.setyLocation(yLocation);
		super.setName("archer");
		super.setPlayerName(nameOfPlayer);
		super.setDefaultHealth(START_HEALTH);
		super.setAvatarStringImage("src/pt/iul/pcd/tribes/client/images/characters/"+ playerNameDefault + "/" + "archer_avatar.png");
		super.buildSoundLibraries("archer");
	}

	/**
	 * Method that calls {@link Movement} classicMovement
	 */
	@Override
	public void mover() throws InterruptedException {
		getMovement().classicMovement();
	}

	/**
	 * Method that calls {@link Movement} followMode
	 */
	@Override
	public void follow() throws InterruptedException {
		getMovement().followMode();
	}
}