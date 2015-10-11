/** This program implements 3 dimensional maze that you can create by generating the maze by random way 
 * or using the DFS algorithm. you can solve the maze using 2 different algorithms, one is the A star algorithm 
 * that using 2 different heuristic methods (Manhattan&Euclidean distance and the other is BFS algorithm.
 *  
* @author  Zlil Korman
* @version 1.0
* @since   2015-08-23*/


package algorithms.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import algorithms.mazeGenerators.DFS;
import algorithms.mazeGenerators.DFS2;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.ASTARalgorithm;
import algorithms.search.BFSalgorithm;
import algorithms.search.EuclideanDistance;
import algorithms.search.ManhattanDistance;
import algorithms.search.Searcher;

public class Demo {
	
	/**
	 * Run.
	 */
	public static void run(){
		// Creates a new 3D maze using DFS algorithm, size (5,5,3), and prints it.
		Maze3dGenerator maze3dGenerator = new DFS2();
		Maze3d maze = maze3dGenerator.generate(15,1,15);
		System.out.println(maze.getStartPosition());
		System.out.println(maze.getGoalPosition());
		//maze.walls();
		maze.print();
		// Solving the maze using BFS
		System.out.println();
		System.out.println("Solving the maze using BFS: ");
		Searcher<Position> searcherBFS= new BFSalgorithm<Position>(); 
		System.out.println(searcherBFS.search(new searchMaze3DAdapter(maze)));
		System.out.println();
		System.out.println("Solving the maze using A star and HeuristicManhattanDistance: ");
		Searcher<Position> searcherAStarM= new ASTARalgorithm<Position>(new ManhattanDistance()); 
		System.out.println(searcherAStarM.search(new searchMaze3DAdapter(maze)));
		System.out.println();
		System.out.println("Solving the maze using A star and HeuristicAirDistance: ");
		Searcher<Position> searcherAStarA= new ASTARalgorithm<Position>(new EuclideanDistance()); 
		System.out.println(searcherAStarA.search(new searchMaze3DAdapter(maze)));
		System.out.println("Amount of evaluated nodes for BFS Search algoritm: "+ searcherBFS.getNumberOfNodesEvaluated());
		System.out.println("Amount of evaluated nodes for ASTAR Search algoritm using HeuristicManhattanDistance: "+ searcherAStarM.getNumberOfNodesEvaluated());
		System.out.println("Amount of evaluated nodes for ASTAR Search algoritm using HeuristicAirDistance: "+ searcherAStarA.getNumberOfNodesEvaluated());
	}
	
/*	public static void main(String[] args) {
	
		
		Maze3dGenerator maze3dGenerator = new MyMaze3dGenerator();
		Maze3d maze = maze3dGenerator.generate(5,5,3);
		//... generate it
		maze.getStartPosition().PrintPosition();
		maze.getGoalPosition().PrintPosition();
		// save it to a file
		OutputStream out = null;
		try {
			out = new MyCompressorOutputStream(new FileOutputStream("1.maz"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.write(maze.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream in = null;
		try {
			in = new MyDecompressorInputStream(new FileInputStream("1.maz"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte b[] = null;
		try {
			b = new byte[maze.toByteArray().length];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.read(b);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Maze3d loaded=new Maze3d(b);
		loaded.getStartPosition().PrintPosition();
		loaded.getGoalPosition().PrintPosition();
		System.out.println(loaded.equals(maze));
	}*/

	public static void main(String[] args) {
		run();
		
	}
	
}