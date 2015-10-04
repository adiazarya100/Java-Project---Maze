package view;

// TODO: Auto-generated Javadoc
/**
 * The Interface Adapter.
 * The getData() method will return a generic value for the displayer to use.
 * @param <T> the generic type
 */
public interface Adapter<T> {

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	T getData();
	
}
