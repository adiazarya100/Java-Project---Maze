package presenter;
import algorithms.mazeGenerators.Maze3d;
import view.Adapter;

/**
 * The Class Maze3dAdapter.
 * Object adapter for maze3d
 */
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
	
	/* (non-Javadoc)
	 * @see view.Adapter#getData()
	 */
	@Override
	public int[][][] getData() {
		// TODO Auto-generated method stub
		return maze.getMaze();
	}

}
