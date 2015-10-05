package presenter;

import algorithms.mazeGenerators.Maze2d;
import view.Adapter;


/**
 * The Class Maze2dAdapter.
 * Object adapter for maze2d
 */
public class Maze2dAdapter implements Adapter<int[][]> {
	
	
	/** The maze. */
	Maze2d maze;
	
	/**
	 * Instantiates a new maze2d drawable adapter.
	 *
	 * @param m the maze
	 */
	public Maze2dAdapter(Maze2d m) {

		this.maze = m;
	}
	
	/* (non-Javadoc)
	 * @see view.Adapter#getData()
	 */
	@Override
	public int[][] getData() {
		// TODO Auto-generated method stub
		return maze.getMyMaze();
	}

}
