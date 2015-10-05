package view;

// TODO: Auto-generated Javadoc
/**
 * The Interface Adapter.
 * define the behavior of an object that can be displayed.
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
