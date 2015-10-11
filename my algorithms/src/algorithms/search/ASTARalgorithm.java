package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/** A* uses a best-first search and finds a least-cost path from a given initial node to one goal node (out of one or more possible goals).
 *  As A* traverses the graph, it builds up a tree of partial paths. The leaves of this tree (called the open set or fringe) are stored
 *   in a priority queue that orders the leaf nodes by a cost function, which combines a heuristic estimate of the cost to reach a goal
 *    and the distance traveled from the initial node.
 */

/**
 * The Class ASTARalgorithm.
 *
 * @param <T> the generic type
 */
public class ASTARalgorithm <T> extends CommonSearcher<T>{
	
	/** The hueristic. */
	Heuristic<T> hueristic;
	
	
	/**
	 * Instantiates a new ASTA ralgorithm.
	 *
	 * @param h the h
	 */
	public ASTARalgorithm(Heuristic<T> h) {
		super();
		hueristic = h;
	}


	/* (non-Javadoc)
	 * @see algorithms.search.CommonSearcher#search(algorithms.search.Searchable)
	 */
	@Override
	public Solution<T> search(Searchable<T> s) { 

		  System.out.println();
		  
			//if you want to print start position and goal position remove marks from the 2 line ahead:
		  /*	System.out.println("Start"+s.getStartState());
		  		System.out.println("Goal"+s.getGoalState());*/
		  
		  openList.add(s.getStartState()); //initialize priority queue
		  HashSet<State<T>> closedSet=new HashSet<State<T>>();
		  HashMap<State<T>, Double > CostForState =  new HashMap<State<T>, Double>(); //(state, cost)
		  CostForState.put(s.getStartState(), s.getStartState().getCost());
//		  System.out.println("start state: "+s.getStartState());
//		  System.out.println("goal state: "+s.getGoalState());
//		  System.out.println();
		  while(!openList.isEmpty())
		  {
		    State<T> state=popOpenList();  //dequeue from priority queue
		    closedSet.add(state);
			//System.out.println("state: "+state);
		    	if(state.equals(s.getGoalState()))
				{
		    //		System.out.println("Great!");
					return generatePathToGoal(state);
				}
			
		    ArrayList<State<T>> successors=new ArrayList<State<T>>();
		    successors = s.getAllPossibleStates(state);
		    //System.out.println();
			 for(State<T> ss:successors)
			 {
				 
			//HEURISTIC PRICE !!
				double newPathPrice = state.getCost() + ss.getCost() + hueristic.H(ss, s.getGoalState()); 
			//	System.out.println(newPathPrice);
				//System.out.println("Total:" +  newPathPrice);
				if(!closedSet.contains(ss) && !openList.contains(ss))
				{
			//		System.out.println(ss);
					
			//		System.out.println("blaa");
					ss.setCameFrom(state);
			//		System.out.println("new path price:"+newPathPrice);
					ss.setCost(newPathPrice);
			//		System.out.println(ss.getCost());
					openList.add(ss);
			//		System.out.println(ss);
			//		System.out.println(ss.getCost());
					CostForState.put(ss, ss.getCost());
			//		System.out.println(ss.getCost());

				}
				else
				{
					double oldPrice = CostForState.get(ss);
					if(newPathPrice < oldPrice)
					{
						ss.setCameFrom(state); 
						ss.setCost(newPathPrice);
						if (!openList.contains(ss))
						{
							openList.add(ss);
							CostForState.put(ss, ss.getCost());
						}
						else
						{
							openList.remove(ss);
							openList.add(ss);
							//CostForState.put(ss, ss.getCost());
						}
					}
				}
				//System.out.println("State"+state);
				//System.out.println("StatePrice"+state.getCost());
			    //System.out.println("camefrom: "+ss.getCameFrom());
				//System.out.println("ss"+ss);
				//System.out.println("ssPrice"+ss.getCost());
				
			 }
			// for(State<T> ss:successors)
			 //{
//				 System.out.println("successor: "+ss.getCurrentPosition());
//				 System.out.println("camefrom: "+ss.getCameFrom());
//				 System.out.println("price: "+ss.getCost());
			// }
		//	 System.out.println();
		}
		 
		return null;
	}

}



