package pt.iul.pcd.tribes.client.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameEndedPanel extends JPanel {

	private Image img;

	public GameEndedPanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public GameEndedPanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(new BorderLayout());
	}

	public void paintComponent(Graphics g) {
		g.fillRoundRect(0, 0, img.getWidth(null), img.getHeight(null), 0, 0);
		g.drawImage(img, 0, 0, null);
	}

}