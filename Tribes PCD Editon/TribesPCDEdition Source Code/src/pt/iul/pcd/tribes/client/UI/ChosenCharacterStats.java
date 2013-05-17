package pt.iul.pcd.tribes.client.UI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import pt.iul.pcd.tribes.client.characters.CharacterObject;

@SuppressWarnings("serial")
public class ChosenCharacterStats extends JPanel {

	// --- CHOSEN CHARACTER STATS VARIABLES ---
	private JPanel statsPanel = new JPanel();
	private JLabel characterAvatar = new JLabel(new ImageIcon("src/pt/iul/pcd/tribes/client/images/othergraphics/nullImage.png"));
	private JLabel characterHealth = new JLabel();
	
	private Border blackLine = BorderFactory.createLineBorder(Color.black);
	
	// --- CHOSEN CHARACTER STATS FUNCTIONS ---
	public ChosenCharacterStats() {
		setLayout(new BorderLayout());
		setBounds(292, 679, 70, 124);
		setBorder(blackLine);
		setBackground(Color.BLACK);
		characterHealth.setForeground(Color.GREEN);
		characterHealth.setHorizontalAlignment(SwingConstants.CENTER);
		statsPanel.setLayout(new BorderLayout());
		statsPanel.setBackground(Color.BLACK);
		statsPanel.add(characterAvatar, BorderLayout.CENTER);
		statsPanel.add(characterHealth, BorderLayout.SOUTH);
		this.add(statsPanel, BorderLayout.CENTER);
	}
	
	public void showStatsOnUIFor(CharacterObject character){
		if(character == null){
			characterAvatar.setIcon(new ImageIcon("src/pt/iul/pcd/tribes/client/images/othergraphics/nullImage.png"));
			characterHealth.setText("");
			validate();
		}
		else {
			characterAvatar.setIcon(new ImageIcon(character.getAvatarStringImage()));
			validate();	
			characterHealth.setText("" + character.getCurrentHealth() + " / " + character.getStartHealth());
		}
	}
}