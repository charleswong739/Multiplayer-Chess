package common;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import client.ClientBoard;

/**
 * 
 * @author Charles Wong
 *
 */
public abstract class Piece {
	
	private int point;
	private BufferedImage sprite;
	protected Team team;
	protected Position position;
	
	public Piece (String spriteName, int p, Team t, Position pos) {
		point = p;
		team = t;
		position = pos;
		
		String filePath = "sprites";
		
		if (t == Team.WHITE) {
			filePath += "/white/";
		} else {
			filePath += "/black/";
		}
		
		filePath += spriteName + ".png";
		
		try {
			sprite = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			System.out.println("Could not find image: " + filePath);
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
	
	public Position getPos() {
		return position;
	}
	
	public void setPos(Position p) {
		position = p;
	}
	
	public abstract Position[] possibleMoves(ClientBoard board);
}
