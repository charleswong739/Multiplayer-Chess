/**
 * Knight
 * Version 1.0
 * @author Charles Wong
 * 2020-06-21
 * Finds possible moves for a knight
 */

//package statement
package common;

//import statements
import java.util.ArrayList;

import client.ClientBoard;

public class Knight extends Piece {
	
	//Constructor
	public Knight(Team t, Position pos) {
		super("knight", 3, t, pos);
	}

	@Override
	public Position[] possibleMoves(ClientBoard board) {
		ArrayList <Position> list = new ArrayList <Position>();
		Piece [][] chessBoard = board.getBoard();
		
		Position target;
		if (position.rank < 7) {
			if (position.file < 6) { // top right horizontal
				target = new Position(position.file+2, position.rank+1);
				if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}
			}
			
			if (position.file > 1) { // top left horizontal
				target = new Position(position.file-2, position.rank+1);
				if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}
			}

			if (position.rank < 6) {
				if (position.file < 7) { // top right vertical
					target = new Position(position.file+1, position.rank+2);
					if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
						if (board.simulateMove(position, target)) {
							list.add(target);
						}
					}
				}

				if (position.file > 0) { // top left vertical
					target = new Position(position.file-1, position.rank+2);
					if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
						if (board.simulateMove(position, target)) {
							list.add(target);
						}
					}
				}
			}
		}

		if (position.rank > 0) {
			if (position.file < 6) { // bottom right horizontal
				target = new Position(position.file+2, position.rank-1);
				if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}
			}
			
			if (position.file > 1) { // bottom left horizontal
				target = new Position(position.file-2, position.rank-1);
				if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
					if (board.simulateMove(position, target)) {
						list.add(target);
					}
				}
			}

			if (position.rank > 1) {
				if (position.file < 7) { // bottom right vertical
					target = new Position(position.file+1, position.rank-2);
					if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
						if (board.simulateMove(position, target)) {
							list.add(target);
						}
					}
				}

				if (position.file > 0) { // top left vertical
					target = new Position(position.file-1, position.rank-2);
					if (chessBoard[target.file][target.rank] == null || chessBoard[target.file][target.rank].team != team) {
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
		return "Knight";
	}
} //end of Knight class
