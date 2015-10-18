package view;

import java.io.IOException;
import java.util.HashMap;

import controller.Command;
import controller.MyController;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


	/**
	 * The Interface View.
	 */
	public interface View {

		/**
		 * Dir.
		 * this command displays every files in the current path
		 * @param string the string
		 */
		void dir(String string);

		
		/**
		 * Display maze.
		 * this command prints the given maze
		 * @param current the current
		 */ 
		void displayMaze(Maze3d current);

		
		/**
		 * Maze is ready.
		 * notify the user the maze is ready after generates new Maze3D
		 * @param string the string
		 */
		void mazeIsReady(String string);
		
		
		/**
		 * Prints the cross section.
		 * print cross section
		 * @param crossSection the cross section
		 */
		void printCrossSection(int[][] crossSection);

		
		/**
		 * Prints the maze size.
		 * prints the size of the maze in memory
		 * @param size the size
		 */
		void printMazeSize(long size);
		
		
		/**
		 * Prints the maze in file size.
		 * prints the size of the maze in file
		 * @param size the size
		 */
		void printMazeInFileSize(String size);

		/**
		 * Solution is ready.
		 * notify the user that the solution for his maze is ready
		 * @param name the name
		 */
		void solutionIsReady(String name);

		/**
		 * Prints the solution.
		 * print the solution
		 * @param solution the solution
		 */
		void printSolution(Solution<Position> solution);

		/**
		 * Sets the hash map.
		 * set the commands map in the view layer
		 * @param commandsMap the commands map
		 */
		void setHashMap(HashMap<String, Command> commandsMap);
		
		/**
		 * Start.
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		void start() throws IOException;

		/**
		 * Sets the controller.
		 * @param myController the new controller
		 */
		void setController(MyController myController);

	
}
