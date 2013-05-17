package pt.iul.pcd.tribes.client.soundsystem;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;

public class Player {

	// --- PLAYER VARIABLES ---
	private boolean running;
	private String filePath;
	private int repeater = 0;
	
	
	// --- PLAYER FUNCTIONS ---
	public Player(String filePath) {
		this.filePath = filePath;
		this.running = true;
	}

	public void stop() {
		this.running = false;
	}

	public void play(int x) {
		this.running = true;
		AudioInputStream din = null;
		try {
			File file = new File(filePath);
			while (repeater != x) {

				AudioInputStream in = AudioSystem.getAudioInputStream(file);
				AudioFormat baseFormat = in.getFormat();
				AudioFormat decodedFormat = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						baseFormat.getSampleRate(), 16,
						baseFormat.getChannels(), baseFormat.getChannels() * 2,
						baseFormat.getSampleRate(), false);
				din = AudioSystem.getAudioInputStream(decodedFormat, in);
				DataLine.Info info = new DataLine.Info(SourceDataLine.class,
						decodedFormat);
				SourceDataLine line = (SourceDataLine) AudioSystem
						.getLine(info);
				if (line != null) {
					line.open(decodedFormat);
					byte[] data = new byte[4096];
					// Start
					line.start();

					int nBytesRead;
					while (running
							&& (nBytesRead = din.read(data, 0, data.length)) != -1) {
						line.write(data, 0, nBytesRead);
					}
					// Stop
					line.drain();
					line.stop();
					line.close();
					din.close();
				}
				repeater++;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ups... problem ocurred in Sound Player ");
			//e.printStackTrace();
		} finally {
			if (din != null) {
				try {
					din.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Ups... problem ocurred in Sound Player ");
				}
			}
		}
	}
}
