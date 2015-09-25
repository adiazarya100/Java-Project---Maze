package model;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

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
import controller.Controller;
import controller.MyController;
import algorithms.demo.searchMaze3DAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.ASTARalgorithm;
import algorithms.search.BFSalgorithm;
import algorithms.search.ManhattanDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;

/**
 * The Class MyModel.
 */
public class MyModel implements Model {

	  protected HashMap<String, Maze3d> HM = new HashMap<>(); // save every maze with his (name, maze3d)
	  
	  protected HashMap<String, String> compressedHM = new HashMap<>(); // save every compressed maze with his (name, fileName)
	  
	  protected HashMap<String, Solution<Position>> solutionHM = new HashMap<>(); //save every solution for specific maze
	  
	  protected HashMap<String, Integer> HashFileSize; // save every file and his size
	  Controller controller;
	 
	  
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


		/**
		 * Instantiates a new my model.
		 * SEC CTOR of MyModel()
		 * @param controller the controller
		 */
		public MyModel(Controller controller) {
			this.controller = controller;}



		/* (non-Javadoc)
		 * @see model.Model#provideMaze(java.lang.String)
		 * notify the user the maze is ready after generates new Maze3D
		 */
		@Override
		public void provideMaze(String mazeName) {
			if(HM.containsKey(mazeName)){
				Maze3d current = HM.get(mazeName);
				controller.displayMaze(current);
			}
			else
				System.out.println("Maze Not Found! Please Enter Another Name!");}



		// this command generates Maze3d in new Thread, sending name and 3
		/* (non-Javadoc)
		 * this command generates Maze3d, sending name and 3 dimensions 
		 * @see model.Model#generateMaze(java.lang.String, int, int, int)
		 */
		@Override
		public void generateMaze(String name, int x, int y, int z) {
			if(!HM.containsKey(name)){
				new Thread(new Runnable() {

					@Override
					public void run() { //OPEN Thread! 
						Maze3d maze = new MyMaze3dGenerator().generate(x, y, z);
						HM.put(name, maze);			
					}
				}).start();
				// notify the user the maze is ready
				// notify the controller layer
				controller.mazeIsReady(name);}
			else
				System.out.println("Worng Input Name is Already Used!");}



		/* (non-Javadoc)
		 *this command asks for printing maze3d cross section for the given axis, index and name
		 * @see model.Model#crossSection(java.lang.String, int, java.lang.String)
		 */
		@Override
		public void crossSection(String section, int index, String name) {
			if(HM.containsKey(name)){
				Maze3d current = HM.get(name);
				switch (section) {
				case ("X"):
					controller.displayCrossSection(current.getCrossSectionByX(index));
				break;
				case ("x"):
					controller.displayCrossSection(current.getCrossSectionByX(index));
				break;
				case ("Y"):
					controller.displayCrossSection(current.getCrossSectionByY(index));
				break;
				case ("y"):
					controller.displayCrossSection(current.getCrossSectionByY(index));
				break;
				case ("Z"):
					controller.displayCrossSection(current.getCrossSectionByZ(index));
				break;
				case ("z"):
					controller.displayCrossSection(current.getCrossSectionByZ(index));
				break;
				default:
					System.out.println("Wrong input. please enter valid section {X,Y,Z}");
				}
			}
			else 
				System.out.println("Wrong Input, maze not exists");
		}



		/* (non-Javadoc)
		 * @see model.Model#saveCompressedMaze(java.lang.String, java.lang.String)
		 * compressed the maze and save it
		 */
		@Override
		public void saveCompressedMaze(String name, String fileName) {
			FileOutputStream f;
			try {
				f = new FileOutputStream(fileName);
				OutputStream out = new MyCompressorOutputStream(f);
				compressedHM.put(name, fileName);
				try {
					out.write(HM.get(name).toByteArray());
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
				try {
					HashFileSize.put(fileName, (HM.get(name)).toByteArray().length);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



		/* (non-Javadoc)
		 * @see model.Model#loadCompressedMaze(java.lang.String, java.lang.String)
		 * load compressed maze
		 */
		@Override
		public void loadCompressedMaze(String fileName, String name){
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
			}catch(FileNotFoundException e){
			}
		}



		/* (non-Javadoc)
		 * @see model.Model#mazeSize(java.lang.String)
		 * get maze size in the memory
		 */
		@Override
		public void mazeSize(String name) {
			if(HM.containsKey(name)){
				try {
					long size = HM.get(name).toByteArray().length;
					controller.printMazeSize(size);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
				System.out.println("Wrong Input, maze not exists");}




		/* (non-Javadoc)
		 * @see model.Model#mazeSolveing(java.lang.String, java.lang.String)
		 * solve the maze using BFS or ASTAR algorithm
		 */
		@Override
		public void mazeSolveing(String name, String algorithm) {
			if(HM.containsKey(name) && (algorithm.equals("BFS")|| (algorithm.equals("ASTAR")))){
				Maze3d current = HM.get(name);
				switch(algorithm){
				case "BFS":
					new Thread(new Runnable() {

						@Override
						public void run() {
							Searcher<Position> searcherBFS= new BFSalgorithm<Position>(); 
							Solution<Position> s = new Solution<Position>();
							s= searcherBFS.search(new searchMaze3DAdapter(current));
							solutionHM.put(name, s);
						}
					}).start();
					controller.solutionIsReady(name);

					break;
				case "ASTAR":
					new Thread(new Runnable() {

						@Override
						public void run() {
							Searcher<Position> searcherAStarM= new ASTARalgorithm<Position>(new ManhattanDistance());	
							solutionHM.put(name, searcherAStarM.search(new searchMaze3DAdapter(current)));
						}
					}).start();
					controller.solutionIsReady(name);

					break;
				default:
					System.out.println("Wrong Input");
					break;
				}
			}
			else
				System.out.println("Wrong Input, maze or algorithm (BFS or ASTAR) not exists");}



		/* (non-Javadoc)
		 * @see model.Model#mazeSizeFile(java.lang.String)
		 */
		@Override
		public void mazeSizeFile(String name) {
			try{
				File F = new File(compressedHM.get(name));
				String result="";
				result+=F.length();
				/*if(F.exists()){*/
				controller.printFileSize(result);
			}
			/*System.out.println(F.length());*/
			catch(NullPointerException e){
				e.printStackTrace();
			}
		}



		/* (non-Javadoc)
		 * @see model.Model#displaySolution(java.lang.String)
		 */
		@Override
		public void displaySolution(String name) {
			controller.sendSolutioin(solutionHM.get(name));
		}



		/* (non-Javadoc)
		 * @see model.Model#setController(controller.MyController)
		 */
		@Override
		public void setController(MyController myController) {
			// TODO Auto-generated method stub
			this.controller = myController;}



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
				System.gc();
				System.exit(0);
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}		
		}


	}
