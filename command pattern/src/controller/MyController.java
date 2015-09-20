package controller;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import view.View;
import model.Model;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MyController implements Controller{
	
	Model model;
	View view;
	HashMap<String, Command> commandsMap = new HashMap<>();
	//private PrintWriter out;
	
	//constructor
	public MyController(Model model, View view) 
	{
		
		this.model = model;
		this.view = view;
		//this.out=out;
		this.commandsMap.put("dir", new showFilesInPAth());
		this.commandsMap.put("generate", new generate3DMaze());
		this.commandsMap.put("display", new displayMaze());
		this.commandsMap.put("cross", new displayCross());
		this.commandsMap.put("save", new saveMaze());
		this.commandsMap.put("load", new loadMaze());
		this.commandsMap.put("maze", new mazeSize());
		this.commandsMap.put("file", new fileSize());
		this.commandsMap.put("solve", new solve());
		this.commandsMap.put("solution", new displaySolution());
		//this.commandsMap.put("exit", new exit());
		this.setCommandsMap(commandsMap);
	}

	//get commandsMap
	public HashMap<String, Command> getCommandsMap() {
		//view.viewCommandsMap(commandsMap);
		return commandsMap;
	}

	//set the commands map in the view layer(!) from the controller
	public void setCommandsMap(HashMap<String, Command> commandsMap) {
		view.setHashMap(commandsMap);
	}

	//this command displays every files in the current path
	public class showFilesInPAth implements Command {

		@Override
		public void doCommand(String[] args){
			view.dir(args[1]); //call the "dir" method
			}
	}
	
	//this command generates Maze3d, sending name and 3 dimensions 
	public class generate3DMaze implements Command {

		@Override
		public void doCommand(String[] args) {
			//call the generate method
			if(args.length==7 && (isNumeric(args[4])==true) && (isNumeric(args[5])==true) && (isNumeric(args[6])==true))
			model.generateMaze(args[3] , Integer.parseInt(args[4]),Integer.parseInt(args[5]),Integer.parseInt(args[6])); 
			else
				System.out.println("Wrong input, please enter command + <name> + 3 argument stand for maze dimensions");

		}

	}

	//this command prints the given maze 
	public class displayMaze implements Command {

		@Override
		public void doCommand(String[] args) {
			model.provideMaze(args[1]); //asks for specific maze by his name
		}

	}

	//notify the user the maze is ready after generates new Maze3D (view layer)
	@Override
	public void mazeIsReady(String name) {
		view.mazeIsReady(name);
		
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
			if(isNumeric(args[5]))
			model.crossSection(args[4], Integer.parseInt(args[5]), args[7]);
			else
				System.out.println("Wrong input. please enter command +  axis + index + name");
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
			model.saveCompressedMaze(args[2], args[3]);
			
		}
	}
	
	//this command load compressed maze by given file name and maze name
	public class loadMaze implements Command {

		@Override
		public void doCommand(String[] args) {
			model.loadCompressedMaze(args[1],args[2]);
			
		}
	}
	
	//this command ask for the maze size in memory
	public class mazeSize implements Command {

		@Override
		public void doCommand(String[] args) {
			model.mazeSize(args[2]); //ask the size from model layer
		}
		
	}
	
	//print the size of the maze in memory (view layer)
	@Override
	public void printMazeSize(long size) {
		view.printMazeSize(size);
		
	}
	
	//this command ask for the maze size in file
	public class fileSize implements Command {

		@Override
		public void doCommand(String[] args) {
			model.mazeSizeFile(args[2]);
		}
		
		
	}
	
	//this command solve given maze using the BFS or ASTAR algorithm
	public class solve implements Command{

		@Override
		public void doCommand(String[] args) {
			model.mazeSolveing(args[1],args[2]);
		}
		
	}

	//notify the the solution is ready after solving (view layer)
	@Override
	public void solutionIsReady(String name) {
		view.solutionIsReady(name);
		
	}
	
	//this command asks for the solution 
	public class displaySolution implements Command{

		@Override
		public void doCommand(String[] args) {
			model.displaySolution(args[2]);
		}
		
	}

	//this method gives us the solution and we send it to the view layer so we can print it
	@Override
	public void sendSolutioin(Solution<Position> solution) {
		view.printSolution(solution);
		
	}

	//set all the design pattern, must start the controller in all layers
	@Override
	public void start() throws IOException {
		this.view.setController(this);
		this.model.setController(this);
		this.view.start();
		
	}

	//this method is for the generate method
	//this is validation that the user send integer arguments 
	public boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

	//print maze size in file
	@Override
	public void printFileSize(String size) {
		view.printMazeInFileSize(size);
	}
	
	
}
