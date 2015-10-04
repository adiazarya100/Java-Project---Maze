package presenter;

import algorithms.mazeGenerators.Maze2d;
import view.Adapter;

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
	
	@Override
	public int[][] getData() {
		// TODO Auto-generated method stub
		return maze.getMyMaze();
	}

}
