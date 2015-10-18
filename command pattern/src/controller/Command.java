package controller;

import java.io.FileNotFoundException;

/**
 * The Interface Command.
 */
public interface Command {

	/**
	 * Do command.
	 * All of the classes will implement this method
	 * @param args the args
	 * @throws FileNotFoundException the file not found exception
	 */
	public void doCommand(String[] args) throws FileNotFoundException;


}
