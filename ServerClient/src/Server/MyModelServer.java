/*
 * 
 */
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
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.ASTARalgorithm;
import algorithms.search.BFSalgorithm;
import algorithms.search.EuclideanDistance;
import algorithms.search.ManhattanDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;


public class MyModelServer extends Observable implements Runnable, Model{

	protected HashMap<Maze3d, Solution<Position>> mazeToSolution; //for every maze save his solution.
	private ListeningExecutorService executor; //Manage the thread pool.
	private ThreadPoolExecutor threadpool;
	/** The client status. */
	ConcurrentHashMap<String,String> clientStatus = new ConcurrentHashMap<String, String>();
	MyTCPIPServer server;
	
	Solution<Position> solutionToSend;
	/** The socket. */
	DatagramSocket socket;

	/** The address. */
	InetAddress address;
	
	ServerProperties serverProp;
	
	/** The message data. */
	String [] messageData;
	protected String[] constantArgs = new String[2];
	
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
				System.out.println("my model server side -");
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
	
	
	public Solution<Position> ModelSolveing(Maze3d maze, String algorithm) {
		System.out.println("enter solve"); 
		Maze3d current = maze; 
		current.print();
		Solution<Position> temp = null;
		boolean flag = false;
		Iterator it = mazeToSolution.entrySet().iterator();
		while (it.hasNext() && !flag) {
			Map.Entry pair = (Map.Entry)it.next();
			if(pair.getKey().equals(current))
				temp = (Solution<Position>) pair.getValue();
			if (temp!=null)
				flag = true;
		}

		ListenableFuture<Solution<Position>> futureSolution = null; //Initialize futureSolution.
		
		String tmp;
		if(flag == true){  //IF The Maze is already solved.
			mazeToSolution.get(maze);
			System.out.println("did not solve->solution from the zip");
			//TODO !!!!
			//notifyObservers(constantArgs);
		}
		//else if(mazeToSolution.containsKey(maze)){
			//if(algorithm!="null")
			//tmp = algorithm;
			//else
			//tmp = prop.solver.name().toLowerCase();

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
		//}

		if(futureSolution!=null){

			Futures.addCallback(futureSolution, new FutureCallback<Solution<Position>>() {

				//@Override
				public void onFailure(Throwable sol) {
					System.out.println("on Failure!!!");
					System.out.println("print sol:");
					System.out.println(sol);
					System.out.println("blaaaaaaa");
					constantArgs[0] = Enums.MODEL_ERROR;
					setChanged();
					notifyObservers(constantArgs);
				}


				//@Override
				public void onSuccess(Solution<Position> sol) {
					System.out.println("on success!!!");
					System.out.println("print sol:");
					System.out.println(sol);
					mazeToSolution.put(current,sol);
					
					constantArgs[0] = Enums.MODEL_SOLVED;
					//constantArgs[1] = name;
					setChanged();
					System.out.println(constantArgs);
					notifyObservers(constantArgs);
				}		
			});
			
		}
	
			//return solutionToSend;
		try {
			System.out.println(futureSolution.get());
			return futureSolution.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

/*	public Solution<Position> ModelSolveing(Maze3d maze, String algorithm) {
		Solution<Position> sol = null;
		boolean flag = false;
		Iterator it = mazeToSolution.entrySet().iterator();
		while (it.hasNext() && !flag) {
			Map.Entry pair = (Map.Entry)it.next();
			if(pair.getKey().equals(maze))
				flag = true;
		}
		Future<Solution<Position>> futureSolution = null;
		if(!mazeToSolution.containsKey(maze) && !flag){
			futureSolution = threadpool.submit(new Callable<Solution<Position>>(){
				@Override
				public Solution<Position> call() throws Exception {
					Solution<Position> sol = new Solution<Position>();
					Searcher<Position> searcher = null;
					if (algorithm.contains("BFS") || algorithm.equals("BFS")){
						searcher = new BFSalgorithm<>();
					}
					else if(algorithm.contains("AStar")){
						if(algorithm.contains("Manhattan"))
							searcher= new ASTARalgorithm<Position>(new ManhattanDistance());
						else if (algorithm.contains("Air"))
							searcher = new ASTARalgorithm<Position>(new EuclideanDistance());
					}

					sol = searcher.search(new searchMaze3DAdapter(maze));
					return sol;
				}
			});
			try {
				File file = new File("./Solutions.zip");
				if(file.exists()){
					MyGzipCompressor gzip = new MyGzipCompressor();
					try {
						this.solutions = gzip.decompress();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				this.solutions.put(maze, futureSolution.get());
				MyGzipCompressor gzip = new MyGzipCompressor();
				try {
					gzip.compress(solutions);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			setChanged();
			try {
				notifyObservers(futureSolution.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		else if(flag==true){
			setChanged();
			notifyObservers(mazeToSolution.get(maze));
		}

		threadpool.shutdown();
		try {
			if(!threadpool.awaitTermination(3, TimeUnit.SECONDS)){
				threadpool.shutdownNow();	
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return sol;
	}*/
		
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

@Override
public void run() {
	//server.run();
	new Thread(new Runnable() {

		@Override
		public void run() { //OPEN Thread! 
			server.run();
		}
	}).start();
	System.out.println(Enums.SERVER_START);
	constantArgs[0] = Enums.SERVER_START;
	setChanged();
	notifyObservers(constantArgs);
}


@Override
public void getStatusClient(String client) {

		
		String status=server.clientStatus.get(client);
		messageData[0] = Enums.CLIENT_STATUS;
		messageData[1] = status;
		setChanged();
		notifyObservers();

	}
	



@Override
public void DisconnectClient(String client) {

		Socket tmp=server.SocketStatus.get(client);
		String message=client.split(",")[0]+","+ client.split(",")[1]+",disconnect";  
		byte[] data=message.getBytes(); 
		DatagramPacket sendPacket = new DatagramPacket(data,
				data.length, tmp.getInetAddress(), serverProp.getProperties().getPort());
		try {

			socket.send(sendPacket); 
		} catch (IOException e) {
			this.messageData[0]= Enums.CANNOT_REMOVE_CLIENT;
			this.messageData[1] = client + e.getMessage();
			setChanged();
			notifyObservers(messageData);
			return;
		}
		this.messageData[0] = Enums.CLIENT_REMOVED;
		this.messageData[1] = client;
		setChanged();
		notifyObservers(messageData); 
}
	



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



@Override
public void exit() {
	

}


@Override
public String[] getData() {
	return this.messageData;
}






}