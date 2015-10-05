package model;

import generic.Enums;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import presenter.Properties;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import algorithms.demo.searchMaze2DAdapter;
import algorithms.demo.searchMaze3DAdapter;
import algorithms.mazeGenerators.*;
import algorithms.search.ASTARalgorithm;
import algorithms.search.BFSalgorithm;
import algorithms.search.ManhattanDistance;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;

// TODO: Auto-generated Javadoc
/**
 * The Class MyModel.
 */
public class MyModel extends Observable implements Model{


	/** The hm. */
	protected HashMap<String, Maze3d> HM;														
	
	/** The compressed hm. */
	protected HashMap<String, String> compressedHM;
	
	/** The solution hm. */
	protected HashMap<String, Solution<Position>> solutionHM;   // for every NAME maze save his solution.
	
	/** The maze to solution. */
	protected HashMap<Maze3d, Solution<Position>> mazeToSolution; //for every maze save his solution.
	
	/** The Hash file size. */
	protected HashMap<String, Integer> HashFileSize;
	
	/** The executor. */
	private ListeningExecutorService executor; //Manage the thread pool.
	
	/** The properties. */
	private Properties properties;
	
	/** The my compressor. */
	MyCompressorOutputStream myCompressor;
	
	/** The my decompressor. */
	MyDecompressorInputStream myDecompressor;
	
	/** The constant args. */
	String [] constantArgs;
	
	/** The my xml encoder. */
	XMLEncoder myXMLEncoder;
	
	/**
	 * Instantiates a new my model.
	 * FIRST CTOR of MyModel()
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	public MyModel() throws IOException, ClassNotFoundException {
		HM = new HashMap<String, Maze3d>();
		compressedHM = new HashMap<String, String>();
		loadSolution(); //load all possible solutions.
		executor=MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5)); //Initialize the ThreadPool with 5 threads.
		HashFileSize = null;
		
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

	/**
	 * Instantiates a new my model. get a Preferences object!
	 * SEC CTOR of MyModel()
	 *
	 * @param properties the properties
	 */
	public MyModel(Properties properties) {

		this.HM = new HashMap<String, Maze3d>();
		this.compressedHM = new HashMap<String, String>();
		this.solutionHM = new HashMap<String, Solution<Position>>();
		this.mazeToSolution = new HashMap<Maze3d, Solution<Position>>();
		this.myCompressor = null;
		this.myDecompressor = null;
		this.constantArgs = new String[2];
		loadSolution();
		this.properties = properties;
		executor=MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(properties.poolSize));
		HashFileSize = null;
		
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


	/* (non-Javadoc)
	 * @see model.Model#getHM()
	 */
	public HashMap<String, Maze3d> getHM() {
		return HM;
	}

	/**
	 * Sets the hm.
	 *
	 * @param hM the h m
	 */
	public void setHM(HashMap<String, Maze3d> hM) {
		HM = hM;
	}

	/* (non-Javadoc)
	 * @see model.Model#saveModel(java.lang.String, java.lang.String)
	 * compressed the maze and save it
	 */
	@Override
	public void saveModel(String name, String fileName) {
		FileOutputStream f;
		try {
			f = new FileOutputStream(fileName);
			OutputStream out = new MyCompressorOutputStream(f);
			compressedHM.put(name, fileName);
			try {
				out.write(HM.get(name).toByteArray());
			} catch (IOException e) {
				setChanged();
				notifyObservers(Enums.FILE_NOT_FOUND);
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
				setChanged();
				notifyObservers(Enums.ERROR_CLOSING_FILE);
			}
			try {
				Maze3d tmp = HM.get(name);
				int tmp2=tmp.toByteArray().length;
				HashFileSize.put(fileName, tmp2);
				//HashFileSize.put(fileName, (HM.get(name)).toByteArray().length);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			constantArgs[0] = Enums.MODEL_SAVED;
			constantArgs[1] = fileName;
			setChanged();
			notifyObservers(constantArgs);
		}
		catch (FileNotFoundException e) {
			setChanged();
			notifyObservers(Enums.ERROR_CLOSING_FILE);
		}
	}



	/* (non-Javadoc)
	 * @see model.Model#loadModel(java.lang.String, java.lang.String)
	 * load compressed maze
	 */
	@Override
	public void loadModel(String fileName, String name) {
		try {
			InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName));
			byte b[] = new byte[HashFileSize.get(fileName)];
			try {
				in.read(b);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Maze3d loaded = new Maze3d(b);
			HM.put(name, loaded);
			compressedHM.put(name, fileName);
			constantArgs[0] = Enums.MODEL_LOADED; 
			setChanged();
			notifyObservers(constantArgs);
			
		}catch(FileNotFoundException e){
			setChanged();
			notifyObservers(Enums.FILE_NOT_FOUND);
		}	
	}



	/* (non-Javadoc)
	 * @see model.Model#getModelSizeInMemory(java.lang.String)
	 * get maze size in the memory
	 */
	@Override
	public long getModelSizeInMemory(String name) throws IOException {
		if(HM.containsKey(name)){
			long size = HM.get(name).toByteArray().length;
			return size;}
		else
			System.out.println("Wrong Input, maze not exists");
		return 0;}



	// this command generates Maze3d in new Thread, sending name and 3
	/* (non-Javadoc)
	 * this command generates Maze3d, sending name and 3 dimensions 
	 * @see model.Model#generateModel(java.lang.String, int, int, int)
	 */
	@Override
	public void generateModel(String name, int x, int y, int z) {
		
		if(this.properties==null)
		{
			constantArgs[0] = Enums.PROPERTIES_ARE_NO_SET;
			setChanged();
			notifyObservers(constantArgs);
			return;
		}
		
		ListenableFuture<Maze3d> futureMaze=null;
		if(!HM.containsKey(name)){	
			futureMaze = executor.submit(new Callable<Maze3d>() {

				@Override
				public Maze3d call() throws Exception {
					Maze3d maze = new MyMaze3dGenerator().generate(x, y, z);	
					return maze;
				}
			});
		}
/*			// notify the observer.
			setChanged(); //must have this method for notify the observer.
			notifyObservers(name+ " generated");} //let the Presenter know that changes has been made
*/		else
			System.out.println("Worng Input Name is Already Used!");

		if(futureMaze!=null)
		{			
			Futures.addCallback(futureMaze, new FutureCallback<Maze3d>() {

				@Override
				public void onFailure(Throwable arg0) {
					constantArgs[0] = Enums.MODEL_ERROR;
					setChanged();
					notifyObservers(constantArgs); //an error whike creating the maze.
				}
				@Override
				public void onSuccess(Maze3d maze) {
					HM.put(name, maze);
					constantArgs[0] = Enums.MODEL_GENERATED;
					constantArgs[1] = name;
					setChanged();
					//System.out.println(constantArgs[0]);
					//System.out.println(constantArgs[1]);
					notifyObservers(constantArgs);
				}

			});
		}	
	}



	/* (non-Javadoc)
	 * @see model.Model#getModelSizeInFile(java.lang.Long)
	 */
	@Override
	public long getModelSizeInFile(String name){
		try{
			File F = new File(compressedHM.get(name));
			long result= 0;
			result+=F.length();
			return result;
		}
		catch(NullPointerException e){
			e.printStackTrace();
		}
		return 0;
	}






	/* (non-Javadoc)
	 * @see model.Model#ModelSolveing(java.lang.String, java.lang.String)
	 * solve the maze using BFS or ASTAR algorithm
	 */
	@Override
	public void ModelSolveing(String name, String algorithm) {

		ListenableFuture<Solution<Position>> futureSolution = null; //Initialize futureSolution.
		Maze3d current = HM.get(name); 

		//if(current != null){  //IF The Maze is already solved.
			if(solutionHM.get(name) != null){
				constantArgs[0] = Enums.MODEL_SOLVED;
				constantArgs[1] = name;
				System.out.println("You already solved this maze :/ ");
				setChanged();
				notifyObservers(constantArgs);
			}
		//}

		if(HM.containsKey(name)){
			switch(algorithm.toLowerCase()){
			case "bfs":
				searchMaze3DAdapter bfsMaze= new searchMaze3DAdapter(current);
				futureSolution=executor.submit(new Callable<Solution<Position>>() {

					@Override
					public Solution<Position> call() throws Exception {
						Searcher<Position> searcherBFS= new BFSalgorithm<Position>(); 
						Solution<Position> s = new Solution<Position>();
						s= searcherBFS.search(bfsMaze);
						if(s==null){
							System.out.println("The maze is unsolveable, please generate another maze.");
							return null;
						}
						else{
						System.out.println(s);
						return s;
						}
					}
				});
				break;

			case "astar":
				searchMaze3DAdapter astarMaze= new searchMaze3DAdapter(current);
				futureSolution=executor.submit(new Callable<Solution<Position>>() {
					@Override
					public Solution<Position> call() throws Exception {
						Searcher<Position> searcherAStar= new ASTARalgorithm<Position>(new ManhattanDistance());
						Solution<Position> s = new Solution<Position>();
						s= searcherAStar.search(astarMaze);
						System.out.println(s);
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

				@Override
				public void onFailure(Throwable sol) {
					setChanged();
					notifyObservers(Enums.MODEL_ERROR);
				}


				@Override
				public void onSuccess(Solution<Position> sol) {
					mazeToSolution.put(current,sol);
					solutionHM.put(name, sol);
					constantArgs[0] = Enums.MODEL_SOLVED;
					constantArgs[1] = name;
					setChanged();
					notifyObservers(constantArgs);
				}		
			});
		}
	}



	/* (non-Javadoc)
	 *this command asks for printing maze3d cross section for the given axis, index and name
	 * @see model.Model#CrossSectionBy(java.lang.String, int, java.lang.String)
	 */
	@Override
	public Searchable<Position> CrossSectionBy (String section, int index, String name){
		Maze2d maze2d;
		int [][] tempMaze2d = null;
		if(HM.containsKey(name)){
			Maze3d current = HM.get(name);
			switch (section.toLowerCase()) {
			case ("x"):
				tempMaze2d = current.getCrossSectionByX(index);
			break;
			
			case ("y"):
				tempMaze2d = current.getCrossSectionByY(index);
			break;
			
			case ("z"):
				tempMaze2d = current.getCrossSectionByZ(index);
			break;
			default:
				System.out.println("Wrong input. please enter valid section {X,Y,Z}");
			}
		}
		else 
			System.out.println("Wrong Input, maze not exists");
		maze2d = new Maze2d(tempMaze2d);
		searchMaze2DAdapter myMazeAdapter = new searchMaze2DAdapter(maze2d);
		return myMazeAdapter;
	}
	
	
	
	/* (non-Javadoc)
	 * @see model.Model#getSolution(java.lang.String)
	 */
	@Override
	public Solution<Position> getSolution(String name) {
		if(solutionHM.get(name) != null){
			return solutionHM.get(name);
		}
		return null;
	}
	
	
	
	/* (non-Javadoc)
	 * @get the Maze by his name.
	 */
	@Override
	public Searchable<Position> getNameToModel(String name) {
		Maze3d maze = HM.get(name); //Give an name and get the Maze.
		if(maze != null){
			searchMaze3DAdapter myMaze = new searchMaze3DAdapter(maze);
			return  myMaze;
		}

		return null;

	}
	
	
	
	/**
	 * Save solution.
	 *
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	/* (non-Javadoc)
	 * save the solution and mazes name to zip file.
	 */
	private void saveSolution() throws FileNotFoundException, IOException{
		try {
			FileOutputStream fos=new FileOutputStream(Enums.FILE_PATH);
			GZIPOutputStream gzos=new GZIPOutputStream(fos);
			ObjectOutputStream out=new ObjectOutputStream(gzos);
			out.writeObject(mazeToSolution);
			out.flush();
			out.close();
		}
		catch (IOException e) {
			e.getStackTrace();
		}

	}

	
	
	/**
	 * Load solution.
	 */
	/* (non-Javadoc)
	 * load the solution and mazes name from a zip file.
	 */
	private void loadSolution() {

		try {
			FileInputStream fos=new FileInputStream(Enums.FILE_PATH);
			GZIPInputStream gzos=new GZIPInputStream(fos);
			ObjectInputStream out=new ObjectInputStream(gzos);
			mazeToSolution = (HashMap<Maze3d, Solution<Position>>) out.readObject();
			out.close();
		}
		catch (  IOException e) {
			e.getStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	
	/* (non-Javadoc)
	 * @see model.Model#exit(java.lang.String)
	 */
	@Override
	public void exit(String string) {
		try{
			System.out.println(string);
			FileOutputStream fos = new FileOutputStream("NameAndSize.txt");//save the maze into file before exit
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(HashFileSize);
			oos.flush();
			oos.close();
			fos.close();
			saveSolution(); //save the solution on zip file. 
			saveProperties();
			executor.shutdownNow();
			constantArgs[0]= Enums.MODEL_EXIT;
			setChanged();
			notifyObservers(constantArgs);
			
			//System.gc();
			//System.exit(0);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}	
	}


	/**
	 * Save properties.
	 */
	public void saveProperties(){

		try {
			
			myXMLEncoder = new XMLEncoder(
					new BufferedOutputStream(
							new FileOutputStream(Enums.XML_FILE_PATH)));
			myXMLEncoder.flush();
			myXMLEncoder.writeObject(properties);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			myXMLEncoder.close();
		}
	}


}