package common;

import java.util.ArrayList;

import client.ClientBoard;

/**
 * 
 * @author Charles Wong
 *
 */
public class Pawn extends Piece {

	public Pawn(Team t, Position p) {
		super ("pawn", 1, t, p);
	}

	@Override
	public Position[] possibleMoves(ClientBoard board) {
		ArrayList<Position> list = new ArrayList<Position>(3);
		Piece [][] chessBoard = board.getBoard();
		
		if (team == Team.WHITE) {
			if (position.rank < 7) {
				Position target = new Position(position.file, position.rank+1);
				if (chessBoard[target.file][target.rank] == null) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}

				if (position.file > 0) {
					target = new Position(position.file-1, position.rank+1);
					if (chessBoard[target.file][target.rank] != null && chessBoard[target.file][target.rank].team == Team.BLACK) {
						if (board.simulateMove(position, target)) {
							list.add(target);
						}
					}
				}

				if (position.file < 7) {
					target = new Position(position.file+1, position.rank+1);
					if (chessBoard[target.file][target.rank] != null && chessBoard[target.file][target.rank].team == Team.BLACK) {
						if (board.simulateMove(position, target)) {
							list.add(target);
						}
					}
				}
			}
		} else {
			if (position.rank > 0) {
				Position target = new Position(position.file, position.rank-1);
				if (chessBoard[target.file][target.rank] == null) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}

				if (position.file > 0) {
					target = new Position(position.file-1, position.rank-1);
					if (chessBoard[target.file][target.rank] != null && chessBoard[target.file][target.rank].team == Team.BLACK) {
						if (board.simulateMove(position, target)) {
							list.add(target);
						}
					}
				}

				if (position.file < 7) {
					target = new Position(position.file+1, position.rank-1);
					if (chessBoard[target.file][target.rank] != null && chessBoard[target.file][target.rank].team == Team.BLACK) {
						if (board.simulateMove(position, target)) {
							list.add(target);
						}
					}
				}
			}
		}

		return list.toArray(new Position[list.size()]);
	}

}
