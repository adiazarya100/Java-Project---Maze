
package Model;

public interface Model {


	/**
	 * Start server.
	 */
	public void run();

	/**
	 * Disconnect server.
	 */
	public void DisconnectServer();

	/**
	 * Exit.
	 */
	public void exit();

	/**
	 * Gets the data. For the Observers.
	 *
	 * @return the data
	 */
	public String[] getData();

}