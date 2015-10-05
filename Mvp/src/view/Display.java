package view;

/**
 * The Interface Display.
 * The displayer gets a drawable object.
 * @param <T> the generic type
 */
public interface Display <T>{


	/**
	 * Gets the displayer.
	 *
	 * @param draw the draw
	 * @return the displayer
	 */
	void getDisplayer(Adapter<T> draw);
	
	/**
	 * Display.
	 */
	void display();
}
