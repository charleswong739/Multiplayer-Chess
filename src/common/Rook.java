package common;

import client.ClientBoard;

/**
 * 
 * @author Charles Wong
 *
 */
public class Rook extends Piece {

	public Rook(Team t, Position p) {
		super ("rook", 5, t, p);
	}
	
	@Override
	public Position[] possibleMoves(ClientBoard board) {
		// TODO Auto-generated method stub
		return null;
	}

}
