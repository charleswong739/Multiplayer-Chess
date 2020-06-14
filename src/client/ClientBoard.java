package client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import common.Board;
import common.King;
import common.Piece;
import common.Position;
import common.Team;

/**
 * 
 * @author Charles Wong
 *
 */
public class ClientBoard extends Board{

	private BufferedImage bg;
	private Team orientation;

	public ClientBoard(Team o) {
		super();
		orientation = o;
		
		try {
			bg = ImageIO.read(new File("sprites/bg"));
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g, int x, int y) {
		
	}
	
	/**
	 * Simulates the movement of a piece and checks if the move places the King in check. This
	 * method assumes that the target position is a valid move location for the piece in the source
	 * position. However, if the source position is empty, then an IllegalArgumentException is thrown
	 * 
	 * @param src The original position of a piece
	 * @param tar The target position a piece 
	 * @return true if the move is valid (does not place the King in check), false otherwise
	 */
	public boolean simulateMove (Position src, Position tar) {
		if (chessBoard[src.file][src.rank] == null) {
			throw new IllegalArgumentException("Source position is empty");
		}
		
		King k;
		if (chessBoard[src.file][src.rank].getTeam() == Team.WHITE)
			k = getWhiteKing();
		else
			k = getBlackKing();
		
		Piece temp = chessBoard[tar.file][tar.rank];
		chessBoard[tar.file][tar.rank] = chessBoard[src.file][src.rank]; 
		chessBoard[src.file][src.rank] = null;
		
		boolean b = k.inCheck(chessBoard);
		
		chessBoard[src.file][src.rank] = chessBoard[tar.file][tar.rank];
		chessBoard[tar.file][tar.rank] = temp; 
		
		return !b;
	}
}
