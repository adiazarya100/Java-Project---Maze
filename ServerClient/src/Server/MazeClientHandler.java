/*
 * 
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.common.util.concurrent.ListeningExecutorService;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * The Class MazeClientHandler. Define implementation ofclient handler.
 */

public class MazeClientHandler extends Observable implements ClientHandler,Observer  { 

	protected HashMap<Maze3d, Solution<Position>> mazeToSolution; //for every maze save his solution.
	private ListeningExecutorService executor; //Manage the thread pool.

	volatile ConcurrentHashMap<String,Socket> activeClients=new ConcurrentHashMap<String,Socket>();
	volatile ConcurrentLinkedQueue<String> messages=new ConcurrentLinkedQueue<String>();
	
	MyModelServer MyModelServer;
	Solution<Position> clientSolution;

	public MazeClientHandler() {
		this.MyModelServer= new MyModelServer(this);
	}



	@Override
	public void handleClient(Socket client) throws IOException, ClassNotFoundException {

		String clientIP=client.getInetAddress().getHostAddress();
		int clientPort=client.getPort();
		activeClients.put(clientIP+","+clientPort, client);
		String message=new String(clientIP +","+ clientPort+",connected");
		messages.add(message);
		//setChanged();
		//notifyObservers();//check messages
		messages.remove(message);

		//Maze3d maze;
		//String[] params;
		ObjectOutputStream output =new ObjectOutputStream(client.getOutputStream());
		ObjectInputStream input =new ObjectInputStream(client.getInputStream());

		
		ArrayList<Object> solution=null;
		try {
			solution = (ArrayList<Object>) input.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//if(obj!=null){
		//	if(obj instanceof String){
		//		model.shotDown();
		//	}
		//}
		
		Maze3d maze = (Maze3d)solution.get(0);
		String algo = (String)solution.get(1);
		
		message=clientIP+ ","+clientPort+",solving maze";
		messages.add(message);
		//setChanged();
		//notifyObservers();
		messages.remove(message);
		clientSolution = MyModelServer.ModelSolveing(maze, algo);
		
		System.out.println("Solution is:");
		System.out.println(clientSolution);
		
		output.writeObject(clientSolution);
		//output.flush();
	
		
		
		
		
		
		//outputCompressedToClient.flush();
		//String command=readerFromClient.readLine();


		//solve maze
		//String solverProperties=readerFromClient.readLine();
		//Maze3d Maze3d = (Maze3d) readClient.readObject();
		//message=clientIP+ ","+clientPort+",solving maze";
		//messages.add(message);
		//setChanged();
		//notifyObservers();

		//outputCompressedToClient.writeObject(ModelSolveing(Maze3d,));
		//outputCompressedToClient.flush();
		//setChanged();
		//notifyObservers();
		//messages.remove(message);		
	}




	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(arg.toString().contains("disconnect"))
		{
			Socket clientToDisconnect=activeClients.get(arg.toString().substring(0, arg.toString().length()-"disconnect".length()-1));

			try{
				clientToDisconnect.close();
			}catch(Exception e)
			{

			}

		}
	}

	
}
