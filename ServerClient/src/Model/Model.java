
package Model;

public interface Model {


	/**
	 * Gets the status client.
	 *
	 * @param client the client
	 * @return the status client
	 */
	public void getStatusClient(String client);

	/**
	 * Disconnect client.
	 *
	 * @param params the client
	 */
	public void DisconnectClient(String client);

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