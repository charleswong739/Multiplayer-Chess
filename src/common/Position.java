package common;

/**
 * Utility class for wrapping a position on the chess board. Allows for intuitive usage
 * mirroring real life board position namings while converting to the correct array index
 * internally.
 * <p>
 * Note: I really want this but we can change it if no one likes it.
 * 
 * @author Charles Wong
 *
 */

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


	public Position(int f, int r) {
		file = f;
		rank = r - 1;
	}
}
