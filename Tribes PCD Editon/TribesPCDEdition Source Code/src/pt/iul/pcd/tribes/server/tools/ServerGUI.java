
package pt.iul.pcd.tribes.server.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.BoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class ServerGUI extends JFrame {

	// --- SERVER GUI VARIABLES ---
	private static final String SERVER_VERSION = "Tribes SERVER - PCD EDITION V.1/(28-11-2011) - *While True* ";
	private static final String WELCOME_MESSAGE = "********** TRIBES SERVER **********\n";
	private static final Image ICON_SERVER= Toolkit.getDefaultToolkit().getImage("src/pt/iul/pcd/tribes/client/images/othergraphics/iconServer.png");
	private Container container = this.getContentPane();
	private JTextArea console = new JTextArea();
	private JTextField input = new JTextField();
	private JScrollPane scrollPane = new JScrollPane(console);
	private Dimension windowSize = new Dimension(700, 200);
	private boolean translucidIsOn = true;
	
	// --- SERVER GUI FUNCTIONS ---
	public ServerGUI() {
		this.setTitle(SERVER_VERSION);
		this.setSize(windowSize);
		this.setLocation(WIDTH, HEIGHT+100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(ICON_SERVER);
		setConsoleAdjustments();
		setInputFieldAdjustments();
		setWindowTranslucid();

		console.setText(WELCOME_MESSAGE);
		printHelp();
		container.add(scrollPane, BorderLayout.CENTER);
		container.add(input, BorderLayout.SOUTH);
	}

	private void setInputFieldAdjustments() {
		// ---- Input Graphics Adjustments 
		input.setForeground(Color.GREEN);
		input.setBackground(Color.BLACK);
		
		// ---- EVENTS -----
		input.addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {}

			public void keyPressed(KeyEvent key) {
				if(key.getKeyCode() == KeyEvent.VK_ENTER){
					analiseAction(input.getText());
					input.setText("");
				}
			}
		});
	}

	private void analiseAction(String action) {
		if(action.equals("/?")){
			console.setText("");
			printHelp();
		}
		if(action.equals("/clear")){
			writeMessageOnConsole(action);
			console.setText("");
		}
		if(action.equals("/reboot")){
			try {
				restartApplication();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void writeMessageOnConsole(String string) {
		console.append(string + "\n");
	}

	private void setWindowTranslucid() {
		if (translucidIsOn) {
			try {
				Class<?> awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
				Method mSetWindowOpacity = awtUtilitiesClass.getMethod("setWindowOpacity", Window.class, float.class);
				mSetWindowOpacity.invoke(null, this, Float.valueOf(0.75f));
			} catch (NoSuchMethodException ex) {
				ex.printStackTrace();
			} catch (SecurityException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (InvocationTargetException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void printHelp(){
		final String COMMANDS = "/reboot - Reboot Server \n /clear - Clean Console \n /? Help commands\n";
		writeMessageOnConsole("[ HELP COMMANDS ]\n" + COMMANDS );
	}
	
	private void setConsoleAdjustments(){
		// --- Console Adjustments ---
		console.setForeground(Color.GREEN);
		console.setBackground(Color.BLACK);
		console.setEditable(false);
		
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
	}
	
	
	// ----- CODIGO REAPROVEITADO E PARCIALMENTE ALTERADO DA INTERNET! ---
	// ----- DIREITOS DE AUTOR MANTIDOS ---------
	// ESTA FUNÇÃO APENAS FUNCIONA EM ALGUNS COMPUTADORES.
	
	/**
	 * Leo Lewis on Wed, 2011/07/06
	 * Restart the current Java application
	 * @param runBeforeRestart some custom code to be run before restarting
	 * @throws IOException
	 */
	public void restartApplication() throws IOException {
		try {
			// java binary
			String java = System.getProperty("java.home") + "/bin/java";
			// vm arguments
			List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
			StringBuffer vmArgsOneLine = new StringBuffer();
			for (String arg : vmArguments) {
				// if it's the agent argument : we ignore it otherwise the
				// address of the old application and the new one will be in conflict
				if (!arg.contains("-agentlib")) {
					vmArgsOneLine.append(arg);
					vmArgsOneLine.append(" ");
				}
			}
			// init the command to execute, add the vm args
			final StringBuffer cmd = new StringBuffer("\"" + java + "\" " + vmArgsOneLine);

			// program main and program arguments
			String[] mainCommand = System.getProperty("sun.java.command").split(" ");
			mainCommand.toString();
			// program main is a jar
			if (mainCommand[0].endsWith(".jar")) {
				// if it's a jar, add -jar mainJar
				cmd.append("-jar " + new File(mainCommand[0]).getPath());
			} else {
				// else it's a .class, add the classpath and mainClass
				cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + mainCommand[0]);
			}
			// finally add program arguments
			for (int i = 1; i < mainCommand.length; i++) {
				cmd.append(" ");
				cmd.append(mainCommand[i]);
			}
			// execute the command in a shutdown hook, to be sure that all the
			// resources have been disposed before restarting the application
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					try {
						Runtime.getRuntime().exec(cmd.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			
			// exit
			System.exit(0);
		} catch (Exception e) {
			// something went wrong
			throw new IOException("Error while trying to restart the application", e);
		}
	}
}