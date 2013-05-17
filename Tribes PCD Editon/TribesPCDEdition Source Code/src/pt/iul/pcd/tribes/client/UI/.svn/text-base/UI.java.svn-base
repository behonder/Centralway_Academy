package pt.iul.pcd.tribes.client.UI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import pt.iul.pcd.tribes.client.characters.CharacterObject;
import pt.iul.pcd.tribes.client.moveEngine.Graphic;
import pt.iul.pcd.tribes.client.network.MessageBox;
import pt.iul.pcd.tribes.client.soundsystem.Player;
import pt.iul.pcd.tribes.client.world2d.MapWorld;

@SuppressWarnings("serial")
public class UI extends JLayeredPane{

	// ---- SOUND VARIABLES ---
	private Player soundPlayer = new Player("src/pt/iul/pcd/tribes/client/sounds/soundtrack/battleSong.mp3");
	private static final String GAME_START_SOUND = "src/pt/iul/pcd/tribes/client/sounds/gameSounds/gameStart.mp3";
	private static final String VICTORY_SOUND = "src/pt/iul/pcd/tribes/client/sounds/gameSounds/victory.mp3";
	private static final String DEFEAT_SOUND = "src/pt/iul/pcd/tribes/client/sounds/gameSounds/defeat.mp3";
	private boolean isSoundOn = true;
	
	// --- WINDOW VARIABLES ---
	private static final int WINDOW_WIDTH = 650;
	private static final int WINDOW_HEIGHT = 850;
	
	private JFrame window = new JFrame();
	private JLayeredPane lpane = window.getLayeredPane();

	// --- MAIN SYSTEMS ----
	private JPanel worldLayer;
	private JPanel userInterfaceBackground = new ImagePanel("src/pt/iul/pcd/tribes/client/images/othergraphics/uiImage.png");
	private static final String BLACKBACKGROUND_END_GAME = "src/pt/iul/pcd/tribes/client/images/othergraphics/blackBackground.png";
	private static final String VICTORY_SCREEN = "src/pt/iul/pcd/tribes/client/images/othergraphics/victoryScreen.png";
	private static final String DEFEATSCREEN_END_GAME = "src/pt/iul/pcd/tribes/client/images/othergraphics/defeatScreen.png";
	
	private JPanel chatSystem;
	private ChosenCharacterStats characterStats = new ChosenCharacterStats();
	
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	
	private Graphic charactersLayer;
	private LinkedList<CharacterObject> listOfThreads;
	private MessageBox messageBox;
	
	
	// --- UI FUNCTIONS ---
	public UI(String playerName, MapWorld world, String gameVersion, MessageBox messageBox){
			
		this.messageBox = messageBox;
		setCharactersLayer(new Graphic(messageBox, getListOfCharacters(), characterStats));
				
		window.setTitle(gameVersion + " - " + playerName.toUpperCase());
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setLocation(400, 0);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		buildWorldLayer(world);
		buildUIBackground();
		buildChatSystem(playerName);
		buildChosenCharacterStats();
		addAllLayers();	
	}

	private void buildChosenCharacterStats() {
		getCharactersLayer().setBounds(0, 0, 640, 640);
	}

	private void addAllLayers() {
		lpane.add(worldLayer, JLayeredPane.DEFAULT_LAYER);
		lpane.add(getCharactersLayer(), JLayeredPane.PALETTE_LAYER);
		lpane.add(userInterfaceBackground, JLayeredPane.PALETTE_LAYER);
		lpane.add(chatSystem, JLayeredPane.MODAL_LAYER);
		lpane.add(characterStats, JLayeredPane.MODAL_LAYER);
	}

	
	// ------ BUILDS ------
	private void buildWorldLayer(MapWorld world){
		worldLayer = world.getMapWorld();
		worldLayer.setBackground(Color.BLACK);
		worldLayer.setBounds(0, 0, 641, 641);
	}
		
	private void buildUIBackground() {
		userInterfaceBackground.setBounds(0, 640, 850, 300);
	}
	
	private void buildChatSystem(String playerName) {
		chatSystem = new ChatSystem(playerName, messageBox);
		chatSystem.setBorder(blackline);
		chatSystem.setBounds(9,660, 237, 163);	
		
		// --- MESSAGE LISTENER ---
		new Thread (new Runnable() {
			
			@Override
			public void run() {
				((ChatSystem) chatSystem).waitForMessages();	
			}
		}).start();
	}
	
	public void init() {
		getWindow().setVisible(true);
	}
	
	
	public void setListOfCharacters(LinkedList<CharacterObject> linkedList) {
		this.listOfThreads = linkedList;
	}

	public LinkedList<CharacterObject> getListOfCharacters() {
		return listOfThreads;
	}

	public void setCharactersLayer(Graphic charactersLayer) {
		this.charactersLayer = charactersLayer;
	}

	public Graphic getCharactersLayer() {
		return charactersLayer;
	}

	public void setWindow(JFrame window) {
		this.window = window;
	}

	public JFrame getWindow() {
		return window;
	}
	
	public void startSound(){
		if(isSoundOn){
			Thread sound = new Thread(new Runnable() {

				@Override
				public void run() {
					soundPlayer.play(99);
				}
			});
			sound.start();
			
			Thread hornSound = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Player horn = new Player(GAME_START_SOUND);
					horn.play(1);
					
				}
			});
			hornSound.start();
		}
	}

	public Player getSoundPlayer() {
		return soundPlayer;
	}

	public void showEndGameStatus(boolean winStatus) {
		soundPlayer.stop();	
		int xCenter = worldLayer.getWidth()/2 - 35;
		int yCenter = worldLayer.getHeight()/2 + 75;
		
		JPanel back = new ImagePanel(BLACKBACKGROUND_END_GAME);
		JButton buttonClose = new JButton("Close game");
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBackground(Color.BLACK);
		
		JPanel endGameStatusImage; 
		
		if(winStatus){
			soundPlayer = new Player(VICTORY_SOUND);
			endGameStatusImage = new ImagePanel(VICTORY_SCREEN);
			endGameStatusImage.setBounds(128, 220, 600, 500);
			buttonClose.setBackground(new Color(1, 133, 225));
			buttonClose.setForeground(new Color(41, 33, 27));
			buttonPanel.setBounds(xCenter, yCenter, 105, 40);
		}
		else {
			soundPlayer = new Player(DEFEAT_SOUND);
			endGameStatusImage = new ImagePanel(DEFEATSCREEN_END_GAME);
			endGameStatusImage.setBounds(100, 220, 600, 500);
			buttonClose.setBackground(new Color(187, 19, 32));
			buttonClose.setForeground(new Color(50, 0, 12));
			buttonPanel.setBounds(xCenter-15, yCenter, 105, 40);
		}
		
		buttonClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		buttonPanel.add(buttonClose);
		lpane.remove(worldLayer);
		lpane.remove(charactersLayer);
		lpane.add(back, JLayeredPane.DEFAULT_LAYER);
		lpane.add(endGameStatusImage, JLayeredPane.PALETTE_LAYER);
		lpane.add(buttonPanel, JLayeredPane.DRAG_LAYER);
		lpane.repaint();	
		lpane.validate();
		soundPlayer.play(1);
	}

}
