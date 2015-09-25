package model;

import java.io.IOException;

import controller.MyController;

/**
 * The Interface Model.
 */
public interface Model {

	/**
	 * Provide maze.
	 * provide the requested maze for display
	 * @param string the string
	 */
	void provideMaze(String string);


	/**
	 * Generate maze.
	 * this command generates Maze3d, sending name and 3 dimensions 
	 * @param name the name
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	void generateMaze(String name, int x, int y, int z);


	/**
	 * Cross section.
	 * this command asks for printing maze3d cross section for the given axis, index and name
	 * @param section the section
	 * @param index the index
	 * @param name the name
	 */
	void crossSection(String section, int index, String name);


	/**
	 * Save compressed maze.
	 *
	 * @param name the name
	 * @param fileName the file name
	 */
	//compressed the maze and save it
	void saveCompressedMaze(String name, String fileName);


	/**
	 * Load compressed maze.
	 * load compressed maze 
	 * @param fileName the file name
	 * @param name the name
	 */
	void loadCompressedMaze(String fileName, String name);


	/**
	 * Maze size.
	 * get maze size in the memory
	 * @param name the name
	 */
	void mazeSize(String name);

	/**
	 * Maze solving.
	 * solve the maze using BFS or ASTAR algorithm
	 * @param name the name
	 * @param algorithm the algorithm
	 */
	void mazeSolveing(String name, String algorithm);


	/**
	 * Maze size file.
	 * asks for the maze size in the file
	 * @param name the name
	 */
	void mazeSizeFile(String name);


	/**
	 * Display solution.
	 * asks for the solution
	 * @param string the string
	 */
	void displaySolution(String string);


	/**
	 * Sets the controller.
	 * set the controller
	 * @param myController the new controller
	 */
	void setController(MyController myController);


	/**
	 * Exit.
	 * @param string the string
	 */
	void exit(String string);


}

	

