package pt.iul.pcd.tribes.server.messages;



public class WorldMessage extends Message{

	private int[][] worldMatrix;
	
	@Override
	public MessageType getType() {		
		return MessageType.WORLD;
	}

	public int[][] getWorldMatrix() {
		return worldMatrix;
	}

	public void setWorldMatrix(int[][] tiles) {
		this.worldMatrix = tiles;
	}
}
