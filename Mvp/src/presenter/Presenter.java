package presenter;


import java.util.HashMap;
import java.util.Observer;


/**
 * The Presenter Interface.
 * The Presenter is responsible for the Connect between the view & model.
 * it uses the command interface to define the relation between the view & model. 
 */
public interface Presenter extends Observer{
	/**
	 * @return the commands.
	 */
	public HashMap<String, Command> getCommands();
	
	/**
	 * Start the Presenter.
	 */
	public void start();
}

	
