package common;

/**
 * 
 * @author Charles Wong
 *
 */

public abstract class Board {

	protected Piece[][] chessBoard;
	
	private King whiteKing;
	private King blackKing;
	
	protected Board() {
		//initialize white pawns
		for (int i = 0; i < 7; i++) {
			Position p = new Position(i, 2);
			chessBoard[p.rank][p.file] = new Pawn(Team.WHITE, p);
		}
		
		//white rooks
		chessBoard[0][0] = new Rook(Team.WHITE, new Position(Position.A, 0));
		chessBoard[7][0] = new Rook(Team.WHITE, new Position(Position.H, 0));
		
		//white knights
		chessBoard[1][0] = new Knight(Team.WHITE, new Position(Position.B, 0));
		chessBoard[6][0] = new Knight(Team.WHITE, new Position(Position.G, 0));
		
		//white bishops
		chessBoard[2][0] = new Bishop(Team.WHITE, new Position(Position.C, 0));
		chessBoard[5][0] = new Bishop(Team.WHITE, new Position(Position.F, 0));
		
		//white queen
		chessBoard[3][0] = new Queen(Team.WHITE, new Position(Position.D, 0));
		
		//white king
		whiteKing = new King(Team.WHITE, new Position(Position.E, 0));
		chessBoard[4][0] = whiteKing;
		
		//initialize black pawns
		for (int i = 0; i < 7; i++) {
			Position p = new Position(i, 7);
			chessBoard[p.rank][p.file] = new Pawn(Team.BLACK, p);
		}

		//white rooks
		chessBoard[0][7] = new Rook(Team.WHITE, new Position(Position.A, 7));
		chessBoard[7][7] = new Rook(Team.WHITE, new Position(Position.H, 7));

		//white knights
		chessBoard[1][7] = new Knight(Team.WHITE, new Position(Position.B, 7));
		chessBoard[6][7] = new Knight(Team.WHITE, new Position(Position.G, 7));

		//white bishops
		chessBoard[2][7] = new Bishop(Team.WHITE, new Position(Position.C, 7));
		chessBoard[5][7] = new Bishop(Team.WHITE, new Position(Position.F, 7));

		//white queen
		chessBoard[3][7] = new Queen(Team.WHITE, new Position(Position.D, 7));

		//white king
		blackKing = new King(Team.WHITE, new Position(Position.E, 7));
		chessBoard[4][7] = blackKing;
	}

	public Piece[][] getBoard() {
		return chessBoard;
	}
	
	public King getWhiteKing() {
		return whiteKing;
	}
	
	public King getBlackKing() {
		return blackKing;
	}
}
