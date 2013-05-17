package pt.iul.pcd.tribes.client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import pt.iul.pcd.tribes.client.UI.UI;
import pt.iul.pcd.tribes.client.UI.WelcomeScreen;
import pt.iul.pcd.tribes.server.Server;
import pt.iul.pcd.tribes.server.messages.GamesList;
import pt.iul.pcd.tribes.server.messages.Message;
import pt.iul.pcd.tribes.server.messages.MessageType;
import pt.iul.pcd.tribes.server.messages.StartGame;
import pt.iul.pcd.tribes.server.messages.WorldMessage;

/**
 * Class that creates {@link Thread} that is responsable to receive {@link Message} from the {@link Server}.
 * @author Miguel Oliveira && João Barreto
 */
public class MessageReceiver extends Thread{

	private final Socket socket;
	private final WelcomeScreen welcomeScreen;
	private MessageBox messageBox;
	private ObjectInputStream in;
	private UI ui;
	private boolean firstMessage = true;

	/**
	 * Constructor
	 * @param socket
	 * @param welcomeScreen
	 * @param messageBox
	 */
	public MessageReceiver(Socket socket, WelcomeScreen welcomeScreen, MessageBox messageBox) {
		this.socket = socket;
		this.welcomeScreen = welcomeScreen;
		this.messageBox = messageBox;
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Implements {@link Runnable}
	 */
	public void run(){
		try {

			try {
				try {
					while(true){
						Message newMessage = (Message)in.readObject();
						dealWithMessage(newMessage);
					}
				}catch (ClassNotFoundException e1) {		
					e1.printStackTrace();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		} finally{
			try {
				JOptionPane.showMessageDialog(null, "Socket Close from Client");
				socket.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error Socket not closed!");
			}
		}
	}
	
	/**
	 * Manages the type of {@link Message} and chooses a method using {@link MessageType}.
	 * @param newMessage
	 */
	public void dealWithMessage(Message newMessage){
		switch(newMessage.getType()){
			case GETLIST:
					welcomeScreen.buildJoinMenu(((GamesList) newMessage).getGamesList());
					break;
			case WORLD:
					setUi(welcomeScreen.startGame((WorldMessage) newMessage));
					break;
			case STARTGAME:
					ui.getCharactersLayer().setListOfDolls(((StartGame) newMessage).getListOfCharacters());
					ui.getCharactersLayer().repaint();
					playInicialSound();
					break;
			case TEXT:
					messageBox.addMessageText(newMessage);
					break;
			case LOSE: 
					showFinalScreen(false);
					break;
			case WIN:
					showFinalScreen(true);
					break;
		}
	}

	private void playInicialSound() {
		if(firstMessage){
			ui.startSound();
			firstMessage = false;
		}		
	}

	public void setUi(UI ui) {
		this.ui = ui;
	}

	public UI getUi() {
		return ui;
	}
	
	private void showFinalScreen(boolean status){
		ui.showEndGameStatus(status);
	}
}