package view;

import java.util.HashMap;

import presenter.Command;
import algorithms.search.Solution;

/**	the interface defines what each View in MVP architecture has to do!
 * 
 *
 */

public interface View {

	
	public void dirCommand(String  fileName);
	
	
	public <T> void displayModel(Adapter<T> draw);
	
	
	public <T> void displayCrossSectionBy(Adapter<T> draw);
	
	
	public <T> void displaySolution(Solution<T> solution);
	
	
	public void setCommands(HashMap<String, Command>commands);
	
	
	public void displayString(String toPrint);
	
	
	public Command getUserCommand();
	
	public void setUserCommand(Command command);
	
	public void start();
	
	public void exit();
	
}