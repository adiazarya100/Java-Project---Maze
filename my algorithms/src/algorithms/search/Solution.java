package algorithms.search;

import java.util.ArrayList;


/**
 * The Class Solution.
 *
 * @param <T> the generic type
 */
public class Solution<T> {

	/** The solution. */
	private ArrayList<State<T>> solution = new ArrayList<State<T>>(); 

	/**
	 * Gets the solution.
	 *
	 * @return the solution
	 */
	public ArrayList<State<T>> getSolution() { 
		return solution;
	}

	/**
	 * Sets the solution.
	 *
	 * @param solution the new solution
	 */
	public void setSolution(ArrayList<State<T>> solution) { 
		this.solution = solution;
	}
	
	/**
	 * Sets the state.
	 *
	 * @param solution the new state
	 */
	public void setState(State<T> solution) { 
		this.solution.add(solution);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		System.out.println();
		StringBuilder sb = new StringBuilder();
		for(State<T> s: solution)
		{ sb.append(s+"<");
		}
		return sb.toString();
	}
		
	
}
