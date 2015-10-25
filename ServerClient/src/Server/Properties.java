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

	
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getNumOfClients() {
		return numOfClients;
	}

	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}
}