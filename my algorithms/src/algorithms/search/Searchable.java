package algorithms.search;

import java.util.ArrayList;

/**
 * The Interface Searchable.
 *
 * @param <T> the generic type
 */
public interface Searchable <T>{
	
	   /**
   	 * Gets the start state.
   	 *
   	 * @return the start state
   	 */
   	public State<T> getStartState();
	   
   	/**
   	 * Gets the goal state.
   	 *
   	 * @return the goal state
   	 */
   	public State<T> getGoalState();
	   
   	/**
   	 * Gets the all possible states.
   	 *
   	 * @param s the s
   	 * @return the all possible states
   	 */
   	public ArrayList<State<T>> getAllPossibleStates(State<T> s);

}
