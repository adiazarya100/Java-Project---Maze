package algorithms.search;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

/** Best-First Search is a search algorithm which explores a graph by expanding the
 *  most promising node chosen according to a specified rule.
 * */
/**
 * The Class BFSalgorithm.
 *
 * @param <T> the generic type
 */
public class BFSalgorithm<T> extends CommonSearcher<T>{

	/* (non-Javadoc)
	 * @see algorithms.search.CommonSearcher#search(algorithms.search.Searchable)
	 */
	@Override
	public Solution<T> search(Searchable<T> s) { 
		System.out.println();
		System.out.println();
		System.out.println("Start"+s.getStartState());
		System.out.println("Goal"+s.getGoalState());
		  openList.add(s.getStartState()); //initialize priority queue
		  HashSet<State<T>> closedSet=new HashSet<State<T>>();
		  HashMap<State<T>, Double > CostForState =  new HashMap<State<T>, Double>(); //(state, cost)
		  CostForState.put(s.getStartState(), s.getStartState().getCost());
		 // System.out.println();
		 // System.out.println();
		 // System.out.println("start state: "+s.getStartState());
		 // System.out.println("goal state: "+s.getGoalState());
		 //System.out.println();
		  while(!openList.isEmpty())
		  {
		    State<T> state=popOpenList();  //dequeue from priority queue
		    closedSet.add(state);
			//System.out.println("state: "+state);
		    	if(state.equals(s.getGoalState()))
				{
		    //		System.out.println("Great!");
		    		
					return generatePathToGoal(state); //backTrace function!
				}
			
		    ArrayList<State<T>> successors=new ArrayList<State<T>>();
		    successors = s.getAllPossibleStates(state);
			 for(State<T> ss:successors)
			 {
				 
			//	System.out.println();
				double newPathPrice = state.getCost() + ss.getCost();
				//System.out.println(newPathPrice);
				//System.out.println("Total:" +  newPathPrice);
				if(!closedSet.contains(ss) && !openList.contains(ss))
				{
				//	System.out.println(ss);
					ss.setCameFrom(state);
				//	System.out.println("new path price: "+newPathPrice);
					ss.setCost(newPathPrice);
				//	System.out.println(ss.getCost());
					openList.add(ss);
					CostForState.put(ss, ss.getCost());
					

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
			// {
			//	 System.out.println("successor: "+ss.getCurrentPosition());
			//	 System.out.println("camefrom: "+ss.getCameFrom());
			//	 System.out.println("price: "+ss.getCost());
			// }
		//	 System.out.println();
		}
		 
		return null;
	}





}


