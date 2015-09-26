package presenter;

import java.io.IOException;

import Algorithm.mazeGeneraors.Maze3d;
import Algorithm.mazeGeneraors.Position;
import algorithms.search.Solution;

public interface Presenter {
	/**
	 * Maze is ready.
	 * notify the user the maze is ready after generates new Maze3D
	 * @param name the name
	 */
	public void mazeIsReady(String name);


	/**
	 * Display maze.
	 * this command prints the given maze 
	 * @param current the current
	 */
	public void displayMaze(Maze3d current);


	/**
	 * Display cross section.
	 * display cross section by {X, Y, Z} 
	 * @param crossSectionByX the cross section by x
	 */
	public void displayCrossSection(int[][] crossSectionByX);


	/**
	 * Prints the maze size.
	 * print maze size in memory
	 * @param size the size
	 */
	public void printMazeSize(long size);


	/**
	 * Solution is ready.
	 * notify the the solution is ready after solving
	 * @param name the name
	 */
	public void solutionIsReady(String name);


	/**
	 * Send solution.
	 * send solution to the controller let us print the solution
	 * @param solution the solution
	 */
	public void sendSolutioin(Solution<Position> solution);


	/**
	 * Start.
	 * start controller
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void start() throws IOException;


	/**
	 * Prints the file size.
	 * print maze size in file
	 * @param size the size
	 */
	public void printFileSize(String size);
	
}
