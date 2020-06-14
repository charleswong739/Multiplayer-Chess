package common;

import java.util.ArrayList;

import client.ClientBoard;

/**
 * 
 * @author Charles Wong
 *
 */
public class Bishop extends Piece {

	public Bishop(Team t, Position pos) {
		super("bishop", 3, t, pos);
	}

	@Override
	public Position[] possibleMoves(ClientBoard board) {
		ArrayList <Position> list = new ArrayList<Position>();
		Piece [][] chessBoard = board.getBoard();
		
		Position target = new Position(position.file+1, position.rank+1);
		while (target.rank <= 7 && target.file <= 7) { // up right
			if (chessBoard[target.file][target.rank].team == team)
				break;
			
			if (chessBoard[target.file][target.rank].team != team) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
				break;
			}
			
			if (chessBoard[target.file][target.rank] == null) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
			}
			
			target = new Position(position.file+1, target.rank+1);
		}
		
		// Reset
		target = new Position(position.file+1, position.rank-1);
		
		while (target.rank >= 0) { // down right
			if (chessBoard[target.file][target.rank].team == team)
				break;
			
			if (chessBoard[target.file][target.rank].team != team) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
				break;
			}
			
			if (chessBoard[target.file][target.rank] == null) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
			}
			
			target = new Position(position.file+1, target.rank-1);
		}

		// Reset
		target = new Position(position.file-1, position.rank+1);

		while (target.file >= 0) { // left up
			if (chessBoard[target.file][target.rank].team == team)
				break;

			if (chessBoard[target.file][target.rank].team != team) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
				break;
			}

			if (chessBoard[target.file][target.rank] == null) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
			}

			target = new Position(target.file-1, position.rank+1);
		}

		// Reset
		target = new Position(position.file+1, position.rank-1);

		while (target.file <= 0) { // right
			if (chessBoard[target.file][target.rank].team == team)
				break;

			if (chessBoard[target.file][target.rank].team != team) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
				break;
			}
			
			if (chessBoard[target.file][target.rank] == null) {
				if (board.simulateMove(position, target)) {
					list.add(target);
				}
			}
			
			target = new Position(target.file+1, position.rank-1);
		}
		
		
		return list.toArray(new Position[list.size()]);
	}

}
