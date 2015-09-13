package algorithms.search;
import algorithms.mazeGenerators.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class ManhattanDistance.
 */
public class ManhattanDistance implements Heuristic<Position>
{
	
	/* (non-Javadoc)
	 * @see algorithms.search.Heuristic#H(algorithms.search.State, algorithms.search.State)
	 */
	public double H(State<Position> neighbor, State<Position> goal)
	{
		double x;
		double y;
		double z;
		
		//Manhattan Distance
		x=Math.abs(goal.getCurrentPosition().getX()-neighbor.getCurrentPosition().getX());
		y=Math.abs(goal.getCurrentPosition().getY()-neighbor.getCurrentPosition().getY());
		z=Math.abs(goal.getCurrentPosition().getZ()-neighbor.getCurrentPosition().getZ());
		
		return x +y +z;
	}
	
	
	
}
