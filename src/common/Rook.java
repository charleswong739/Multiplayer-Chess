/**
 * Rook
 * Version 1.0
 * @author Charles Wong
 * 2020-06-21
 * Finds possible moves for a rook
 */

//package statement
package common;

//import statements
import java.util.ArrayList;

import client.ClientBoard;

public class Rook extends Piece {
	//class variable (castling)
	private boolean canCastle;
	
	//Constructor
	public Rook(Team t, Position p) {
		super ("rook", 5, t, p);
		
		canCastle = true;
	}
	
	/**
	 *setPos
	 * @param: Position to be set 
	 * @return: null
	 * sets the new position after castling
	 */
	public void setPos(Position p) {
		position = p;
		canCastle = false;
	}
	
	/**
	 * getCastleStatus
	 * @param: null
	 * @return: boolean if can castle or not
	 * determines if it is possible to castle
	 */
	public boolean getCastleStatus() {
		return canCastle;
	}
	
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
		
		
		return list.toArray(new Position[list.size()]);
	}
	/**
         *toString
 	 *@param: null
 	 *@return: String of piece name
 	 *gets string
 	 */
	public String toString() {
		return "Rook";
	}
} //end of Rook Class
