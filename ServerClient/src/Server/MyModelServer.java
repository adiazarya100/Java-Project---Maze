package Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import Model.Enums;
import Model.Model;
import algorithms.demo.searchMaze3DAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.ASTARalgorithm;
import algorithms.search.BFSalgorithm;
import algorithms.search.EuclideanDistance;
import algorithms.search.ManhattanDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;

/** The MyModelServer Class.
 * Part of the mvp architecture */

public class MyModelServer extends Observable implements Model,Runnable{

	/** mazeToSulution hashMap that keeps every maze and his solution **/
	protected HashMap<Maze3d, Solution<Position>> mazeToSolution; 

	/** Manage the thread pool. **/
	private ExecutorService executor; 

	/** The client status. */
	ConcurrentHashMap<String,String> clientStatus = new ConcurrentHashMap<String, String>();
	MyTCPIPServer server;

	Solution<Position> solutionToSend;
	/** The socket. */
	DatagramSocket socket;

	/** The address. */
	InetAddress address;

	/** Server Properties **/
	ServerProperties serverProp;

	/** The message data. */
	String [] messageData;

	protected String[] constantArgs = new String[2];;



	/** The MyModelServer constructor. */
	public MyModelServer(MazeClientHandler clientHandler) {
		this.server = new MyTCPIPServer();
		this.messageData= new String [2];
		this.mazeToSolution = new HashMap<Maze3d, Solution<Position>>();
		executor= Executors.newFixedThreadPool(10); //Change to properties.poolSize
		solutionToSend = new Solution<Position>(); 
		//loadSolution();
		try {
			//FileInputStream fis = new FileInputStream(Enums.FILE_PATH);
			File f = new File("./Solutions.zip");
			if(f.exists()){
				System.out.println("file exists Solutions.zip");
				FileInputStream fis = new FileInputStream(f);
				GZIPInputStream gis = new GZIPInputStream(fis);
				ObjectInputStream ois = new ObjectInputStream(gis);
				mazeToSolution = (HashMap <Maze3d,Solution<Position>>)ois.readObject();
				ois.close();
				fis.close();
			}

		}catch (ClassNotFoundException c) {
			// TODO Auto-generated catch block
			c.printStackTrace();
		} catch (IOException e) {
			mazeToSolution = new HashMap <Maze3d,Solution<Position>>();
		}
	}

	/** The ModelSolveing method. 
	 *  This method checks if the given maze is already solved, 
	 *  if not, the mthod will solve the given maze using the given algorithm*/
	public Solution<Position> ModelSolveing(Maze3d maze, String algorithm) {

		int[][][] tmpMaze = maze.getMaze3d();
		for (int x = 0; x < tmpMaze.length; x++) {
			for (int y = 0; y < tmpMaze[0].length; y++) {
				for (int z = 0; z < tmpMaze[0][0].length; z++) {
					if (tmpMaze[x][y][z] == 2)
						tmpMaze[x][y][z] = 0;
				}
			}
		}
		
		
		System.out.println("enter solve");
		try {
			File f = new File("./Solutions.zip");
			if(f.exists()){
				System.out.println("file exists Solutions.zip");
				FileInputStream fis = new FileInputStream(f);
				GZIPInputStream gis = new GZIPInputStream(fis);
				ObjectInputStream ois = new ObjectInputStream(gis);
				mazeToSolution = (HashMap <Maze3d,Solution<Position>>)ois.readObject();
				ois.close();
				fis.close();
			}

		}catch (ClassNotFoundException c) {
			// TODO Auto-generated catch block
			c.printStackTrace();
		} catch (IOException e) {
			mazeToSolution = new HashMap <Maze3d,Solution<Position>>();
		}


		Maze3d current = maze; 
		current.print();
		Solution<Position> temp = null;
		boolean flag = false;
		Iterator it = mazeToSolution.entrySet().iterator();
		while (it.hasNext() && !flag) {
			Map.Entry pair = (Map.Entry)it.next();
			if(pair.getKey().equals(current))
				temp = (Solution<Position>) pair.getValue();
			if (temp!=null){
				flag = true;
				solutionToSend = temp;
				System.out.println("solution from memory");}
		}
				
		if(flag==false){
		class solutionCallable implements Callable<Solution<Position>>{
			private Maze3d mg;
			private String algo;
			
			public solutionCallable(Maze3d maze, String algorithm){
				mg = maze;
				algo = algorithm;
			}
			
			@Override
			public Solution<Position> call() throws Exception {
				switch(algorithm.toLowerCase()){
				case "bfs":
					System.out.println("Attention: using bfs");
					searchMaze3DAdapter bfsMaze = new searchMaze3DAdapter(current);
					Searcher<Position> searcherBFS= new BFSalgorithm<Position>(); 
					Solution<Position> s = new Solution<Position>();
					s= searcherBFS.search(bfsMaze);
					solutionToSend =s;
					break;

				case "manhattanastar":
					System.out.println("Attention: using manhattan_astar");
					searchMaze3DAdapter manhattanAstarMaze= new searchMaze3DAdapter(current);
					Searcher<Position> searcherAStar= new ASTARalgorithm<Position>(new ManhattanDistance());
					Solution<Position> s1 = new Solution<Position>();
					s1= searcherAStar.search(manhattanAstarMaze);
					solutionToSend =s1;
					break;

				case "euclidianastar":
					System.out.println("Attention: using euclidian_astar");
					searchMaze3DAdapter euclidianAstarMaze= new searchMaze3DAdapter(current);	
					Searcher<Position> searcherAStar1= new ASTARalgorithm<Position>(new EuclideanDistance());
					Solution<Position> s2 = new Solution<Position>();
					s2= searcherAStar1.search(euclidianAstarMaze);
					solutionToSend =s2;
					break;
				default:
					System.out.println("Wrong Input");
					break;
				}
				return solutionToSend;
			}
			
		}
		
		
		
		solutionCallable generator = new solutionCallable(current,algorithm);
		Future <Solution<Position>> futureSolution = executor.submit(generator);
		try {
			mazeToSolution.put(current, futureSolution.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			saveSolution();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		constantArgs[0] = Enums.MODEL_SOLVED;
		setChanged();
		notifyObservers(constantArgs);
		}
		//if(solutionToSend!=null){
		constantArgs[0] = Enums.MODEL_SOLVED;
		setChanged();
		notifyObservers(constantArgs);
		//}
		return solutionToSend;
	}

	
	
	
	/** saveSolution method:
	 * write the mazeToSulution hashMap that keeps every maze and his solution**/
	public void saveSolution() throws FileNotFoundException, IOException{
		try {
			FileOutputStream fos=new FileOutputStream("./Solutions.zip");
			GZIPOutputStream gzos=new GZIPOutputStream(fos);
			ObjectOutputStream out=new ObjectOutputStream(gzos);
			out.writeObject(mazeToSolution);
			out.flush();
			gzos.close();
			out.close();
			fos.close();
			constantArgs[0] = Enums.MODEL_SAVED;
			setChanged();
			notifyObservers(constantArgs);
		}
		catch (IOException e) {
			e.getStackTrace();
		}
	}


	/** run method open new anonymous thread and start the TcpIpServer **/
	@Override
	public void run() {
		//server.run();
		new Thread(new Runnable() {

			@Override
			public void run() {
				server.run();	
			}
		}).start();
		System.out.println(Enums.SERVER_START);
		//server.startServer();
		this.constantArgs[0] = Enums.SERVER_START;
		setChanged();
		notifyObservers(constantArgs);
	}




	/** stop the server **/
	@Override
	public void DisconnectServer() {

		/*		String message="stop server"; 
		byte[] data=message.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(data,
				data.length, address, serverProp.getProperties().getPort());
		try {
			socket.send(sendPacket);
		} catch (IOException e) {

			this.messageData[0] = Enums.CANNOT_DISCONNECT_SERVER;
			this.messageData[1] = e.getMessage();
			setChanged();
			notifyObservers();
		}*/
		server.stoppedServer();
		this.messageData[0] = Enums.DISCONNECT_SERVER;
		setChanged();
		notifyObservers(messageData);
	}



	/* (non-Javadoc)
	 * @see Model.Model#exit()
	 */
	@Override
	public void exit() {


	}


	@Override
	public String[] getData() {
		return this.messageData;
	}


}




/*if(flag == false ){
Future<Solution<Position>> future = executor.submit(new Callable<Solution<Position>>(){
	@Override
	public Solution<Position> call() throws Exception {
		switch(algorithm.toLowerCase()){
		case "bfs":
			System.out.println("Attention: using bfs");
			searchMaze3DAdapter bfsMaze = new searchMaze3DAdapter(current);
			Searcher<Position> searcherBFS= new BFSalgorithm<Position>(); 
			Solution<Position> s = new Solution<Position>();
			s= searcherBFS.search(bfsMaze);
			solutionToSend =s;
			break;

		case "manhattanastar":
			System.out.println("Attention: using manhattan_astar");
			searchMaze3DAdapter manhattanAstarMaze= new searchMaze3DAdapter(current);
			Searcher<Position> searcherAStar= new ASTARalgorithm<Position>(new ManhattanDistance());
			Solution<Position> s1 = new Solution<Position>();
			s1= searcherAStar.search(manhattanAstarMaze);
			solutionToSend =s1;
			break;

		case "euclidianastar":
			System.out.println("Attention: using euclidian_astar");
			searchMaze3DAdapter euclidianAstarMaze= new searchMaze3DAdapter(current);	
			Searcher<Position> searcherAStar1= new ASTARalgorithm<Position>(new EuclideanDistance());
			Solution<Position> s2 = new Solution<Position>();
			s2= searcherAStar1.search(euclidianAstarMaze);
			solutionToSend =s2;
			break;
		default:
			System.out.println("Wrong Input");
			break;
		}
		return solutionToSend;
	}

});
executor.execute(new Runnable() {

	@Override
	public void run() {
		Solution<Position> sol = null;
		try {
			sol = future.get();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(sol!=null){
			try {
				mazeToSolution.put(current,future.get());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				saveSolution();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			constantArgs[0] = Enums.MODEL_SOLVED;
			setChanged();
			notifyObservers(constantArgs);
		}
		else{
			constantArgs[0] = Enums.MODEL_ERROR;
			setChanged();
			notifyObservers(constantArgs);
		}
		
	}
	
});
try {
	return future.get();
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (ExecutionException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}
return solutionToSend;*/