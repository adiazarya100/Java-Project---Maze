package controller;



import view.View;
import model.Model;
import algorithms.mazeGenerators.Maze3d;

public class MyController implements Controller{
	
	Model model;
	View view;

	//this command displays every files in the current path
	public class showFilesInPAth implements Command {

		@Override
		public void doCommand(String[] args){
			view.dir(args[0]); //call the "dir" method
			}
	}
	
	//this command generates Maze3d, sending name and 3 dimensions 
	public class generate3DMaze implements Command {

		@Override
		public void doCommand(String[] args) {
			//call the generate method
			model.generateMaze(args[0] , Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3])); 

		}

	}

	//this command prints the given maze 
	public class displayMaze implements Command {

		@Override
		public void doCommand(String[] args) {
			model.provideMaze(args[0]); //asks for specific maze by his name
		}

	}

	//notify the user the maze is ready after generates new Maze3D (view layer)
	@Override
	public void mazeIsReady(String string) {
		view.mazeIsReady(string);
		
	}

	//this command prints the given maze  (view layer)
	@Override
	public void displayMaze(Maze3d current) {
		view.displayMaze(current);
		
	}
	
	//this command asks for printing maze3d cross section for the given axis, index and name
	public class displayCross implements Command {

		@Override
		public void doCommand(String[] args) {
			model.crossSection(args[0], Integer.parseInt(args[1]), args[2]);
		}
	}

	//this method will print the cross section (view layer)
	@Override
	public void displayCrossSection(int[][] crossSection) {
		view.printCrossSection(crossSection);
		
	}
	
	//this command gets name and file name and save the compressed maze
	public class saveMaze implements Command {

		@Override
		public void doCommand(String[] args) {
			model.saveCompressedMaze(args[0], args[1]);
			
		}
	}
	
	//this command load compressed maze by given file name and maze name
	public class loadMaze implements Command {

		@Override
		public void doCommand(String[] args) {
			model.loadCompressedMaze(args[0],args[1]);
			
		}
	}
	
	//this command ask for the maze size in memory
	public class mazeSize implements Command {

		@Override
		public void doCommand(String[] args) {
			model.mazeSize(args[0]); //ask the size from model layer
		}
		
	}
	
	//print the size of the maze (view layer)
	@Override
	public void printMazeSize(long size) {
		view.printMazeSize(size);
		
	}
	
	public class fileSize implements Command {

		@Override
		public void doCommand(String[] args) {
			
		}
		
		
	}
	
	public class solve implements Command{

		@Override
		public void doCommand(String[] args) {
			model.mazeSolveing(args[0],args[1]);
		}
		
	}
}
