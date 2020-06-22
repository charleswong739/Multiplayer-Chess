/**
 * Piece
 * Version 1.0
 * @author Charles Wong
 * 2020-06-21
 * Abstract class for different chess pieces
 */

//package statement
package common;

//import statements
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import client.ClientBoard;

public abstract class Piece {
	//class variables
	private int point;
	private BufferedImage sprite;
	protected Team team;
	protected Position position;
	
	//Constructor
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
	
	/**
	 *draw
	 * @param: Graphics g, and coordinates x and y
	 * @return: null
	 * draws the sprite for each piece
	 */
	public void draw(Graphics g, int x, int y) {
		g.drawImage(sprite, x, y, null);
	}
	
	/**
	 * getPoint
	 * @param: null
	 * @return: int 
	 * returns the point for the piece
	 */
	public int getPoint() {
		return point;
	}
	
	/**
	 * getTeam
	 * @param: null
	 * @return: Team
	 * returns the Team for the piece
	 */
	public Team getTeam() {
		return team;
	}
	
	/**
	 * getPos
	 * @param: null
	 * @return: Position
	 * return the piece's position
	 */
	public Position getPos() {
		return position;
	}
	
	/**
	 * setPos
	 * @param: Position
	 * @return: null
	 * sets the piece's position
	 */
	public void setPos(Position p) {
		position = p;
	}
	
	//abstract method that determines each piece's possible moves
	public abstract Position[] possibleMoves(ClientBoard board);
} // end of Piece class
