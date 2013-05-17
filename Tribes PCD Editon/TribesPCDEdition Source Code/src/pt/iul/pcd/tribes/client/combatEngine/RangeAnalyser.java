package pt.iul.pcd.tribes.client.combatEngine;

import pt.iul.pcd.tribes.client.characters.CharacterObject;
/**
 * 
 * @author Miguel Oliveira && João Barreto
 * Class that created a {@link Thread} and that thread will use {@link CombatEngine} to attack and check for enemies. 
 */
public class RangeAnalyser extends Thread {

	private static final int SLEEP_TIMER = 1000;
	private CombatEngine combatEngine;
	private final CharacterObject character;

	/**
	 * Constructor
	 * @param combatEngine
	 * @param character
	 */
	public RangeAnalyser(CombatEngine combatEngine, CharacterObject character) {
		this.combatEngine = combatEngine;
		this.character = character;
	}

	/**
	 * Type of method: link {@link Runnable}
	 * Description: While the {@link CharacterObject} is alive, it will {@link RangeAnalyser} -  analyse range around him.
	 * It will enter in a sleep after checking and will throw a {@link InterruptedException} if interrupted.  
	 */
	public void run() {
		try {
			while (true){
				combatEngine.analyseRange();
				sleep(SLEEP_TIMER * character.getAttackSpeed());
			}
		} catch (InterruptedException e) {}
	}
}
