/**
 * ClientBoard
 * Version 1.0
 * @author Charles Wong, Andy Li
 * Date: 06-21-2020
 * Description: Creates and draws the board that's displayed for each client.
 */

//package statement
package client;

//import statements
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import common.Board;
import common.GameState;
import common.King;
import common.Piece;
import common.Position;
import common.Team;

public class ClientBoard extends Board {
	
	//class variables
	private BufferedImage bg;
	private BufferedImage selectbg;
	private BufferedImage movespoint;
	private BufferedImage[] endCards;

	private Team orientation;

	private Piece selected;
	private Position[] possibleMoves;
	
	private int gameState;
	
	//Constructor
	//Reads and stores images for endCards and the lettered board.
	public ClientBoard(Team o) {
		super();
		orientation = o;
		gameState = GameState.ACTIVE;
		endCards = new BufferedImage[5];

		try {
			if (orientation == Team.WHITE)
				bg = ImageIO.read(new File("sprites/white_lettered_bg.png"));
			else
				bg = ImageIO.read(new File("sprites/black_lettered_bg.png"));
			
			selectbg = ImageIO.read(new File("sprites/selectbg.png"));
			movespoint = ImageIO.read(new File("sprites/movespoint.png"));
			
			//adds all the possible end result pictures
			if (orientation == Team.WHITE) {
				endCards[0] = ImageIO.read(new File("sprites/endcards/checkmate_white_win.png"));
				endCards[1] = ImageIO.read(new File("sprites/endcards/checkmate_white_loss.png"));
				endCards[2] = ImageIO.read(new File("sprites/endcards/stalemate.png"));
				endCards[3] = ImageIO.read(new File("sprites/endcards/resign_white_win.png"));
				endCards[4] = ImageIO.read(new File("sprites/endcards/resign_white_loss.png"));
			} else {
				endCards[0] = ImageIO.read(new File("sprites/endcards/checkmate_black_win.png"));
				endCards[1] = ImageIO.read(new File("sprites/endcards/checkmate_black_loss.png"));
				endCards[2] = ImageIO.read(new File("sprites/endcards/stalemate.png"));
				endCards[3] = ImageIO.read(new File("sprites/endcards/resign_black_win.png"));
				endCards[4] = ImageIO.read(new File("sprites/endcards/resign_black_loss.png"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
 	 * draw method
 	 * @param: Graphics g, x and y coordinaes
 	 * @return: null
 	 * draws the pieces on the board
 	 */
	public void draw(Graphics g, int x, int y) {
		g.drawImage(bg, x, y, null);

		if (selected != null) {
			if (orientation == Team.WHITE) {
				g.drawImage(selectbg, selected.getPos().file * 60 + 40, 460 - selected.getPos().rank * 60, null);
			} else {
				g.drawImage(selectbg, 460 - selected.getPos().file * 60, selected.getPos().rank * 60 + 40, null);
			}
		}

		int pieceX;
		int pieceY;
		
		//draws pieces
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (orientation == Team.WHITE) {
					pieceX = i * 60 + 40;
					pieceY = 460 - j * 60;
				} else {
					pieceX = 460 - i * 60;
					pieceY = j * 60 + 40;
				}

				if (chessBoard[i][j] != null) {
					chessBoard[i][j].draw(g, pieceX, pieceY);
				}
			}
		}
		
		//draw possible moves if a piece is selected
		if (possibleMoves != null) {
			for (int i = 0; i < possibleMoves.length; i++) {
				if (orientation == Team.WHITE) {
					g.drawImage(movespoint, possibleMoves[i].file * 60 + 40, 460 - possibleMoves[i].rank * 60, null);
				} else {
					g.drawImage(movespoint, 460 - possibleMoves[i].file * 60, possibleMoves[i].rank * 60 + 40, null);
				}
			}
		}
		
		//if game is over draw an  endcard.
		if (gameState != GameState.ACTIVE){
			g.drawImage(endCards[gameState],159, 212, null);
		}
	}

	/**
	 * selectPiece
	 * Makes the designated Position selected if the piece in the position is the
	 * same team as the current board orientation
	 * 
	 * @param p The Position to be designated as selected
	 */
	public void selectPiece(Position p) {
		if (chessBoard[p.file][p.rank] != null && chessBoard[p.file][p.rank].getTeam() == orientation) {
			selected = chessBoard[p.file][p.rank];
			possibleMoves = selected.possibleMoves(this);
		}
	}

	/**
	 * makeMove
	 * Makes a move with the currently selected Piece after checking if the target
	 * Position is a valid move.
	 * 
	 * @param tar The target position to move to
	 * @return true if the move is valid and made, false otherwise
	 */
	public boolean makeMove(Position tar, NetClient nc) {

		if (selected != null && possibleMoves != null) {
			for (int i = 0; i < possibleMoves.length; i++) {
				if (possibleMoves[i].equals(tar)) {
					if ((selected instanceof King) && (Math.abs(tar.file - selected.getPos().file) > 1)) {
						// The king must be castling
						if (tar.file > selected.getPos().file) {
							// Right castle move rook
							chessBoard[5][tar.rank] = chessBoard[7][tar.rank];
							chessBoard[7][tar.rank] = null;
							chessBoard[5][tar.rank].setPos(new Position(5, tar.rank));
							
							chessBoard[tar.file][tar.rank] = selected;
							chessBoard[selected.getPos().file][selected.getPos().rank] = null;
							selected.setPos(tar);

							selected = null;
							possibleMoves = null;
							
							nc.write("CAST K");
							nc.recieve();
							
							return true;
						} else {
							// Left castle move rook
							chessBoard[3][tar.rank] = chessBoard[0][tar.rank];
							chessBoard[0][tar.rank] = null;
							chessBoard[3][tar.rank].setPos(new Position(3, tar.rank));
							
							chessBoard[tar.file][tar.rank] = selected;
							chessBoard[selected.getPos().file][selected.getPos().rank] = null;
							selected.setPos(tar);

							selected = null;
							possibleMoves = null;
							
							nc.write("CAST Q");
							nc.recieve();
							
							return true;
						}
					}
					
					nc.write("MOVE " + selected.getPos().file + " " + selected.getPos().rank + " " + tar.file + " " + tar.rank);
					nc.recieve();
					
					chessBoard[tar.file][tar.rank] = selected;
					chessBoard[selected.getPos().file][selected.getPos().rank] = null;
					selected.setPos(tar);

					selected = null;
					possibleMoves = null;
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * opponentMove
	 * @param: string of message
 	 * @return: null
 	 * given a command from the server, makes the opponent's move
 	 */
	public void opponentMove(String s) {
		String command = s.substring(0, 4);
		
		if (command.equals("MOVE")) {
			chessBoard[s.charAt(9) - 48][s.charAt(11) - 48] = chessBoard[s.charAt(5) - 48][s.charAt(7) - 48];
			chessBoard[s.charAt(5) - 48][s.charAt(7) - 48] = null;
		} else if (command.equals("CAST")) { //castling
			if (s.charAt(5) == 'K') {
				if (orientation == Team.WHITE) {
					// Move king
					chessBoard[6][7] = chessBoard[4][7];
					chessBoard[4][7] = null;
					//Move rook
					chessBoard[5][7] = chessBoard[7][7];
					chessBoard[7][7] = null;
				} else {
					chessBoard[6][0] = chessBoard[4][0];
					chessBoard[4][0] = null;
					//Move rook
					chessBoard[5][0] = chessBoard[7][0];
					chessBoard[7][0] = null;
				}
			} else {
				if (orientation == Team.WHITE) {
					// Move king
					chessBoard[2][7] = chessBoard[4][7];
					chessBoard[4][7] = null;
					//Move rook
					chessBoard[3][7] = chessBoard[0][7];
					chessBoard[0][7] = null;
				} else {
					chessBoard[2][0] = chessBoard[4][0];
					chessBoard[4][0] = null;
					//Move rook
					chessBoard[3][0] = chessBoard[0][0];
					chessBoard[0][0] = null;
				}
			}
		} else if (command.equals("CHEK")) { //check end results
			gameState = GameState.CHECKMATE_VICTORY;
		} else if (command.equals("STAL")) {
			gameState = GameState.STALEMATE;
		} else if (command.equals("RESN")) {
			gameState = GameState.RESIGN_VICTORY;
		}
	}

	/**
	 * simulateMove
	 * Simulates the movement of a piece and checks if the move places the King in
	 * check. This method assumes that the target position is a valid move location
	 * for the piece in the source position. However, if the source position is
	 * empty, then an IllegalArgumentException is thrown
	 * 
	 * @param src The original position of a piece
	 * @param tar The target position a piece
	 * @return true if the move is valid (does not place the King in check), false
	 *         otherwise
	 */
	public boolean simulateMove(Position src, Position tar) {
		if (chessBoard[src.file][src.rank] == null) {
			throw new IllegalArgumentException("Source position is empty");
		}

		King k;
		if (chessBoard[src.file][src.rank].getTeam() == Team.WHITE)
			k = getWhiteKing();
		else
			k = getBlackKing();

		Piece temp = chessBoard[tar.file][tar.rank];
		chessBoard[src.file][src.rank].setPos(tar);
		chessBoard[tar.file][tar.rank] = chessBoard[src.file][src.rank];
		chessBoard[src.file][src.rank] = null;

		boolean b = k.inCheck(chessBoard);

		chessBoard[src.file][src.rank] = chessBoard[tar.file][tar.rank];
		chessBoard[tar.file][tar.rank] = temp;
		chessBoard[src.file][src.rank].setPos(src);

		return !b;
	}
	
	/*
 	 * getSelectedPosition
  	 * @param: null
  	 * @return: the Position
	 * Description: gets the position.
       	 */
	public Position getSelectedPosition() {
		if (selected != null) {
			return selected.getPos();
		}
		return null;
	}
	
	/*
	 * getOrientation
  	 * @param: null
  	 * @return: Team
	 * Gets the orientation
 	 */
	public Team getOrientation() {
		return orientation;
	}
	
	/*
 	 * checkmate
  	 * @param: null
 	 * @return: boolean if in checkmate or not
	 * determines if in checkmate or not
 	 */
	public boolean checkmate() {
		King k;
		if (this.orientation == Team.WHITE) {
			k = getWhiteKing();
		} else {
			k = getBlackKing();
		}
		if (!k.inCheck(chessBoard)) {
			return false;
		}

		for (int file = 0; file < 8; file++) {
			for (int rank = 0; rank < 8; rank++) {
				if (chessBoard[file][rank] != null) { // If a piece is there
					if (chessBoard[file][rank].getTeam() == this.orientation) { // If that piece is on the same team as
																				// the current player
						if (chessBoard[file][rank].possibleMoves(this).length > 0)
							return false;
					}
				}
			}
		}

		return true;
	}
	
	/*
 	 * stalemate()
 	 * @param: null
 	 * @return: boolean of if in stalemate or not
	 * determines if in stalemate or not
 	 */
	public boolean stalemate() {
		King k;
		if (this.orientation == Team.WHITE) {
			k = getWhiteKing();
		} else {
			k = getBlackKing();
		}
		if (k.inCheck(chessBoard)) {
			return false;
		}
		for (int file = 0; file < 8; file++) {
			for (int rank = 0; rank < 8; rank++) {
				if (chessBoard[file][rank] != null) { // If a piece is there
					if (chessBoard[file][rank].getTeam() == this.orientation) { // If that piece is on the same team as
																				// the current player
						if (chessBoard[file][rank].possibleMoves(this).length > 0)
							return false;
					}
				}
			}
		}
		return true;
	}
	
	/*
 	 * getState
  	 * @param:null
  	 * @return: integer of current state
	 * returns the current state.
  	 */
	public int getState() {
		return gameState;
	}
	
	/*
 	 * setState
 	 * @param: state that is to be set
 	 * @return: null
	 * sets the game state
 	 */
	public void setState(int gs) {
		gameState = gs;
	}
} // end of ClientBoard class
