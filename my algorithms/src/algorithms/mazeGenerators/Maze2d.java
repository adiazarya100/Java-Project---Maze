package algorithms.mazeGenerators;


/**
 * The Class Maze2d.
 */
public class Maze2d {

	/** The my maze. */
	int[][] myMaze;
	
	/** The length. */
	int length;
	
	/** The width. */
	int width;
	
	/**
	 * Instantiates a new maze2d.
	 *
	 * @param myMaze the my maze
	 */
	public Maze2d(int[][] myMaze) {
		this.myMaze = myMaze;
		this.length = myMaze.length;
		this.width = myMaze[0].length;
	}
	
	/**
	 * Gets the my maze.
	 *
	 * @return the my maze
	 */
	public int[][] getMyMaze() {
		return myMaze;
	}
	
	/**
	 * Sets the my maze.
	 *
	 * @param myMaze the new my maze
	 */
	public void setMyMaze(int[][] myMaze) {
		this.myMaze = myMaze;
	}
	
	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * Sets the length.
	 *
	 * @param length the new length
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
}
