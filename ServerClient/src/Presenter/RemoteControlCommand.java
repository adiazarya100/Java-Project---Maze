package Presenter;

public interface RemoteControlCommand {
	/**
	 * Do command. An interface that will be later implemented by other classes in order to initiate commands from the view to the Model.
	 */
	void doCommand();

	/**
	 * Sets the arguments.
	 *
	 * @param args the new arguments
	 */
	public void setArguments(String[] args);
}
