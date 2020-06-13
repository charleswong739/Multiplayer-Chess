package common;

import client.ClientBoard;

public class King extends Piece {

	public King(Team t, Position pos) {
		super("king", 0, t, pos);
	}

	@Override
	public Position[] possibleMoves(ClientBoard board) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Checks to see if this piece (the King) is in check
	 * 
	 * @param board the ClientBoard object the King is in
	 * @return true if the King is in check, false otherwise
	 */
	public boolean inCheck(ClientBoard board) {
		
		Piece[][] chessBoard = board.getBoard();

		Team enemy;
		if (team == Team.WHITE) {
			enemy = Team.BLACK;

			// Check pawns (only pawns are dependent on team)
			if (position.rank < 6) { // black pawns cannot be behind rank 7 (index 6)
				if (position.file < 7) {
					return isPiece(chessBoard, position.file+1, position.rank+1, enemy, Pawn.class);
				}
				if (position.file > 0) {
					return isPiece(chessBoard, position.file-1, position.rank+1, enemy, Pawn.class);
				}
			}

		} else {
			enemy = Team.WHITE;

			// Check pawns
			if (position.rank > 1) { // white pawns cannot be behind rank 2 (index 1)
				if (position.file < 7) {
					return isPiece(chessBoard, position.file+1, position.rank-1, enemy, Pawn.class);
				}
				if (position.file > 0) {
					return isPiece(chessBoard, position.file-1, position.rank-1, enemy, Pawn.class);
				}
			}
		}

		// Check knights
		if (position.rank > 0) {
			if (position.file > 1) {
				return isPiece(chessBoard, position.file-2, position.rank-1, enemy, Knight.class);
			}
			if (position.file < 6) {
				return isPiece(chessBoard, position.file+2, position.rank-1, enemy, Knight.class);
			}

			if (position.rank > 1) { // if it's not greater than 0, it's not greater than 1
				if (position.file > 0) {
					return isPiece(chessBoard, position.file-1, position.rank-2, enemy, Knight.class);
				}
				if (position.file < 7) {
					return isPiece(chessBoard, position.file+1, position.rank-2, enemy, Knight.class);
				}
			}
		}

		if (position.rank < 7) {
			if (position.file > 1) {
				return isPiece(chessBoard, position.file-2, position.rank+1, enemy, Knight.class);
			}
			if (position.file < 6) {
				return isPiece(chessBoard, position.file+2, position.file-1, enemy, Knight.class);
			}

			if (position.rank < 6) {
				if (position.file > 0) {
					return isPiece(chessBoard, position.file-1, position.rank+2, enemy, Knight.class);
				}
				if (position.file < 7) {
					return isPiece(chessBoard, position.file+1, position.rank+2, enemy, Knight.class);
				}
			}
		}

		// Check bishop/queen
		Position checker = new Position(position.file, position.rank);

		while (checker.file < 7 && checker.rank < 7) { // up right
			if (chessBoard[checker.file+1][checker.rank+1].team == team)
				break;
			if (isPiece(chessBoard, checker.file+1, checker.rank+1, enemy, Bishop.class) 
					|| isPiece(chessBoard, checker.file+1, checker.rank+1, enemy, Queen.class))
				return true;
			else {
				checker.file++;
				checker.rank++;
			}
		}

			// Reset checker
		checker.file = position.file;
		checker.rank = position.rank;

		while (checker.file < 7 && checker.rank > 0) { // down right
			if (chessBoard[checker.file+1][checker.rank-1].team == team)
				break;
			if (isPiece(chessBoard, checker.file+1, checker.rank-1, enemy, Bishop.class) 
					|| isPiece(chessBoard, checker.file+1, checker.rank-1, enemy, Queen.class))
				return true;
			else {
				checker.file++;
				checker.rank--;
			}
		}

			// Reset checker
		checker.file = position.file;
		checker.rank = position.rank;

		while (checker.file > 0 && checker.rank < 7) { // up left
			if (chessBoard[checker.file-1][checker.rank+1].team == team)
				break;
			if (isPiece(chessBoard, checker.file-1, checker.rank+1, enemy, Bishop.class) 
					|| isPiece(chessBoard, checker.file-1, checker.rank+1, enemy, Queen.class))
				return true;
			else {
				checker.file--;
				checker.rank++;
			}
		}
		
			// Reset checker
		checker.file = position.file;
		checker.rank = position.rank;

		while (checker.file > 0 && checker.rank > 0) { // down left
			if (chessBoard[checker.file-1][checker.rank-1].team == team)
				break;
			if (isPiece(chessBoard, checker.file-1, checker.rank-1, enemy, Bishop.class) 
					|| isPiece(chessBoard, checker.file-1, checker.rank-1, enemy, Queen.class))
				return true;
			else {
				checker.file--;
				checker.rank--;
			}
		}
		
		//Check rook/queen
			//Reset checker
		checker.file = position.file;
		checker.rank = position.rank;
		
		while (checker.rank < 7) { // up
			if (chessBoard[checker.file][checker.rank+1].team == team)
				break;
			if (isPiece(chessBoard, checker.file, checker.rank+1, enemy, Rook.class) 
					|| isPiece(chessBoard, checker.file, checker.rank+1, enemy, Queen.class))
				return true;
			else
				checker.rank++;
		}

			//Reset checker
		checker.rank = position.rank;
		
		while (checker.rank > 0) { // down
			if (chessBoard[checker.file][checker.rank-1].team == team)
				break;
			if (isPiece(chessBoard, checker.file, checker.rank-1, enemy, Rook.class) 
					|| isPiece(chessBoard, checker.file, checker.rank-1, enemy, Queen.class))
				return true;
			else
				checker.rank--;
		}

			//Reset checker
		checker.rank = position.rank;

		while (checker.file > 0) { // left
			if (chessBoard[checker.file-1][checker.rank].team == team)
				break;
			if (isPiece(chessBoard, checker.file-1, checker.rank, enemy, Rook.class) 
					|| isPiece(chessBoard, checker.file-1, checker.rank, enemy, Queen.class))
				return true;
			else
				checker.file--;
		}
		
			//Reset checker
		checker.file = position.file;

		while (checker.rank < 7) { // right
			if (chessBoard[checker.file+1][checker.rank].team == team)
				break;
			if (isPiece(chessBoard, checker.file+1, checker.rank, enemy, Rook.class) 
					|| isPiece(chessBoard, checker.file+1, checker.rank, enemy, Queen.class))
				return true;
			else
				checker.file++;
		}
		
		
		return false;
	}
	
	private boolean isPiece (Piece[][] board, int file, int rank, Team t, Class<?> c) {
		return (board[file][rank].team == t && board[file][rank].getClass() == c);
	}
}
