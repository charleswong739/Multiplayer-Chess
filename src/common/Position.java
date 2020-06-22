/**
 * Position
 * Version 1.0
 * @author Charles Wong
 * 2020-06-21
 * Utility class for wrapping a position on the chess board.
 */

//package statement
package common;

public class Position {
	
	public int file; // column
	public int rank; // row

	public static final int A = 0;
	public static final int B = 1;
	public static final int C = 2;
	public static final int D = 3;
	public static final int E = 4;
	public static final int F = 5;
	public static final int G = 6;
	public static final int H = 7;

	//Constructor
	public Position(int f, int r) {
		file = f;
		rank = r;
	}
	
	/**
	 *toString
 	 *@param: null
 	 *@return: String of position
 	 *gets string
 	 */
	public String toString() {
		String s = "ABCDEFGH";
		return "(" + s.charAt(file) + ", " + (rank + 1) + ")";
	}
	
	/**
 	 * equals
 	 * @param: Object that is being compared
 	 * @return: boolean if they are the same
 	 * determines if object at position is same as parameter
 	 */
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Position))
			return false;
		if (o == this)
			return true;
		
		Position p = (Position) o;
		
		return p.rank == this.rank && p.file == this.file;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + file;
		result = prime * result + rank;
		return result;
	}
} // end of Position class
