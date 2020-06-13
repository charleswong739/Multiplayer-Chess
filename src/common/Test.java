package common;

import client.ClientBoard;

public class Test {

	public static void main(String[] args) {
		ClientBoard b = new ClientBoard(Team.WHITE);
		
		System.out.println(b.getWhiteKing().inCheck(b));
	}

}
