package pt.iul.pcd.tribes.client.moveEngine;

import java.awt.Dimension;

import javax.swing.ImageIcon;

import pt.iul.pcd.tribes.client.characters.CharacterObject;
import pt.iul.pcd.tribes.server.tools.Broadcaster;

/**
 * Class that is used for movement of {@link CharacterObject}
 * There isn's any constructor in this class.
 * After a movement it will use {@link Broadcaster} to broadcast the changes of the game.
 * @author Miguel Oliveira && João Barreto
 *
 */
public class Movement {
	private Dimension dimensionImageMovementChosen;
	private Dimension worldDimension;
	private boolean forced;
	/**
	 * Method that manage the movement of {@link CharacterObject}
	 * @throws InterruptedException
	 */
	public void classicMovement() throws InterruptedException {
	
		MovimentEngine currentThread = (MovimentEngine) Thread.currentThread();
		dimensionImageMovementChosen = new Dimension(currentThread.getNextXAfterSelection(), currentThread.getNextYAfterSelection());
		Dimension dimensionImage = estimateMatrixPosition();
		worldDimension = new Dimension((currentThread.getMapWorld().getWorldDimension().width), (currentThread.getMapWorld().getWorldDimension().height));

		if ((dimensionImage.getWidth() == dimensionImageMovementChosen.getWidth() && dimensionImage.getHeight() == dimensionImageMovementChosen.getHeight()
				|| dimensionImageMovementChosen.getHeight() >= worldDimension.width - 1
				|| dimensionImageMovementChosen.getWidth() >= worldDimension.height - 1
				|| dimensionImageMovementChosen.getWidth() == 0
				|| dimensionImageMovementChosen.getHeight() == 0))
			return;

		else if (dimensionImage.getWidth() < dimensionImageMovementChosen.getWidth())
			// MOVE RIGHT
			currentThread.setCaseOnSwitchForPainting(1);

		else if (dimensionImage.getWidth() > dimensionImageMovementChosen.getWidth())
			// MOVE LEFT
			currentThread.setCaseOnSwitchForPainting(2);

		else if (dimensionImage.getHeight() < dimensionImageMovementChosen.getHeight())
			// MOVE DOWN
			currentThread.setCaseOnSwitchForPainting(3);
		
		else if (dimensionImage.getHeight() > dimensionImageMovementChosen.getHeight())
			// MOVE UP
			currentThread.setCaseOnSwitchForPainting(4);

		switch (currentThread.getCaseOnSwitchForPainting()) {
		case 1:
			moveTypeRight(dimensionImageMovementChosen);
			break;

		case 2:
			moveTypeLeft(dimensionImageMovementChosen);
			break;
		case 3:
			moveTypeDown(dimensionImageMovementChosen);
			break;
		case 4:
			moveTypeUp(dimensionImageMovementChosen);
			break;
		default:
			System.err.println("ERRO SWITCH CASE");
			break;
		}
		defineLastPosition();
	}

	/**
	 * Change the cefault {@link ImageIcon} of {@link CharacterObject} to default.
	 */
	private void defineLastPosition() {
		MovimentEngine currentThread = (MovimentEngine) Thread.currentThread();
		changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),	currentThread.getCharacterObject().getName(), "front");
		currentThread.getCreatedGame().broadcastChanges();
	}

	private void moveTypeUp(Dimension dimensionImageMovementChosen)	throws InterruptedException {
		moveYUp(dimensionImageMovementChosen);
		checkVerifyArrival(estimateMatrixPosition(), dimensionImageMovementChosen);
	}

	/**
	 * Moves {@link CharacterObject} UP.
	 * @param dimensionImageMovementChosen
	 * @throws InterruptedException
	 */
	private void moveYUp(Dimension dimensionImageMovementChosen) throws InterruptedException {
		MovimentEngine currentThread = (MovimentEngine) Thread.currentThread();
		changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),	currentThread.getCharacterObject().getName(), "up");
		
		while (estimateMatrixPosition().height != dimensionImageMovementChosen.height) {
			if (checkIfICanGoToThisSquare(estimateMatrixPosition().width, estimateMatrixPosition().height - 1) == true) {
				if (checkIfICanGoToThisSquare(estimateMatrixPosition().width + 1, estimateMatrixPosition().height) == false) {
					forcedMoveRight();
					changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),	currentThread.getCharacterObject().getName(), "up");
				} else if (checkIfICanGoToThisSquare(estimateMatrixPosition().width - 1, estimateMatrixPosition().height) == false) {
					forcedMoveLeft();
					changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),	currentThread.getCharacterObject().getName(), "up");
				}
				else {
					setForced(true);
					break;	
				}
			}
			else {
				changeLocationOnMatrixAndInTheObject(2);
				currentThread.getCreatedGame().broadcastChanges();
				sleepThread();
			}
		}
	}

	/**
	 * Moves {@link CharacterObject} DOWN.
	 * @param dimensionImageMovementChosen
	 * @throws InterruptedException
	 */
	private void moveYDown(Dimension dimensionImageMovementChosen) throws InterruptedException {
		MovimentEngine currentThread = (MovimentEngine) Thread.currentThread();
		changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),currentThread.getCharacterObject().getName(), "front");
		while (estimateMatrixPosition().height != dimensionImageMovementChosen.height) {
			if (checkIfICanGoToThisSquare(estimateMatrixPosition().width,
					estimateMatrixPosition().height + 1) == true) {
				if (checkIfICanGoToThisSquare(estimateMatrixPosition().width + 1, estimateMatrixPosition().height) == false) {
					forcedMoveRight();
					changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),	currentThread.getCharacterObject().getName(), "front");
				} else if (checkIfICanGoToThisSquare(
						estimateMatrixPosition().width - 1,	estimateMatrixPosition().height) == false) {
					forcedMoveLeft();
					changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),	currentThread.getCharacterObject().getName(), "front");
				}
				else {
					setForced(true);
					break;	
				}
				changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),	currentThread.getCharacterObject().getName(), "front");
			}
			else {
				changeLocationOnMatrixAndInTheObject(3);
				currentThread.getCreatedGame().broadcastChanges();
				sleepThread();
			}
		}
	}

	/**
	 * Forces the character to go LEFT
	 * @param dimensionImageMovementChosen
	 * @throws InterruptedException
	 */
	private void forcedMoveLeft() throws InterruptedException {
		MovimentEngine currentThread = (MovimentEngine) Thread.currentThread();
		sleepThread();
		changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),	currentThread.getCharacterObject().getName(), "left");
		changeLocationOnMatrixAndInTheObject(1);
		currentThread.getCreatedGame().broadcastChanges();		
		sleepThread();
	}

	private void moveTypeDown(Dimension dimensionImageMovementChosen) throws InterruptedException {
		moveYDown(dimensionImageMovementChosen);
		checkVerifyArrival(estimateMatrixPosition(), dimensionImageMovementChosen);
	}

	/**
	 * Checks if {@link CharacterObject} has reached destination.
	 * @param estimateMatrixPosition
	 * @param dimensionImageMovementChosen2
	 * @throws InterruptedException
	 */
	private void checkVerifyArrival(Dimension estimateMatrixPosition,
			Dimension dimensionImageMovementChosen2) throws InterruptedException {
		if(!isForced())
			verifyArrival(estimateMatrixPosition(), dimensionImageMovementChosen);
		else 
			setForced(false);
	}

	private void moveTypeLeft(Dimension dimensionImageMovementChosen) throws InterruptedException {
		moveXLeft(dimensionImageMovementChosen);
		checkVerifyArrival(estimateMatrixPosition(), dimensionImageMovementChosen);
	}

	/**
	 * Moves {@link CharacterObject} LEFT.
	 * @param dimensionImageMovementChosen
	 * @throws InterruptedException
	 */
	private void moveXLeft(Dimension dimensionImageMovementChosen) throws InterruptedException {
		MovimentEngine currentThread = (MovimentEngine) Thread.currentThread();
		changeImage(currentThread.getCharacterObject().getPlayerNameDefault(), currentThread.getCharacterObject().getName(), "left");
		while (estimateMatrixPosition().width != dimensionImageMovementChosen.width) {
			if (checkIfICanGoToThisSquare((currentThread.getCharacterObject().getMatrixXPosition() - 1), currentThread.getCharacterObject().getMatrixYPosition()) == false) {
				changeLocationOnMatrixAndInTheObject(1);
			} else if (checkIfICanGoToThisSquare(estimateMatrixPosition().width,estimateMatrixPosition().height - 1) == false && estimateMatrixPosition().height < dimensionImageMovementChosen.height) {
				forcedMoveUP();
				changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),	currentThread.getCharacterObject().getName(), "left");
			} else if (checkIfICanGoToThisSquare(estimateMatrixPosition().width,estimateMatrixPosition().height + 1) == false) {
				forcedMoveDown();
				changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),	currentThread.getCharacterObject().getName(), "left");
			}
			else {
				setForced(true);
				break;
			}
			currentThread.getCreatedGame().broadcastChanges();
			sleepThread();
		}
	}

	/**
	 * Forces the character to go DOWN
	 * @param dimensionImageMovementChosen
	 * @throws InterruptedException
	 */
	private void forcedMoveDown() throws InterruptedException {
		MovimentEngine currentThread = (MovimentEngine) Thread.currentThread();
		sleepThread();
		changeImage(currentThread.getCharacterObject().getPlayerNameDefault(), currentThread.getCharacterObject().getName(), "front");
		currentThread.getCreatedGame().broadcastChanges();
		changeLocationOnMatrixAndInTheObject(3);
		currentThread.getCreatedGame().broadcastChanges();
		sleepThread();
	}

	/**
	 * Forces the character to go UP
	 * @param dimensionImageMovementChosen
	 * @throws InterruptedException
	 */
	private void forcedMoveUP() throws InterruptedException {
		MovimentEngine currentThread = (MovimentEngine) Thread.currentThread();
		sleepThread();
		changeImage(currentThread.getCharacterObject().getPlayerNameDefault(), currentThread.getCharacterObject().getName(), "up");
		changeLocationOnMatrixAndInTheObject(2);
		currentThread.getCreatedGame().broadcastChanges();
		sleepThread();
	}

	/**
	 * Forces the character to go RIGHT
	 * @param dimensionImageMovementChosen
	 * @throws InterruptedException
	 */
	private void forcedMoveRight() throws InterruptedException {
		MovimentEngine currentThread = (MovimentEngine) Thread.currentThread();
		sleepThread();
		changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),	currentThread.getCharacterObject().getName(), "right");
		currentThread.getCreatedGame().broadcastChanges();
		changeLocationOnMatrixAndInTheObject(0);
		currentThread.getCreatedGame().broadcastChanges();
		sleepThread();
	}

	/**
	 * Changes the {@link ImageIcon} of the {@link CharacterObject}.
	 * Left, Right, UP, FRONT are the available {@link ImageIcon}
	 * @param playerNameDefault
	 * @param nameObject
	 * @param position
	 */
	private void changeImage(String playerNameDefault, String nameObject, String position) {
		((MovimentEngine) Thread.currentThread()).getCharacterObject().setNameOfImageString("src/pt/iul/pcd/tribes/client/images/characters/" + playerNameDefault+ "/" + nameObject + "_" + position + ".png");
	}

	/**
	 * Method that checks if the {@link CharacterObject} can go to that square.
	 * @param coordinateX
	 * @param coordinateY
	 * @return
	 */
	private boolean checkIfICanGoToThisSquare(int coordinateX, int coordinateY) {
		MovimentEngine currentThread = (MovimentEngine) Thread.currentThread();
		Boolean isTileBlocked = currentThread.getMapWorld().isTileBlocked(coordinateX, coordinateY);
		MovimentEngine isPositionOcupied = currentThread.getCreatedGame().getMatrixThreads()[coordinateX][coordinateY];
		if(isTileBlocked == false && isPositionOcupied == null)
			return false;
		else
			return true;
	}

	private void moveTypeRight(Dimension dimensionImageMovementChosen)
			throws InterruptedException {
		moveXRight(dimensionImageMovementChosen);
		checkVerifyArrival(estimateMatrixPosition(), dimensionImageMovementChosen);
	}

	private boolean isForced() {
		return forced;
	}
	
	private void setForced(boolean a){
		forced = a;
	}

	/**
	 * Verifies if the {@link CharacterObject} has reached destionation.
	 * @param estimateMatrixPosition
	 * @param dimensionImageMovementChosen
	 * @throws InterruptedException
	 */
	private void verifyArrival(Dimension estimateMatrixPosition,
			Dimension dimensionImageMovementChosen) throws InterruptedException {
		if (estimateMatrixPosition.getWidth() != dimensionImageMovementChosen.getWidth()|| estimateMatrixPosition.getHeight() != dimensionImageMovementChosen.getHeight()) {
			classicMovement();
		}
	}

	/**
	 * Moves {@link CharacterObject} RIGHT.
	 * @param dimensionImageMovementChosen
	 * @throws InterruptedException
	 */
	private void moveXRight(Dimension dimensionImageMovementChosen)
		throws InterruptedException {
		MovimentEngine currentThread = (MovimentEngine) Thread.currentThread();
		changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),	currentThread.getCharacterObject().getName(), "right");
		while (estimateMatrixPosition().width != dimensionImageMovementChosen.width) {
			if (checkIfICanGoToThisSquare(estimateMatrixPosition().width + 1, estimateMatrixPosition().height) == true) {
				if (checkIfICanGoToThisSquare(estimateMatrixPosition().width, estimateMatrixPosition().height - 1) == false) {
					forcedMoveUP();
				changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),	currentThread.getCharacterObject().getName(), "right");
				}
				else if (checkIfICanGoToThisSquare(estimateMatrixPosition().width,estimateMatrixPosition().height + 1) == false) {
					forcedMoveDown();
					changeImage(currentThread.getCharacterObject().getPlayerNameDefault(),	currentThread.getCharacterObject().getName(), "right");
				}
				else {
					setForced(true);
					break;
				}
			} else {
				changeLocationOnMatrixAndInTheObject(0);
				currentThread.getCreatedGame().broadcastChanges();
				sleepThread();
			}
		}
	}

	/**
	 * Sleep Method.
	 * @throws InterruptedException
	 */
	private void sleepThread() throws InterruptedException {
		Thread.sleep(200 * ((MovimentEngine) Thread.currentThread()).getCharacterObject().getMovimentSpeed());	
	}

	/**
	 * Method that moves the {@link CharacterObject} to the new position and puts the older one to null.
	 * @param i
	 */
	private void changeLocationOnMatrixAndInTheObject(int i) {
		MovimentEngine currentThread = (MovimentEngine) Thread.currentThread();
		switch (i) {
		case 0: // MOVER DIREITA 
			currentThread.getCreatedGame().getMatrixThreads()[currentThread.getCharacterObject().getMatrixXPosition()][currentThread.getCharacterObject().getMatrixYPosition()] = null;
			currentThread.getCharacterObject().setxLocation(((MovimentEngine) Thread.currentThread()).getCharacterObject().getMatrixXPosition() + 1);
			currentThread.getCreatedGame().getMatrixThreads()[currentThread.getCharacterObject().getMatrixXPosition()][currentThread.getCharacterObject().getMatrixYPosition()] = currentThread;
			break;
		case 1: // MOVER ESQUERDA 
			currentThread.getCreatedGame().getMatrixThreads()[currentThread.getCharacterObject().getMatrixXPosition()][currentThread.getCharacterObject().getMatrixYPosition()] = null;
			currentThread.getCharacterObject().setxLocation(((MovimentEngine) Thread.currentThread()).getCharacterObject().getMatrixXPosition() - 1);
			currentThread.getCreatedGame().getMatrixThreads()[currentThread.getCharacterObject().getMatrixXPosition()][currentThread.getCharacterObject().getMatrixYPosition()] = currentThread;
			break;
		case 2: // MOVE UP
			currentThread.getCreatedGame().getMatrixThreads()[currentThread.getCharacterObject().getMatrixXPosition()][currentThread.getCharacterObject().getMatrixYPosition()] = null;
			currentThread.getCharacterObject().setyLocation(((MovimentEngine) Thread.currentThread()).getCharacterObject().getMatrixYPosition() - 1);
			currentThread.getCreatedGame().getMatrixThreads()[currentThread.getCharacterObject().getMatrixXPosition()][currentThread.getCharacterObject().getMatrixYPosition()] = currentThread;
			break;
		case 3: // MOVE DOWN
			currentThread.getCreatedGame().getMatrixThreads()[currentThread.getCharacterObject().getMatrixXPosition()][currentThread.getCharacterObject().getMatrixYPosition()] = null;
			currentThread.getCharacterObject().setyLocation(((MovimentEngine) Thread.currentThread()).getCharacterObject().getMatrixYPosition() + 1);
			currentThread.getCreatedGame().getMatrixThreads()[currentThread.getCharacterObject().getMatrixXPosition()][currentThread.getCharacterObject().getMatrixYPosition()] = currentThread;
		default:
			break;
		}
		currentThread.getCharacterObject().getCombatEngine().notifyRangeAnalyser();
	}

	/**
	 * Calculates {@link CharacterObject} Destination
	 * @return
	 */
	private Dimension estimateMatrixPosition() {
		return new Dimension(((MovimentEngine) Thread.currentThread()).getCharacterObject().getMatrixXPosition(),((MovimentEngine) Thread.currentThread()).getCharacterObject().getMatrixYPosition());
	}

	/**
	 * Method that set the {@link CharacterObject} to follow.
	 * THIS METHOD WAS NOT IMPLEMENTED!
	 */
	public void followMode() {
		/*
		 * Não está implementado.
		 */
	}
}