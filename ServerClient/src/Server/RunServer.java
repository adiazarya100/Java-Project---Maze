/*
 * 
 */
package Server;



/**
 * The Class RunServer.
 * Run the server.
 */
public class RunServer {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		MyTCPIPServer server = new MyTCPIPServer();
		server.run();
	}
}

