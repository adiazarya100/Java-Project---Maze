/*
 * 
 */
package Server;


/**
 * The Class RunServer.
 * Run the server.
 */
public class RunServer {
	public static void main(String[] args) {
		MyTCPIPServer server = new MyTCPIPServer();
		server.run();
	}
}

