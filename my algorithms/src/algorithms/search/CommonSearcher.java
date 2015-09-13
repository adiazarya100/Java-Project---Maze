package algorithms.search;

import java.util.HashSet;
import java.util.PriorityQueue;


// TODO: Auto-generated Javadoc
/**
 * The Class CommonSearcher.
 *
 * @param <T> the generic type
 */
public abstract class CommonSearcher<T> implements Searcher<T> {
	
	
	 /** The closed set. */
 	HashSet<State<T>> closedSet=new HashSet<State<T>>();
	 
 	/** The open list. */
 	protected PriorityQueue<State<T>> openList = new PriorityQueue<State<T>>();
	 
 	/** The evaluated nodes. */
 	private int evaluatedNodes=0;
	 
	 /* (non-Javadoc)
 	 * @see algorithms.search.Searcher#search(algorithms.search.Searchable)
 	 */
 	@Override
	 public abstract Solution<T> search(Searchable<T> s);
	 
	 /* (non-Javadoc)
 	 * @see algorithms.search.Searcher#getNumberOfNodesEvaluated()
 	 */
 	@Override	 //return number of evaluated nodes (the total distance for each algorithm)
	 public int getNumberOfNodesEvaluated() {
			return evaluatedNodes;
		}
	 
	 
	 /**
 	 * Pop open list.
 	 *
 	 * @return the state
 	 */
 	protected State<T> popOpenList() {
		 evaluatedNodes++;
		 return openList.poll();
	 }
	 
	 /**
 	 * Generate path to goal.
 	 *
 	 * @param goalState the goal state
 	 * @return the solution
 	 */
 	protected Solution<T> generatePathToGoal(State<T> goalState)
	 {
	 	Solution<T> solution=new Solution<T>();		
	 	solution.setState(goalState);
	 	do
	 	{
	 		solution.setState(goalState.getCameFrom());
	 		goalState = goalState.getCameFrom();
	 	} while (goalState.getCameFrom() != null);
	 	return solution;
	 }	
	 
/*	 public PriorityQueue<State> getOpenList() {
		return openList;
	}

	public void setOpenList(PriorityQueue<State> openList) {
		this.openList = openList;
	}

	public int getEvaluatedNodes() {
		return evaluatedNodes;
	}

	public void setEvaluatedNodes(int evaluatedNodes) {
		this.evaluatedNodes = evaluatedNodes;
	}

	private int evaluatedNodes;

	public CommonSearcher() {
	  openList=new PriorityQueue<State>();
	  evaluatedNodes=0;
	 }

	protected State popOpenList() {
	  evaluatedNodes++;
	  return openList.poll();
	 }
*/
}
