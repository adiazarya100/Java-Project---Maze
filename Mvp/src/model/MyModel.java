package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Observable;

import Algorithm.mazeGeneraors.Maze3d;
import Algorithm.mazeGeneraors.Position;
import algorithms.search.Searchable;
import algorithms.search.Solution;

public class MyModel extends Observable implements Model{


	protected HashMap<String, Maze3d> HM;														
	protected HashMap<String, String> compressedHM;
	protected HashMap<String, Solution<Position>> solutionHM; 
	protected HashMap<String, Integer> HashFileSize;
	
	
	/**
	 * Instantiates a new my model.
	 * FIRST CTOR of MyModel()
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	public MyModel() throws IOException, ClassNotFoundException {	
		super();
		HM = new HashMap<String, Maze3d>();
		compressedHM = new HashMap<String, String>();	
		HashFileSize = null;;
		try
		{
			//Read from this file all the data into HashFileSize when the program starts.
			FileInputStream fis = new FileInputStream("NameAndSize.txt"); 
			ObjectInputStream ois = new ObjectInputStream(fis);
			HashFileSize = (HashMap)ois.readObject();
			ois.close();
			fis.close();
		}catch(IOException ioe)
		{
			HashFileSize = new HashMap<String, Integer>();
		}catch(ClassNotFoundException c)
		{
			c.printStackTrace();
		}
	}
	
	
	
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getModelSizeInMemory(String name) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void getModelSizeInFile(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveModel(String name, String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadModel(String fileName, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ModelSolveing(String name, String algorithm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateModel(String name, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> Solution<T> getSolution(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Searchable<T> getNameToModel(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Searchable<T> CrossSectionBy(String name, String dimention, int section) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exit() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit(String string) {
		// TODO Auto-generated method stub
		
	}

}
