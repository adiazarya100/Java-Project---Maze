/*
 * 
 */
package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;




import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;


/** This class creates a generic TCP/IP Server. 
 *
 */
public class MyTCPIPServer implements Runnable{

	ConcurrentHashMap<String,Socket> SocketStatus = new ConcurrentHashMap<String, Socket>();
	/** The client status. */
	ConcurrentHashMap<String,String> clientStatus = new ConcurrentHashMap<String, String>();
	
	/**	The client handler that will be injected to this field will change how clients will be handled.
	 * 
	 */
	ClientHandler clientHandler;
	/** a flag to know when the server was signaled to stop.
	 * 
	 */
	private volatile boolean stopped;
	
	/**
	 * Instantiates a new my tcpip server.
	 *
	 * @param serverProperties the server properties
	 * @param clientHandler the client handler
	 */
	public MyTCPIPServer() {
		//this.serverProperties=serverProperties;
		stopped=false;
		this.clientHandler=clientHandler;
	}
	
	
	
	/**	This method will start the TCP/IP Server.
	 * Please Inject your desired client handler first.
	 * 
	 */
	
	@Override
	public void run() {
		
			ServerSocket server;
			try {
				ServerProperties serverProperties = ServerProperties.getInstance();
				server = new ServerSocket(serverProperties.getProperties().getPort());
				System.out.println("Server is on: "+server.getInetAddress());
				System.out.println("Server is now listeing on port " + serverProperties.getProperties().getPort());
				ListeningExecutorService threadPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(serverProperties.getProperties().getNumOfClients()));
				server.setSoTimeout(500);// changed from 500 to 0 which is infinite timoute
				while(!stopped)
				{
					try {
						final Socket someClient=server.accept();
						clientStatus.put(someClient.getInetAddress().getHostAddress(), "connected");
						System.out.println("New client" + " port: " + someClient.getPort() + " IP: " + someClient.getInetAddress().getHostAddress());
						threadPool.execute(new Runnable() {

							@Override
							public void run() {
								try {
									ClientHandler clientHandler = new MazeClientHandler();
									clientHandler.handleClient(someClient);
									someClient.close();
								} catch (IOException | ClassNotFoundException e) {
									e.printStackTrace();
								}
							}
						});

					} catch (SocketTimeoutException e) {
						
					}
				}
				threadPool.shutdownNow();
				server.close();

			} catch (IOException e1) {
				e1.printStackTrace();
			}


		}

		
	
	
	/**
	 * Stopped server.
	 */
	public void stoppedServer()
	{
		stopped=true;
	}




	
}     