package common;

import client.ClientBoard;

public class Test {

	public static void main(String[] args) {
		ClientBoard b = new ClientBoard(Team.WHITE);
		
		b.getBoard()[3][2] = new Rook(Team.BLACK, new Position(Position.D, 2));
		
		Position [] p = b.getBoard()[2][1].possibleMoves(b);
		
		for (int i = 0; i < p.length; i++)
			System.out.println(p[i]);
	}

}
