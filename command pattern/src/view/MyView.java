package view;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;
import controller.Controller;
import controller.MyController;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * The Class MyView.
 */
public class MyView implements View {
	
	/** The cli. */
	protected CLI cli;
	
	/** The out. */
	private PrintWriter out;
	
	/** The hm. */
	private HashMap<String, Command> hm;
	
	/** The controller. */
	private Controller controller;
	
	/**
	 * Instantiates a new my view.
	 * constructor
	 * @param cli the cli
	 */
	public MyView(CLI cli) {
	this.cli = cli;
}

	
	
	/* (non-Javadoc)
	 * @see view.View#dir(java.lang.String)
	 * this command displays every files in the current path
	 */
		@Override
		public void dir(String string) {
			
			File folder = new File(string);
			File[] listOfFiles = folder.listFiles();
			if(folder.exists())
			{
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					System.out.println("File " + listOfFiles[i].getName());
				} else if (listOfFiles[i].isDirectory()) {
					System.out.println("Directory " + listOfFiles[i].getName());
				}
			}
			}
			else
				System.out.println("path not exists, try again.");
		}
	
	
		
	/* (non-Javadoc)
	 * @see view.View#displayMaze(Algorithm.mazeGeneraors.Maze3d)
	 */
	@Override
	public void displayMaze(Maze3d current) {

		int[][][] tmp = current.getMaze();
		for (int i = 0; i < current.getY(); i++) {
			if (i != 0)
				System.out.println();
			for (int j = 0; j < current.getX(); j++) {
				System.out.println();
				for (int g = 0; g < current.getZ(); g++) {
					System.out.print(tmp[j][i][g]);
				}
			}
		}
		System.out.println();

	}

	
	
	/* (non-Javadoc)
	 * @see view.View#mazeIsReady(java.lang.String)
	 * this command prints the given maze
	 */
	@Override
	public void mazeIsReady(String string) {
		System.out.println("maze "+string +" is ready");}

	
	
	/* (non-Javadoc)
	 * @see view.View#printCrossSection(int[][])
	 * print cross section 
	 */
	@Override
	public void printCrossSection(int[][] crossSection) {
		System.out.println("Displaying cross section: ");
		System.out.println();
		
		for (int i = 0; i < crossSection.length; i++) {
			for (int j = 0; j < crossSection[i].length; j++) {
				System.out.print(crossSection[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		
	}

	
	
	/* (non-Javadoc)
	 * @see view.View#printMazeSize(long)
	 * prints the size of the maze in memory
	 */
	@Override
	public void printMazeSize(long size) {
		System.out.println("The size of the maze in memory is: "+size);	}

	
	
	/* (non-Javadoc)
	 * @see view.View#solutionIsReady(java.lang.String)
	 * this command prints the given maze
	 */
	@Override
	public void solutionIsReady(String name) {
		System.out.println("Solution for "+name+" is ready!");}

	
	
	/* (non-Javadoc)
	 * @see view.View#printSolution(algorithms.search.Solution)
	 * this prints the solution
	 */
	@Override
	public void printSolution(Solution<Position> solution) {
		System.out.println(solution);}
	
	
	
	/* (non-Javadoc)
	 * @see view.View#setHashMap(java.util.HashMap)
	 * set the commands map in the view layer
	 */
	public void setHashMap(HashMap<String, Command> hashmap){
		this.hm=hashmap;}

	
	
	/* (non-Javadoc)
	 * @see view.View#start()
	 */
	public void start() throws IOException{
		cli.setHashMap(hm);
		cli.start();}

	
	
	/* (non-Javadoc)
	 * @see view.View#setController(controller.MyController)
	 */
	@Override
	public void setController(MyController myController) {
		// TODO Auto-generated method stub
		this.controller=myController;}

	
	
	/* (non-Javadoc)
	 * @see view.View#printMazeInFileSize(java.lang.String)
	 * prints the size of the maze in memory or File
	 */
	@Override
	public void printMazeInFileSize(String size) {
		System.out.println("The size of the maze in the file is: "+size);}

}
