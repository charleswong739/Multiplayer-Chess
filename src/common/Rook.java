package common;

import java.util.ArrayList;

import client.ClientBoard;

/**
 * 
 * @author Charles Wong
 *
 */
public class Rook extends Piece {
	
	private boolean canCastle;

	public Rook(Team t, Position p) {
		super ("rook", 5, t, p);
		
		canCastle = true;
	}
	
	public void setPos(Position p) {
		position = p;
		canCastle = false;
	}
	
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

}
