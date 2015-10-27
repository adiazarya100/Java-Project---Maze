/*
 * 
 */
package Server;

import java.io.Serializable;


/**
 * The Class Represents the server property.
 */

public class Properties implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The port. */
	private int port ;
	
	/** The clients. */
	private int numOfClients;

	
	
	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port.
	 *
	 * @param port the new port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Gets the num of clients.
	 *
	 * @return the num of clients
	 */
	public int getNumOfClients() {
		return numOfClients;
	}

	/**
	 * Sets the num of clients.
	 *
	 * @param numOfClients the new num of clients
	 */
	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}
}