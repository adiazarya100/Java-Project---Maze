package algorithms.mazeGenerators;

/** All the common methods for any type of generator. */


public interface Maze3dGenerator{
	
	/**
	 * Generate.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @return the maze3d
	 */
	public Maze3d generate(int x, int y, int z);
		
	/**
	 * Measure algorithm time.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @return the string
	 */
	public String measureAlgorithmTime(int x, int y, int z);
}
