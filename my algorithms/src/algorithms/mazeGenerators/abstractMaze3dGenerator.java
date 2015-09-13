package algorithms.mazeGenerators;

/** abstract class, every algorithm will implement the "generate" method like he needs to. */
public abstract class abstractMaze3dGenerator implements Maze3dGenerator {



	/* (non-Javadoc)
	 * @see algorithms.mazeGenerators.Maze3dGenerator#generate(int, int, int)
	 */
	@Override
	public abstract Maze3d generate (int x, int y, int z); //abstract method, every algorithm implement by his way
	
	
	/* (non-Javadoc)
	 * @see algorithms.mazeGenerators.Maze3dGenerator#measureAlgorithmTime(int, int, int)
	 */
	@Override
	public String measureAlgorithmTime(int x, int y, int z) {
   //measure Algorithm time
		long start = System.currentTimeMillis();
		generate(x,y,z);
		long end = System.currentTimeMillis();
		System.out.println();
		return "Total Time Is: " +Long.toString(end - start);
	}
}
