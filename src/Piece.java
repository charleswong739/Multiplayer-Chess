import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Piece {
	
	private int point;
	private BufferedImage sprite;
	private Team team;
	
	public Piece (String spriteName, int p, Team t) {
		point = p;
		team = t;
		
		String filePath = "sprite";
		
		if (t == Team.WHITE) {
			filePath += "/white/";
		} else {
			filePath += "/black/";
		}
		
		filePath += spriteName;
		
		try {
			sprite = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g, int x, int y) {
		g.drawImage(sprite, x, y, null);
	}
	
	public int getPoint() {
		return point;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public abstract Position[] possibleMoves();
}
