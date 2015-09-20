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

public class MyView implements View {
//CTOR (IN & OUT) לוקח אותם ויוצר CLI
	
	private Controller controller;
	protected CLI cli;
	private PrintWriter out;
	private HashMap<String, Command> hm;
	
	//constructor
	public MyView(CLI cli) {
		this.cli = cli;
}

	//this command displays every files in the current path
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
	
	//this command prints the given maze 
	@Override
	public void displayMaze(Maze3d current) {

		int[][][] tmp = current.getMaze3d();
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

	//this command prints the given maze 
	@Override
	public void mazeIsReady(String string) {
		System.out.println("maze "+string +" is ready");
		
	}

	//print cross section 
	@Override
	public void printCrossSection(int[][] crossSection) {
		System.out.println("Displaying cross section: ");
		for (int i = 0; i < crossSection.length; i++) {
			for (int j = 0; j < crossSection[i].length; j++) {
				System.out.print(crossSection[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		
	}

	//prints the size of the maze in memory
	@Override
	public void printMazeSize(long size) {
		System.out.println("The size of the maze in memory is: "+size);
		
	}
	
	//prints the size of the maze in memory or File
	@Override
	public void printMazeInFileSize(String size) {
		System.out.println("The size of the maze in the file is: "+size);
	}
	
	//this command prints the given maze 
	@Override
	public void solutionIsReady(String name) {
		System.out.println("Solution for "+name+" is ready!");
		
	}

	//this prints the solution
	@Override
	public void printSolution(Solution<Position> solution) {
		System.out.println(solution);
	}

	
	//set the commands map in the view layer
	public void setHashMap(HashMap<String, Command> hashmap){
		this.hm=hashmap;
	}

	public void start() throws IOException{
		cli.setHashMap(hm);
		cli.start();
	}

	@Override
	public void setController(MyController myController) {
		this.controller=myController;
	}

}
