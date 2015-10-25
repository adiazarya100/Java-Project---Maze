/*
 * 
 */
package Server;

import java.io.IOException;
import java.net.Socket;

public interface ClientHandler {
	/**
	 * Handle client. This function will be implement client.
	 *
	 * @param client the client
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	void handleClient(Socket client) throws IOException, ClassNotFoundException;
}
