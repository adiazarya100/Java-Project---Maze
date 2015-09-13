package algorithms.search;

/**
 *  Since each node in the maze represents a “state” of the problem, we can create a State class
 * Inside, we can use whatever types that can describe states generally.
 * the cost calculation is domain specific! 
 * i.e., relevant to a specific problem and not a general solution.
 *
 * @param <T> the generic type
 */ 
public class State <T> implements Comparable<State<T>>{
	
	/** The state. */
	private String state; //state represented by string
	
	/** The last state. */
	private String lastState;
	
	/** The cost. */
	private double cost;  //cost to reach this state
	
	/** The came from. */
	private State<T> cameFrom; //the state we came from to the current state
	
	/** The current position. */
	private T currentPosition;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		
		return currentPosition.toString().hashCode();
	}

	/**
	 * Gets the last state.
	 *
	 * @return the last state
	 */
	public String getLastState() {
		return lastState;
	}

	/**
	 * Sets the last state.
	 *
	 * @param lastState the new last state
	 */
	public void setLastState(String lastState) {
		this.lastState = lastState;
	}

	
	/**
	 * Gets the current position.
	 *
	 * @return the current position
	 */
	public T getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Sets the current position.
	 *
	 * @param currentPosition the new current position
	 */
	public void setCurrentPosition(T currentPosition) {
		this.currentPosition = currentPosition;
	}


	/**
	 * Instantiates a new state.
	 *
	 * @param currentPosition the current position
	 */
	public State(T currentPosition) {
		super();
		this.currentPosition = currentPosition;
		this.cost=0;
		this.cameFrom=null;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Sets the cost.
	 *
	 * @param cost the new cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Gets the came from.
	 *
	 * @return the came from
	 */
	public State<T> getCameFrom() {
		return cameFrom;
	}

	/**
	 * Sets the came from.
	 *
	 * @param cameFrom the new came from
	 */
	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}

	
	/**
	 * Instantiates a new state.
	 *
	 * @param state the state
	 */
	public State(String state){ //Ctor
		this.state=state;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
    public boolean equals(Object obj){ // we override Object's equals method
        return currentPosition.equals(((State<T>)obj).getCurrentPosition());
    } 
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return currentPosition.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(State<T> t) {
		// TODO Auto-generated method stub
		return (int)(this.cost-t.getCost());
	}
	

}
