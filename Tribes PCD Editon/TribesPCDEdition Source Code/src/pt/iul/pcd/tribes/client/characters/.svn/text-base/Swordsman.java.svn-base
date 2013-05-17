package pt.iul.pcd.tribes.client.characters;

public class Swordsman extends CharacterObject {

	private final int START_HEALTH = 200;
	/**
	 *
	 * {@inheritDoc} Archer
	 *
	 * @param ID
	 * @param playerNameDefault
	 * @param playerName
	 * @param xLocation
	 * @param yLocation
	 */
	public Swordsman(int ID, String playerNameDefault, String playerName, int xLocation, int yLocation) {
		super.setNameOfImageString("src/pt/iul/pcd/tribes/client/images/characters/" + playerNameDefault + "/" + "swordsman" + "_front.png");
		super.setPlayerNameDefault(playerNameDefault);
		super.setHealth(START_HEALTH);
		super.setAttackPower(10);
		super.setDefense(0);
		super.setID(ID);
		super.setRange(3);
		super.setMovimentSpeed(3);
		super.setAttackSpeed(4);
		super.setxLocation(xLocation);
		super.setyLocation(yLocation);
		super.setName("swordsman");
		super.setPlayerName(playerName);
		super.setDefaultHealth(START_HEALTH);
		super.setAvatarStringImage("src/pt/iul/pcd/tribes/client/images/characters/" + playerNameDefault + "/"	+ "swordsman_avatar.png");
		super.buildSoundLibraries("swordsman");
	}

	@Override
	public void mover() throws InterruptedException {
		getMovement().classicMovement();
	}

	@Override
	public void follow() throws InterruptedException {
		getMovement().followMode();
	}

}
