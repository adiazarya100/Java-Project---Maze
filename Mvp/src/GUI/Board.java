package GUI;

import org.eclipse.swt.events.PaintEvent;

import algorithms.search.Solution;

public interface Board {

	void drawBoard(PaintEvent arg0);
	
	public void moveFloorUp();
	
	public void moveFloorDown();
	
	public void moveUp();
	
	public void moveDown(); 
	
	public void moveLeft();
	
	public void moveRight();
	
	public void setCharacterPosition(int row, int col);
	
	boolean hasPathUp(int characterRow, int characterFloor ,int characterCol);
		
	boolean hasPathRight(int characterRow, int characterFloor ,int characterCol);
	
	boolean hasPathDown(int characterRow, int characterFloor ,int characterCol);
	
	boolean hasPathLeft(int characterRow, int characterFloor ,int characterCol);
	 
	boolean hasPathFloorUp(int characterRow, int characterFloor ,int characterCol);
	 
	boolean hasPathFloorDown(int characterRow, int characterFloor ,int characterCol);
	
/*	void applyInputDirection(Direction direction);

	void destructBoard();
	
	void displayProblem(Object o);*/
	
	<T> void displaySolution(Solution<T> s);
}
