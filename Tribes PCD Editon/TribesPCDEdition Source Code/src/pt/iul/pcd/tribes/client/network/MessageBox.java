package pt.iul.pcd.tribes.client.network;

import java.util.LinkedList;

import javax.swing.JOptionPane;

import pt.iul.pcd.tribes.server.messages.Message;
import pt.iul.pcd.tribes.server.messages.TextMessage;
/**
 * 
 * @author Miguel Oliveira && João Barreto
 * Creates two {@link LinkedList} of type {@link Message} and {@link TextMessage}
 */
public class MessageBox {
	private LinkedList<Message> messageList = new LinkedList<Message>();
	private LinkedList<TextMessage> messageListText = new LinkedList<TextMessage>();
	
	/**
	 * {@link LinkedList} - Adds a {@link Message} to the {@link LinkedList}
	 * @param message
	 */
	public synchronized void addMessage(Message message){
		messageList.add(message);
		notifyAll();
	}
	
	/**
	 * Iterates and removes all {@link Message} of type {@link Message} from the {@link LinkedList}
	 * @return
	 */
	public synchronized Message getMessage(){
		while(messageList.isEmpty())
			try {
				wait();
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Thread Message Sender was interrupted in Mailbox");
			}
		return messageList.poll();
	}
	
	public synchronized void addMessageText(Message message){
		messageListText.add((TextMessage) message);
		notifyAll();
	}

	/**
	 * Iterates and removes all {@link Message} of type {@link TextMessage} from the {@link LinkedList}
	 * @return
	 */
	public synchronized TextMessage waitForStringMessages() {
		while(messageListText.isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Thread Chat was interrupted in MailBox Text Messages");
			}
		}
		return messageListText.poll();
	}
}
