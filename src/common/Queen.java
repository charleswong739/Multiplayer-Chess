package common;

import java.util.ArrayList;

import client.ClientBoard;

/**
 * 
 * @author Charles Wong
 *
 */
public class Queen extends Piece {

	public Queen(Team t, Position pos) {
		super("queen", 9, t, pos);
	}

	// this method is literally the bishop and rook methods combined
	@Override
	public Position[] possibleMoves(ClientBoard board) {
		ArrayList <Position> list = new ArrayList<Position>();
		Piece [][] chessBoard = board.getBoard();
		
		Position target = new Position(position.file, position.rank+1);
		while (target.rank <= 7) { // up
			
			if (chessBoard[target.file][target.rank] == null) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
			} else if (chessBoard[target.file][target.rank].team == team)
				break;
			else if (chessBoard[target.file][target.rank].team != team) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
				break;
			}
			
			target = new Position(position.file, target.rank+1);
		}
		
		// Reset
		target = new Position(position.file, position.rank-1);
		
		while (target.rank >= 0) { // down
			
			if (chessBoard[target.file][target.rank] == null) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
			} else if (chessBoard[target.file][target.rank].team == team)
				break;
			else if (chessBoard[target.file][target.rank].team != team) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
				break;
			}
			
			target = new Position(position.file, target.rank-1);
		}

		// Reset
		target = new Position(position.file-1, position.rank);

		while (target.file >= 0) { // left

			if (chessBoard[target.file][target.rank] == null) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
			} else if (chessBoard[target.file][target.rank].team == team)
				break;
			else if (chessBoard[target.file][target.rank].team != team) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
				break;
			}
			
			target = new Position(target.file-1, position.rank);
		}

		// Reset
		target = new Position(position.file+1, position.rank);

		while (target.file <= 7) { // right

			if (chessBoard[target.file][target.rank] == null) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
			} else if (chessBoard[target.file][target.rank].team == team)
				break;
			else if (chessBoard[target.file][target.rank].team != team) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
				break;
			}
			
			target = new Position(target.file+1, position.rank);
		}
		
		target = new Position(position.file+1, position.rank+1);
		while (target.rank <= 7 && target.file <= 7) { // up right
			if (chessBoard[target.file][target.rank] == null) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
			} else if (chessBoard[target.file][target.rank].team == team)
				break;
			else if (chessBoard[target.file][target.rank].team != team) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
				break;
			}
			
			target = new Position(target.file+1, target.rank+1);
		}
		
		// Reset
		target = new Position(position.file+1, position.rank-1);
		
		while (target.rank >= 0 && target.file <= 7) { // down right
			
			if (chessBoard[target.file][target.rank] == null) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
			} else if (chessBoard[target.file][target.rank].team == team)
				break;
			else if (chessBoard[target.file][target.rank].team != team) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
				break;
			}
			
			target = new Position(target.file+1, target.rank-1);
		}

		// Reset
		target = new Position(position.file-1, position.rank+1);

		while (target.file >= 0 && target.rank <= 7) { // up left

			if (chessBoard[target.file][target.rank] == null) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
			} else if (chessBoard[target.file][target.rank].team == team)
				break;
			else if (chessBoard[target.file][target.rank].team != team) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
				break;
			}

			target = new Position(target.file-1, target.rank+1);
		}

		// Reset
		target = new Position(position.file-1, position.rank-1);

		while (target.file >= 0 && target.rank >= 0) { // down left
			
			if (chessBoard[target.file][target.rank] == null) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
			} else if (chessBoard[target.file][target.rank].team == team)
				break;
			else if (chessBoard[target.file][target.rank].team != team) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
				break;
			}
			
			target = new Position(target.file-1, target.rank-1);
		}
		
		return list.toArray(new Position[list.size()]);
	}

	public String toString() {
		return "Queen";
	}
}
