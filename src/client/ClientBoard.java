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
	private BufferedImage selectbg;
	private BufferedImage movespoint;
	
	private Team orientation;
	
	private Piece selected;
	private Position[] possibleMoves;

	public ClientBoard(Team o) {
		super();
		orientation = o;
		
		try {
			bg = ImageIO.read(new File("sprites/bg.png"));
			selectbg = ImageIO.read(new File("sprites/selectbg.png"));
			movespoint = ImageIO.read(new File ("sprites/movespoint.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g, int x, int y) {
		g.drawImage(bg, x, y, null);
		
		if (selected != null) {
			if (orientation == Team.WHITE) {
				g.drawImage(selectbg, selected.getPos().file * 60, 420 - selected.getPos().rank * 60, null);
			} else {
				g.drawImage(selectbg, 420 - selected.getPos().file * 60, selected.getPos().rank * 60, null);
			}
		}
		
		int pieceX;
		int pieceY;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (orientation == Team.WHITE) {
					pieceX = i * 60;
					pieceY = 420 - j * 60;
				} else {
					pieceX = 420 - i * 60;
					pieceY = j * 60;
				}
				
				if (chessBoard[i][j] != null) {
					chessBoard[i][j].draw(g, pieceX, pieceY);
				}
			}
		}
		
		if (possibleMoves != null) {
			for (int i = 0; i < possibleMoves.length; i++) {
				if (orientation == Team.WHITE) {
					g.drawImage(movespoint, possibleMoves[i].file * 60, 420 - possibleMoves[i].rank * 60, null);
				} else {
					g.drawImage(movespoint, 420 - possibleMoves[i].file * 60, possibleMoves[i].rank * 60, null);
				}
			}
		}
	}
	
	/**
	 * Makes the designated Position selected if the piece in the position is the same team
	 * as the current board orientation
	 * 
	 * @param p The Position to be designated as selected
	 */
	public void selectPiece(Position p) {
		if (chessBoard[p.file][p.rank] != null && chessBoard[p.file][p.rank].getTeam() == orientation ) {
			selected = chessBoard[p.file][p.rank];
			possibleMoves = selected.possibleMoves(this);
		}
	}
	
	/**
	 * Makes a move with the currently selected Piece after checking if the 
	 * target Position is a valid move.
	 * 
	 * @param tar The target position to move to
	 * @return true if the move is valid and made, false otherwise
	 */
	public boolean makeMove(Position tar) {
		
		for (int i = 0; i < possibleMoves.length; i++) {
			if (possibleMoves[i].equals(tar)) {
				chessBoard[tar.file][tar.rank] = selected;
				chessBoard[selected.getPos().file][selected.getPos().rank] = null;
				selected.setPos(tar);
				
				selected = null;
				possibleMoves = null;
				return true;
			}
		}
		
		return false;
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
