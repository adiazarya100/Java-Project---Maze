package view;

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
