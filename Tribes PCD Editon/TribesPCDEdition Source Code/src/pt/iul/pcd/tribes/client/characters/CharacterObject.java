package pt.iul.pcd.tribes.client.characters;
import java.io.Serializable;
import java.util.Random;
import javax.swing.ImageIcon;

import org.omg.CORBA.TRANSIENT;

import pt.iul.pcd.tribes.client.combatEngine.CombatEngine;
import pt.iul.pcd.tribes.client.moveEngine.HumanActions;
import pt.iul.pcd.tribes.client.moveEngine.Movement;
import pt.iul.pcd.tribes.client.soundsystem.CharacterSounds;
import pt.iul.pcd.tribes.server.CreatedGame;
/**
 * 
 * @author Miguel Oliveira && João Barreto
 * This class is abstract because there is different types of Units.
 * {@link TRANSIENT} - ImageIcon, Movement, CombatEngine because of {@link Serializable} 
 */
public abstract class CharacterObject implements HumanActions, Serializable {

	private static final long serialVersionUID = 1L;
	private static final int IconWidth = 32;
	private static final int IconHeight = 32;
	private int matrixXPosition;
	private int matrixYPosition;
	private int START_HEALTH = 0;
	private int health;
	private int movimentSpeed;
	private int attackPower;
	private int defense;
	private int range;
	private int attackSpeed;
	private int xLocation;
	private int yLocation;
	
	private transient ImageIcon imageIcon;
	private transient Movement movement = new Movement();
	private transient CombatEngine combatEngine;
	private transient ImageIcon imageAvatar;
	
	private String playerNameDefault;
	private String playerName;
	private String name;
	private String nameOfImage;
	private String avatarStringImage;
	
	private int hashCode;
	
	// ---- SOUND VARIABLES ----
	private CharacterSounds characterSounds = new CharacterSounds();
	private String[] acknowledgeSounds = new String[3];
	private String[] confirmSounds = new String[3];	
	// -------------------------

	public String getPlayerNameDefault() {
		return playerNameDefault;
	}

	public CombatEngine getCombatEngine(){
		return combatEngine;
	}

	@Override
	public String toString() {
		return "CharacterObject [health=" + health + ", movimentSpeed="
				+ getMovimentSpeed() + ", attackPower=" + attackPower
				+ ", defense=" + defense + ", imageIcon=" + imageIcon
				+ ", range=" + range + "]";
	}

	public synchronized void setHealth(int health) {
		this.health = health;
	}

	public synchronized int getCurrentHealth() {
		return health;
	}

	public int getDefense() {
		return defense;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public int getMovimentSpeed() {
		return movimentSpeed;
	}

	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getRange() {
		return range;
	}

	public void setMovimentSpeed(int movimentSpeed) {
		this.movimentSpeed = movimentSpeed;
	}

	public void setyLocation(int yLocation) {
		matrixYPosition = yLocation;
		this.yLocation = yLocation*IconHeight;
	}

	public int getyLocation() {
		return yLocation;
	}

	public void setxLocation(int xLocation) {
		matrixXPosition = xLocation;
		this.xLocation = xLocation*IconWidth;

	}

	public int getxLocation() {
		return xLocation;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;

	}

	public String getPlayerName() {
		return playerName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMatrixXPosition(){
		return matrixXPosition;
	}

	public int getMatrixYPosition() {
		return matrixYPosition;
	}
	
	public int getCalculateRangeWidth(){
		return (getImageIcon().getIconWidth()*getRange())+3;
	}
	
	public int getCalculateRangeHeight(){
		return getImageIcon().getIconHeight()*getRange();
	}

	public ImageIcon getAvatarImage() {
		return imageAvatar;
		
	}
	
	public void setAvatarImage(ImageIcon image){
		this.imageAvatar = image;
	}
	
	public int getStartHealth(){
		return START_HEALTH;
	}

	public void setMovement(Movement movement) {
		this.movement = movement;
	}

	public Movement getMovement() {
		return movement;
	}

	public void setPlayerNameDefault(String playerNameDefault) {
		this.playerNameDefault = playerNameDefault;
	}

	public void createCombatEngine(CreatedGame createdGame) {
		combatEngine = new CombatEngine(createdGame, this);
		
	}
		
	public void setDefaultHealth(int startHealth) {
		this.START_HEALTH = startHealth;
	}
	
	public int getDefaultHealth(){
		return START_HEALTH;
	}

	public void setNameOfImageString(String nameOfImage) {
		this.nameOfImage = nameOfImage;
	}
	
	public String getNameOfImageString(){
		return nameOfImage;
	}
	
	public void setAvatarStringImage(String avatarStringImage){
		this.avatarStringImage = avatarStringImage;
	}
	
	public String getAvatarStringImage(){
		return avatarStringImage;
	}

	public void setID(int hashCode) {
		this.hashCode = hashCode;		
	}
	
	public int getID(){
		return hashCode;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
		
	}
	
	public int getAttackSpeed(){
		return attackSpeed;
	}
	
	//---------- SOUND METHODS TESTING --------------------
	/**
	 * Builds sound Libraries
	 */
	public void buildSoundLibraries(String characterType){
		acknowledgeSounds[0] = "src/pt/iul/pcd/tribes/client/sounds/characterSounds/" + characterType + "/acknowledgeSounds/acknowledge1.mp3";
		acknowledgeSounds[1] = "src/pt/iul/pcd/tribes/client/sounds/characterSounds/" + characterType + "/acknowledgeSounds/acknowledge2.mp3";
		acknowledgeSounds[2] = "src/pt/iul/pcd/tribes/client/sounds/characterSounds/" + characterType + "/acknowledgeSounds/acknowledge3.mp3";
		
		confirmSounds[0] = "src/pt/iul/pcd/tribes/client/sounds/characterSounds/" + characterType + "/confirmSounds/confirm1.mp3";
		confirmSounds[1] = "src/pt/iul/pcd/tribes/client/sounds/characterSounds/" + characterType + "/confirmSounds/confirm2.mp3";
		confirmSounds[2] = "src/pt/iul/pcd/tribes/client/sounds/characterSounds/" + characterType + "/confirmSounds/confirm3.mp3";
	}
	
	/**
	 *  Plays the acknowledge sound from {@link buildSoundLibraries}
	 */
	public void playAcknowledgeSound() {
		int soundNumber = new Random().nextInt(acknowledgeSounds.length-1);
		characterSounds.playSound(acknowledgeSounds[soundNumber]);
	}

	/**
	 *  Plays the confirmation sound from {@link buildSoundLibraries}
	 */
	public void playConfirmSound() {
		int soundNumber = new Random().nextInt(confirmSounds.length-1);
		characterSounds.playSound(confirmSounds[soundNumber]);
	}

}
