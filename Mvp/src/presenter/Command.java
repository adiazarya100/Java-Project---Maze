package presenter;

import java.io.FileNotFoundException;

/**
 * Interface Command - define the behavior of a command.
 * The command will Connect between the view & model.
 */

public interface Command {

	/**
	 * Do command.
	 * All of the classes will implement this method
	 * @param args the args
	 * @throws FileNotFoundException the file not found exception
	 */
	public void doCommand();
	public void setArguments(String [] args);
}
