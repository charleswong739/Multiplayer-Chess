/**
 * Bishop
 * Version 1.0
 * @author Charles Wong
 * 2020-06-21
 * Finds possible moves for a bishop
 */

//package statement
package common;

//import statements
import java.util.ArrayList;

import client.ClientBoard;

public class Bishop extends Piece {

	//Constructor
	public Bishop(Team t, Position pos) {
		super("bishop", 3, t, pos);
	}

	@Override
	public Position[] possibleMoves(ClientBoard board) {
		ArrayList <Position> list = new ArrayList<Position>();
		Piece [][] chessBoard = board.getBoard();
		
		Position target = new Position(position.file+1, position.rank+1);
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
	
	/**
	 *toString
	 *@param: null
	 *@return: String of piece name
	 *gets string
	 */
	public String toString() {
		return "Bishop";
	}
}//end of Bishop class
