package common;

/**
 * 
 * @author Charles Wong
 *
 */

public abstract class Board {

	protected Piece[][] chessBoard;
	
	protected Board() {
		//initialize white pawns
		for (int i = 0; i < 7; i++) {
			Position p = new Position(i, 2);
			chessBoard[p.rank][p.file] = new Pawn(Team.WHITE, p);
		}
		
		//white rooks
		chessBoard[0][0] = new Rook(Team.WHITE, new Position(Position.A, 1));
		chessBoard[7][0] = new Rook(Team.WHITE, new Position(Position.H, 1));
		
		//white knights
		chessBoard[1][0] = new Knight(Team.WHITE, new Position(Position.B, 1));
		chessBoard[6][0] = new Knight(Team.WHITE, new Position(Position.G, 1));
		
		//white bishops
		chessBoard[2][0] = new Bishop(Team.WHITE, new Position(Position.C, 1));
		chessBoard[5][0] = new Bishop(Team.WHITE, new Position(Position.F, 1));
		
		//white queen
		chessBoard[3][0] = new Queen(Team.WHITE, new Position(Position.D, 1));
		
		//white king
		chessBoard[4][0] = new King(Team.WHITE, new Position(Position.E, 1));
		
		//initialize black pawns
		for (int i = 0; i < 7; i++) {
			Position p = new Position(i, 7);
			chessBoard[p.rank][p.file] = new Pawn(Team.BLACK, p);
		}

		//white rooks
		chessBoard[0][7] = new Rook(Team.WHITE, new Position(Position.A, 8));
		chessBoard[7][7] = new Rook(Team.WHITE, new Position(Position.H, 8));

		//white knights
		chessBoard[1][7] = new Knight(Team.WHITE, new Position(Position.B, 8));
		chessBoard[6][7] = new Knight(Team.WHITE, new Position(Position.G, 8));

		//white bishops
		chessBoard[2][7] = new Bishop(Team.WHITE, new Position(Position.C, 8));
		chessBoard[5][7] = new Bishop(Team.WHITE, new Position(Position.F, 8));

		//white queen
		chessBoard[3][7] = new Queen(Team.WHITE, new Position(Position.D, 8));

		//white king
		chessBoard[4][7] = new King(Team.WHITE, new Position(Position.E, 8));
	}

	public Piece[][] getBoard() {
		return chessBoard;
	}
}
