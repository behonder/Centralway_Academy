package pt.iul.pcd.tribes.client.world2d;

import java.util.Random;

public class World{

	// --- WORLD VARIABLES ---
	private final int WORLD_WIDTH = 20;
	private final int WORLD_HEIGTH = 20;
	private final double BLOCK_RACIO = 0.1;
	private final int BLOCK_TILES_PER_LINE = 1;

	private int blockTilesCounter = 0;

	// -------------- TILES INFO ------------------
	// LOCATION 0 - GRASS
	// LOCATION 1 - ROCKS
	private String[] tilesLocations = new String[2];
	// ---------------------------------------------

	private Tile[][] worldMatrix = new Tile[WORLD_WIDTH][WORLD_HEIGTH];
	private boolean blockCreated;
	
	
	// --- WORLD FUNCTIONS ---
	public World() {
		createTilesVector();
		inicializeWorld();
		generateWorldTileBlocks();
	}

	private void createTilesVector() {
		tilesLocations[0] = "src/pt/iul/pcd/tribes/client/images/world/tiles/tile_grass.png";
		tilesLocations[1] = "src/pt/iul/pcd/tribes/client/images/world/tiles/tile_rock_block.png";
	}

	private void inicializeWorld() {
		for (int i = 0; i < worldMatrix.length; i++) {
			for (int j = 0; j < worldMatrix[i].length; j++) {
				worldMatrix[i][j] = new Tile(tilesLocations[0]);
			}
		}
	}

	private void generateWorldTileBlocks() {
		for (int i = 1; i < worldMatrix.length - 1; i++) {
			for (int j = 3; j < worldMatrix[i].length - 3; j++) {
				worldMatrix[i][j] = generateRandomTile();
			}
			blockCreated = false;
			blockTilesCounter = 0;
		}
	}

	private Tile generateRandomTile() {
		if (blockCreated == false && new Random().nextDouble() < BLOCK_RACIO) {
			blockTilesCounter++;
			if (blockTilesCounter == BLOCK_TILES_PER_LINE)
				blockCreated = true;
			return new Tile(tilesLocations[1]);
		}
		return new Tile(tilesLocations[0]);
	}

	// ---------- PUBLIC METHODS --------------------
	public int[][] getNumericMatrix() {
		int[][] numericMatrix = new int[WORLD_HEIGTH][WORLD_WIDTH];
		for (int i = 0; i < worldMatrix.length; i++) {
			for (int j = 0; j < worldMatrix[i].length; j++) {
				if (worldMatrix[i][j].isTileBlocked())
					numericMatrix[i][j] = 1;
				else
					numericMatrix[i][j] = 0;
			}
		}
		return numericMatrix;
	}

	public int getNumberOfLines() {
		return WORLD_WIDTH;
	}

	public int getNumberOfColumns() {
		return WORLD_HEIGTH;
	}

	public Tile[][] getWorldMatrix() {
		return worldMatrix;
	}
	
	public int getWORLD_WIDTH() {
		return WORLD_WIDTH;
	}

	public int getWORLD_HEIGTH() {
		return WORLD_HEIGTH;
	}
}
