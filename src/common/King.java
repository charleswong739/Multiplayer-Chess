package common;

import java.util.ArrayList;

import client.ClientBoard;

/**
 * 
 * @author Charles Wong
 *
 */
public class King extends Piece {

	public King(Team t, Position pos) {
		super("king", 0, t, pos);
	}

	@Override
	public Position[] possibleMoves(ClientBoard board) {
		ArrayList<Position> list = new ArrayList<Position>();
		Piece[][] chessBoard = board.getBoard();
		
		Position target;
		if (position.rank < 7) {
			if (position.file < 7) {
				target = new Position(position.file, position.rank+1); // up
				if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}
				
				target = new Position(position.file+1, position.rank+1); // up right
				if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}
				
				target = new Position(position.file+1, position.rank); // right
				if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}
			}
			
			if (position.file > 0) {
				target = new Position(position.file-1, position.rank+1); // up left
				if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}
				
				target = new Position(position.file-1, position.rank); // left
				if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}
			}
		}
		
		if (position.rank > 0) {
			if (position.file < 7) {
				target = new Position(position.file+1, position.rank-1); // down right
				if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}
				
				target = new Position(position.file, position.rank-1); // down
				if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}
			}
			
			if (position.file > 0) {
				target = new Position(position.file-1, position.rank-1); // down left
				if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}
			}
		}
		
		
		return null;
	}

	/**
	 * Checks to see if this piece (the King) is in check
	 * 
	 * @param board the ClientBoard object the King is in
	 * @return true if the King is in check, false otherwise
	 */
	public boolean inCheck(Piece[][] chessBoard) {
		
		Team enemy;
		if (team == Team.WHITE) {
			enemy = Team.BLACK;

			// Check pawns (only pawns are dependent on team)
			if (position.rank < 6) { // black pawns cannot be behind rank 7 (index 6)
				if (position.file < 7) {
					if (isPiece(chessBoard, position.file+1, position.rank+1, enemy, Pawn.class))
						return true;
				}
				if (position.file > 0) {
					if (isPiece(chessBoard, position.file-1, position.rank+1, enemy, Pawn.class))
						return true;
				}
			}

		} else {
			enemy = Team.WHITE;

			// Check pawns
			if (position.rank > 1) { // white pawns cannot be behind rank 2 (index 1)
				if (position.file < 7) {
					if (isPiece(chessBoard, position.file+1, position.rank-1, enemy, Pawn.class))
						return true;
				}
				if (position.file > 0) {
					if (isPiece(chessBoard, position.file-1, position.rank-1, enemy, Pawn.class))
						return true;
				}
			}
		}

		// Check knights
		if (position.rank > 0) {
			if (position.file > 1) {
				if (isPiece(chessBoard, position.file-2, position.rank-1, enemy, Knight.class))
					return true;
			}
			if (position.file < 6) {
				if (isPiece(chessBoard, position.file+2, position.rank-1, enemy, Knight.class))
					return true;
			}

			if (position.rank > 1) { // if it's not greater than 0, it's not greater than 1
				if (position.file > 0) {
					if (isPiece(chessBoard, position.file-1, position.rank-2, enemy, Knight.class))
						return true;
				}
				if (position.file < 7) {
					if (isPiece(chessBoard, position.file+1, position.rank-2, enemy, Knight.class))
						return true;
				}
			}
		}

		if (position.rank < 7) {
			if (position.file > 1) {
				if (isPiece(chessBoard, position.file-2, position.rank+1, enemy, Knight.class))
					return true;
			}
			if (position.file < 6) {
				if (isPiece(chessBoard, position.file+2, position.rank+1, enemy, Knight.class))
					return true;
			}

			if (position.rank < 6) {
				if (position.file > 0) {
					if (isPiece(chessBoard, position.file-1, position.rank+2, enemy, Knight.class))
						return true;
				}
				if (position.file < 7) {
					if (isPiece(chessBoard, position.file+1, position.rank+2, enemy, Knight.class))
						return true;
				}
			}
		}

		// Check bishop/queen
		Position checker = new Position(position.file, position.rank);

		while (checker.file < 7 && checker.rank < 7) { // up right
			if (chessBoard[checker.file+1][checker.rank+1] != null && chessBoard[checker.file+1][checker.rank+1].team == team)
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
			if (chessBoard[checker.file+1][checker.rank-1] != null && chessBoard[checker.file+1][checker.rank-1].team == team)
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
			if (chessBoard[checker.file-1][checker.rank+1] != null && chessBoard[checker.file-1][checker.rank+1].team == team)
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
			if (chessBoard[checker.file-1][checker.rank-1] != null && chessBoard[checker.file-1][checker.rank-1].team == team)
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
			if (chessBoard[checker.file][checker.rank+1] != null && chessBoard[checker.file][checker.rank+1].team == team)
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
			if (chessBoard[checker.file][checker.rank-1] != null && chessBoard[checker.file][checker.rank-1].team == team)
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
			if (chessBoard[checker.file-1][checker.rank] != null && chessBoard[checker.file-1][checker.rank].team == team)
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
			if (chessBoard[checker.file+1][checker.rank] != null && chessBoard[checker.file+1][checker.rank].team == team)
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
		if (board[file][rank] == null)
			return false;
		return (board[file][rank].team == t && board[file][rank].getClass() == c);
	}
}
