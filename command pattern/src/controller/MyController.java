package controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import view.View;
import model.Model;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * The Class MyController.
 */
public class MyController implements Controller{

	/** The model & The view. */
	Model model;	
	View view;

	/** The commands map.
	 *  Will include the command by String as a key will give the command as a value*/
	HashMap<String, Command> commandsMap = new HashMap<>();

	/**
	 * Instantiates a new my controller.
	 *
	 * @param model the model
	 * @param view the view
	 */
	//constructor
	public MyController(Model model, View view) 
	{

		this.model = model;
		this.view = view;
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
		this.commandsMap.put("exit", new exit());
		this.setCommandsMap(commandsMap);
	}

	/**
	 * Gets the commands map.
	 * Will return all the HashMap of String commands.
	 * @return the commands map
	 */
	public HashMap<String, Command> getCommandsMap() {
		return commandsMap;}


	/**
	 * Sets the commands map.
	 * set the commands map in the VIEW LAYER(!) from the controller
	 * @param commandsMap the commands map
	 */
	public void setCommandsMap(HashMap<String, Command> commandsMap) {
		view.setHashMap(commandsMap);}


	/**
	 * The Class showFilesInPAth.
	 * this command displays every files in the current path
	 */
	public class showFilesInPAth implements Command {


		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args){
			view.dir(args[1]); //call the "dir" method
		}
	}


	/**
	 * The Class generate3DMaze.
	 * this command generates Maze3d, sending name and 3 dimensions 
	 */
	public class generate3DMaze implements Command {

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			//call the generate method & IsNumeric check if the args are integers.
			if(args.length==7 && (isNumeric(args[4])==true) && (isNumeric(args[5])==true) && (isNumeric(args[6])==true))
				model.generateMaze(args[3] , Integer.parseInt(args[4]),Integer.parseInt(args[5]),Integer.parseInt(args[6])); 
			else
				System.out.println("Wrong input, please enter command + <name> + 3 argument stand for maze dimensions");

		}

	}


	/**
	 * The Class displayMaze.
	 * this command prints the given maze 
	 */
	public class displayMaze implements Command {

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			model.provideMaze(args[1]); //asks for specific maze by his name
		}

	}


	/* (non-Javadoc)
	 * @see controller.Controller#mazeIsReady(java.lang.String)
	 * notify the user the maze is ready after generates new Maze3D (VIEW LAYER(!))
	 */
	@Override
	public void mazeIsReady(String name) {
		view.mazeIsReady(name);}



	/* (non-Javadoc)
	 * @see controller.Controller#displayMaze(Algorithm.mazeGeneraors.Maze3d)
	 * this command prints the given maze  (view layer)
	 */
	@Override
	public void displayMaze(Maze3d current) {
		view.displayMaze(current);}



	/**
	 * The Class displayCross.
	 * this command asks for printing maze3d cross section for the given axis, index and name
	 */
	public class displayCross implements Command {

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			if(isNumeric(args[5]))//isNumeric check if Int.
				model.crossSection(args[4], Integer.parseInt(args[5]), args[7]);//parseInt change arg to Int
			else
				System.out.println("Wrong input. please enter command +  axis + index + name");
		}
	}


	/* (non-Javadoc)
	 * @see controller.Controller#displayCrossSection(int[][])
	 */
	//this method will print the cross section (VIEW LAYER)
	@Override
	public void displayCrossSection(int[][] crossSection) {
		view.printCrossSection(crossSection);}


	/**
	 * The Class saveMaze.
	 * this command gets name and file name and save the compressed maze
	 */
	public class saveMaze implements Command {
		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			model.saveCompressedMaze(args[2], args[3]);
		}
	}


	/**
	 * The Class loadMaze.
	 * this command load compressed maze by given file name and maze name
	 */
	public class loadMaze implements Command {
		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			model.loadCompressedMaze(args[2],args[3]);	
		}
	}


	/**
	 * The Class mazeSize.
	 * this command ask for the maze size in memory
	 */
	public class mazeSize implements Command {
		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			model.mazeSize(args[2]); //ask the size from model layer
		}	
	}


	/* (non-Javadoc)
	 * @see controller.Controller#printMazeSize(long)
	 */
	//print the size of the maze in memory (view layer)
	@Override
	public void printMazeSize(long size) {
		view.printMazeSize(size);}


	/**
	 * The Class fileSize.
	 * this command ask for the maze size in file
	 */
	public class fileSize implements Command {
		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			model.mazeSizeFile(args[2]);
		}	
	}


	/**
	 * The Class solve.
	 * this command solve given maze using the BFS or ASTAR algorithm
	 */
	public class solve implements Command{
		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			model.mazeSolveing(args[1],args[2]);
		}
	}


	/* (non-Javadoc)
	 * @see controller.Controller#solutionIsReady(java.lang.String)
	 * notify the the solution is ready after solving (view layer)
	 */
	@Override
	public void solutionIsReady(String name) {
		view.solutionIsReady(name);}


	/**
	 * The Class displaySolution.
	 * this command asks for the solution to be display. 
	 */
	public class displaySolution implements Command{
		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			model.displaySolution(args[2]);
		}
	}


	/* (non-Javadoc)
	 * @see controller.Controller#sendSolutioin(algorithms.search.Solution)
	 * this method gives us the solution and we send it to the view layer so we can print it
	 */
	@Override
	public void sendSolutioin(Solution<Position> solution) {
		view.printSolution(solution);}


	/* (non-Javadoc)
	 * @see controller.Controller#start()
	 * set all the design pattern, must start the controller in all layers
	 */
	@Override
	public void start() throws IOException {
		printCommands();
		this.view.setController(this);
		this.model.setController(this);
		this.view.start();	
	}

	/**
	 * Checks if is numeric.
	 * this is validation that the user send integer arguments 
	 * @param str the str
	 * @return true, if is numeric
	 */
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


	/* (non-Javadoc)
	 * @see controller.Controller#printFileSize(java.lang.String)
	 * print maze size in file
	 */

	@Override
	public void printFileSize(String size) {
		view.printMazeInFileSize(size);}


	/**
	 * The Class exit.
	 */
	public class exit implements Command{

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			if(args[0].equals("exit"))
				model.exit(args[0]);
			else
				System.out.println("wrong input");

		}

	}


	/**
	 * Prints all the commands.
	 */
	public void printCommands(){

		System.out.println("--------------welcome!--------------");
		System.out.println();
		System.out.println("please enter one of the following command without the < > symbol!!!");
		System.out.println();
		System.out.println("command 1: dir <path> ----------------------------------------- this command print all the files in the current folder");
		System.out.println("command 2: generate 3d maze <name> <x, y, z> ------------------ this command generates new 3d maze");
		System.out.println("command 3: display <name> ------------------------------------- this command display the maze");
		System.out.println("command 4: display cross section by <axis> <index> for <name> - this command display maze cross section  ");
		System.out.println("command 5: save maze <name> <file name> ----------------------- this command save the compressed maze in requested file ");
		System.out.println("command 6: load maze <file name> <name> ----------------------- this command load the requested maze");
		System.out.println("command 7: maze size <name> ----------------------------------- this command display the maze size in memory");
		System.out.println("command 8: file size <name> ----------------------------------- this command display the maze size in file");
		System.out.println("command 9: solve <name> <algorithm> --------------------------- this command solve the maze using BFS or ASTAR algorithm");
		System.out.println("command 10: display solution <name> --------------------------- this command display the solution nodes");
		System.out.println("command 11: exit ---------------------------------------------- this command exit the program without any open threads or files");
		System.out.println();
	}
}