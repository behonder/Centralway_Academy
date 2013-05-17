package pt.iul.pcd.tribes.server.messages;

import java.awt.Point;

public class SelectMessage extends Message {

	private final Point pointTo;
	private final Point pointFrom;

	public SelectMessage(Point pointFrom, Point pointTo) {
		this.pointFrom = pointFrom;
		this.pointTo = pointTo;
	}

	@Override
	public MessageType getType() {
		return MessageType.SELECTEDANDMOVE;
	}

	public Point getPointTo() {
		return pointTo;
	}

	public Point getPointFrom() {
		return pointFrom;
	}
	
}
