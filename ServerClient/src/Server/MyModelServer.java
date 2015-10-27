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
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

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
	private ListeningExecutorService executor; 
	
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
		executor=MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5)); //Change to properties.poolSize
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
		
		System.out.println("enter solve");
		
		
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
			solutionToSend = temp;}
		}

		ListenableFuture<Solution<Position>> futureSolution = null; //Initialize futureSolution.
		
		//String tmp;
		if(flag == true){  //IF The Maze is already solved.
			/*System.out.println("flag");
			System.out.println(algorithm);
			
			solutionToSend = mazeToSolution.get(current);
			System.out.println("solutionToSend");
			System.out.println(solutionToSend);
			
			System.out.println("did not solve->solution from the zip");*/
			
			//notifyObservers(constantArgs);
			
		}
		//else if(mazeToSolution.containsKey(maze)){
			//if(algorithm!="null")
			//tmp = algorithm;
			//else
			//tmp = prop.solver.name().toLowerCase();
		
		else{
			System.out.println("else");
			System.out.println(algorithm);
			switch(algorithm.toLowerCase()){ //tmp changed.
			case "bfs":
				System.out.println("Attention: using bfs");
				searchMaze3DAdapter bfsMaze = new searchMaze3DAdapter(current);
				futureSolution=executor.submit(new Callable<Solution<Position>>() {

					//@Override
					public Solution<Position> call() throws Exception {
						Searcher<Position> searcherBFS= new BFSalgorithm<Position>(); 
						Solution<Position> s = new Solution<Position>();
						s= searcherBFS.search(bfsMaze);
						solutionToSend =s;
						//System.out.println(s);//print the solve
						return s;
					}
				});
				break;

			case "manhattanastar":
				System.out.println("Attention: using manhattan_astar");
				searchMaze3DAdapter manhattanAstarMaze= new searchMaze3DAdapter(current);
				futureSolution=executor.submit(new Callable<Solution<Position>>() {
					@Override
					public Solution<Position> call() throws Exception {
						Searcher<Position> searcherAStar= new ASTARalgorithm<Position>(new ManhattanDistance());
						Solution<Position> s = new Solution<Position>();
						s= searcherAStar.search(manhattanAstarMaze);
						solutionToSend =s;
						//System.out.println(s);
						return s;
					}
				});

				break;

			case "euclidianastar":
				System.out.println("Attention: using euclidian_astar");
				searchMaze3DAdapter euclidianAstarMaze= new searchMaze3DAdapter(current);
				futureSolution=executor.submit(new Callable<Solution<Position>>() {
					@Override
					public Solution<Position> call() throws Exception {
						Searcher<Position> searcherAStar= new ASTARalgorithm<Position>(new EuclideanDistance());
						Solution<Position> s = new Solution<Position>();
						s= searcherAStar.search(euclidianAstarMaze);
						solutionToSend =s;
						//System.out.println(s);
						return s;
					}
				});

				break;
			default:
				System.out.println("Wrong Input");
				break;
			}
		}

		if(futureSolution!=null){

			Futures.addCallback(futureSolution, new FutureCallback<Solution<Position>>() {

				//@Override
				public void onFailure(Throwable sol) {
					constantArgs[0] = Enums.MODEL_ERROR;
					setChanged();
					notifyObservers(constantArgs);
				}


				//@Override
				public void onSuccess(Solution<Position> sol) {
					mazeToSolution.put(current,sol);
					try {
						saveSolution();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("solution from hash map");
					System.out.println(mazeToSolution.get(current));
					
					System.out.println("on sucsses111");
					System.out.println("on sucsses solution:");
					System.out.println(sol);
					
					constantArgs[0] = Enums.MODEL_SOLVED;
					//constantArgs[1] = name;
					setChanged();
					notifyObservers(constantArgs);
				}		
			});
			
		}
		System.out.println("return");
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