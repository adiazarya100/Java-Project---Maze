package GUI;

import org.eclipse.swt.events.PaintEvent;

import algorithms.search.Solution;

// TODO: Auto-generated Javadoc
/**
 * The Interface Board.
 */
public interface Board {

	/**
	 * Draw board.
	 *
	 * @param arg0 the arg0
	 */
	void drawBoard(PaintEvent arg0);
	
	/**
	 * Move floor up.
	 */
	public void moveFloorUp();
	
	/**
	 * Move floor down.
	 */
	public void moveFloorDown();
	
	/**
	 * Move up.
	 */
	public void moveUp();
	
	/**
	 * Move down.
	 */
	public void moveDown(); 
	
	/**
	 * Move left.
	 */
	public void moveLeft();
	
	/**
	 * Move right.
	 */
	public void moveRight();
	
	/**
	 * Sets the character position.
	 *
	 * @param row the row
	 * @param col the col
	 */
	public void setCharacterPosition(int row, int col);
	
	/**
	 * Checks for path up.
	 *
	 * @param characterRow the character row
	 * @param characterFloor the character floor
	 * @param characterCol the character col
	 * @return true, if successful
	 */
	boolean hasPathUp(int characterRow, int characterFloor ,int characterCol);
		
	/**
	 * Checks for path right.
	 *
	 * @param characterRow the character row
	 * @param characterFloor the character floor
	 * @param characterCol the character col
	 * @return true, if successful
	 */
	boolean hasPathRight(int characterRow, int characterFloor ,int characterCol);
	
	/**
	 * Checks for path down.
	 *
	 * @param characterRow the character row
	 * @param characterFloor the character floor
	 * @param characterCol the character col
	 * @return true, if successful
	 */
	boolean hasPathDown(int characterRow, int characterFloor ,int characterCol);
	
	/**
	 * Checks for path left.
	 *
	 * @param characterRow the character row
	 * @param characterFloor the character floor
	 * @param characterCol the character col
	 * @return true, if successful
	 */
	boolean hasPathLeft(int characterRow, int characterFloor ,int characterCol);
	 
	/**
	 * Checks for path floor up.
	 *
	 * @param characterRow the character row
	 * @param characterFloor the character floor
	 * @param characterCol the character col
	 * @return true, if successful
	 */
	boolean hasPathFloorUp(int characterRow, int characterFloor ,int characterCol);
	 
	/**
	 * Checks for path floor down.
	 *
	 * @param characterRow the character row
	 * @param characterFloor the character floor
	 * @param characterCol the character col
	 * @return true, if successful
	 */
	boolean hasPathFloorDown(int characterRow, int characterFloor ,int characterCol);
	
/*	void applyInputDirection(Direction direction);

	void destructBoard();
	
	void displayProblem(Object o);*/
	
	/**
 * Display solution.
 *
 * @param <T> the generic type
 * @param s the s
 */
<T> void displaySolution(Solution<T> s);
}
