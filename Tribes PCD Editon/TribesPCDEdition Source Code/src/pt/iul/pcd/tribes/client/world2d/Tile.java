package pt.iul.pcd.tribes.client.world2d;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import pt.iul.pcd.tribes.client.characters.CharacterObject;

@SuppressWarnings("serial")
public class Tile extends JPanel{

	// --- TILE VARIABLES ---
	private String tileName;
	private Image imageFile;
	private CharacterObject characterInTile = null;

	// --- TILE FUNCTIONS ---
	public Tile(String imageLocation) {
		this(new ImageIcon(imageLocation).getImage());

		if (!new File(imageLocation).exists()) {
			System.err.println("Tile " + imageLocation + " not found...");
			return;
		}
		this.tileName = imageLocation;
	}

	public Tile(Image imageFile) {
		this.imageFile = imageFile;
		Dimension size = new Dimension(imageFile.getWidth(null),imageFile.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(imageFile, 0, 0, null);
	}

	public String getTileName() {
		return tileName;
	}

	public boolean isTileBlocked() {
		if (tileName.contains("block"))
			return true;
		return false;
	}
	
	public CharacterObject getCharacterObjectInTile(){
		return characterInTile;
	}
	
	public void setCharacterObjectInTile(CharacterObject character){
		this.characterInTile = character;
	}
}
