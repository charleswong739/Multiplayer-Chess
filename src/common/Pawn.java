/**
 * Pawn
 * Version 1.0
 * @author Charles Wong
 * 2020-06-21
 * Finds possible moves for a pawn
 */

//package statement
package common;

//import statements
import java.util.ArrayList;

import client.ClientBoard;

public class Pawn extends Piece {
	
	//Constructor
	public Pawn(Team t, Position p) {
		super ("pawn", 1, t, p);
	}

	@Override
	public Position[] possibleMoves(ClientBoard board) {
		ArrayList<Position> list = new ArrayList<Position>(3);
		Piece [][] chessBoard = board.getBoard();
		
		if (team == Team.WHITE) {
			if (position.rank < 7) {
				Position target = new Position(position.file, position.rank+1); // one step
				if (chessBoard[target.file][target.rank] == null) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}

				if (position.rank == 1) { // two step
					target = new Position(position.file, position.rank+2);
					if (chessBoard[target.file][target.rank] == null) {
						if (board.simulateMove(position, target)) {
							list.add(target);
						}
					}
				}

				if (position.file > 0) { // up left
					target = new Position(position.file-1, position.rank+1);
					if (chessBoard[target.file][target.rank] != null && chessBoard[target.file][target.rank].team == Team.BLACK) {
						if (board.simulateMove(position, target)) {
							list.add(target);
						}
					}
				}

				if (position.file < 7) { // up right
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
				Position target = new Position(position.file, position.rank-1); // one step
				if (chessBoard[target.file][target.rank] == null) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}
				
				if (position.rank == 6) { // two step
					target = new Position(position.file, position.rank-2);
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}

				if (position.file > 0) { // up right
					target = new Position(position.file-1, position.rank-1);
					if (chessBoard[target.file][target.rank] != null && chessBoard[target.file][target.rank].team == Team.WHITE) {
						if (board.simulateMove(position, target)) {
							list.add(target);
						}
					}
				}

				if (position.file < 7) { // up left
					target = new Position(position.file+1, position.rank-1);
					if (chessBoard[target.file][target.rank] != null && chessBoard[target.file][target.rank].team == Team.WHITE) {
						if (board.simulateMove(position, target)) {
							list.add(target);
						}
					}
				}
			}
		}

		return list.toArray(new Position[list.size()]);
	}

	/**
 	 *toString
	 *@param: null
	 *@return: String of piece name
	 *gets string
 	 */
	public String toString() {
		return "Pawn";
	}
} //end of Pawn class
