package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Searchable;
import algorithms.search.Solution;

public interface Model{
	
	
	
	
	public HashMap<String, Maze3d> getHM();
	/**
	 * Maze size.
	 * get model size in the memory
	 * @param name the name
	 * @throws IOException 
	 */
	public long getModelSizeInMemory(String name) throws IOException;
	
	
	/**
	 * Maze size file.
	 * asks for the model size in the file
	 * @param name the name
	 * @return 
	 */
	public long getModelSizeInFile(String name);
	
	
	/**
	 * Save compressed model.
	 * compressed the model and save it
	 * @param name the name
	 * @param fileName the file name
	 */
	public void saveModel(String name, String fileName);
	
	
	/**
	 * Load compressed model.
	 * @param fileName the file name
	 * @param name the name
	 */
	public void loadModel(String fileName, String name);

	
	/**
	 * model solving.
	 * solve the model using BFS or ASTAR algorithm
	 * @param name the name
	 * @param algorithm the algorithm
	 */
	public void ModelSolveing(String name, String algorithm);
	
	
	/**
	 * Generate model.
	 * this command Generate model.
	 */
	public void generateModel(String name, int x, int y, int z);
	

	/**
	 * Gets the solution.
	 *
	 * @param <T> the generic type
	 * @param name the name
	 * @return the solution
	 */
	public <T> Solution<T> getSolution(String name);
	
	/**
	 * Gets the name to model.
	 *
	 * @param <T> the generic type
	 * @param name the name
	 * @return the name to model
	 */
	public <T> Searchable<T>  getNameToModel(String name);
	
	/**
	 * Cross section by.
	 * @param <T>
	 *
	 * @param <T> the generic type
	 * @param name the name
	 * @param dimention the dimention
	 * @param section the section
	 * @return the searchable
	 */
	public <T> Searchable<T> CrossSectionBy(String section, int index, String name);	
	
	
	//public HashMap<String, Maze3d> getNameToMaze() ;

	/**
	 * Exit.
	 * @param string the string
	 */
	void exit(String string);
 
}

