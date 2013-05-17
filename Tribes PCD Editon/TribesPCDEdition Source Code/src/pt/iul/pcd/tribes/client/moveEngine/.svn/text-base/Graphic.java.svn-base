package pt.iul.pcd.tribes.client.moveEngine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.sound.sampled.Line;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import pt.iul.pcd.tribes.client.UI.ChosenCharacterStats;
import pt.iul.pcd.tribes.client.UI.WelcomeScreen;
import pt.iul.pcd.tribes.client.characters.CharacterObject;
import pt.iul.pcd.tribes.client.network.MessageBox;
import pt.iul.pcd.tribes.client.network.MessageReceiver;
import pt.iul.pcd.tribes.client.network.MessageSender;
import pt.iul.pcd.tribes.server.messages.SelectMessage;

/**
 * 
 * @author Miguel Oliveira && João Barreto
 * Graphic's class,
 * It will receive a {@link LinkedList} from {@link MessageReceiver} to {@link Paint} - repaint the {@link JLayeredPane} - Units Layer.
 */
public class Graphic extends JLabel {
	private static final int BAR_HEALTH_HEIGHT = 5;
	private static final int BAR_HEALTH_CLOSE_TO_IMAGE = 8;
	private LinkedList<CharacterObject> listOfDolls;
	private boolean some1WasSelected;
	private CharacterObject characterObject;
	private ChosenCharacterStats characterStats;
	private MessageBox messageBox;
	
	/**
	 * Constructor 
	 * @param messageBox
	 * @param listOfDolls
	 * @param characterStats
	 */
	public Graphic(final MessageBox messageBox, LinkedList<CharacterObject> listOfDolls, ChosenCharacterStats characterStats) {
		this.messageBox = messageBox;
		this.characterStats = characterStats;
		this.setListOfDolls(listOfDolls);

		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}

			/**
			 * Left Click - Selects or ask the {@link MessageSender} to send a {@link Message} to {@link Server}.
			 * Right Click - Removes focus and avatar image from {@link ChoosenCharactersStats}
			 * After receiving a second click it will create a {@link Message} and put in {@link Mailbox} and {@link Notify} the ({@link Thread}) {@link MessageSender} to send it. 
			 */
			@Override
			public void mousePressed(MouseEvent arg0) {
				switch (arg0.getModifiers()) {
					case InputEvent.BUTTON3_MASK:
						// LEFT CLICK !!!!
						setSome1WasSelected(false);
						characterObject = null;
						showOnScreen();
						break;
						
					case InputEvent.BUTTON1_MASK:
						// RIGHT CLICK !!!
						if (isSome1WasSelected() == false) {
							selectMode(arg0);			
						}
	
						else {
							Point pointSelectedFrom = new Point(characterObject.getMatrixXPosition(), characterObject.getMatrixYPosition());
							Point pointSelectedTo = calculateCorrectMatrixPositions(new Point(arg0.getX(), arg0.getY()));
							messageBox.addMessage(new SelectMessage(pointSelectedFrom, pointSelectedTo));							
							if(WelcomeScreen.nickName.equals(characterObject.getPlayerName()))
								characterObject.playConfirmSound();
							setSome1WasSelected(false);
						}
						break;
					default:
						System.err.println("ERROR MOUSE LISTENER");
				}
			}

			/**
			 * Transforms pixels to coordinates.
			 * @param point
			 * @return
			 */
			private Point calculateCorrectMatrixPositions(Point point) {
				int x = point.x/characterObject.getImageIcon().getIconWidth();
				int y = point.y/characterObject.getImageIcon().getIconHeight();
				return new Point(x,y);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});

	}
	/**
	 * Paints the layer.
	 * Uses:
	 * DefineImageIconAndAvatarIcon
	 * DrawRangeArea
	 * DrawLifeBar
	 * DrawDamageBar
	 * RefreshFocusUnit
	 * DrawCombatLines 
	 */
	@Override
	public void paint(Graphics g) {
	   if(getListOfDolls() != null) {
		for (CharacterObject mov : getListOfDolls()) {
			// Define ImageIcon and Avatar Icon, Motive Transient.
			defineImageIconAndAvatarIcon(mov);
			// ------- Range Area ------ 
			drawRangeArea(mov, g); 		
			// ------- LIFE BAR ------
			drawLifeBar(mov, g);
			// --- COMBAT BAR HEALTH MECH -----
			drawDamageBar(mov, g);
			// Refresh Focus Unit
			refreshFocusUnit(mov);
			// DRAW LINES
			drawLines(mov, g);
		}
	   }
	}

	
	/**
	 * Set the Image and avatar to {@link CharacterObject}
	 * @param mov
	 */
	private void defineImageIconAndAvatarIcon(CharacterObject mov) {
		mov.setImageIcon(new ImageIcon(mov.getNameOfImageString()));
		mov.setAvatarImage(new ImageIcon(mov.getAvatarStringImage()));
	}

	/**
	 * Refresh the stats of {@link CharacterObject}
	 * @param mov
	 */
	private void refreshFocusUnit(CharacterObject mov) {
		if(characterObject != null && mov.getID() == characterObject.getID()){
			characterObject = mov;
			showOnScreen();
		}
	}

	/**
	 * Draws a {@link Rectangle} with the amount of life of the {@link CharacterObject}
	 * @param mov
	 * @param g
	 */
	private void drawDamageBar(CharacterObject mov, Graphics g) {
		int currentHealth;
		g.setColor(Color.RED);
		int denominador = mov.getDefaultHealth()/mov.getImageIcon().getIconWidth();
		currentHealth = (int)(mov.getDefaultHealth()-mov.getCurrentHealth())/denominador;
		g.fillRect(mov.getxLocation(), mov.getyLocation() + 3 * BAR_HEALTH_CLOSE_TO_IMAGE, currentHealth, BAR_HEALTH_HEIGHT);	
	}

	/**
	 * Draws a {@link Rectangle} with the amount of damage of the {@link CharacterObject}
	 * @param mov
	 * @param g
	 */
	private void drawLifeBar(CharacterObject mov, Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(mov.getxLocation(), mov.getyLocation() + 3 * BAR_HEALTH_CLOSE_TO_IMAGE, mov.getImageIcon().getIconWidth(),BAR_HEALTH_HEIGHT);
	}

	/**
	 * Draws a {@link Rectangle} with the range of the {@link CharacterObject}
	 * @param mov
	 * @param g
	 */
	private void drawRangeArea(CharacterObject mov, Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.GRAY);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		g.fillRect(calculateCorrectRectangleWidth(mov),calculateCorrectRectangleHeight(mov), mov.getCalculateRangeWidth(), mov.getCalculateRangeHeight());
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g.drawImage(mov.getImageIcon().getImage(), mov.getxLocation(), mov.getyLocation(), null);
		
	}

	/**
	 * Draws the {@link Line} that connects each enemy.
	 * @param mov
	 * @param g
	 */
	private void drawLines(CharacterObject mov, Graphics g) {
		int xLocationLeftSide = mov.getxLocation() - (mov.getImageIcon().getIconWidth()*mov.getRange()/2-1);
		int xLocationRightSide = mov.getxLocation() + (mov.getImageIcon().getIconWidth()*mov.getRange()/2-1);
		int yLocationUP = mov.getyLocation() - (mov.getImageIcon().getIconHeight()*mov.getRange()/2-1);
		int yLocationDown = mov.getyLocation() + (mov.getImageIcon().getIconHeight()*mov.getRange()/2-1);
		String playerName = mov.getPlayerName();
		int id = mov.getID();
		
		for(CharacterObject allyOrFoe: getListOfDolls()){
			if(allyOrFoe.getImageIcon() == null){
				allyOrFoe.setImageIcon(new ImageIcon(allyOrFoe.getNameOfImageString()));
			}
			int xLocationAllyOrFoe = allyOrFoe.getxLocation();
			int yLocationAllyOrFoe = allyOrFoe.getyLocation();
			String playerNameAllyOrFoe = allyOrFoe.getPlayerName();
			int idAllyOrFoe = allyOrFoe.getID();


			if(id != idAllyOrFoe && !playerName.equals(playerNameAllyOrFoe)
					&& xLocationLeftSide <= xLocationAllyOrFoe &&
					xLocationRightSide >= xLocationAllyOrFoe &&
					yLocationDown >= yLocationAllyOrFoe &&
					yLocationUP <= yLocationAllyOrFoe){
				
				g.setColor(Color.RED);
				int correctMovgetXLocation = mov.getxLocation()+(mov.getImageIcon().getIconWidth()/2);
				int correctMovgetYLocation = mov.getyLocation()+(mov.getImageIcon().getIconHeight()/2);
				int correctAllyOrFoegetYLocation = allyOrFoe.getyLocation()+(allyOrFoe.getImageIcon().getIconHeight()/2);
				int correctAllyOrFoegetXLocation = allyOrFoe.getxLocation()+(allyOrFoe.getImageIcon().getIconWidth()/2);
				g.drawLine(correctMovgetXLocation, correctMovgetYLocation, correctAllyOrFoegetXLocation, correctAllyOrFoegetYLocation);
			}
		}
		
	}

	public void setListOfDolls(LinkedList<CharacterObject> listOfThreads) {
		this.listOfDolls = listOfThreads;
	}

	public LinkedList<CharacterObject> getListOfDolls() {
		return listOfDolls;
	}

	public void setSome1WasSelected(boolean some1WasSelected) {
		this.some1WasSelected = some1WasSelected;
	}

	public boolean isSome1WasSelected() {
		return some1WasSelected;
	}

	/**
	 * Method that analyse the second click if {@link CharacterObject} positions is different from the click, will return true. Otherwise returns false.
	 * @param arg0
	 * @return
	 */
	private boolean selectMode(MouseEvent arg0) {

		if (getListOfDolls() != null) {
			for (CharacterObject mov : getListOfDolls()) {
				
				if(mov.getImageIcon() == null){
					mov.setImageIcon(new ImageIcon(mov.getNameOfImageString()));
					mov.setAvatarImage(new ImageIcon(mov.getAvatarStringImage()));
				}
				int imageHeight = mov.getImageIcon().getIconHeight();
				int imageWidth = mov.getImageIcon().getIconWidth();
				int imageXLocation = mov.getxLocation();
				int imageYLocation = mov.getyLocation();

				if ((imageXLocation + (imageWidth)) >= arg0.getX()
						&& (imageXLocation - (imageWidth / 2)) <= arg0.getX()
						&& (imageYLocation - (imageHeight / 2)) <= arg0.getY()
						&& (imageYLocation + (imageHeight)) >= arg0.getY()) {

					if (isSome1WasSelected() == false) {
						some1WasSelected = true;
						characterObject = mov;
						showOnScreen();
						
						// SOUND TRIGGER
						if(WelcomeScreen.nickName.equals(characterObject.getPlayerName())){
							characterObject.playAcknowledgeSound();
						}
					}

					else {
						/*
						 * Não está feito.
						 * threadInFocus.setThreadToFollow(mov);
						 */
					}
					return true;
				}
			}

		}
		return false;
	}

	private int calculateCorrectRectangleWidth(CharacterObject mov) {
		return mov.getxLocation()- mov.getImageIcon().getIconWidth()*(mov.getRange()/2);
	}

	private int calculateCorrectRectangleHeight(CharacterObject mov) {
		return mov.getyLocation()- mov.getImageIcon().getIconHeight()*(mov.getRange()/2);
	}
	
	public void showOnScreen(){
		characterStats.showStatsOnUIFor(characterObject);
	}

	public MessageBox getMessageBox() {
		return messageBox;
	}
}
