package pt.iul.pcd.tribes.client.characters;

/**
 * Superclass - {@link CharacterObject}
 * @author Miguel Oliveira && João Barreto
 */
public class King extends CharacterObject {
	private final int START_HEALTH = 250;
	/**
	 * {@inheritDoc} Archer
	 * @param ID
	 * @param playerNameDefault
	 * @param nameOfPlayer
	 * @param xLocation
	 * @param yLocation
	 */
	public King(int ID, String playerNameDefault, String nameOfPlayer, int xLocation, int yLocation) {
		super.setNameOfImageString("src/pt/iul/pcd/tribes/client/images/characters/" + playerNameDefault + "/" + "king_front.png");
		super.setPlayerNameDefault(playerNameDefault);
		super.setHealth(START_HEALTH);
		super.setID(ID);
		super.setAttackPower(30);
		super.setDefense(0);
		super.setRange(3);
		super.setAttackSpeed(5);
		super.setMovimentSpeed(6);
		super.setxLocation(xLocation);
		super.setyLocation(yLocation);
		super.setName("king");
		super.setPlayerName(nameOfPlayer);
		super.setDefaultHealth(START_HEALTH);
		super.setAvatarStringImage("src/pt/iul/pcd/tribes/client/images/characters/" + playerNameDefault + "/" + "king_avatar.png");
		super.buildSoundLibraries("king");
	}

	/**
	 * {@inheritDoc} Archer
	 */
	@Override
	public void mover() throws InterruptedException {
		getMovement().classicMovement();
	}

	/**
	 * {@inheritDoc} Archer
	 */
	@Override
	public void follow() throws InterruptedException {
		getMovement().followMode();
	}
}
