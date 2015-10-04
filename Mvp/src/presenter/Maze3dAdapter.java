package presenter;
import algorithms.mazeGenerators.Maze3d;
import view.Adapter;

public class Maze3dAdapter implements Adapter<int[][][]>{
	
	/** The maze. */
	Maze3d maze;
	
	/**
	 * Instantiates a new maze2d drawable adapter.
	 *
	 * @param m the maze
	 */
	public Maze3dAdapter(Maze3d m) {

		this.maze = m;
	}
	
	@Override
	public int[][][] getData() {
		// TODO Auto-generated method stub
		return maze.getMaze();
	}

}
