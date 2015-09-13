package algorithms.search;

import algorithms.mazeGenerators.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class EuclideanDistance.
 */
public class EuclideanDistance  implements Heuristic<Position>
{
	
	/* (non-Javadoc)
	 * @see algorithms.search.Heuristic#H(algorithms.search.State, algorithms.search.State)
	 */
	public double H(State<Position> neighbor, State<Position> goal)
	{
		double x;
		double y;
		double z;
		
		//euclidean distance 
		x=Math.pow(goal.getCurrentPosition().getX()-neighbor.getCurrentPosition().getX(), 2);
		y=Math.pow(goal.getCurrentPosition().getY()-neighbor.getCurrentPosition().getY(), 2);
		z=Math.pow(goal.getCurrentPosition().getZ()-neighbor.getCurrentPosition().getZ(), 2);
		
		return Math.sqrt(x +y +z);
	}	

}
