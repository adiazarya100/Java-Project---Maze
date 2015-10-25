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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import algorithms.demo.searchMaze3DAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.ASTARalgorithm;
import algorithms.search.BFSalgorithm;
import algorithms.search.EuclideanDistance;
import algorithms.search.ManhattanDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;


public class MyModelServer implements Runnable{

	protected HashMap<Maze3d, Solution<Position>> mazeToSolution; //for every maze save his solution.
	private ListeningExecutorService executor; //Manage the thread pool.
	MyTCPIPServer server;
	Solution<Position> solutionToSend;
	
	public MyModelServer(MazeClientHandler clientHandler) {
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
				System.out.println("bfs");
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
				}


				//@Override
				public void onSuccess(Solution<Position> sol) {
					mazeToSolution.put(current,sol);
					
					//solutionHM.put(name, sol);
				}		
			});
			
		}
	
			return solutionToSend;
	}

	
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
	}
	catch (IOException e) {
		e.getStackTrace();
	}
}

@Override
public void run() {
	server.startServer();
	
}


}