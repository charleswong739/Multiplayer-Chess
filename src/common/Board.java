/**
 * Board
 * Version 1.0
 * @author Charles Wong
 * 2020-06-21
 * Abstract class holding 2-d array of pieces
 */

//package statement
package common;

public abstract class Board {
	//class variables
	protected Piece[][] chessBoard;  //holds pieces
	
	private King whiteKing;
	private King blackKing;
	
	//Constructor
	protected Board() {
		chessBoard = new Piece[8][8];
		
		//initialize white pawns
		for (int i = 0; i < 8; i++) {
			Position p = new Position(i, 1);
			chessBoard[p.file][p.rank] = new Pawn(Team.WHITE, p);
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
		for (int i = 0; i < 8; i++) {
			Position p = new Position(i, 6);
			chessBoard[p.file][p.rank] = new Pawn(Team.BLACK, p);
		}

		//white rooks
		chessBoard[0][7] = new Rook(Team.BLACK, new Position(Position.A, 7));
		chessBoard[7][7] = new Rook(Team.BLACK, new Position(Position.H, 7));

		//white knights
		chessBoard[1][7] = new Knight(Team.BLACK, new Position(Position.B, 7));
		chessBoard[6][7] = new Knight(Team.BLACK, new Position(Position.G, 7));

		//white bishops
		chessBoard[2][7] = new Bishop(Team.BLACK, new Position(Position.C, 7));
		chessBoard[5][7] = new Bishop(Team.BLACK, new Position(Position.F, 7));

		//white queen
		chessBoard[3][7] = new Queen(Team.BLACK, new Position(Position.D, 7));

		//white king
		blackKing = new King(Team.BLACK, new Position(Position.E, 7));
		chessBoard[4][7] = blackKing;
	}
	
	/**
	 *getBoard()
	 *@param: null
	 *@return: the 2-d array holding the pieces
	 */
	public Piece[][] getBoard() {
		return chessBoard;
	}
	
	/**
	 *getWhiteKing()
	 *@param: null
	 *@return: the white king
	 */
	public King getWhiteKing() {
		return whiteKing;
	}
	
	/**
	 *getWhiteKing()
	 *@param: null
	 *@return: the black king
	 */
	public King getBlackKing() {
		return blackKing;
	}
} // end of Board  Class
