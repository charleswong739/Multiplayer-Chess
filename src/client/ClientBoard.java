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
 * @author Charles Wong, Andy Li
 *
 */
public class ClientBoard extends Board {

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
			movespoint = ImageIO.read(new File("sprites/movespoint.png"));
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

	public void opponentMove(String s) {
		String command = s.substring(0, 4);
		
		if (command.equals("MOVE")) {
			chessBoard[s.charAt(9) - 48][s.charAt(11) - 48] = chessBoard[s.charAt(5) - 48][s.charAt(7) - 48];
			chessBoard[s.charAt(5) - 48][s.charAt(7) - 48] = null;
		} else if (command.equals("CAST")) {
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
		}
	}

	/**
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
		System.out.println(k.getPos());
		if (src.equals(new Position(4, 0)))
			System.out.println("Simulate " + src.toString() + " to " + tar.toString() + ": " + b);

		chessBoard[src.file][src.rank] = chessBoard[tar.file][tar.rank];
		chessBoard[tar.file][tar.rank] = temp;
		chessBoard[src.file][src.rank].setPos(src);

		return !b;
	}

	public Position getSelectedPosition() {
		if (selected != null)
			return selected.getPos();
		return null;
	}

	public Team getOrientation() {
		return orientation;
	}

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
		
		for (int file = 0; file < 7; file++) {
			for (int rank = 0; rank < 7; rank++) {
				if (chessBoard[file][rank] != null) { // If a piece is there
					if (chessBoard[file][rank].getTeam() == this.orientation) { // If that piece is on the same team as
																				// the current player
						Piece curPiece = chessBoard[file][rank];
						Position[] potentialMoves = curPiece.possibleMoves(this);
						if (potentialMoves != null) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

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
		for (int file = 0; file < 7; file++) {
			for (int rank = 0; rank < 7; rank++) {
				if (chessBoard[file][rank] != null) { // If a piece is there
					if (chessBoard[file][rank].getTeam() == this.orientation) { // If that piece is on the same team as
																				// the current player
						Piece curPiece = chessBoard[file][rank];
						Position[] potentialMoves = curPiece.possibleMoves(this);
						if (potentialMoves != null) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
}