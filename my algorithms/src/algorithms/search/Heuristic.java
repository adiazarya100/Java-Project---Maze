package algorithms.search;

// TODO: Auto-generated Javadoc
/**
 * The Interface Heuristic.
 *
 * @param <T> the generic type
 */
public interface Heuristic<T> {

	/**
	 * H.
	 *
	 * @param neighbor the neighbor
	 * @param goal the goal
	 * @return the double
	 */
	public double H(State<T> neighbor,State<T> goal);
}
