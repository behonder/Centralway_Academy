package pt.iul.pcd.tribes.client.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import pt.iul.pcd.tribes.client.network.MessageBox;
import pt.iul.pcd.tribes.client.network.MessageReceiver;
import pt.iul.pcd.tribes.client.network.MessageSender;
import pt.iul.pcd.tribes.client.soundsystem.Player;
import pt.iul.pcd.tribes.client.world2d.MapWorld;
import pt.iul.pcd.tribes.server.Server;
import pt.iul.pcd.tribes.server.messages.CreateGameMessage;
import pt.iul.pcd.tribes.server.messages.GamesList;
import pt.iul.pcd.tribes.server.messages.JoinGameMessage;
import pt.iul.pcd.tribes.server.messages.WorldMessage;

public class WelcomeScreen {

	// ---- GAME VARIABLES ----
	private final String GAME_VERSION = "Tribes - PCD EDITION V.1/(30-11-2011)";
	private final int MAX_PLAYERS = 5;
	private final int MIN_PLAYERS = 1;
	public static String nickName; 

	// ---- NETWORK VARIABLES ----
	private Socket socket;
	private InetAddress serverAdress;
	private MessageBox messageBox = new MessageBox();
	private MessageSender messageSender;
	private MessageReceiver messageReceiver;
	
	// ---- GRAPHICS VARIABLES ---
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	private final int LOGO_SLEEP_TIMER = 1000;
	private final String LOGO_PATH = "src/pt/iul/pcd/tribes/client/images/othergraphics/background.png";
	private final JPanel LOGO_IMAGE = new ImagePanel("src/pt/iul/pcd/tribes/client/images/othergraphics/companyscreen.png");
	private final Image imageCursor = toolkit.getImage("src/pt/iul/pcd/tribes/client/images/othergraphics/cursor.png");
	private final Image tribesIcon = toolkit.getImage("src/pt/iul/pcd/tribes/client/images/othergraphics/tribesIcon.png");

	
	private Cursor cursor = toolkit.createCustomCursor(imageCursor , new Point(0,0), "img");
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	
	private JFrame window = new JFrame();
	private JLayeredPane container = window.getLayeredPane();
	private JPanel userChoicePanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JTextField nickNameField = new JTextField();
	private JTextField gameNameField = new JTextField();
	private JTextField playerMaxField = new JTextField();
	private Color backGroundColor = new Color(200, 167, 88);
	private Color letringColor = new Color(181, 53, 38);

	private UI ui;
	
	// ---- SOUND VARIABLES ---------
	private boolean soundSystemFlag;
	private Player soundPlayer = new Player("src/pt/iul/pcd/tribes/client/sounds/soundtrack/welcomeScreenSong.mp3");
	protected boolean allowEnterPress = false;
	
	// ---- PROCEDURES/FUNCTIONS ----
	
	/** @author João Barreto & Miguel Oliveira
	 *  The WelcomeScreen class provides the first interation with the game. With this, the player is able to create or join an existing game. 
	 * @param masterServerIp 
	 * @param soundIsOn 
	 */
	public WelcomeScreen(String masterServerIp, boolean soundIsOn) {
		window.setSize(800, 625);
		window.setResizable(true);
		window.setLocation(300, 100);
		window.setIconImage(tribesIcon);
		container.add(LOGO_IMAGE, JLayeredPane.DEFAULT_LAYER);
		window.setCursor(cursor);
		soundSystemFlag = soundIsOn;
		
		try {
			this.serverAdress = InetAddress.getByName(masterServerIp);
			socket = new Socket(serverAdress, Server.GAME_PORT);
			messageSender = new MessageSender(socket, messageBox);
			messageReceiver = new MessageReceiver(socket, this, messageBox);
			messageSender.start();
			messageReceiver.start();
	
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not connect to master server...\n Please try again later.");
			System.exit(0);
		}
		
		window.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent k) {
				if (k.getKeyCode() == KeyEvent.VK_ENTER) {
					if (allowEnterPress)
						openCreationOrJoinMenu();
				}
			}
		});
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// -------- INIT METHOD ---------------
	/** The init method starts the welcome screen. At first, it loads the company screen, shows it for a few seconds, 
	 * and changes it again for the welcome image.
	 */
	public void init() throws UnknownHostException {
		window.setVisible(true);

		Thread loadingScreen = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(LOGO_SLEEP_TIMER);
					container.remove(LOGO_IMAGE);	
					container.add(new ImagePanel(LOGO_PATH));
					allowEnterPress = true;
					if(soundSystemFlag)
						soundPlayer.play(99);
					

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		loadingScreen.start();
	}
	
	/**   Constructs the CREATE / JOIN Menu
	 * 
	 */
	// ---- MENU BUILDERS METHODS --------
	private void openCreationOrJoinMenu() {
		allowEnterPress = false;
		JButton joinButton = new JButton("Join game");
		JButton createdButton = new JButton("Create Game");
		buttonPanel.setBackground(backGroundColor);
		buttonPanel.add(joinButton);
		buttonPanel.add(createdButton);

		// --- JOIN BUTTON ---
		joinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getExistingGames();
			}
		});

		createdButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					userChoicePanel.remove(buttonPanel);
					userChoicePanel.repaint();
					userChoicePanel.add(getInputs_CREATE(), BorderLayout.CENTER);
					userChoicePanel.add(getButtons_CREATE(), BorderLayout.SOUTH);
					window.validate();
			}
		});

		userChoicePanel.setBounds(window.getWidth() / 4,(window.getHeight() / 2) + 70, 400, 125);
		userChoicePanel.setBackground(backGroundColor);
		userChoicePanel.setBorder(blackline);
		userChoicePanel.add(buttonPanel);
		container.add(userChoicePanel, JLayeredPane.PALETTE_LAYER);

	}
	/** Request the server for the games list.
	 * 
	 */
	private void getExistingGames() {			
		try{
			messageBox.addMessage(new GamesList());
			
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, "JOIN GAME FAILED.");
			e.printStackTrace();
		}
			
	}
	/**
	 * Constructs create, reset and back buttons, with action listeners.
	 * @return buttonGrid
	 */
	private JPanel getButtons_CREATE() {

		JButton createButton = new JButton("Create");
		JButton resetButton = new JButton("Reset");
		JButton returnPreviousMenuButton = new JButton("Back");
		JPanel buttonGrid = new JPanel();
		buttonGrid.setBackground(backGroundColor);
		
		// ---- CREATE BUTTON ------------------
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createGameData();
			}
		});
		// ---- RESET BUTTON -------------------
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFields();
			}
		});
		// ---- RETURN BUTTON ------------------
		returnPreviousMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMainMenu();
			}
		});
		buttonGrid.add(createButton);
		buttonGrid.add(resetButton);
		buttonGrid.add(returnPreviousMenuButton);
		return buttonGrid;
	}
	/** 
	 * Constructs Create Game window.
	 * @return inputsPanel
	 */
	private JPanel getInputs_CREATE() {
		JPanel inputsPanel = new JPanel();
		JLabel nickNameLabel = new JLabel("Nickname: ");
		JLabel gameNameLabel = new JLabel("Game name: ");
		JLabel playerMaxLabel = new JLabel("Max Players nº: ");

		inputsPanel.setLayout(new GridLayout(3, 2));
		inputsPanel.setBackground(backGroundColor);
		inputsPanel.add(nickNameLabel);
		inputsPanel.add(nickNameField);
		inputsPanel.add(gameNameLabel);
		inputsPanel.add(gameNameField);
		inputsPanel.add(playerMaxLabel);
		inputsPanel.add(playerMaxField);

		return inputsPanel;
	}

	/** Compile and send Created Game data to server. CreateGame(nickName, gameName, numberOfPlayers)
	 *  
	 */
	private void createGameData() {

		nickName = nickNameField.getText();
		String gameName = gameNameField.getText();
		Integer numberOfPlayers = 0;
		try {
			numberOfPlayers = Integer.parseInt(playerMaxField.getText());
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "Minimum players: 2 | Maximum players: 4");
		}

		if (!nickName.isEmpty() && !gameName.isEmpty() && numberOfPlayers < MAX_PLAYERS && numberOfPlayers > MIN_PLAYERS) {
			CreateGameMessage newGame = new CreateGameMessage(nickName,	gameName, numberOfPlayers);
			messageBox.addMessage(newGame);
		
		} else
			JOptionPane.showMessageDialog(null, "Game not created. Please try again.");
	}

	/** Resets all fields in the Create menu.
	 * 
	 */
	private void resetFields() {
		nickNameField.setText("");
		gameNameField.setText("");
		playerMaxField.setText("");
	}

	/** Constructs the join game menu.
	 * 
	 * @param list
	 */
	public void buildJoinMenu(JList list) {
		userChoicePanel.removeAll();
		
		JPanel mainPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		final JList gamesList = list;
		JScrollPane gamesScroll = new JScrollPane(gamesList);

		JButton cancelButton = new JButton("Cancel");
		JButton joinButton = new JButton("Join");

		joinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				joinSelectedGame(gamesList);
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				returnToMainMenu();
			}
		});

		buttonPanel.setBackground(backGroundColor);
		buttonPanel.add(joinButton);
		buttonPanel.add(cancelButton);

		JPanel listPanel = new JPanel();

		gamesList.setBackground(backGroundColor);
		gamesList.setForeground(letringColor);
		listPanel.setBackground(backGroundColor);

		gamesScroll.setPreferredSize(new Dimension(userChoicePanel.getWidth() / 2,userChoicePanel.getHeight() / 2));
		listPanel.add(gamesScroll);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(listPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		userChoicePanel.add(mainPanel, BorderLayout.CENTER);
		window.validate();
	}

	/** Allows the player to join the selected game, in the games list.
	 * 
	 * @param gamesList
	 */
	protected void joinSelectedGame(JList gamesList) {
		if (socket == null)
			try {
				throw new SocketException();
			} catch (SocketException e) {
				JOptionPane.showMessageDialog(null, "SOCKET ERROR " + socket.toString() );
			}
		else {
			nickName = JOptionPane.showInputDialog("Please insert your nickname:");
			JoinGameMessage joinMessage = new JoinGameMessage(nickName, (String) gamesList.getSelectedValue());
			messageBox.addMessage(joinMessage);	
		}
	}

	/** Reconstructs the main menu.
	 * 
	 */
	private void returnToMainMenu() {
		resetFields();
		userChoicePanel.removeAll();
		userChoicePanel.add(buttonPanel, BorderLayout.CENTER);
		window.repaint();
	}
	
	/** Receives a message from the server with World information and launchs the UI (UserInterface - characters, chat system, etc). 
	 * 
	 * @param message 
	 * @return ui
	 */
	public UI startGame(WorldMessage message){
		if(soundSystemFlag)
			soundPlayer.stop();
		JOptionPane.showMessageDialog(null, "Press ok to start game.");
		window.dispose();
		
		MapWorld world = new MapWorld(message.getWorldMatrix());
		ui = new UI(nickName, world, GAME_VERSION, messageBox);
		ui.getWindow().setCursor(cursor);
		ui.getWindow().setIconImage(tribesIcon);
		ui.init();
		return ui;
	}

	public MessageSender getMessageSender() {
		return messageSender;
	}
}
