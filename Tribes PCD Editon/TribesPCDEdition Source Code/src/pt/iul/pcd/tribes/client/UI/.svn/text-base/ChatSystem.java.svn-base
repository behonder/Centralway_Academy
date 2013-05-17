package pt.iul.pcd.tribes.client.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import pt.iul.pcd.tribes.client.network.MessageBox;
import pt.iul.pcd.tribes.client.soundsystem.Player;
import pt.iul.pcd.tribes.server.messages.TextMessage;

public class ChatSystem extends JPanel{

	// --- CHAT SYSTEM VARIABLES ---
	private Calendar calendar = new GregorianCalendar();
	
	private final String welcomeMessage = "Welcome summoners...\n";
	private String playerName = null;
	
	private JPanel botPanel = new JPanel();
	private JButton sendMessageButton = new JButton("SEND");
	private JTextArea textArea = new JTextArea(welcomeMessage);
	private JTextField inputText = new JTextField("");
	private JScrollPane scrollPane = new JScrollPane(textArea);

	private final MessageBox messageBox;
	private Player chatSound;

	// --- CHAT SYSTEM FUNCTIONS ---
	
	public ChatSystem(final String playerName, final MessageBox messageBox){
		this.playerName = playerName;
		this.messageBox = messageBox;
		this.setLayout(new BorderLayout());

		// ------ ChatWindow Settings -----
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.GREEN);
		inputText.setBackground(Color.BLACK);
		inputText.setForeground(Color.GREEN);
		sendMessageButton.setBackground(new Color(59,82,35));
		sendMessageButton.setForeground(Color.WHITE);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
				
		// --- ScrollPane Adjustments ---
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

					BoundedRangeModel brm = scrollPane.getVerticalScrollBar().getModel();
					boolean wasAtBottom = true;

					public void adjustmentValueChanged(AdjustmentEvent e) {
						if (!brm.getValueIsAdjusting()) {
							if (wasAtBottom)
								brm.setValue(brm.getMaximum());
						} else
							wasAtBottom = ((brm.getValue() + brm.getExtent()) == brm.getMaximum());
					}
				});
		// -------------------------------	
		
		botPanel.setLayout(new BorderLayout());
		botPanel.add(inputText, BorderLayout.CENTER);
		botPanel.add(sendMessageButton, BorderLayout.EAST);
		
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(botPanel, BorderLayout.SOUTH);
		
		// ------ LISTENERS ------
		sendMessageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		
		inputText.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent key) {}
			
			@Override
			public void keyReleased(KeyEvent arg0) {}
			
			@Override
			public void keyPressed(KeyEvent key) {
				if(key.getKeyCode() == KeyEvent.VK_ENTER){
					sendMessage();
				}
			}
		});		
	}
	
	private String buildTime(){
		calendar.setTime(new Date());
		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		Integer minutes = calendar.get(Calendar.MINUTE);
		Integer seconds = calendar.get(Calendar.SECOND);
		return "("+hour + ":" + minutes + ":" + seconds +")";		
	}
	

	public void waitForMessages() {
		while (true) {
			TextMessage text = messageBox.waitForStringMessages();
			textArea.append(buildTime() + " " + ((String)text.getPlayerName()) + " > " + ((TextMessage) text).getText() + "\n");
			inputText.setText("");
			if(!text.getPlayerName().equals(playerName))
				playSound();
		}
	}
	
	private void sendMessage(){
		messageBox.addMessage(new TextMessage(playerName, inputText.getText()));
		inputText.setText("");
	}
	
	private void playSound(){
		new Player("src/pt/iul/pcd/tribes/client/sounds/gameSounds/chat_messageReceived.mp3").play(1);
	}
}