package pt.iul.pcd.tribes.client.world2d;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MapWorld extends JLabel {
	
	// --- MAP WORLD VARIABLES ---
	private JPanel botLayer = new JPanel();
	private Tile[][] worldMatrix;
	private int[][] numericMatrix;
	private String[] tilesLocations = new String[2];
	
	// --- MAP WORLD FUNCTIONS ---
	public MapWorld(int[][] numericMatrix) {
		this.numericMatrix = numericMatrix;
		this.worldMatrix = new Tile[numericMatrix.length][numericMatrix.length];
		createTilesVector();
		inicializeWorld();
	
		Insets insets = new Insets(0, 1, 1, 0);
		botLayer.setLayout(new GridBagLayout());
		for (int i = 0; i < worldMatrix.length; i++) {
			for (int j = 0; j < worldMatrix[i].length; j++) {
				botLayer.add(worldMatrix[j][i], new GridBagConstraints(i, j, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START ,GridBagConstraints.BOTH, insets, 0, 0));
			}
		}
	}

	public JPanel getMapWorld() {
		return botLayer;
	}
	
	public Dimension getWorldDimension(){
		return new Dimension(worldMatrix.length, worldMatrix[0].length);
	}
	
	public synchronized boolean isTileBlocked(int x, int y){
		return worldMatrix[y][x].isTileBlocked();
	}
	
	private void createTilesVector() {
		tilesLocations[0] = "src/pt/iul/pcd/tribes/client/images/world/tiles/tile_grass.png";
		tilesLocations[1] = "src/pt/iul/pcd/tribes/client/images/world/tiles/tile_rock_block.png";
	}
	
	private void inicializeWorld() {
		for (int i = 0; i < numericMatrix.length; i++) {
			for (int j = 0; j < numericMatrix[i].length; j++) {
				if(numericMatrix[i][j] == 0)
					worldMatrix[i][j] = new Tile(tilesLocations[0]);
				else
					worldMatrix[i][j] = new Tile(tilesLocations[1]);
			}
		}
	}

}
