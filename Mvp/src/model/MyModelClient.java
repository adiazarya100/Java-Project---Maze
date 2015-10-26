package model;

import generic.Enums;
import generic.ServerEnums;
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
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
import algorithms.search.Searchable;
import algorithms.search.Solution;

// TODO: Auto-generated Javadoc
/**
 * The Class MyModel.
 */
public class MyModelClient extends Observable implements Model{


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
	
	protected boolean flag;
	
	Solution<Position> mySolution = null;
	
	ArrayList<Object> serverList = new ArrayList<Object>();
	ObjectOutputStream serverOutput;
	ObjectInputStream serverInput;
	
	/**
	 * Instantiates a new my model.
	 * FIRST CTOR of MyModel()
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	public MyModelClient() throws IOException, ClassNotFoundException {
		HM = new HashMap<String, Maze3d>();
		compressedHM = new HashMap<String, String>();
		loadSolution(); //load all possible solutions.
		executor=MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5)); //Initialize the ThreadPool with 5 threads.
		HashFileSize = null;
		solutionHM= new HashMap<String, Solution<Position>> ();
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
		}try {
			File f = new File(Enums.FILE_PATH);
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

	/**
	 * Instantiates a new my model. get a Preferences object!
	 * SEC CTOR of MyModel()
	 *
	 * @param properties the properties
	 */


		public MyModelClient(presenter.Properties properties) {

			this.HM = new HashMap<String, Maze3d>();
			this.compressedHM = new HashMap<String, String>();
			this.solutionHM = new HashMap<String, Solution<Position>>();
			this.mazeToSolution = new HashMap<Maze3d, Solution<Position>>();
			this.myCompressor = null;
			this.myDecompressor = null;
			this.constantArgs = new String[2];
			//loadSolution();
			this.properties = properties;
			executor=MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5)); //cjange to properties.poolSize
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
			}try {
				//FileInputStream fis = new FileInputStream(Enums.FILE_PATH);
				File f = new File(Enums.FILE_PATH);
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
	public Maze3d loadModel(String fileName, String name) {
		
		Maze3d loaded=null;
		flag=true;
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
			
			loaded = new Maze3d(b);
			HM.put(name, loaded);
			solutionHM.put(name, mazeToSolution.get(loaded));
/*			System.out.println("blabla1");
			System.out.println(mazeToSolution.get(loaded));
			System.out.println("blabla2");
			System.out.println(solutionHM.get(name));*/
			compressedHM.put(name, fileName);
			constantArgs[0] = Enums.MODEL_LOADED; 
			setChanged();
			notifyObservers(constantArgs);
			
		}catch(FileNotFoundException e){
			setChanged();
			notifyObservers(Enums.FILE_NOT_FOUND);
		}	
		return loaded;
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
					
					if(properties.generator.name().equals("DFS")){
					Maze3d maze = new DFS().generate(x, y, z); //has changed to simple.
					//maze.print(); //just to print the maze to the log for testing.
					return maze;
					}
					else{
						Maze3d maze = new SimpleMaze3dGenerator().generate(x, y, z); //has changed to simple.
						//maze.print(); //just to print the maze to the log for testing.
						return maze;
					}
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
					notifyObservers(constantArgs); 
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
		Maze3d current = HM.get(name);
		
		
		String generator=null;
		switch(properties.getSolver())
		{
		case BFS:
			generator="BFS";
			break;
		case ManhattanASTAR:
			generator="ManhattanASTAR";
			break;
		case EuclidianASTAR:
			generator="EuclidianASTAR";
			break;
		default:
			return;
		}

		@SuppressWarnings("unchecked")
		Solution<Position> solution=(Solution<Position>)queryServer(properties.getServerIP(),properties.getServerPort(),ServerEnums.SOLVE_MAZE,current,generator);
		if(solution==null)
		{
			constantArgs[0] = ServerEnums.DISCONNECT;
			constantArgs[1] = name;
			setChanged();
			notifyObservers(constantArgs);
			return;
		}
		solutionHM.put(name, solution); 
		//solutionHM.keySet(name) = solution;
		
		System.out.println(solution);
		this.mySolution = solution;
		
		constantArgs[0] = Enums.MODEL_SOLVED;
		constantArgs[1] = name;
		setChanged();
		notifyObservers(constantArgs);
	}
	
	private Object queryServer(String serverIP,int serverPort,String command,Maze3d data,String generator)
	{
		System.out.println(serverIP);
		System.out.println(serverPort);
		Object result=null;
		Socket server;			
		try {
			System.out.println("Trying to connect server, IP: " + serverIP + " " + serverPort);
			server = new Socket(serverIP,serverPort);
			serverOutput = new ObjectOutputStream(server.getOutputStream());//writerToServer=new PrintWriter((new OutputStreamWriter(server.getOutputStream())));
			serverInput = new ObjectInputStream(server.getInputStream());
			
			//ObjectInputStream inputDecompressed;
			//inputDecompressed = new ObjectInputStream(server.getInputStream());
			serverList.add(data);
			serverList.add(generator);
			
			serverOutput.writeObject(serverList);
			//serverOutput.reset();
			serverOutput.flush();
			serverList.clear();
			
			result=serverInput.readObject();
			if(result.toString().contains("disconnect"))
			{
				setChanged();
				notifyObservers(ServerEnums.DISCONNECT);
			}
			
		
		//writerToServer.close();	
		/*	serverInput.close();
			server.close();
		*/} catch (ClassNotFoundException | IOException  e) {
			e.printStackTrace();

		}
		/*try {
			result = serverInput.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return result;

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
		if(flag==true){
			this.ModelSolveing(name, "");
			flag=false;
		}
		
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
			gzos.flush();
			out.close();
			fos.flush();
			
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
			mazeToSolution = (HashMap<Maze3d, Solution<Position>>)out.readObject();
			out.close();

		}
		catch (  IOException e) {
			e.getStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 

		//Object value = mazeToSolution.get(HM);
		System.out.println("test");
	}

	
	
	/* (non-Javadoc)
	 * @see model.Model#exit(java.lang.String)
	 */
	@Override
	public void exit() {
		try{
			
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