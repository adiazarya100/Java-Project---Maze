package algorithms.search;

/**
 * The Interface Searcher.
 *
 * @param <T> the generic type
 */
public interface Searcher <T>{

	    /**
    	 * Search.
    	 *
    	 * @param s the s
    	 * @return the solution
    	 */
    	// the search method
	    public Solution<T> search(Searchable<T> s);
	    
    	/**
    	 * Gets the number of nodes evaluated.
    	 *
    	 * @return the number of nodes evaluated
    	 */
    	// get how many nodes were evaluated by the algorithm
	    public int getNumberOfNodesEvaluated();
	

}
