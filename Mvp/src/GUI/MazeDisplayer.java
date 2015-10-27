package GUI;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import view.Adapter;

/**
 * The Class MazeDisplayer.
 */
public abstract class MazeDisplayer extends Canvas {
	
	/** The maze data. */
	int[][] mazeData;

	/**
	 * Instantiates a new maze displayer.
	 *
	 * @param parent the parent
	 * @param style the style
	 */
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Sets the maze data.
	 *
	 * @param adapter the new maze data
	 */
	public void setMazeData(Adapter<Maze3d> adapter){
		int startFloor=adapter.getData().getStartPosition().getY();
		this.mazeData= adapter.getData().getCrossSectionByY(startFloor);
	}
	
	
	/**
	 * Sets the character position.
	 *
	 * @param row the row
	 * @param col the col
	 */
	public abstract  void setCharacterPosition(int row,int col);

	/**
	 * Move up.
	 */
	public abstract void moveUp();

	/**
	 * Move down.
	 */
	public abstract  void moveDown();

	/**
	 * Move left.
	 */
	public abstract  void moveLeft();

	/**
	 * Move right.
	 */
	public  abstract void moveRight();

}
