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
		
		if (team == Team.WHITE) {
			Position pleft = new Position(position.file-1, position.rank+1);
			if (pleft.rank > 0) {
				if (board.getBoard()[pleft.rank][pleft.file].getTeam() == Team.BLACK) {
					list.add(pleft);
				}
			}
		}
		return null;
	}

}
