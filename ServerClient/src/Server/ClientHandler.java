/*
 * 
 */
package Server;

import java.io.IOException;
import java.net.Socket;


/**
 * The Interface ClientHandler.
 */
public interface ClientHandler {
	
	/**
	 * Handle client. This function will be implement client.
	 *
	 * @param client the client
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 * read object from the client pass it to the mvp and then write it back to the client.
	 */
	void handleClient(Socket client) throws IOException, ClassNotFoundException;
}
