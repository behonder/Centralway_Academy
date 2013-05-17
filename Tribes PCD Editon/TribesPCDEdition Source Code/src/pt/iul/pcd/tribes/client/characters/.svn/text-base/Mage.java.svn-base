package pt.iul.pcd.tribes.client.characters;


public class Mage extends CharacterObject {
	
	private final int START_HEALTH = 100;
	
	/**
	 * {@inheritDoc} Archer
	 * @param ID
	 * @param playerNameDefault
	 * @param nameOfPlayer
	 * @param xLocation
	 * @param yLocation
	 */
	public Mage(int ID, String playerNameDefault, String nameOfPlayer, int xLocation, int yLocation) {
		super.setNameOfImageString("src/pt/iul/pcd/tribes/client/images/characters/" + playerNameDefault + "/"+ "mage_front.png");
		super.setPlayerNameDefault(playerNameDefault);
		super.setHealth(START_HEALTH);
		super.setAttackPower(20);
		super.setID(ID);
		super.setDefense(0);
		super.setRange(5);
		super.setMovimentSpeed(3);
		super.setAttackSpeed(3);
		super.setxLocation(xLocation);
		super.setyLocation(yLocation);
		super.setName("mage");
		super.setPlayerName(nameOfPlayer);
		super.setDefaultHealth(START_HEALTH);
		super.setAvatarStringImage("src/pt/iul/pcd/tribes/client/images/characters/" + playerNameDefault + "/"	+ "mage_avatar.png");
		super.buildSoundLibraries("mage");
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
