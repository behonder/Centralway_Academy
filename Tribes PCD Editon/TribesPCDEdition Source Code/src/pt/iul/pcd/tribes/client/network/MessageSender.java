package pt.iul.pcd.tribes.client.network;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import pt.iul.pcd.tribes.server.Server;
import pt.iul.pcd.tribes.server.messages.Message;

/**
 * Class that creates {@link Thread} that is responsable to send {@link Message} to the {@link Server}.
 * @author Miguel Oliveira && João Barreto
 */
public class MessageSender extends Thread{

	private final Socket socket;
	private MessageBox messageBox;
	private ObjectOutputStream out;
	/**
	 * Constructor
	 * @param socket
	 * @param messageBox
	 */
	public MessageSender(Socket socket, MessageBox messageBox) {
		this.socket = socket;
		this.messageBox = messageBox;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the {@link ObjectOutputStream} linked to the {@link Server}.
	 * @return
	 */
	public ObjectOutputStream getOut() {
		return out;
	}

	/**
	 * {@link Runnable} method.
	 */
	public void run(){
		try {
			while(true){
				try {
					Message newMessage = messageBox.getMessage();
					out.writeObject(newMessage);
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} finally{
			try {
				socket.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error MessageSender: socket not closed!");
				//e.printStackTrace();
			}
		}
	}
}